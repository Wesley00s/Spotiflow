package com.example.spotiflow.ui.screen.auth_callback

sealed interface AuthCallbackUiEvent {
    data class ExchangeCodeForToken(val code: String) : AuthCallbackUiEvent
}