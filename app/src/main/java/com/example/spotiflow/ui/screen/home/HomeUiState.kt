package com.example.spotiflow.ui.screen.home

import com.example.spotiflow.data.model.UiAlbum
import com.example.spotiflow.data.model.UiArtist
import com.example.spotiflow.data.model.UiCategory
import com.example.spotiflow.data.model.UiPlaylist
import com.example.spotiflow.data.model.UiTrack
import com.example.spotiflow.data.model.UiUserProfile

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val userProfile: UiUserProfile? = null,
    val selectedTimeRange: TopTimeRange = TopTimeRange.MEDIUM_TERM,
    val recentlyPlayedTracks: List<UiTrack> = emptyList(),
    val savedTracks: List<UiTrack> = emptyList(),
    val topTracks: List<UiTrack> = emptyList(),
    val userPlaylists: List<UiPlaylist> = emptyList(),
    val followedArtists: List<UiArtist> = emptyList(),
    val featuredPlaylists: List<UiPlaylist> = emptyList(),
    val recommendedTracks: List<UiTrack> = emptyList(),
    val relatedArtists: List<UiArtist> = emptyList(),
    val featuredCategories: List<UiCategory> = emptyList(),
    val isCategoriesLoadingNextPage: Boolean = false,
    val categoriesCurrentPage: Int = 0,
    val canLoadMoreCategories: Boolean = true,
    val newReleases: List<UiAlbum> = emptyList()
)