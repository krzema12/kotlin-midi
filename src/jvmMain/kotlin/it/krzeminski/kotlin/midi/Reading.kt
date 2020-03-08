package it.krzeminski.kotlin.midi

import it.krzeminski.kotlin.midi.entities.MidiFile
import it.krzeminski.kotlin.midi.entities.Track
import java.io.DataInputStream
import java.io.InputStream

// http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html

fun InputStream.readMidi(): MidiFile {
    with (DataInputStream(this)) {
        val headerChunk =  readHeaderChunk()
        val tracks = (1..headerChunk.numberOfTracks).mapNotNull {
            val chunk = when (readChunkTypeString()) {
                "MTrk" -> Track
                else -> null // "Alien chunk", should be omitted.
            }
            val chunkLength = readInt()
            skip(chunkLength.toLong())
            chunk
        }
        return MidiFile(tracks)
    }
}

private fun DataInputStream.readChunkTypeString() =
    (1..4).map { readByte().toChar() }.joinToString(separator = "")

private data class HeaderChunk(
    val format: Short,
    val numberOfTracks: Int,
    val division: Int
)

private fun DataInputStream.readHeaderChunk() : HeaderChunk {
    val chunkTypeString = readChunkTypeString()
    require(chunkTypeString == "MThd") { "First chunk should be header, and chunk type '$chunkTypeString' found!" }
    val length = readInt()
    val chunk = HeaderChunk(
        format = readShort(),
        numberOfTracks = readShort().toInt(),
        division = readShort().toInt())
    val numberOfBytesUnderstoodFromHeaderChunk = 6
    skip((length - numberOfBytesUnderstoodFromHeaderChunk).toLong()) // In case a new field appears in the header.
    return chunk
}
