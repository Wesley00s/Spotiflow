package com.example.spotiflow.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotiflow.data.model.UiAlbum
import com.example.spotiflow.data.model.UiArtist
import com.example.spotiflow.data.model.UiCategory
import com.example.spotiflow.data.model.UiPlaylist
import com.example.spotiflow.data.model.UiTrack
import com.example.spotiflow.data.model.UiUserProfile
import com.example.spotiflow.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(HomeUiEvent.LoadData)
    }

    companion object {
        private const val CATEGORY_PAGE_SIZE = 20
    }

    fun onEvent(event: HomeUiEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeUiEvent.LoadData -> loadInitialData()
                is HomeUiEvent.Refresh -> loadInitialData(isRefresh = true)
                is HomeUiEvent.LoadLikedTracks -> loadSavedTracks()
                is HomeUiEvent.LoadFollowingArtists -> loadFollowedArtists()
                is HomeUiEvent.LoadMyPlayList -> loadUserPlaylists()
                is HomeUiEvent.LoadPlayedTracks -> loadRecentlyPlayed()
                is HomeUiEvent.LoadTopPlayedTracks -> loadTopTracks(uiState.value.selectedTimeRange)
                is HomeUiEvent.LoadRecommendedTracks -> loadRecommendedTracks()
                is HomeUiEvent.LoadRecommendedPlayList -> loadFeaturedPlaylists()
                is HomeUiEvent.LoadRelatedArtists -> loadRelatedArtists()

                is HomeUiEvent.ChangeTimeRange -> {
                    _uiState.update { it.copy(selectedTimeRange = event.timeRange) }
                    loadTopTracks(event.timeRange)
                }
                is HomeUiEvent.LoadFeaturedCategories -> loadFeaturedCategories(isRefreshing = true)
                is HomeUiEvent.LoadMoreCategories -> loadFeaturedCategories(isPaginating = true)

                is HomeUiEvent.PlayTrack -> {
                    Log.d("HomeViewModel", "Tocar música: ${event.trackUri}")
                    
                }

                HomeUiEvent.LoadReleases -> loadReleases()
            }
        }
    }

    private suspend fun loadInitialData(isRefresh: Boolean = false) {
        _uiState.update { it.copy(isLoading = !isRefresh, isRefreshing = isRefresh, error = null) }

        try {
            val profileDto = profileRepository.getUserProfile()
            val playedDto = profileRepository.getRecentlyPlayed()

            val profileUi = UiUserProfile(
                displayName = profileDto.displayName,
                imageUrl = profileDto.images.firstOrNull()?.url
            )
            val tracksUi = playedDto.items.map { playHistory ->
                UiTrack(
                    id = playHistory.track.id,
                    uri = playHistory.track.uri,
                    name = playHistory.track.name,
                    artistName = playHistory.track.artists.firstOrNull()?.name ?: "",
                    imageUrl = playHistory.track.album.images.firstOrNull()?.url
                )
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    userProfile = profileUi,
                    recentlyPlayedTracks = tracksUi
                )
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar dados iniciais", e)
            _uiState.update { it.copy(isLoading = false, isRefreshing = false, error = "Falha ao carregar dados.") }
        }
    }

    private suspend fun loadSavedTracks() {
        try {
            val savedTracksDto = profileRepository.getSavedTracks()
            val tracksUi = savedTracksDto.items.map { savedTrack ->
                UiTrack(
                    id = savedTrack.track.id,
                    uri = savedTrack.track.uri,
                    name = savedTrack.track.name,
                    artistName = savedTrack.track.artists.firstOrNull()?.name ?: "",
                    imageUrl = savedTrack.track.album.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(savedTracks = tracksUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Músicas Curtidas", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Músicas Curtidas")
            }
        }
    }

    private suspend fun loadFollowedArtists() {
        try {
            val artistsDto = profileRepository.getFollowedArtists()
            val artistsUi = artistsDto.artists.items.map { artist ->
                UiArtist(
                    id = artist.id,
                    name = artist.name,
                    imageUrl = artist.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(followedArtists = artistsUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Artistas Seguidos", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Artistas Seguidos")
            }
        }
    }

    private suspend fun loadTopTracks(timeRange: TopTimeRange) {
        try {
            val topTracksDto = profileRepository.getTopTracks(timeRange.apiValue)
            val tracksUi = topTracksDto.items.map { track ->
                UiTrack(
                    id = track.id,
                    uri = track.uri,
                    name = track.name,
                    artistName = track.artists.firstOrNull()?.name ?: "",
                    imageUrl = track.album.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(topTracks = tracksUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Top Músicas", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Top Músicas")
            }
        }
    }

    private suspend fun loadUserPlaylists() {
        try {
            val playlistsDto = profileRepository.getUserPlaylists()
            val playlistsUi = playlistsDto.items.map { playlist ->
                UiPlaylist(
                    id = playlist.id,
                    name = playlist.name,
                    imageUrl = playlist.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(userPlaylists = playlistsUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Playlists do Usuário", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Playlists do Usuário")
            }
        }
    }

    private suspend fun loadRecentlyPlayed() {
        try {
            val playedDto = profileRepository.getRecentlyPlayed()
            val tracksUi = playedDto.items.map { playHistory ->
                UiTrack(
                    id = playHistory.track.id,
                    uri = playHistory.track.uri,
                    name = playHistory.track.name,
                    artistName = playHistory.track.artists.firstOrNull()?.name ?: "",
                    imageUrl = playHistory.track.album.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(recentlyPlayedTracks = tracksUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Ouvidas Recentemente", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Ouvidas Recentemente")
            }
        }
    }

    private suspend fun loadRecommendedTracks() {
        try {
            val seedTrack = "6rqhFgbbKwnb9MLmUQDhG6"
            val seedArstist = "4NHQUGzhtTLFvgF5SZesLK"
            val seedGender = "alternative"

            val recommendationsDto = profileRepository.getRecommendations(
                seedTrack = seedTrack,
                seedArtist = seedArstist,
                seedGenre = seedGender
            )
            val tracksUi = recommendationsDto.tracks.map { track ->
                UiTrack(
                    id = track.id,
                    uri = track.uri,
                    name = track.name,
                    artistName = track.artists.firstOrNull()?.name ?: "",
                    imageUrl = track.album.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(recommendedTracks = tracksUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Recomendações", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Recomendações")
            }
        }
    }
    private suspend fun loadFeaturedPlaylists() {
        try {
            val featuredDto = profileRepository.getFeaturedPlaylists()
            val playlistsUi = featuredDto.playlists.items.map { playlist ->
                UiPlaylist(
                    id = playlist.id,
                    name = playlist.name,
                    imageUrl = playlist.images.firstOrNull()?.url
                )
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(featuredPlaylists = playlistsUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Playlists Recomendadas", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Playlists Recomendadas")
            }
        }
    }

    private suspend fun loadRelatedArtists() {
        try {
            val firstArtistId = uiState.value.followedArtists.firstOrNull()?.id

            if (firstArtistId != null) {
                val relatedDto = profileRepository.getRelatedArtists(firstArtistId)
                val artistsUi = relatedDto.artists.map { artist ->
                    UiArtist(
                        id = artist.id,
                        name = artist.name,
                        imageUrl = artist.images.firstOrNull()?.url
                    )
                }
                _uiState.update { currentUiState ->
                    currentUiState.copy(relatedArtists = artistsUi)
                }
            } else {
                Log.w("HomeViewModel", "Utilizador não segue artistas, não é possível carregar artistas relacionados.")
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Artistas Relacionados", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Artistas Relacionados")
            }
        }
    }

    private suspend fun loadFeaturedCategories(
        isRefreshing: Boolean = false,
        isPaginating: Boolean = false
    ) {
        if (_uiState.value.isCategoriesLoadingNextPage) return
        if (isPaginating && !_uiState.value.canLoadMoreCategories) return

        val currentPage = if (isRefreshing) 0 else _uiState.value.categoriesCurrentPage

        _uiState.update {
            it.copy(
                isLoading = !isRefreshing && !isPaginating,
                isRefreshing = isRefreshing,
                isCategoriesLoadingNextPage = isPaginating
            )
        }

        try {
            val offset = currentPage * CATEGORY_PAGE_SIZE
            val categoriesDto = profileRepository.getBrowseCategories(
                limit = CATEGORY_PAGE_SIZE,
                offset = offset
            )

            val categoriesUi = categoriesDto.categories.items.map { category ->
                UiCategory(
                    id = category.id,
                    name = category.name,
                    imageUrl = category.icons.firstOrNull()?.url
                )
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    isCategoriesLoadingNextPage = false,
                    featuredCategories = if (isRefreshing) categoriesUi else currentUiState.featuredCategories + categoriesUi,
                    categoriesCurrentPage = currentPage + 1,
                    canLoadMoreCategories = categoriesUi.isNotEmpty()
                )
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar Categorias", e)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    isCategoriesLoadingNextPage = false,
                    error = "Falha ao carregar Categorias"
                )
            }
        }
    }

    private suspend fun loadReleases() {
        try {
            val releasesDto = profileRepository.getNewReleases()

            val releasesUi = releasesDto.albums.items.map { albumDto ->
                UiAlbum(
                    id = albumDto.id,
                    name = albumDto.name,
                    artistName = albumDto.artists.joinToString(", ") { it.name },
                    imageUrl = albumDto.images.firstOrNull()?.url
                )
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(newReleases = releasesUi)
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Falha ao carregar New Releases", e)
            _uiState.update { currentUiState ->
                currentUiState.copy(error = "Falha ao carregar Lançamentos")
            }
        }
    }

}