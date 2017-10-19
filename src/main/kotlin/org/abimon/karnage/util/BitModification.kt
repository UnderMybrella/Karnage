package org.abimon.karnage.util

fun toInt16(lower: Byte, upper: Byte): Int = (lower.toInt() and 0xFF) or ((upper.toInt() and 0xFF) shl 8)

fun toIntN(nums: ByteArray): Long {
    var r: Long = 0L

    for(i in nums.indices)
        r = (r shl 8) or (nums[i].toLong() and 0xFF)

    return r
}