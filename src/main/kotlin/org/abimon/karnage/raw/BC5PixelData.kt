package org.abimon.karnage.raw

import org.abimon.karnage.util.BitModification
import java.awt.image.BufferedImage
import java.io.InputStream

object BC5PixelData: RawPixelDataNoHeader {
    override fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        inputStream.use { stream ->
            for (supposedIndex in 0 until (width * height / 16)) {
                val red0 = stream.read()
                val red1 = stream.read()

                val redData = stream.read().toLong() or
                        (stream.read().toLong() shl 8) or
                        (stream.read().toLong() shl 16) or
                        (stream.read().toLong() shl 24) or
                        (stream.read().toLong() shl 32) or
                        (stream.read().toLong() shl 40)

                val redIndices = IntArray(16) { index -> 7 and (redData shr (3 * index)).toInt() }

                val green0 = stream.read()
                val green1 = stream.read()

                val greenData = stream.read().toLong() or
                        (stream.read().toLong() shl 8) or
                        (stream.read().toLong() shl 16) or
                        (stream.read().toLong() shl 24) or
                        (stream.read().toLong() shl 32) or
                        (stream.read().toLong() shl 40)

                val greenIndices = IntArray(16) { index -> 7 and (greenData shr (3 * index)).toInt() }

                val red = IntArray(8)

                red[0] = red0
                red[1] = red1

                if (red0 > red1) {
                    red[2] = (6 * red0 + 1 * red1) / 7
                    red[3] = (5 * red0 + 2 * red1) / 7
                    red[4] = (4 * red0 + 3 * red1) / 7
                    red[5] = (3 * red0 + 4 * red1) / 7
                    red[6] = (2 * red0 + 5 * red1) / 7
                    red[7] = (1 * red0 + 6 * red1) / 7
                }
                else {
                    red[2] = (4 * red0 + 1 * red1) / 5
                    red[3] = (3 * red0 + 2 * red1) / 5
                    red[4] = (2 * red0 + 3 * red1) / 5
                    red[5] = (1 * red0 + 4 * red1) / 5

                    red[6] = 0
                    red[7] = 255
                }

                val green = IntArray(8)

                green[0] = green0
                green[1] = green1

                if (green0 > green1) {
                    green[2] = (6 * green0 + 1 * green1) / 7
                    green[3] = (5 * green0 + 2 * green1) / 7
                    green[4] = (4 * green0 + 3 * green1) / 7
                    green[5] = (3 * green0 + 4 * green1) / 7
                    green[6] = (2 * green0 + 5 * green1) / 7
                    green[7] = (1 * green0 + 6 * green1) / 7
                }
                else {
                    green[2] = (4 * green0 + 1 * green1) / 5
                    green[3] = (3 * green0 + 2 * green1) / 5
                    green[4] = (2 * green0 + 3 * green1) / 5
                    green[5] = (1 * green0 + 4 * green1) / 5

                    green[6] = 0
                    green[7] = 255
                }

                for (i in 0 until 16)
                    img.setRGB((supposedIndex % (width / 4)) * 4 + (i % 4), (supposedIndex / (width / 4)) * 4 + (i / 4), BitModification.toARGB(red[redIndices[i]], green[greenIndices[i]], 255, 255))
            }
        }

        return img
    }
}