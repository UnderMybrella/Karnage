package org.abimon.karnage.raw

import org.abimon.karnage.readers.TGAImageReader
import org.abimon.karnage.util.BitModification

data class TGAHeader(
    val idLength: Int,
    val colourMapType: Int,
    val imageType: Int,

    val firstEntryIndex: Int,
    val colourMapLength: Int,
    val colourMapEntrySize: Int,

    val xOrigin: Int,
    val yOrigin: Int,
    val imageWidth: Int,
    val imageHeight: Int,
    val pixelDepth: Int,
    val imageDescriptor: Int
) {
    constructor(header: ByteArray): this(
            header[0].toInt() and 0xFF,
            header[1].toInt() and 0xFF,
            header[2].toInt() and 0xFF,

            BitModification.toInt16(header[3], header[4]),
            BitModification.toInt16(header[5], header[6]),
            header[7].toInt() and 0xFF,

            BitModification.toInt16(header[8], header[9]),
            BitModification.toInt16(header[10], header[11]),
            BitModification.toInt16(header[12], header[13]),
            BitModification.toInt16(header[14], header[15]),

            header[16].toInt() and 0xFF,
            header[17].toInt() and 0xFF
    )

    val isValid: Boolean
        get() = imageType in TGAPixelData.VALID_IMAGE_TYPES && imageWidth in TGAImageReader.MAXIMUM_WIDTH_RANGE && imageHeight in TGAImageReader.MAXIMUM_HEIGHT_RANGE
}