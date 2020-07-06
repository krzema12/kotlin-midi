package it.krzeminski.kotlin.midi.entities

enum class Key {
    C,
    Csharp,
    D,
    Dsharp,
    E,
    F,
    Fsharp,
    G,
    Gsharp,
    A,
    Asharp,
    B;
}

data class Note(val key: Key, val octave: Int)

fun Int.toNote(): Note {
    val octave = this / 12
    val keyOridinal = this % 12

    return Note(key = Key.values()[keyOridinal], octave = octave)
}
