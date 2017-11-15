package org.abimon.karnage.util

enum class ColourByteOrder(val toARGB: (Int, Int, Int, Int) -> Int) {
    RGBA({ r, g, b, a -> BitModification.toARGB(r, g, b, a) }),
    BGRA({ b, g, r, a -> BitModification.toARGB(r, g, b, a) })
}