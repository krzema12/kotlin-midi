package it.krzeminski.kotlin.midi

import java.io.DataInputStream
import kotlin.experimental.and

/**
 * @return Within the pair, the first element is the requested quantity,
 *         and the second element is the number of bytes read.
 */
fun DataInputStream.readVariableLengthQuantity(): Pair<Int, Int> {
    var readValue: Int = 0
    val oldestBitMask = 0b10000000.toByte()
    val byte = readByte()

//    if ((byte and oldestBitMask) != 0.toByte()) {

//    }
    return Pair(byte.toInt(), 1)
}
