package it.krzeminski.kotlin.midi

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import java.lang.Integer.max

class VariableLengthQuantityReadingTest : StringSpec({
    "edge cases from MIDI specification" {
        // Source: http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html
        forAll(
            row(1, 5, 4),
            row(1, 0, 1),
            row(0, 0, 1)
        ) { a, b, max ->
            max(a, b) shouldBe max
        }
    }
})

//@ExperimentalUnsignedTypes
//@RunWith(Parameterized::class)
//class VariableLengthQuantityReadingTest {
////    @Test
////    fun validCase1() {
////        listOf(
////            Pair(ubyteArrayOf(0x00u),                      0x00),
////            Pair(ubyteArrayOf(0x40u),                      0x40),
////            Pair(ubyteArrayOf(0x7Fu),                      0x7F)
////            Pair(ubyteArrayOf(0x81u, 0x00u),               0x80),
////            Pair(ubyteArrayOf(0xC0u, 0x00u),               0x2000),
////            Pair(ubyteArrayOf(0xFFu, 0x7Fu),               0x3FFF),
////            Pair(ubyteArrayOf(0x81u, 0x80u, 0x00u),        0x4000),
////            Pair(ubyteArrayOf(0xC0u, 0x80u, 0x00u),        0x100000),
////            Pair(ubyteArrayOf(0xFFu, 0xFFu, 0x7Fu),        0x1FFFFF),
////            Pair(ubyteArrayOf(0x81u, 0x80u, 0x80u, 0x00u), 0x200000),
////            Pair(ubyteArrayOf(0xC0u, 0x80u, 0x80u, 0x00u), 0x8000000),
////            Pair(ubyteArrayOf(0xFFu, 0xFFu, 0xFFu, 0x7Fu), 0xFFFFFFF)
////        ).forEach { (inputByteArray: UByteArray, expectedNumber: Int) ->
////            val inputStream = DataInputStream(inputByteArray.asByteArray().inputStream())
////            assertEquals(expectedNumber, inputStream.readVariableLengthQuantity().first)
////        }
////    }
//
//
//    @Test
//    fun testCaseFromSpecs_00() {
//        // given
//        val encodedNumberAsStream = DataInputStream(ubyteArrayOf(0x00u).asByteArray().inputStream())
//        val actualQuantity = 0x00
//
//        // when
//        val (readQuantity, readBytes) = encodedNumberAsStream.readVariableLengthQuantity()
//        assertEquals(actualQuantity, readQuantity)
//        assertEquals(1, readBytes)
//    }
//
//    @Test
//    fun testCaseFromSpecs_40() {
//        // given
//        val encodedNumberAsStream = DataInputStream(ubyteArrayOf(0x40u).asByteArray().inputStream())
//        val actualQuantity = 0x40
//
//        // when
//        val (readQuantity, readBytes) = encodedNumberAsStream.readVariableLengthQuantity()
//        assertEquals(actualQuantity, readQuantity)
//        assertEquals(1, readBytes)
//    }
//
//    @Test
//    fun testCaseFromSpecs_7F() {
//        // given
//        val encodedNumberAsStream = DataInputStream(ubyteArrayOf(0x7Fu).asByteArray().inputStream())
//        val actualQuantity = 0x7F
//
//        // when
//        val (readQuantity, readBytes) = encodedNumberAsStream.readVariableLengthQuantity()
//        assertEquals(actualQuantity, readQuantity)
//        assertEquals(1, readBytes)
//    }
//}
