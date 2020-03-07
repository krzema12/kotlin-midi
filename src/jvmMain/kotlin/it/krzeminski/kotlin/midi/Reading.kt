package it.krzeminski.kotlin.midi

import it.krzeminski.kotlin.midi.entities.HeaderChunk
import it.krzeminski.kotlin.midi.entities.MidiFile
import it.krzeminski.kotlin.midi.entities.TrackChunk
import java.io.InputStream

fun readMidi(inputStream: InputStream): MidiFile {
    // Temporarily, return some hardcoded stuff.
    return MidiFile(listOf(HeaderChunk, TrackChunk, TrackChunk, TrackChunk, TrackChunk))
}
