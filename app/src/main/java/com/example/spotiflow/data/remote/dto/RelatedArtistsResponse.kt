package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RelatedArtistsResponse(
    val artists: List<ArtistObject>
)