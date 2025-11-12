package com.example.spotiflow.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import com.example.spotiflow.data.manager.TokenManager
import com.example.spotiflow.data.remote.dto.TokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri
import com.example.spotiflow.core.network.di.NoAuthHttpClient

@Singleton
class AuthRepository @Inject constructor(
    @param:NoAuthHttpClient private val httpClient: HttpClient,
    private val tokenManager: TokenManager,
    @param:ApplicationContext private val context: Context
) {
    private val CLIENT_ID = "b005715b55b94b8aa19860d921401f16"
    private val REDIRECT_URI = "spotiflow://auth-callback"
    private val TOKEN_URL = "https://accounts.spotify.com/api/token"

    suspend fun exchangeCodeForToken(code: String, codeVerifier: String) {
        try {
            val response: TokenResponse = httpClient.post(TOKEN_URL) {
                setBody(FormDataContent(Parameters.build {
                    append("grant_type", "authorization_code")
                    append("code", code)
                    append("redirect_uri", REDIRECT_URI)
                    append("client_id", CLIENT_ID)
                    append("code_verifier", codeVerifier)
                }))
            }.body()

            tokenManager.saveAccessToken(response.accessToken)

            response.refreshToken?.let { newRefreshToken ->
                tokenManager.saveRefreshToken(newRefreshToken)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Falha ao trocar o token: ${e.message}")
            throw e
        }
    }

    suspend fun launchAuthFlow() {
        val (verifier, challenge) = generatePkceChallenge()
        Log.d("AuthRepository", "CODE_VERIFIER: $verifier")
        tokenManager.saveCodeVerifier(verifier)

        val authUrl = "https://accounts.spotify.com/authorize".toUri()
            .buildUpon()
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", "user-read-private user-top-read playlist-read-private user-library-read user-follow-read user-read-recently-played")
            .appendQueryParameter("code_challenge", challenge)
            .appendQueryParameter("code_challenge_method", "S256")
            .appendQueryParameter("show_dialog", "true")
            .build()

        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        customTabsIntent.launchUrl(context, authUrl)
    }
    private fun generatePkceChallenge(): Pair<String, String> {
        val verifier = generateCodeVerifier()
        val challenge = generateCodeChallenge(verifier)
        return Pair(verifier, challenge)
    }

    private fun generateCodeVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(64)
        sr.nextBytes(code)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code)
    }

    private fun generateCodeChallenge(verifier: String): String {
        val bytes = verifier.toByteArray(Charsets.US_ASCII)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }
}