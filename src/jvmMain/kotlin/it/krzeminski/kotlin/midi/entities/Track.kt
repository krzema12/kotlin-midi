package it.krzeminski.kotlin.midi.entities

data class Track(
    val events: List<TrackEvent>
)

data class TrackEvent(
    val deltaTime: Int,
    val event: Event
)

sealed class Event

sealed class MidiEvent : Event()
object SystemExclusiveEvent : Event()
sealed class MetaEvent : Event()

data class TimeSignatureMetaEvent(
    val numerator: Int,
    val denominator: Int,
    val midiClocksPerMetronomeTick: Int,
    val noOf32thNotesPerMidiQuarterNote: Int
) : MetaEvent()

data class KeySignatureMetaEvent(
    /**
     * From -7 to 7, negative means the number of flats, positive means the number of sharps, 0 means the key of C.
     */
    val flatsOrSharps: Int,
    val isMinor: Boolean
) : MetaEvent()

data class SetTempoMetaEvent(
    val microsecondsPerMidiQuarterNote: Int
) : MetaEvent()

object EndOfTrackMetaEvent : MetaEvent()

data class ControlChangeMidiEvent(
    val channel: Int,
    val controller: Int,
    val newValue: Int
) : MidiEvent()

data class ProgramChangeMidiEvent(
    val channel: Int,
    val program: Int
) : MidiEvent()

data class NoteOnMidiEvent(
    val note: Note,
    val velocity: Int) : MidiEvent()
