package it.krzeminski.kotlin.midi

import kotlin.test.Test
import kotlin.test.assertEquals

class EndToEndTest {
    @Test
    fun simpleSong_parsingHeaders() {
        // given
        val testMidiFile = javaClass.classLoader.getResource("Simple Song.mid")?.openStream()
            ?: throw RuntimeException("Could not load test resource!")

        // when
        val parsedMidi = testMidiFile.readMidi()

        // then
        with (parsedMidi) {
            assertEquals(1, tracks.size)
        }
    }
}
