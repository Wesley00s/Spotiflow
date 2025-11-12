package com.example.spotiflow.ui.screen.sign_in

sealed interface SignInUiEvent {
    data object PerformSignIn : SignInUiEvent
}