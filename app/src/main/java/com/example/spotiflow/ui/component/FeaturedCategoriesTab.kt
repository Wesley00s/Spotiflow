package com.example.spotiflow.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.spotiflow.ui.screen.home.HomeUiEvent
import com.example.spotiflow.ui.screen.home.HomeUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


@Composable
fun FeaturedCategoriesTab(uiState: HomeUiState, onEvent: (HomeUiEvent) -> Unit) {
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        if (uiState.featuredCategories.isEmpty()) {
            onEvent(HomeUiEvent.LoadFeaturedCategories)
        }
    }

    LaunchedEffect(gridState, uiState.canLoadMoreCategories) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filter { it != null }
            .map { it!! }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                val buffer = 5
                val totalItems = gridState.layoutInfo.totalItemsCount

                if (lastVisibleIndex >= totalItems - buffer &&
                    !uiState.isCategoriesLoadingNextPage &&
                    uiState.canLoadMoreCategories
                ) {
                    onEvent(HomeUiEvent.LoadMoreCategories)
                }
            }
    }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    CategoriesGrid(
        categories = uiState.featuredCategories,
        gridState = gridState,
        onCategoryClick = { }
    )
}