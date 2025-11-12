package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SavedTrackObject(
    val track: TrackObject
)