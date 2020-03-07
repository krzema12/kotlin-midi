package it.krzeminski.kotlin.midi

import it.krzeminski.kotlin.midi.entities.HeaderChunk
import it.krzeminski.kotlin.midi.entities.TrackChunk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
            assertEquals(6, chunks.size)
            assertTrue(chunks[0] is HeaderChunk)
            assertTrue(chunks[1] is TrackChunk)
            assertTrue(chunks[2] is TrackChunk)
            assertTrue(chunks[3] is TrackChunk)
            assertTrue(chunks[4] is TrackChunk)
            assertTrue(chunks[5] is TrackChunk)
        }
    }
}
