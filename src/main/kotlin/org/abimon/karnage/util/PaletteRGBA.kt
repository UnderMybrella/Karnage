package org.abimon.karnage.util

import java.awt.Color

class PaletteRGBA(val colourByteOrder: ColourByteOrder) {
    private val palette = IntArray(256)
    operator fun get(index: Int): Int = palette[index]
    operator fun set(index: Int, color: Color) {
        palette[index] = color.rgb
    }
    operator fun set(index: Int, rgba: Int) {
        palette[index] = rgba
    }
    operator fun set(index: Int, a: Int, b: Int, c: Int, d: Int) {
        palette[index] = colourByteOrder.toARGB(a, b, c, d)
    }
    operator fun set(index: Int, colours: IntArray) {
        palette[index] = colourByteOrder.toARGB(colours[0], colours[1], colours[2], colours[3])
    }

    operator fun contains(rgba: Int): Boolean = rgba in palette
    fun indexOf(rgba: Int): Int = palette.indexOf(rgba)

    val rawPalette: IntArray
        get() = palette.copyOf()
}