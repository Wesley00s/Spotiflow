package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsResponse(
    val tracks: List<TrackObject>
)