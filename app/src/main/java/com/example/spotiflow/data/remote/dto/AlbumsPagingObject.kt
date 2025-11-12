package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class AlbumsPagingObject(
    val items: List<AlbumSimpleObject>,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val href: String,
    val next: String? = null,
    val previous: String? = null
)