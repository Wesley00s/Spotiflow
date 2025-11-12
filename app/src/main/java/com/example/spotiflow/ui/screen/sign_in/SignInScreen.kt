package com.example.spotiflow.ui.screen.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spotiflow.R
import com.example.spotiflow.ui.component.CustomButton
import com.example.spotiflow.ui.theme.SpotiflowTheme


@Composable
fun SignInScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SignInScreen(
        modifier = modifier,
        uiState = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    uiState: SignInUiState,
    onEvent: (SignInUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Spotify Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to Spotiflow",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomButton(
                modifier = Modifier,
                color = MaterialTheme.colorScheme.surface,
                text = "Sign In With Spotify",
                icon = R.drawable.ic_spotify,
                onClick = { onEvent(SignInUiEvent.PerformSignIn) },
                enabled = !uiState.isLoading
            )

            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SpotiflowTheme {
        SignInScreen(
            uiState = SignInUiState(),
            onEvent = {}
        )
    }
}