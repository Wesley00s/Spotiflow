package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeaturedPlaylistsResponse(
    val message: String,
    val playlists: PlaylistsResponse
)