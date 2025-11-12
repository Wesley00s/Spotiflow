package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SavedTracksResponse(
    val items: List<SavedTrackObject>
)