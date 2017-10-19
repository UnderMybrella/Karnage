package org.abimon.karnage.readers

import java.io.File
import java.io.InputStream
import java.util.*
import javax.imageio.ImageReader
import javax.imageio.spi.ImageReaderSpi
import javax.imageio.stream.ImageInputStream

class TGAImageReaderSpi: ImageReaderSpi(
        "Adobe",
        "0.1",
        arrayOf("TGA"),
        arrayOf("tga"),
        arrayOf("image/targa", "image/tga"),
        TGAImageReader::class.java.name,
        arrayOf(File::class.java, InputStream::class.java, ImageInputStream::class.java),
        emptyArray(),
        false,
        null,
        null,
        emptyArray(),
        emptyArray(),
        false,
        null,
        null,
        emptyArray(),
        emptyArray()
) {
    override fun createReaderInstance(extension: Any?): ImageReader = TGAImageReader(this)

    override fun getDescription(locale: Locale?): String = "Works with TGA images"

    override fun canDecodeInput(source: Any?): Boolean = true
}