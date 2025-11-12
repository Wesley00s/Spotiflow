package com.example.spotiflow.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spotiflow.R
import com.example.spotiflow.ui.component.FeaturedCategoriesTab
import com.example.spotiflow.ui.component.HomeTabLayout
import com.example.spotiflow.ui.component.NewReleasesTab
import com.example.spotiflow.ui.component.RecentlyTracksTab
import com.example.spotiflow.ui.component.UserProfileIcon
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tabs = listOf("Categories", "Recents", "Releases")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        tabs = tabs,
        pagerState = pagerState,
        coroutineScope = coroutineScope,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    tabs: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    val pages = listOf<@Composable () -> Unit>(
        { FeaturedCategoriesTab(uiState = uiState, onEvent = onEvent) },
        { RecentlyTracksTab(uiState = uiState, onEvent = onEvent) },
        { NewReleasesTab(uiState = uiState, onEvent = onEvent) }
    )
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = { /* TODO */ },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Pesquisar",
                )
            }
            Image(
                modifier = Modifier
                    .width(120.dp)
                    .padding(vertical = 15.dp),
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "App Logo",
            )

            UserProfileIcon(
                modifier = Modifier,
                prifileImage = uiState.userProfile?.imageUrl,
                onNavigateToProfile = { /* TODO */ }
            )
        }

        HomeTabLayout(
            modifier = Modifier.weight(1f),
            tabs = tabs,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            pages = pages,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(),
        onEvent = {},
        tabs = listOf(
            "Recents",
            "Top",
            "Liked",
            "Recommended tracks",
            "Following",
            "My Playlists",
            "Recommended playlists"
        ),
        pagerState = rememberPagerState { 4 },
        coroutineScope = rememberCoroutineScope()
    )
}