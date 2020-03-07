package it.krzeminski.kotlin.midi

import it.krzeminski.kotlin.midi.entities.Chunk
import it.krzeminski.kotlin.midi.entities.HeaderChunk
import it.krzeminski.kotlin.midi.entities.MidiFile
import it.krzeminski.kotlin.midi.entities.TrackChunk
import java.io.DataInputStream
import java.io.EOFException
import java.io.InputStream
import java.lang.IllegalArgumentException

// http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html

fun InputStream.readMidi(): MidiFile {
    val chunks = mutableListOf<Chunk>()
    with (DataInputStream(this)) {
        while(true) {
            try {
                val chunk = when (readChunkTypeString()) {
                    "MThd" -> HeaderChunk
                    "MTrk" -> TrackChunk
                    else -> null // "Alien chunk", should be omitted.
                }
                chunk?.let {
                    chunks.add(it)
                }
                val chunkLength = readInt()
                skip(chunkLength.toLong())
            } catch (e: EOFException) {
                // Exception-driven logic is not the best approach, but no better way so far
                // to stop parsing when the input stream has no more data.
                break
            }
        }
    }
    return MidiFile(chunks)
}

private fun DataInputStream.readChunkTypeString() =
    (1..4).map { readByte().toChar() }.joinToString(separator = "")
