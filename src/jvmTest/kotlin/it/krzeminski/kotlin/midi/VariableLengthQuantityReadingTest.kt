package it.krzeminski.kotlin.midi

import java.io.DataInputStream
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalUnsignedTypes
class VariableLengthQuantityReadingTest {
    @Test
    fun edgeCasesFromMIDISpecification() {
        // Source: http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html
        listOf(
            Pair(ubyteArrayOf(0x00u),                      0x00000000),
            Pair(ubyteArrayOf(0x40u),                      0x00000040),
            Pair(ubyteArrayOf(0x7Fu),                      0x0000007F),
            Pair(ubyteArrayOf(0x81u, 0x00u),               0x00000080),
            Pair(ubyteArrayOf(0xC0u, 0x00u),               0x00002000),
            Pair(ubyteArrayOf(0xFFu, 0x7Fu),               0x00003FFF),
            Pair(ubyteArrayOf(0x81u, 0x80u, 0x00u),        0x00004000),
            Pair(ubyteArrayOf(0xC0u, 0x80u, 0x00u),        0x00100000),
            Pair(ubyteArrayOf(0xFFu, 0xFFu, 0x7Fu),        0x001FFFFF),
            Pair(ubyteArrayOf(0x81u, 0x80u, 0x80u, 0x00u), 0x00200000),
            Pair(ubyteArrayOf(0xC0u, 0x80u, 0x80u, 0x00u), 0x08000000),
            Pair(ubyteArrayOf(0xFFu, 0xFFu, 0xFFu, 0x7Fu), 0x0FFFFFFF)
        ).forEach { (inputBytes, expectedQuantity) ->
            val (actualQuantity, numberOfBytesRead) =
                DataInputStream(inputBytes.asByteArray().inputStream()).readVariableLengthQuantity()

            assertEquals(expectedQuantity, actualQuantity)
            assertEquals(inputBytes.size, numberOfBytesRead)
        }
    }
}
