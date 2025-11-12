package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    @SerialName("display_name") val displayName: String,
    val images: List<ImageObject> = emptyList()
)

@Serializable
data class ImageObject(
    val url: String,
    val height: Int?,
    val width: Int?
)