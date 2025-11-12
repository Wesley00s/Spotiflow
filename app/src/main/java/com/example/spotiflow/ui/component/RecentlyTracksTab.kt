package com.example.spotiflow.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.spotiflow.ui.screen.home.HomeUiEvent
import com.example.spotiflow.ui.screen.home.HomeUiState

@Composable
fun RecentlyTracksTab(uiState: HomeUiState, onEvent: (HomeUiEvent) -> Unit) {
    LaunchedEffect(Unit) {
        onEvent(HomeUiEvent.LoadPlayedTracks)
    }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(uiState.recentlyPlayedTracks) { track ->
            
            TrackListItem(
                track = track,
                onPlayClick = {
                    onEvent(HomeUiEvent.PlayTrack(it.uri)) 
                },
                onTrackClick = {
                    
                }
            )
        }
    }
}