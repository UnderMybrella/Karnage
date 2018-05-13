package org.abimon.karnage.raw

import java.io.InputStream

class BC7Block(val input: InputStream) {
    val currentBlock: ByteArray = ByteArray(16)
    var index: Int = 0
    var totalIndex: Int = 0

    fun readNextBlock(): ByteArray {
        //This is going to be (marginally?) slower than input.read(currentBlock), but prevents issues with buffering.
        var read = 0
        while (read > -1 && read < 16)
            read = input.read(currentBlock, read, 16 - read)

        index = 0
        return currentBlock
    }

    operator fun get(num: Int): Int {
        if(index + num > 128)
            readNextBlock()

        if(num == 1)
            return getBit()
        else
            return getBits(num)
    }

    fun getBit(): Int {
        val bitIndex = index shr 3
        val bit = ((currentBlock[bitIndex].toInt() and 0xFF) shr (index - (bitIndex shr 3))) and 0x01
        index++
        totalIndex++

        return bit
    }

    fun getBits(numBits: Int): Int {
        val bitIndex = index shr 3
        val base = index - (bitIndex shl 3)
        index += numBits
        totalIndex += numBits

        if(base + numBits > 8) {
            val firstIndexBits = 8 - base
            val nextIndexBits = numBits - firstIndexBits

            return ((currentBlock[bitIndex].toInt() and 0xFF) shr base) or ((((currentBlock[bitIndex + 1].toInt() and 0xFF) and ((1 shl nextIndexBits) - 1)) shl firstIndexBits))
        } else
            return ((currentBlock[bitIndex].toInt() and 0xFF) shr base) and ((1 shl numBits) - 1)
    }

    init {
        readNextBlock()
    }
}