package com.example.spotiflow.ui.screen.sign_in

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotiflow.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignInUiEvent) {
        viewModelScope.launch {
            when (event) {
                is SignInUiEvent.PerformSignIn -> performSignIn()
            }
        }
    }

    private suspend fun performSignIn() {
        Log.d("SignInViewModel", "Iniciando fluxo de autenticação...")

        _uiState.update { it.copy(isLoading = true) }

        try {
            authRepository.launchAuthFlow()

        } catch (e: Exception) {
            Log.e("SignInViewModel", "Falha ao lançar Auth Flow", e)
            _uiState.update {
                it.copy(isLoading = false, error = "Falha ao iniciar login.")
            }
        }

        _uiState.update { it.copy(isLoading = false) }
    }
}