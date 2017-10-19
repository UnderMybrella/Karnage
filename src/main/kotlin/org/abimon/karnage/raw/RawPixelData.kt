package org.abimon.karnage.raw

import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream

interface RawPixelData<T: Any> {
    fun read(width: Int, height: Int, file: File, header: T): BufferedImage = read(width, height, file.inputStream(), header)
    fun read(width: Int, height: Int, inputStream: InputStream, header: T): BufferedImage
}

interface RawPixelDataNoHeader: RawPixelData<Unit> {
    fun read(width: Int, height: Int, file: File): BufferedImage = read(width, height, file, Unit)
    fun read(width: Int, height: Int, inputStream: InputStream): BufferedImage = read(width, height, inputStream, Unit)
}