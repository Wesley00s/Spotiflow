package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayHistoryObject(
    val track: TrackObject
)

@Serializable
data class RecentlyPlayedResponse(
    val items: List<PlayHistoryObject>
)