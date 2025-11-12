package com.example.spotiflow.ui.screen.home

enum class TopTimeRange(val apiValue: String) {
    SHORT_TERM("short_term"),
    MEDIUM_TERM("medium_term"),
    LONG_TERM("long_term")
}

sealed interface HomeUiEvent {
    data object LoadData : HomeUiEvent
    data object LoadPlayedTracks : HomeUiEvent
    data object LoadLikedTracks : HomeUiEvent
    data object LoadTopPlayedTracks : HomeUiEvent
    data object LoadMyPlayList : HomeUiEvent
    data object LoadFollowingArtists : HomeUiEvent
    data object LoadRecommendedPlayList : HomeUiEvent
    data object LoadRecommendedTracks : HomeUiEvent
    data object LoadRelatedArtists : HomeUiEvent
    data object Refresh : HomeUiEvent
    data class ChangeTimeRange(val timeRange: TopTimeRange) : HomeUiEvent
    data class PlayTrack(val trackUri: String) : HomeUiEvent
    data object LoadFeaturedCategories: HomeUiEvent
    data object LoadMoreCategories : HomeUiEvent
}