package org.abimon.karnage.readers

import org.abimon.karnage.raw.TGAHeader
import org.abimon.karnage.raw.TGAPixelData
import org.abimon.karnage.util.ImageInputStreamWrapper
import java.awt.image.BufferedImage
import java.awt.image.ColorModel
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.ImageTypeSpecifier
import javax.imageio.metadata.IIOMetadata
import javax.imageio.spi.ImageReaderSpi
import javax.imageio.stream.ImageInputStream

class TGAImageReader(originatingProvider: ImageReaderSpi): ImageReader(originatingProvider) {
    companion object Specifications {
        val HEADER_LENGTH = 18
        var MAXIMUM_WIDTH_RANGE = (1 until 8192)
        var MAXIMUM_HEIGHT_RANGE = (1 until 8192)


    }

    lateinit var header: TGAHeader

    override fun getImageMetadata(imageIndex: Int): IIOMetadata? = null

    override fun getStreamMetadata(): IIOMetadata? = null

    override fun getWidth(imageIndex: Int): Int {
        if(!readHeader())
            return -1

        return header.imageWidth
    }

    override fun getHeight(imageIndex: Int): Int {
        if(!readHeader())
            return -1

        return header.imageHeight
    }

    override fun getNumImages(allowSearch: Boolean): Int = 1

    override fun getImageTypes(imageIndex: Int): MutableIterator<ImageTypeSpecifier> = mutableListOf(getImageSpecifier(imageIndex)).listIterator()

    override fun read(imageIndex: Int, param: ImageReadParam?): BufferedImage? {
        readHeader()

        if(!header.isValid)
            return null

        when(input ?: return null) {
            //is File -> return TGAPixelData.read(header.imageWidth, header.imageHeight, ImageInputStreamWrapper(input as ImageInputStream), header)
            //is InputStream -> return TGAReader.readImage((input as InputStream).readBytes())
            is ImageInputStream -> return TGAPixelData.read(header.imageWidth, header.imageHeight, ImageInputStreamWrapper(input as ImageInputStream), header)
            else -> return null
        }
    }

    fun getImageSpecifier(imageIndex: Int): ImageTypeSpecifier {
        val (w, h) = getWidthAndHeight(imageIndex)
        val colour = ColorModel.getRGBdefault()

        return ImageTypeSpecifier(colour, colour.createCompatibleSampleModel(w, h))
    }

    fun getWidthAndHeight(imageIndex: Int): Pair<Int, Int> = getWidth(imageIndex) to getHeight(imageIndex)

    fun readHeader(): Boolean {
        if(this::header.isInitialized)
            return true

        if(input !is ImageInputStream)
            return false

        header = TGAHeader(ByteArray(HEADER_LENGTH).apply { (input as ImageInputStream).read(this) })

        return true
    }
}