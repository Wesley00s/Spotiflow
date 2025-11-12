package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TopArtistsResponse(
    val items: List<ArtistObject>
)