package org.abimon.karnage.util

import java.io.InputStream

object BitModification {
    fun toInt16(lower: Byte, upper: Byte): Int = (lower.toInt() and 0xFF) or ((upper.toInt() and 0xFF) shl 8)
    fun toInt32(a: Byte, b: Byte, c: Byte, d: Byte): Int = (a.toInt() and 0xFF) or ((b.toInt() and 0xFF) shl 8) or ((c.toInt() and 0xFF) shl 16) or ((d.toInt() and 0xFF) shl 24)

    fun toInt16(lower: Int, upper: Int): Int = (lower and 0xFF) or ((upper and 0xFF) shl 8)
    fun toInt32(a: Int, b: Int, c: Int, d: Int): Int = (a and 0xFF) or ((b and 0xFF) shl 8) or ((c and 0xFF) shl 16) or ((d and 0xFF) shl 24)

    fun readInt(stream: InputStream): Int {
        val a = stream.read()
        val b = stream.read()
        val c = stream.read()
        val d = stream.read()

        return toInt32(d, c, b, a)
    }

    fun readIntLower(stream: InputStream): Int {
        val a = stream.read()
        val b = stream.read()
        val c = stream.read()
        val d = stream.read()

        return toInt32(a, b, c, d)
    }

    fun toIntN(nums: ByteArray): Long {
        var r: Long = 0L

        for (i in nums.indices)
            r = (r shl 8) or (nums[i].toLong() and 0xFF)

        return r
    }

    inline fun toARGB(r: Int, g: Int, b: Int, a: Int): Int
            = (a and 0xFF shl 24) or
            (r and 0xFF shl 16) or
            (g and 0xFF shl 8) or
            (b and 0xFF shl 0)
}