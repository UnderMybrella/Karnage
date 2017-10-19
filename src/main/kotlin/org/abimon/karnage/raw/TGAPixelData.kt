package org.abimon.karnage.raw

import java.awt.image.BufferedImage
import java.io.InputStream

object TGAPixelData: RawPixelData<TGAHeader> {
    private val COLORMAP = 1
    private val RGB = 2
    private val GRAYSCALE = 3
    private val COLORMAP_RLE = 9
    private val RGB_RLE = 10
    private val GRAYSCALE_RLE = 11

    override fun read(width: Int, height: Int, inputStream: InputStream, header: TGAHeader): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        when(header.imageType) {
            RGB_RLE -> {
                if(header.pixelDepth != 32)
                    println(header.pixelDepth)

                var i: Int = 0
                do {
                    val packet = inputStream.read()

                    if(packet and 0x80 != 0) {
                        val count = (packet and 0x7F) + 1

                        val b = inputStream.read()
                        val g = inputStream.read()
                        val r = inputStream.read()
                        val a = inputStream.read()

                        for(j in 0 until count) {
                            img.setRGB(i % width, i / width, (r shl 16) or (g shl 8) or (b) or (a shl 24))
                            i++
                        }

                    } else {
                        val count = (packet and 0x7F) + 1

                        for(j in 0 until count) {
                            val b = inputStream.read()
                            val g = inputStream.read()
                            val r = inputStream.read()
                            val a = inputStream.read()

                            img.setRGB(i % width, i / width, (r shl 16) or (g shl 8) or (b) or (a shl 24))
                            i++
                        }
                    }
                } while(i < width * height)
            }
        }

        return img
    }
}