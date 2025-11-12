package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class ArtistSimpleObject(
    val id: String,
    val name: String
)