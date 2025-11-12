package com.example.spotiflow.ui.screen.sign_in

data class SignInUiState (
    val isSignInLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null
)