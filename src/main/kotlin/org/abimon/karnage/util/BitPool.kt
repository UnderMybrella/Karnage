package org.abimon.karnage.util

import java.io.InputStream

class BitPool(val input: InputStream) {

    var bitpool = 0
    var bitsLeft = 0

    val isEmpty: Boolean
        get() = input.available() == 0

    var count: Int = 0

    fun readByte(): Int = if (isEmpty) throw IllegalStateException("Bitpool is empty") else input.read()

    /** Note: Overrides the bitpool */
    fun putBits(byte: Int, numBits: Int) {
        bitpool = (byte shr (8 - numBits)) and ((1 shl numBits) - 1)
        count += numBits
        bitsLeft = 8 - numBits
    }

    fun read(numBits: Int): Int {
        var outBits = 0
        var bitsProduced = 0

        while (bitsProduced < numBits) {
            if (bitsLeft == 0) {
                bitpool = if (isEmpty) throw IllegalStateException("Bitpool is empty") else input.read()
                bitsLeft = 8
            }

            val bitsThisRound = minOf(bitsLeft, numBits - bitsProduced)

            outBits = outBits shl bitsThisRound
            outBits = outBits or ((bitpool shr (bitsLeft - bitsThisRound)) and ((1 shl bitsThisRound) - 1))

            bitsLeft -= bitsThisRound
            bitsProduced += bitsThisRound
        }

        count += numBits

        return outBits
    }

    operator fun get(bits: Int): Int = read(bits)

    fun flip(byte: Int): Int {
        var b = byte

        b = (b and 0xF0) shr 4 or (b and 0x0F shl 4)
        b = (b and 0xCC) shr 2 or (b and 0x33 shl 2)
        b = (b and 0xAA) shr 1 or (b and 0x55 shl 1)

        return b
    }
}