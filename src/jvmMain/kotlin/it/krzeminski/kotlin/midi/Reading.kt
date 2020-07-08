package it.krzeminski.kotlin.midi

import it.krzeminski.kotlin.midi.entities.*
import java.io.DataInputStream
import java.io.InputStream
import kotlin.math.pow

// http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html
// https://github.com/colxi/midi-parser-js/blob/master/src/main.js
// http://www.somascape.org/midi/tech/mfile.html

fun InputStream.readMidi(): MidiFile {
    with (DataInputStream(this)) {
        val headerChunk =  readHeaderChunk()
        val tracks = (1..headerChunk.numberOfTracks).mapNotNull {
            val trackOrNull = when (readChunkTypeString()) {
                "MTrk" -> readTrackChunk()
                else ->  {
                    skipAlienChunk()
                    null
                }
            }
            trackOrNull
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

@ExperimentalUnsignedTypes
private fun DataInputStream.readTrackChunk(): Track {
    val length = readInt()
    var read = 0
    var lastTrackEventType: UByte = 0x00u
    val events = mutableListOf<TrackEvent>()
    while (read < length) {
        val (trackEvent, bytesRead, newTrackEventType) = readTrackEvent(lastTrackEventType)

        if (trackEvent?.event is EndOfTrackMetaEvent) {
            break
        }

        lastTrackEventType = newTrackEventType
        read += bytesRead
        trackEvent?.let {
            events.add(it)
        }
    }
    return Track(events)
}

/**
 * @return The second element of the Pair is the number of bytes read.
 */
@ExperimentalUnsignedTypes
private fun DataInputStream.readTrackEvent(lastTrackEventType: UByte): Triple<TrackEvent?, Int, UByte> {
    val (deltaTime, bytesReadFromDeltaTime) = readVariableLengthQuantity()
    mark(1)
    val newTrackEventType = readByte().toUByte()
    val trackEventType = if (newTrackEventType > 128u) {
        newTrackEventType
    } else {
        reset()
        lastTrackEventType
    }

    return when (trackEventType) {
        0xFFu.toUByte() -> Triple(
            readMetaEvent()?.let{ TrackEvent(deltaTime, it) }, bytesReadFromDeltaTime, trackEventType)
        else -> Triple(
            readMidiEvent(trackEventType)?.let { TrackEvent(deltaTime, it) }, bytesReadFromDeltaTime, trackEventType)
    }
}

@ExperimentalUnsignedTypes
private fun DataInputStream.readMetaEvent(): MetaEvent? {
    val type = readByte().toUByte()
    val (length, _) = readVariableLengthQuantity()

    return when (type) {
        0x21u.toUByte() -> readMidiPortMetaEvent()
        0x2Fu.toUByte() -> EndOfTrackMetaEvent
        0x51u.toUByte() -> readSetTempoMetaEvent()
        0x58u.toUByte() -> readTimeSignatureMetaEvent()
        0x59u.toUByte() -> readKeySignatureMetaEvent()
        else -> {
            skip(length.toLong())
            null
        }
    }
}

@ExperimentalUnsignedTypes
private fun DataInputStream.readMidiEvent(trackEventType: UByte): MidiEvent? {
    return when (trackEventType.toInt() shr 4) {
        0b1001 -> readNoteOnMidiEvent(trackEventType.toInt() and 0b1111)
        0b1011 -> readControlChangeMidiEvent(trackEventType.toInt() and 0b1111)
        0b1100 -> readProgramChangeMidiEvent(trackEventType.toInt() and 0b1111)
        else -> null
    }
}

private fun DataInputStream.readNoteOnMidiEvent(channel: Int): MidiEvent {
    val note = readByte().toInt() and 0b011111111
    val velocity = readByte().toInt() and 0b011111111
    return NoteOnMidiEvent(
        note = note.toNote(),
        velocity = velocity)
}

private fun DataInputStream.readControlChangeMidiEvent(channel: Int): MidiEvent {
    val controller = readByte().toInt() and 0b011111111
    val newValue = readByte().toInt() and 0b011111111
    return ControlChangeMidiEvent(
        channel = channel,
        controller = controller,
        newValue = newValue)
}

private fun DataInputStream.readProgramChangeMidiEvent(channel: Int): MidiEvent {
    val program = readByte().toInt() and 0b011111111
    return ProgramChangeMidiEvent(
        channel = channel,
        program = program)
}

private fun DataInputStream.readTimeSignatureMetaEvent(): TimeSignatureMetaEvent {
    val numerator = readByte().toInt()
    val denominatorPowerOfTwo = readByte().toInt()

    return TimeSignatureMetaEvent(
        numerator = numerator,
        denominator = 2.0f.pow(denominatorPowerOfTwo.toFloat()).toInt(),
        midiClocksPerMetronomeTick = readByte().toInt(),
        noOf32thNotesPerMidiQuarterNote = readByte().toInt())
}

private fun DataInputStream.readKeySignatureMetaEvent(): KeySignatureMetaEvent {
    return KeySignatureMetaEvent(
        flatsOrSharps = readByte().toInt(),
        isMinor = readByte().toInt() == 1)
}

private fun DataInputStream.readSetTempoMetaEvent(): SetTempoMetaEvent {
    val byte1 = readUnsignedByte()
    val byte2 = readUnsignedByte()
    val byte3 = readUnsignedByte()
    return SetTempoMetaEvent(
        microsecondsPerMidiQuarterNote = (byte1*0x10000 + byte2*0x100 + byte3))
}

private fun DataInputStream.readMidiPortMetaEvent(): MidiPortMetaEvent {
    val port = readByte().toInt() and 0b011111111
    return MidiPortMetaEvent(
        port = port)
}

private fun DataInputStream.skipAlienChunk() {
    val length = readInt()
    skip(length.toLong())
}
