package org.abimon.karnage.raw

import org.abimon.karnage.util.BitPool
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream

object BC7PixelData {
    val NUMBER_OF_SUBSETS = arrayOf(3, 2, 3, 2, 1, 1, 1, 2)
    val UNIQUE_PBITS = arrayOf(true, false, false, true, false, false, true, true)

    val COLOUR_PRECISION_PLUS_PBIT = arrayOf(5, 7, 5, 8, 5, 7, 8, 6)
    val ALPHA_PRECISION_PLUS_PBIT = arrayOf(0, 0, 0, 0, 6, 8, 8, 6)

    val COLOUR_INDEX_BITCOUNT = arrayOf(3, 3, 2, 2, 2, 2, 4, 2)
    val ALPHA_INDEX_BITCOUNT = arrayOf(3, 3, 2, 2, 3, 2, 4, 2)

    val PARTITION_TABLE_P2 = arrayOf(
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1,
            0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1,
            0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1,
            0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1,
            0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1,
            0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1,
            0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0,
            0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0,
            0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1,
            0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0,
            0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
            0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0,
            0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0,
            0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0,
            0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
            0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1,
            0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0,
            0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0,
            0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0,
            0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1,
            0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0,
            0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0,
            0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
            0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1,
            0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1,
            0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0,
            0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0,
            0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0,
            0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1,
            0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1,
            0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1,
            0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0,
            0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1
    )

    val PARTITION_TABLE_P3 = arrayOf(
            0, 0, 1, 1, 0, 0, 1, 1, 0, 2, 2, 1, 2, 2, 2, 2,
            0, 0, 0, 1, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 2, 1,
            0, 0, 0, 0, 2, 0, 0, 1, 2, 2, 1, 1, 2, 2, 1, 1,
            0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 1, 1, 0, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2,
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 2, 2, 0, 0, 2, 2,
            0, 0, 2, 2, 0, 0, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 1, 1, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2,
            0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2,
            0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2,
            0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2,
            0, 1, 1, 2, 0, 1, 1, 2, 0, 1, 1, 2, 0, 1, 1, 2,
            0, 1, 2, 2, 0, 1, 2, 2, 0, 1, 2, 2, 0, 1, 2, 2,
            0, 0, 1, 1, 0, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2,
            0, 0, 1, 1, 2, 0, 0, 1, 2, 2, 0, 0, 2, 2, 2, 0,
            0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 2, 1, 1, 2, 2,
            0, 1, 1, 1, 0, 0, 1, 1, 2, 0, 0, 1, 2, 2, 0, 0,
            0, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2,
            0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 2, 2, 1, 1, 1, 1,
            0, 1, 1, 1, 0, 1, 1, 1, 0, 2, 2, 2, 0, 2, 2, 2,
            0, 0, 0, 1, 0, 0, 0, 1, 2, 2, 2, 1, 2, 2, 2, 1,
            0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 2, 2, 0, 1, 2, 2,
            0, 0, 0, 0, 1, 1, 0, 0, 2, 2, 1, 0, 2, 2, 1, 0,
            0, 1, 2, 2, 0, 1, 2, 2, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 1, 2, 0, 0, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2,
            0, 1, 1, 0, 1, 2, 2, 1, 1, 2, 2, 1, 0, 1, 1, 0,
            0, 0, 0, 0, 0, 1, 1, 0, 1, 2, 2, 1, 1, 2, 2, 1,
            0, 0, 2, 2, 1, 1, 0, 2, 1, 1, 0, 2, 0, 0, 2, 2,
            0, 1, 1, 0, 0, 1, 1, 0, 2, 0, 0, 2, 2, 2, 2, 2,
            0, 0, 1, 1, 0, 1, 2, 2, 0, 1, 2, 2, 0, 0, 1, 1,
            0, 0, 0, 0, 2, 0, 0, 0, 2, 2, 1, 1, 2, 2, 2, 1,
            0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 2, 2, 1, 2, 2, 2,
            0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 0, 1, 2, 0, 0, 2, 2, 0, 2, 2, 2,
            0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0,
            0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 0, 0, 0, 0,
            0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0,
            0, 1, 2, 0, 2, 0, 1, 2, 1, 2, 0, 1, 0, 1, 2, 0,
            0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 2, 2, 0, 0, 1, 1,
            0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 0, 0, 0, 0, 1, 1,
            0, 1, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2,
            0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 1, 2, 1, 2, 1,
            0, 0, 2, 2, 1, 1, 2, 2, 0, 0, 2, 2, 1, 1, 2, 2,
            0, 0, 2, 2, 0, 0, 1, 1, 0, 0, 2, 2, 0, 0, 1, 1,
            0, 2, 2, 0, 1, 2, 2, 1, 0, 2, 2, 0, 1, 2, 2, 1,
            0, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 0, 1,
            0, 0, 0, 0, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1,
            0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2,
            0, 2, 2, 2, 0, 1, 1, 1, 0, 2, 2, 2, 0, 1, 1, 1,
            0, 0, 0, 2, 1, 1, 1, 2, 0, 0, 0, 2, 1, 1, 1, 2,
            0, 0, 0, 0, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2,
            0, 2, 2, 2, 0, 1, 1, 1, 0, 1, 1, 1, 0, 2, 2, 2,
            0, 0, 0, 2, 1, 1, 1, 2, 1, 1, 1, 2, 0, 0, 0, 2,
            0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 2, 2, 2, 2,
            0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 2, 2, 1, 1, 2,
            0, 1, 1, 0, 0, 1, 1, 0, 2, 2, 2, 2, 2, 2, 2, 2,
            0, 0, 2, 2, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 2, 2,
            0, 0, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 0, 0, 2, 2,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 2,
            0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1,
            0, 2, 2, 2, 1, 2, 2, 2, 0, 2, 2, 2, 1, 2, 2, 2,
            0, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            0, 1, 1, 1, 2, 0, 1, 1, 2, 2, 0, 1, 2, 2, 2, 0
    )

    val ANCHOR_INDEX_SECOND_SUBSET = arrayOf(
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 2, 8, 2, 2, 8, 8, 15,
            2, 8, 2, 2, 8, 8, 2, 2,
            15, 15, 6, 8, 2, 8, 15, 15,
            2, 8, 2, 2, 2, 15, 15, 6,
            6, 2, 6, 8, 15, 15, 2, 2,
            15, 15, 15, 15, 15, 2, 2, 15
    )

    val ANCHOR_INDEX_SECOND_SUBSET_OF_THREE = arrayOf(
            3, 3, 15, 15, 8, 3, 15, 15,
            8, 8, 6, 6, 6, 5, 3, 3,
            3, 3, 8, 15, 3, 3, 6, 10,
            5, 8, 8, 6, 8, 5, 15, 15,
            8, 15, 3, 5, 6, 10, 8, 15,
            15, 3, 15, 5, 15, 15, 15, 15,
            3, 15, 5, 5, 5, 8, 5, 10,
            5, 10, 8, 13, 15, 12, 3, 3
    )

    val ANCHOR_INDEX_THIRD_SUBSET = arrayOf(
            15, 8, 8, 3, 15, 15, 3, 8,
            15, 15, 15, 15, 15, 15, 15, 8,
            15, 8, 15, 3, 15, 8, 15, 8,
            3, 15, 6, 10, 15, 15, 10, 8,
            15, 3, 15, 10, 10, 8, 9, 10,
            6, 15, 8, 15, 3, 6, 6, 8,
            15, 3, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 3, 15, 15, 8
    )

    val A_WEIGHT_2 = arrayOf(0, 21, 43, 64)
    val A_WEIGHT_3 = arrayOf(0, 9, 18, 27, 37, 46, 55, 64)
    val A_WEIGHT_4 = arrayOf(0, 4, 9, 13, 17, 21, 26, 30, 34, 38, 43, 47, 51, 55, 60, 64)

    fun read(width: Int, height: Int, file: File): BufferedImage = read(width, height, file.inputStream())

    fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        inputStream.use { stream ->
            val bits = BitPool(stream)

            loop@ for (supposedIndex in 0 until ((height * width) / 16)) {
                var modeBit: Int = 0
                val starting = bits.count
                for (i in 0 until 8) {
                    if (bits[1] == 1)
                        break
                    modeBit++
                }

                val mode: BC7Mode

                when (modeBit) {
                    0 -> {
                        val partition = bits[4]
                        val red = (0 until 6).map { bits[4] }
                        val green = (0 until 6).map { bits[4] }
                        val blue = (0 until 6).map { bits[4] }
                        val p = (0 until 6).map { bits[1] }
                        val indices = (0 until 16).map { if(isAnchorIndex(partition, modeBit, it)) bits[2] else bits[3] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, p, indices)
                    }
                    1 -> {
                        val partition = bits[6]
                        val red = (0 until 4).map { bits[6] }
                        val green = (0 until 4).map { bits[6] }
                        val blue = (0 until 4).map { bits[6] }
                        val p = (0 until 2).map { bits[1] }
                        val indices = (0 until 16).map { if(isAnchorIndex(partition, modeBit, it)) bits[2] else bits[3] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, p, indices)
                    }
                    2 -> {
                        val partition = bits[6]
                        val red = (0 until 6).map { bits[5] }
                        val green = (0 until 6).map { bits[5] }
                        val blue = (0 until 6).map { bits[5] }
                        val indices = (0 until 16).map { if(isAnchorIndex(partition, modeBit, it)) bits[1] else bits[2] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, null, indices)
                    }
                    3 -> {
                        val partition = bits[6]
                        val red = (0 until 4).map { bits[7] }
                        val green = (0 until 4).map { bits[7] }
                        val blue = (0 until 4).map { bits[7] }
                        val p = (0 until 4).map { bits[1] }

                        val indices = (0 until 16).map { if(isAnchorIndex(partition, modeBit, it)) bits[1] else bits[2] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, p, indices)
                    }
//                    4 -> {
//                        val rotation = bits[2]
//                        val idxMode = bits[1]
//
//                        val red = (0 until 2).map { bits[5] }
//                        val green = (0 until 2).map { bits[5] }
//                        val blue = (0 until 2).map { bits[5] }
//                        val alpha = (0 until 2).map { bits[6] }
//
//                        val twoBitIndices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) bits[1] else bits[2] }
//                        val threeBitIndices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) bits[2] else bits[3] }
//
//                        mode = BC7Mode(modeBit, 0, red, green, blue, alpha, null, if(idxMode == 0) twoBitIndices else threeBitIndices)
//                    }
                    6 -> {
                        val red = (0 until 2).map { bits[7] }
                        val green = (0 until 2).map { bits[7] }
                        val blue = (0 until 2).map { bits[7] }
                        val alpha = (0 until 2).map { bits[7] }
                        val p = (0 until 2).map { bits[1] }
                        val indices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) bits[3] else bits[4] }

                        mode = BC7Mode(modeBit, 0, red, green, blue, alpha, p, indices)
                    }
                    7 -> {
                        val partition = bits[6]
                        val red = (0 until 4).map { bits[5] }
                        val green = (0 until 4).map { bits[5] }
                        val blue = (0 until 4).map { bits[5] }
                        val alpha = (0 until 4).map { bits[5] }
                        val p = (0 until 4).map { bits[1] }
                        val indices = (0 until 16).map { if(isAnchorIndex(partition, modeBit, it)) bits[1] else bits[2] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, alpha, p, indices)
                    }
                    else -> {
                        println("Mode: $modeBit"); bits[127 - modeBit]; continue@loop
                    }
                }

                if((bits.count - starting) > 128)
                    println(":thonk: Mode $mode read ${(bits.count - starting)} bits")

                val numberOfSubsets = NUMBER_OF_SUBSETS[modeBit]
                val endpoints = getEndpoints(mode)

                val subsets = (0 until 16).map { i -> getSubset(mode.partitions, i, numberOfSubsets) }

                mode.indices.forEachIndexed { index, palette ->
                    val endpoint = endpoints[subsets[index]]
                    img.setRGB((supposedIndex % (width / 4)) * 4 + (index % 4), (supposedIndex / (width / 4)) * 4 + (index / 4), interpolate(endpoint, palette, mode.mode).rgb)
                }
            }
        }

        return img
    }

    fun getSubset(partitions: Int, index: Int, numberOfSubsets: Int): Int {
        when (numberOfSubsets) {
            1 -> return 0
            2 -> return PARTITION_TABLE_P2[partitions * 16 + index]
            else -> return PARTITION_TABLE_P3[partitions * 16 + index]
        }
    }

    fun isAnchorIndex(partitions: Int, mode: Int, index: Int): Boolean {
        val i = getSubset(partitions, index, NUMBER_OF_SUBSETS[mode])
        when {
            i == 0 -> return index == 0
            NUMBER_OF_SUBSETS[mode] == 2 -> return ANCHOR_INDEX_SECOND_SUBSET[partitions] == index
            i == 1 -> return ANCHOR_INDEX_SECOND_SUBSET_OF_THREE[partitions] == index
            else -> return ANCHOR_INDEX_THIRD_SUBSET[partitions] == index
        }
    }

    fun extractEndpoints(mode: BC7Mode): Array<Pair<IntArray, IntArray>> =
            (0 until NUMBER_OF_SUBSETS[mode.mode]).map {
                intArrayOf(mode.red[it * 2], mode.green[it * 2], mode.blue[it * 2], mode.alpha?.get(it * 2) ?: -1) to
                        intArrayOf(mode.red[it * 2 + 1], mode.green[it * 2 + 1], mode.blue[it * 2 + 1], mode.alpha?.get(it * 2 + 1) ?: -1)
            }.toTypedArray()

    fun getEndpoints(mode: BC7Mode): Array<Pair<IntArray, IntArray>> {
        val endpoints = extractEndpoints(mode)

        val colourPrecision = COLOUR_PRECISION_PLUS_PBIT[mode.mode]
        val alphaPrecision = ALPHA_PRECISION_PLUS_PBIT[mode.mode]

        for (i in endpoints.indices) {
            if (mode.pBits != null) {
                if (UNIQUE_PBITS[mode.mode]) {
                    endpoints[i].first.apply {
                        for (j in indices) {
                            this[j] = this[j] shl 1
                            this[j] = this[j] or mode.pBits[i * 2]
                        }
                    }
                    endpoints[i].second.apply {
                        for (j in indices) {
                            this[j] = this[j] shl 1
                            this[j] = this[j] or mode.pBits[i * 2 + 1]
                        }
                    }
                } else {
                    endpoints[i].first.apply {
                        for (j in indices) {
                            this[j] = this[j] shl 1
                            this[j] = this[j] or mode.pBits[i]
                        }
                    }
                    endpoints[i].second.apply {
                        for (j in indices) {
                            this[j] = this[j] shl 1
                            this[j] = this[j] or mode.pBits[i]
                        }
                    }
                }
            }

            endpoints[i].first.apply {
                this[0] = this[0] shl (8 - colourPrecision)
                this[1] = this[1] shl (8 - colourPrecision)
                this[2] = this[2] shl (8 - colourPrecision)
                this[3] = this[3] shl (8 - alphaPrecision)

                this[0] = this[0] or (this[0] shr colourPrecision)
                this[1] = this[1] or (this[1] shr colourPrecision)
                this[2] = this[2] or (this[2] shr colourPrecision)
                this[3] = this[3] or (this[3] shr alphaPrecision)
            }

            endpoints[i].second.apply {
                this[0] = this[0] shl (8 - colourPrecision)
                this[1] = this[1] shl (8 - colourPrecision)
                this[2] = this[2] shl (8 - colourPrecision)
                this[3] = this[3] shl (8 - alphaPrecision)

                this[0] = this[0] or (this[0] shr colourPrecision)
                this[1] = this[1] or (this[1] shr colourPrecision)
                this[2] = this[2] or (this[2] shr colourPrecision)
                this[3] = this[3] or (this[3] shr alphaPrecision)
            }
        }

        if (mode.mode <= 3)
            endpoints.forEachIndexed { index, pair ->
                endpoints[index] = pair.apply {
                    first[3] = 0xFF
                    second[3] = 0xFF
                }
            }

        return endpoints
    }

    fun interpolate(endpoints: Pair<IntArray, IntArray>, index: Int, mode: Int): Color
//        = Color(
//            interpolate(endpoints.first[0], endpoints.second[0], index, COLOUR_INDEX_BITCOUNT[mode]),
//            interpolate(endpoints.first[1], endpoints.second[1], index, COLOUR_INDEX_BITCOUNT[mode]),
//            interpolate(endpoints.first[2], endpoints.second[2], index, COLOUR_INDEX_BITCOUNT[mode]),
//            interpolate(endpoints.first[3], endpoints.second[3], index, ALPHA_INDEX_BITCOUNT[mode])
//    )
    = Color(endpoints.first[0], endpoints.first[1], endpoints.first[2], endpoints.first[3])

    fun interpolate(e0: Int, e1: Int, index: Int, indexPrecision: Int): Int {
        when(indexPrecision) {
            2 -> return (((64 - A_WEIGHT_2[index]) * e0 + A_WEIGHT_2[index] * e1 + 32) shr 6)
            3 -> return (((64 - A_WEIGHT_3[index]) * e0 + A_WEIGHT_3[index] * e1 + 32) shr 6)
            else -> return (((64 - A_WEIGHT_4[index]) * e0 + A_WEIGHT_4[index] * e1 + 32) shr 6)
        }
    }
}