package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FollowedArtistsResponse(
    val artists: TopArtistsResponse
)