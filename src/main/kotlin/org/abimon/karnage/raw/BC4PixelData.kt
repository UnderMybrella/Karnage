package org.abimon.karnage.raw

import org.abimon.karnage.util.BitModification
import java.awt.image.BufferedImage
import java.io.InputStream

object BC4PixelData : RawPixelDataNoHeader {
    override fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        inputStream.use { stream ->
            for (supposedIndex in 0 until (width * height / 16)) {
                val r0 = stream.read()
                val r1 = stream.read()

                val data1 = stream.read() or (stream.read() shl 8) or (stream.read() shl 16)
                val data2 = stream.read() or (stream.read() shl 8) or (stream.read() shl 16)

                val indices = IntArray(16) { index -> 7 and ((if(index < 8) data1 else data2) shr (3 * (index % 8))) }

                val colours = IntArray(8)

                colours[0] = r0
                colours[1] = r1

                if (r0 > r1) {
                    colours[2] = (6 * r0 + 1 * r1) / 7
                    colours[3] = (5 * r0 + 2 * r1) / 7
                    colours[4] = (4 * r0 + 3 * r1) / 7
                    colours[5] = (3 * r0 + 4 * r1) / 7
                    colours[6] = (2 * r0 + 5 * r1) / 7
                    colours[7] = (1 * r0 + 6 * r1) / 7
                }
                else {
                    colours[2] = (4 * r0 + 1 * r1) / 5
                    colours[3] = (3 * r0 + 2 * r1) / 5
                    colours[4] = (2 * r0 + 3 * r1) / 5
                    colours[5] = (1 * r0 + 4 * r1) / 5
                    
                    colours[6] = 0
                    colours[7] = 255
                }

                indices.forEachIndexed { pos, index ->
                    img.setRGB((supposedIndex % (width / 4)) * 4 + (pos % 4), (supposedIndex / (width / 4)) * 4 + (pos / 4), BitModification.toARGB(colours[index], colours[index], colours[index], colours[index])) //I think this is right? Not sure honestly
                }
            }
        }
        return img
    }
}