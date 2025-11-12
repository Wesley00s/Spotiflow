package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class ArtistObject(
    val id: String,
    val name: String,
    val genres: List<String> = emptyList(),
    val images: List<ImageObject> = emptyList()
)