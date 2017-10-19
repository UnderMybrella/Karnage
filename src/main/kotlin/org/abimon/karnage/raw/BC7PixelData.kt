package org.abimon.karnage.raw

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
            val block = BC7Block(stream)

            loop@ for (supposedIndex in 0 until ((height * width) / 16)) {
                val mode: BC7Mode
                var modeBit: Int = 0
                
                for(i in 0 until 8) {
                    if(block[1] == 1)
                        break
                    modeBit++
                }

                when (modeBit) {
                    0 -> {
                        val partition = block[4]
                        val red = (0 until 6).map { block[4] }
                        val green = (0 until 6).map { block[4] }
                        val blue = (0 until 6).map { block[4] }
                        val p = (0 until 6).map { block[1] }
                        val indices = (0 until 16).map { if (isAnchorIndex(partition, modeBit, it)) block[2] else block[3] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, p, indices, null, null, null)
                    }
                    1 -> {
                        val partition = block[6]
                        val red = (0 until 4).map { block[6] }
                        val green = (0 until 4).map { block[6] }
                        val blue = (0 until 4).map { block[6] }
                        val p = (0 until 2).map { block[1] }
                        val indices = (0 until 16).map { if (isAnchorIndex(partition, modeBit, it)) block[2] else block[3] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, p, indices, null, null, null)
                    }
                    2 -> {
                        val partition = block[6]
                        val red = (0 until 6).map { block[5] }
                        val green = (0 until 6).map { block[5] }
                        val blue = (0 until 6).map { block[5] }
                        val indices = (0 until 16).map { if (isAnchorIndex(partition, modeBit, it)) block[1] else block[2] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, null, indices, null, null, null)
                    }
                    3 -> {
                        val partition = block[6]
                        val red = (0 until 4).map { block[7] }
                        val green = (0 until 4).map { block[7] }
                        val blue = (0 until 4).map { block[7] }
                        val p = (0 until 4).map { block[1] }

                        val indices = (0 until 16).map { if (isAnchorIndex(partition, modeBit, it)) block[1] else block[2] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, null, p, indices, null, null, null)
                    }
                    4 -> {
                        val rotation = block[2]
                        val idxMode = block[1]

                        val red = (0 until 2).map { block[5] }
                        val green = (0 until 2).map { block[5] }
                        val blue = (0 until 2).map { block[5] }
                        val alpha = (0 until 2).map { block[6] }

                        val twoBitIndices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) block[1] else block[2] }
                        val threeBitIndices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) block[2] else block[3] }

                        mode = BC7Mode(modeBit, 0, red, green, blue, alpha, null, twoBitIndices, threeBitIndices, rotation, idxMode)
                    }
                    5 -> {
                        val rotation = block[2]

                        val red = (0 until 2).map { block[7] }
                        val green = (0 until 2).map { block[7] }
                        val blue = (0 until 2).map { block[7] }
                        val alpha = (0 until 2).map { block[8] }

                        val rgbIndices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) block[1] else block[2] }
                        val alphaIndices = (0 until 16).map { if(isAnchorIndex(0, modeBit, it)) block[1] else block[2] }

                        mode = BC7Mode(modeBit, 0, red, green, blue, alpha, null, rgbIndices, alphaIndices, rotation, null)
                    }
                    6 -> {
                        val red = (0 until 2).map { block[7] }
                        val green = (0 until 2).map { block[7] }
                        val blue = (0 until 2).map { block[7] }
                        val alpha = (0 until 2).map { block[7] }
                        val p = (0 until 2).map { block[1] }
                        val indices = (0 until 16).map { if (isAnchorIndex(0, modeBit, it)) block[3] else block[4] }

                        mode = BC7Mode(modeBit, 0, red, green, blue, alpha, p, indices, null, null, null)
                    }
                    7 -> {
                        val partition = block[6]
                        val red = (0 until 4).map { block[5] }
                        val green = (0 until 4).map { block[5] }
                        val blue = (0 until 4).map { block[5] }
                        val alpha = (0 until 4).map { block[5] }
                        val p = (0 until 4).map { block[1] }
                        val indices = (0 until 16).map { if (isAnchorIndex(partition, modeBit, it)) block[1] else block[2] }

                        mode = BC7Mode(modeBit, partition, red, green, blue, alpha, p, indices, null, null, null)
                    }
                    else -> {
                        System.err.println("Mode: $modeBit");
                        val buffer = block[127 - modeBit]
                        for(index in 0 until 16) {
                            val x = (supposedIndex % (width / 4)) * 4 + (index % 4)
                            val y = (supposedIndex / (width / 4)) * 4 + (index / 4)
                            img.setRGB(x, y, Color.BLACK.rgb)
                        }
                        continue@loop
                    }
                }

                val numberOfSubsets = NUMBER_OF_SUBSETS[modeBit]
                val endpoints = getEndpoints(mode)

                for(index in 0 until 16) {
                    val rgbPalette = mode.indices[index]
                    val alphaPalette = (mode.alphaIndices ?: mode.indices)[index]

                    val subset = getSubset(mode.partitions, index, numberOfSubsets)
                    val endpoint = endpoints[subset]
                    val interpolated = interpolate(endpoint, rgbPalette, alphaPalette, mode.selectionBit, mode.mode)

                    val x = (supposedIndex % (width / 4)) * 4 + (index % 4)
                    val y = (supposedIndex / (width / 4)) * 4 + (index / 4)

                    if(mode.rotation == null)
                        img.setRGB(x, y, interpolated.rgb)
                    else {
                        when(mode.rotation) {
                            1 -> img.setRGB(x, y, Color(interpolated.alpha, interpolated.green, interpolated.blue, interpolated.red).rgb)
                            2 -> img.setRGB(x, y, Color(interpolated.red, interpolated.alpha, interpolated.blue, interpolated.green).rgb)
                            3 -> img.setRGB(x, y, Color(interpolated.red, interpolated.green, interpolated.alpha, interpolated.blue).rgb)
                            else -> img.setRGB(x, y, interpolated.rgb)
                        }
                    }
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
        when {
            index == 0 -> return true
            NUMBER_OF_SUBSETS[mode] == 2 -> return ANCHOR_INDEX_SECOND_SUBSET[partitions] == index
            NUMBER_OF_SUBSETS[mode] == 3 -> return ANCHOR_INDEX_SECOND_SUBSET_OF_THREE[partitions] == index || ANCHOR_INDEX_THIRD_SUBSET[partitions] == index
            else -> return false
        }
    }

    fun extractEndpoints(mode: BC7Mode): Array<Pair<IntArray, IntArray>> {
        val numSubsets = NUMBER_OF_SUBSETS[mode.mode]
        return (0 until numSubsets).map { subset ->
            intArrayOf(
                    mode.red[subset * 2],
                    mode.green[subset * 2],
                    mode.blue[subset * 2],
                    mode.alpha?.get(subset * 2) ?: -1
            ) to intArrayOf(
                    mode.red[subset * 2 + 1],
                    mode.green[subset * 2 + 1],
                    mode.blue[subset * 2 + 1],
                    mode.alpha?.get(subset * 2 + 1) ?: -1
            )
        }.toTypedArray()
    }

    fun getEndpoints(mode: BC7Mode): Array<Pair<Color, Color>> {
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

        return endpoints.map { (a, b) -> Color(a[0], a[1], a[2], a[3]) to Color(b[0], b[1], b[2], b[3]) }.toTypedArray()
    }

    fun interpolate(endpoints: Pair<Color, Color>, index: Int, alphaIndex: Int, selectionBit: Int?, mode: Int): Color {
        if (selectionBit == 1) {
            val r = interpolate(endpoints.first.red, endpoints.second.red, alphaIndex, ALPHA_INDEX_BITCOUNT[mode])
            val g = interpolate(endpoints.first.green, endpoints.second.green, alphaIndex, ALPHA_INDEX_BITCOUNT[mode])
            val b = interpolate(endpoints.first.blue, endpoints.second.blue, alphaIndex, ALPHA_INDEX_BITCOUNT[mode])
            val a = interpolate(endpoints.first.alpha, endpoints.second.alpha, index, COLOUR_INDEX_BITCOUNT[mode])

            return Color(r, g, b, a)
        } else {
            val r = interpolate(endpoints.first.red, endpoints.second.red, index, COLOUR_INDEX_BITCOUNT[mode])
            val g = interpolate(endpoints.first.green, endpoints.second.green, index, COLOUR_INDEX_BITCOUNT[mode])
            val b = interpolate(endpoints.first.blue, endpoints.second.blue, index, COLOUR_INDEX_BITCOUNT[mode])
            val a = interpolate(endpoints.first.alpha, endpoints.second.alpha, alphaIndex, ALPHA_INDEX_BITCOUNT[mode])

            return Color(r, g, b, a)
        }
    }
    //= Color(endpoints.first[0], endpoints.first[1], endpoints.first[2], endpoints.first[3])

    fun interpolate(e0: Int, e1: Int, index: Int, indexPrecision: Int): Int {
        when (indexPrecision) {
            2 -> return (((64 - A_WEIGHT_2[index]) * e0 + A_WEIGHT_2[index] * e1 + 32) shr 6)
            3 -> return (((64 - A_WEIGHT_3[index]) * e0 + A_WEIGHT_3[index] * e1 + 32) shr 6)
            else -> return (((64 - A_WEIGHT_4[index]) * e0 + A_WEIGHT_4[index] * e1 + 32) shr 6)
        }
    }
}