package it.krzeminski.kotlin.midi

import kotlin.test.Test
import kotlin.test.assertEquals

class EndToEndTest {
    @Test
    fun parsingHeaders() {
        // given
        val testMidiFile = javaClass.classLoader.getResource("Mario game - Overworld.mid")?.openStream()
            ?: throw RuntimeException("Could not load test resource!")

        // when
        val parsedMidi = testMidiFile.readMidi()

        // then
        with (parsedMidi) {
            assertEquals(5, tracks.size)
        }
    }
}
