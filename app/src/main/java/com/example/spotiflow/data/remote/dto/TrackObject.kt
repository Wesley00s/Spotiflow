package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrackObject(
    val id: String,
    val name: String,
    val uri: String,
    val album: AlbumObject,
    val artists: List<ArtistObject>
)