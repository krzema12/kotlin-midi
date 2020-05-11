import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        someFunction(Pair(0xFFFFFF, 0x00)) { _, _  -> }
    }
}

suspend fun <A, B> someFunction(row: Pair<A, B>, fn: suspend (A, B) -> Unit) {
    fn(row.first, row.second)
}
