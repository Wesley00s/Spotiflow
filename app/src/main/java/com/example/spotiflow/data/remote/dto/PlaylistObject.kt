package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistObject(
    val id: String,
    val name: String,
    val images: List<ImageObject> = emptyList()
)

@Serializable
data class PlaylistsResponse(
    val items: List<PlaylistObject>
)