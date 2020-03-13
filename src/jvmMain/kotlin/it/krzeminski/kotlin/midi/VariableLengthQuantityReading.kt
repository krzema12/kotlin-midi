package it.krzeminski.kotlin.midi

import java.io.DataInputStream

/**
 * @return Within the pair, the first element is the requested quantity,
 *         and the second element is the number of bytes read.
 */
fun DataInputStream.readVariableLengthQuantity(): Pair<Int, Int> {
    var value = 0
    var bytesRead = 0

    do {
        val thisByte = readByte().toInt()
        bytesRead++
        value = (value shl 7) or (thisByte and 0b01111111)
        val isLastByte = thisByte and 0b10000000 == 0
    } while (!isLastByte)

    return Pair(value, bytesRead)
}
