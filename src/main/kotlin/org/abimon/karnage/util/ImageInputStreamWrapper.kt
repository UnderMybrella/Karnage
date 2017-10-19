package org.abimon.karnage.util

import java.io.InputStream
import javax.imageio.stream.ImageInputStream

class ImageInputStreamWrapper(val imageInputStream: ImageInputStream): InputStream() {
    override fun skip(n: Long): Long = imageInputStream.skipBytes(n)
    override fun available(): Int = (imageInputStream.length() - imageInputStream.streamPosition).toInt()
    override fun reset() = imageInputStream.reset()
    override fun close() = imageInputStream.close()
    override fun mark(readlimit: Int) = imageInputStream.mark()
    override fun markSupported(): Boolean = true
    override fun read(): Int = imageInputStream.read()
    override fun read(b: ByteArray?): Int = imageInputStream.read(b)
    override fun read(b: ByteArray?, off: Int, len: Int): Int = imageInputStream.read(b, off, len)
}