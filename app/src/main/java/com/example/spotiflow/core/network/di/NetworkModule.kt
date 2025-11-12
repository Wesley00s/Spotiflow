package com.example.spotiflow.core.network.di

import android.util.Log
import com.example.spotiflow.data.manager.TokenManager
import com.example.spotiflow.data.remote.dto.TokenResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    @NoAuthHttpClient 
    fun provideNoAuthHttpClient(json: Json): HttpClient {
        return HttpClient(Android) {
            install(Logging) { level = LogLevel.ALL }
            install(ContentNegotiation) { json(json) }
        }
    }

    @Provides
    @Singleton
    @AuthHttpClient 
    fun provideAuthHttpClient(
        json: Json,
        tokenManager: TokenManager,
        @NoAuthHttpClient refreshClient: HttpClient 
    ): HttpClient {
        return HttpClient(Android) {
            install(Logging) { level = LogLevel.ALL }
            install(ContentNegotiation) { json(json) }
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenManager.getAccessToken().firstOrNull()
                        val refreshToken = tokenManager.getRefreshToken().firstOrNull()
                        if (accessToken == null || refreshToken == null) null else BearerTokens(accessToken, refreshToken)
                    }

                    refreshTokens {
                        Log.d("KtorAuth", "Token expirado. A tentar atualizar...")
                        val refreshToken = tokenManager.getRefreshToken().firstOrNull()
                        if (refreshToken == null) {
                            tokenManager.clearTokens()
                            return@refreshTokens null
                        }

                        val tokenResponse: TokenResponse? = try {
                            
                            refreshClient.post("https://accounts.spotify.com/api/token") {
                                setBody(FormDataContent(Parameters.build {
                                    append("grant_type", "refresh_token")
                                    append("refresh_token", refreshToken)
                                    append("client_id", "b005715b55b94b8aa19860d921401f16") 
                                }))
                            }.body()
                        } catch (e: Exception) {
                            Log.e("KtorAuth", "Falha ao atualizar token", e)
                            tokenManager.clearTokens()
                            null
                        }

                        if (tokenResponse != null) {
                            tokenManager.saveAccessToken(tokenResponse.accessToken)
                            tokenResponse.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                            Log.d("KtorAuth", "Token atualizado com sucesso.")
                            BearerTokens(tokenResponse.accessToken, tokenResponse.refreshToken ?: refreshToken)
                        } else {
                            null
                        }
                    }

                    sendWithoutRequest { request ->
                        request.url.host == "api.spotify.com"
                    }
                }
            }
        }
    }
}