package org.abimon.karnage.raw

import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream

interface RawPixelDataNoHeader: RawPixelData<Unit> {
    fun read(width: Int, height: Int, file: File): BufferedImage = read(width, height, file, Unit)
    override fun read(width: Int, height: Int, inputStream: InputStream, header: Unit): BufferedImage = read(width, height, inputStream)

    fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage
}