package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class CategoryObject(
    val id: String,
    val name: String,
    val href: String,
    val icons: List<ImageObject> = emptyList() 
)