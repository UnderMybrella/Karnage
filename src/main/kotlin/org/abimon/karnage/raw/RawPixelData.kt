package org.abimon.karnage.raw

import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import java.io.OutputStream

interface RawPixelData<T: Any> {
    fun read(width: Int, height: Int, file: File, header: T): BufferedImage = read(width, height, file.inputStream(), header)
    fun read(width: Int, height: Int, inputStream: InputStream, header: T): BufferedImage

    fun write(img: BufferedImage, outputStream: OutputStream): T = throw UnsupportedOperationException()
}

interface RawPixelDataNoHeader: RawPixelData<Unit> {
    fun read(width: Int, height: Int, file: File): BufferedImage = read(width, height, file, Unit)
    override fun read(width: Int, height: Int, inputStream: InputStream, header: Unit): BufferedImage = read(width, height, inputStream)

    fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage
}