package com.example.spotiflow.ui.screen.auth_callback

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotiflow.data.manager.TokenManager
import com.example.spotiflow.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthCallbackViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthCallbackUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AuthCallbackUiEvent) {
        when (event) {
            is AuthCallbackUiEvent.ExchangeCodeForToken -> exchangeCode(event.code)
        }
    }

    private fun exchangeCode(code: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            Log.d("AuthCallbackVM", "Tentando trocar o token")

            try {
                val verifier = tokenManager.getCodeVerifier().firstOrNull()
                Log.d("AuthCallbackVM", "Token trocado com sucesso")
                requireNotNull(verifier) { "Code Verifier não encontrado. O fluxo de login foi quebrado." }
                authRepository.exchangeCodeForToken(code, verifier)
                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                Log.e("AuthCallbackVM", "Falha ao trocar o token", e)
                _uiState.update { it.copy(isLoading = false, error = "Falha na autenticação: ${e.message}") }
            }
        }
    }
}