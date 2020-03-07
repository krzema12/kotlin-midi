package it.krzeminski.kotlin.midi.entities

sealed class Chunk

object HeaderChunk : Chunk()
object TrackChunk : Chunk()
