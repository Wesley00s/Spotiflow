package com.example.spotiflow.data.repository

import android.util.Log
import com.example.spotiflow.core.network.di.AuthHttpClient
import com.example.spotiflow.data.manager.TokenManager
import com.example.spotiflow.data.remote.dto.CategoriesResponse
import com.example.spotiflow.data.remote.dto.FeaturedPlaylistsResponse
import com.example.spotiflow.data.remote.dto.FollowedArtistsResponse
import com.example.spotiflow.data.remote.dto.PlaylistsResponse
import com.example.spotiflow.data.remote.dto.RecentlyPlayedResponse
import com.example.spotiflow.data.remote.dto.RecommendationsResponse
import com.example.spotiflow.data.remote.dto.RelatedArtistsResponse
import com.example.spotiflow.data.remote.dto.SavedTracksResponse
import com.example.spotiflow.data.remote.dto.SearchResponse
import com.example.spotiflow.data.remote.dto.TopArtistsResponse
import com.example.spotiflow.data.remote.dto.TopTracksResponse
import com.example.spotiflow.data.remote.dto.UserProfile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    @param:AuthHttpClient private val httpClient: HttpClient
) {
    private val BASE_URL = "https://api.spotify.com/v1"

    suspend fun getUserProfile(): UserProfile {
        return httpClient.get("$BASE_URL/me").body<UserProfile>()
    }

    suspend fun getTopArtists(timeRange: String): TopArtistsResponse {
        return httpClient.get("$BASE_URL/me/top/artists") {
            parameter("time_range", timeRange)
            parameter("limit", 20)
        }.body<TopArtistsResponse>()
    }

    suspend fun getTopTracks(timeRange: String): TopTracksResponse {
        return httpClient.get("$BASE_URL/me/top/tracks") {
            parameter("time_range", timeRange)
            parameter("limit", 20)
        }.body<TopTracksResponse>()
    }

    suspend fun getSavedTracks(): SavedTracksResponse {
        return httpClient.get("$BASE_URL/me/tracks") {
            parameter("limit", 20)
        }.body<SavedTracksResponse>()
    }

    suspend fun getFollowedArtists(): FollowedArtistsResponse {
        return httpClient.get("$BASE_URL/me/following") {
            parameter("type", "artist")
            parameter("limit", 20)
        }.body<FollowedArtistsResponse>()
    }

    suspend fun getUserPlaylists(): PlaylistsResponse {
        return httpClient.get("$BASE_URL/me/playlists") {
            parameter("limit", 20)
        }.body<PlaylistsResponse>()
    }

    suspend fun getRecentlyPlayed(): RecentlyPlayedResponse {
        return httpClient.get("$BASE_URL/me/player/recently-played") {
            parameter("limit", 20)
        }.body<RecentlyPlayedResponse>()
    }

    suspend fun getRecommendations(seedTrack: String, seedArtist: String, seedGenre: String): RecommendationsResponse {
        return httpClient.get("$BASE_URL/recommendations") {
            parameter("seed_tracks", seedTrack)
            parameter("seed_artists", seedArtist)
            parameter("seed_genres", seedGenre)
            parameter("limit", 20)
        }.body<RecommendationsResponse>()
    }

    suspend fun getRelatedArtists(artistId: String): RelatedArtistsResponse {
        return httpClient.get("$BASE_URL/artists/$artistId/related-artists")
        .body<RelatedArtistsResponse>()
    }

    suspend fun getFeaturedPlaylists(): FeaturedPlaylistsResponse {
        return httpClient.get("$BASE_URL/browse/featured-playlists") {
            parameter("limit", 20)
        }.body<FeaturedPlaylistsResponse>()
    }

    suspend fun search(query: String, type: String): SearchResponse {
        return httpClient.get("$BASE_URL/search") {
            parameter("q", query)
            parameter("type", type)
            parameter("limit", 20)
        }.body<SearchResponse>()
    }

    suspend fun getBrowseCategories(limit: Int, offset: Int): CategoriesResponse {
        return httpClient.get("$BASE_URL/browse/categories") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body<CategoriesResponse>()
    }
}