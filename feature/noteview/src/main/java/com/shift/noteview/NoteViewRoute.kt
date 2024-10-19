package com.shift.noteview

import kotlinx.serialization.Serializable

@Serializable
data class NoteViewRoute(
    val note : Int
)