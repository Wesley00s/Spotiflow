package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesPagingObject(
    val items: List<CategoryObject>,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val href: String,
    val next: String? = null,
    val previous: String? = null
)