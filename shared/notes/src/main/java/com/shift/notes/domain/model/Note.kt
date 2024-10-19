package com.shift.notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int = 0,
    var title: String,
    var content: String,
    var tags: List<String>,
    var imageUri: String?,
    var timestamp: Long
)
