import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row

@ExperimentalUnsignedTypes
class KotestUnsignedIntegersIssueTest : StringSpec({
    // FAILS WITH
    // Test failed for (inputBytes, UByteArray(storage=[0])), (someNumber, 0) with error
    //   java.lang.ClassCastException: kotlin.UByteArray cannot be cast to [B
    // java.lang.AssertionError: Test failed for (inputBytes, UByteArray(storage=[0])), (someNumber, 0) with error
    //   java.lang.ClassCastException: kotlin.UByteArray cannot be cast to [B
    "two parameters per row and unsigned integer types used as array" {
        forAll(
            row(ubyteArrayOf(0x00u), 0x00)
        ) { inputBytes, someNumber ->
            // some assertions
        }
    }

    // FAILS WITH
    // Test failed for (inputByte, 0), (someNumber, 0) with error
    //   java.lang.ClassCastException: kotlin.UInt cannot be cast to java.lang.Number
    // java.lang.AssertionError: Test failed for (inputByte, 0), (someNumber, 0) with error
    //   java.lang.ClassCastException: kotlin.UInt cannot be cast to java.lang.Number
    "two parameters per row and unsigned integer types used" {
        forAll(
            row(0x00u, 0x00)
        ) { inputByte, someNumber ->
            // some assertions
        }
    }

    // PASSES
    "one parameter per row, UByteArray" {
        forAll(
            row(ubyteArrayOf(0x00u))
        ) { inputBytes ->
            // some assertions
        }
    }

    // PASSES
    "one parameter per row, UByteA" {
        forAll(
            row(0x00u)
        ) { inputBytes ->
            // some assertions
        }
    }

    // PASSES
    "two parameters per row and NO unsigned integer types used" {
        forAll(
            row(byteArrayOf(0x00), 0x00)
        ) { inputBytes, someNumber ->
            // some assertions
        }
    }

    // PASSES
    "just the 'row' call" {
        row(ubyteArrayOf(0x00u), 0x00)
    }
})
