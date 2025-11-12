package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AlbumSimpleObject(
    val id: String,
    val name: String,
    @SerialName("album_type") val albumType: String,
    val artists: List<ArtistSimpleObject>,
    val images: List<ImageObject>,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("total_tracks") val totalTracks: Int
)