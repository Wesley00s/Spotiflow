package com.example.spotiflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val artists: TopArtistsResponse? = null,
    val tracks: TopTracksResponse? = null,
    val playlists: PlaylistsResponse? = null
)