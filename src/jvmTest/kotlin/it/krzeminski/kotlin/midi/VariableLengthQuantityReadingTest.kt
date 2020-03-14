package it.krzeminski.kotlin.midi

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import java.io.DataInputStream

@ExperimentalUnsignedTypes
class VariableLengthQuantityReadingTest : StringSpec({
    "edge cases from MIDI specification" {
        // Source: http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html
        forAll(
            row(ubyteArrayOf(0x00u), 0x00)
//            row(byteArrayOf(0x40),                      0x40),
//            row(byteArrayOf(0x7F),                      0x7F),
//            row(byteArrayOf(0x81, 0x00),               0x80),
//            row(byteArrayOf(0xC0, 0x00),               0x2000),
//            row(byteArrayOf(0xFF, 0x7F),               0x3FFF),
//            row(byteArrayOf(0x81, 0x80, 0x00),        0x4000),
//            row(byteArrayOf(0xC0, 0x80, 0x00),        0x100000),
//            row(byteArrayOf(0xFF, 0xFF, 0x7F),        0x1FFFFF),
//            row(byteArrayOf(0x81, 0x80, 0x80, 0x00), 0x200000),
//            row(byteArrayOf(0xC0, 0x80, 0x80, 0x00), 0x8000000),
//            row(byteArrayOf(0xFF, 0xFF, 0xFF, 0x7F), 0xFFFFFFF)
        ) { inputBytes, expectedQuantity ->
            // given
//            val test = inputBytes.asByteArray()
            val test = DataInputStream(byteArrayOf(0x00).inputStream()).readVariableLengthQuantity()
//            val encodedNumberAsStream = DataInputStream(inputBytes.asByteArray().inputStream())

            // when
//            val (readQuantity, readBytes) = encodedNumberAsStream.readVariableLengthQuantity()
//
//            readQuantity shouldBe expectedQuantity
//            readBytes shouldBe inputBytes.size
        }
    }
})

//@ExperimentalUnsignedTypes
//@RunWith(Parameterized::class)
//class VariableLengthQuantityReadingTest {
////    @Test
////    fun validCase1() {
////        listOf(
////            row(byteArrayOf(0x00),                      0x00),
////            row(byteArrayOf(0x40),                      0x40),
////            row(byteArrayOf(0x7F),                      0x7F)
////            row(byteArrayOf(0x81, 0x00),               0x80),
////            row(byteArrayOf(0xC0, 0x00),               0x2000),
////            row(byteArrayOf(0xFF, 0x7F),               0x3FFF),
////            row(byteArrayOf(0x81, 0x80, 0x00),        0x4000),
////            row(byteArrayOf(0xC0, 0x80, 0x00),        0x100000),
////            row(byteArrayOf(0xFF, 0xFF, 0x7F),        0x1FFFFF),
////            row(byteArrayOf(0x81, 0x80, 0x80, 0x00), 0x200000),
////            row(byteArrayOf(0xC0, 0x80, 0x80, 0x00), 0x8000000),
////            row(byteArrayOf(0xFF, 0xFF, 0xFF, 0x7F), 0xFFFFFFF)
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
//        val encodedNumberAsStream = DataInputStream(byteArrayOf(0x00).asByteArray().inputStream())
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
//        val encodedNumberAsStream = DataInputStream(byteArrayOf(0x40).asByteArray().inputStream())
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
//        val encodedNumberAsStream = DataInputStream(byteArrayOf(0x7F).asByteArray().inputStream())
//        val actualQuantity = 0x7F
//
//        // when
//        val (readQuantity, readBytes) = encodedNumberAsStream.readVariableLengthQuantity()
//        assertEquals(actualQuantity, readQuantity)
//        assertEquals(1, readBytes)
//    }
//}
