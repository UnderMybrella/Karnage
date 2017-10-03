package org.abimon.karnage.raw

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream

object DXT1PixelData {
    fun read(width: Int, height: Int, file: File): BufferedImage = read(width, height, file.inputStream())

    fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        inputStream.use { stream ->
            for (supposedIndex in 0 until ((height * width) / 16)) {
                val texel_palette = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.BLACK)
                val colourBytes = arrayOf((stream.read() or (stream.read() shl 8)), (stream.read() or (stream.read() shl 8)))
                val indices = stream.read() or (stream.read() shl 8) or (stream.read() shl 16) or (stream.read() shl 24)

                (0 until 2).forEach {
                    val rgb565 = colourBytes[it]

                    val r = (rgb565 and 0xF800) shr 8
                    val g = (rgb565 and 0x7E0) shr 3
                    val b = (rgb565 and 0x1F) shl 3

                    texel_palette[it] = Color(r or (r shr 5), g or (g shr 6), b or (b shr 5))
                }

                if (colourBytes[0] > colourBytes[1]) {
                    texel_palette[2] = Color(
                            (2 * texel_palette[0].red + 1 * texel_palette[1].red) / 3,
                            (2 * texel_palette[0].green + 1 * texel_palette[1].green) / 3,
                            (2 * texel_palette[0].blue + 1 * texel_palette[1].blue) / 3
                    )

                    texel_palette[3] = Color(
                            (1 * texel_palette[0].red + 2 * texel_palette[1].red) / 3,
                            (1 * texel_palette[0].green + 2 * texel_palette[1].green) / 3,
                            (1 * texel_palette[0].blue + 2 * texel_palette[1].blue) / 3
                    )
                } else {
                    texel_palette[2] = Color((texel_palette[0].red + texel_palette[1].red) / 2, (texel_palette[0].green + texel_palette[1].green) / 2, (texel_palette[0].blue + texel_palette[1].blue) / 2)
                    texel_palette[3] = Color(0, 0, 0, 0)
                }

                for (index in 0 until 16)
                    img.setRGB((supposedIndex % (width / 4)) * 4 + (index % 4), (supposedIndex / (width / 4)) * 4 + (index / 4), texel_palette[3 and (indices shr (2 * index))].rgb)
            }
        }

        return img
    }
}