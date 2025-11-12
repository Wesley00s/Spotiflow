package com.example.spotiflow.ui.screen.auth_callback

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AuthCallbackRoute(
    code: String?,
    onAuthSuccess: () -> Unit,
    onAuthError: () -> Unit,
    viewModel: AuthCallbackViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(key1 = code) {
        if (code != null) {
            viewModel.onEvent(AuthCallbackUiEvent.ExchangeCodeForToken(code))
            Log.d("AuthCallbackRoute", "Code: $code")
        } else {
            onAuthError()
        }
    }

    LaunchedEffect(key1 = uiState) {
        if (!uiState.isLoading) { 
            if (uiState.error == null) {
                onAuthSuccess()
            } else {
                
                onAuthError()
            }
        }
    }

    AuthCallbackScreen(
        isLoading = uiState.isLoading,
        error = uiState.error
    )
}

@Composable
private fun AuthCallbackScreen(
    isLoading: Boolean,
    error: String?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Erro na Autenticação",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}