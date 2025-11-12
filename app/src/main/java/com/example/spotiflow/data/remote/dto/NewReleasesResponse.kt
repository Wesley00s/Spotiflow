package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class NewReleasesResponse(
    val albums: AlbumsPagingObject
)