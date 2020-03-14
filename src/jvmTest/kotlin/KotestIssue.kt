import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        forAll(
            row(0xFFFFFFu, 0x00)
        ) { inputByte, someNumber ->
//             some assertions
        }
    }
}

suspend fun <A, B> forAll(vararg rows: Row2<A, B>, testfn: suspend (A, B) -> Unit) {
    table(*rows).forAll { a, b ->
        testfn(a, b)
    }
}

inline fun <A, B> Table2<A, B>.forAll(fn: (A, B) -> Unit) {
    for (row in rows) {
        fn(row.a, row.b)
    }
}

fun <A, B> row(a: A, b: B) = Row2(a, b)

data class Row2<out A, out B>(val a: A, val b: B) {
    fun values() = listOf(a, b)
}

fun <A, B> table(vararg rows: Row2<A, B>) = Table2(rows.asList())

data class Headers2(val labelA: String, val labelB: String) {
    fun values() = listOf(labelA, labelB)
}

data class Table2<out A, out B>(val rows: List<Row2<A, B>>)

fun headers(a: String, b: String) = Headers2(a, b)
