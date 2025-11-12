package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlbumObject(
    val id: String,
    val name: String,
    val images: List<ImageObject> = emptyList()
)