package it.krzeminski.kotlin.midi

import it.krzeminski.kotlin.midi.entities.*
import it.krzeminski.kotlin.midi.entities.Key.*
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
            assertEquals(listOf(
                TrackEvent(deltaTime=0, event=TimeSignatureMetaEvent(
                    numerator=4, denominator=4, midiClocksPerMetronomeTick=24, noOf32thNotesPerMidiQuarterNote=8)),
                TrackEvent(deltaTime=0, event=KeySignatureMetaEvent(flatsOrSharps=0, isMinor=false)),
                TrackEvent(deltaTime=0, event=SetTempoMetaEvent(microsecondsPerMidiQuarterNote=521739)),
                TrackEvent(deltaTime=0, event=ControlChangeMidiEvent(channel=0, controller=121, newValue=0)),
                TrackEvent(deltaTime=0, event=ProgramChangeMidiEvent(channel=0, program=0)),
                TrackEvent(deltaTime=0, event=ControlChangeMidiEvent(channel=0, controller=7, newValue=100)),
                TrackEvent(deltaTime=0, event=ControlChangeMidiEvent(channel=0, controller=10, newValue=64)),
                TrackEvent(deltaTime=0, event=ControlChangeMidiEvent(channel=0, controller=91, newValue=0)),
                TrackEvent(deltaTime=0, event=ControlChangeMidiEvent(channel=0, controller=93, newValue=0)),
                TrackEvent(deltaTime=0, event=MidiPortMetaEvent(port=0)),
                TrackEvent(deltaTime=0,    event=NoteOnMidiEvent(note=Note(C, 5), velocity=80)),
                TrackEvent(deltaTime=1823, event=NoteOnMidiEvent(note=Note(C, 5), velocity=0)),
                TrackEvent(deltaTime=97,   event=NoteOnMidiEvent(note=Note(D, 5), velocity=80)),
                TrackEvent(deltaTime=227,  event=NoteOnMidiEvent(note=Note(D, 5), velocity=0)),
                TrackEvent(deltaTime=13,   event=NoteOnMidiEvent(note=Note(D, 5), velocity=80)),
                TrackEvent(deltaTime=227,  event=NoteOnMidiEvent(note=Note(D, 5), velocity=0)),
                TrackEvent(deltaTime=13,   event=NoteOnMidiEvent(note=Note(E, 5), velocity=80)),
                TrackEvent(deltaTime=455,  event=NoteOnMidiEvent(note=Note(E, 5), velocity=0)),
                TrackEvent(deltaTime=25,   event=NoteOnMidiEvent(note=Note(D, 5), velocity=80)),
                TrackEvent(deltaTime=911,  event=NoteOnMidiEvent(note=Note(D, 5), velocity=0)),
                TrackEvent(deltaTime=49,   event=NoteOnMidiEvent(note=Note(E, 5), velocity=80)),
                TrackEvent(deltaTime=1823, event=NoteOnMidiEvent(note=Note(E, 5), velocity=0))
            ), tracks[0].events)
        }
    }
}
