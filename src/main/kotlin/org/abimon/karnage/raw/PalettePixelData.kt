package org.abimon.karnage.raw

import org.abimon.karnage.util.ColourByteOrder
import org.abimon.karnage.util.PaletteRGBA
import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream

object PalettePixelData : RawPixelData<PaletteRGBA> {
    override fun read(width: Int, height: Int, inputStream: InputStream, header: PaletteRGBA): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        inputStream.use { stream ->
            for (y in 0 until height)
                for (x in 0 until height)
                    img.setRGB(x, y, header[stream.read()])
        }

        return img
    }

    override fun write(img: BufferedImage, outputStream: OutputStream): PaletteRGBA {
        val palette = PaletteRGBA(ColourByteOrder.RGBA)
        var paletteIndice = 0

        for (y in 0 until img.height) {
            for (x in 0 until img.width) {
                val rgba = img.getRGB(x, y)
                if (rgba !in palette) {
                    palette[paletteIndice % 256] = rgba
                    paletteIndice++
                }

                outputStream.write(palette.indexOf(rgba))
            }
        }

        return palette
    }
}