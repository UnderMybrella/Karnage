package org.abimon.karnage.raw

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.InputStream

object DXT1PixelData: RawPixelDataNoHeader {
    override fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        inputStream.use { stream ->
            for (supposedIndex in 0 until ((height * width) / 16)) {
                val texelPalette = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.BLACK)
                val colourBytes = arrayOf((stream.read() or (stream.read() shl 8)), (stream.read() or (stream.read() shl 8)))
                val indices = stream.read() or (stream.read() shl 8) or (stream.read() shl 16) or (stream.read() shl 24)

                (0 until 2).forEach {
                    val rgb565 = colourBytes[it]

                    val r = (rgb565 and 0xF800) shr 8
                    val g = (rgb565 and 0x7E0) shr 3
                    val b = (rgb565 and 0x1F) shl 3

                    texelPalette[it] = Color(r or (r shr 5), g or (g shr 6), b or (b shr 5))
                }

                if (colourBytes[0] > colourBytes[1]) {
                    texelPalette[2] = Color(
                            (2 * texelPalette[0].red + 1 * texelPalette[1].red) / 3,
                            (2 * texelPalette[0].green + 1 * texelPalette[1].green) / 3,
                            (2 * texelPalette[0].blue + 1 * texelPalette[1].blue) / 3
                    )

                    texelPalette[3] = Color(
                            (1 * texelPalette[0].red + 2 * texelPalette[1].red) / 3,
                            (1 * texelPalette[0].green + 2 * texelPalette[1].green) / 3,
                            (1 * texelPalette[0].blue + 2 * texelPalette[1].blue) / 3
                    )
                } else {
                    texelPalette[2] = Color((texelPalette[0].red + texelPalette[1].red) / 2, (texelPalette[0].green + texelPalette[1].green) / 2, (texelPalette[0].blue + texelPalette[1].blue) / 2)
                    texelPalette[3] = Color(0, 0, 0, 0)
                }

                for (index in 0 until 16)
                    img.setRGB((supposedIndex % (width / 4)) * 4 + (index % 4), (supposedIndex / (width / 4)) * 4 + (index / 4), texelPalette[3 and (indices shr (2 * index))].rgb)
            }
        }

        return img
    }
}