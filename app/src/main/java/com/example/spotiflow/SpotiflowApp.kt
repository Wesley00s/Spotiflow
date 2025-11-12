package com.example.spotiflow

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.spotiflow.ui.navigation.RootDestinations
import com.example.spotiflow.ui.screen.MainAppScreen
import com.example.spotiflow.ui.screen.auth_callback.AuthCallbackRoute
import com.example.spotiflow.ui.screen.sign_in.SignInScreenRoute
import com.example.spotiflow.ui.screen.splash.SplashRoute


@Composable
fun SpotiflowApp(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RootDestinations.SPLASH,
    ) {
        composable(RootDestinations.SPLASH) {
            SplashRoute(
                onNavigateToHome = {
                    navController.navigate(RootDestinations.MAIN_APP) {
                        popUpTo(RootDestinations.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(RootDestinations.SIGN_IN) {
                        popUpTo(RootDestinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(RootDestinations.MAIN_APP) {
            MainAppScreen()
        }
        composable(RootDestinations.SIGN_IN) {
            SignInScreenRoute()
        }
        composable(
            route = "auth_callback",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "spotiflow://auth-callback/?code={code}"
                    action = Intent.ACTION_VIEW
                }
            )
        ) { navBackStackEntry ->
            val code = navBackStackEntry.arguments?.getString("code")
            Log.d("AuthCallbackRoute", "Code: $code")

            AuthCallbackRoute(
                code = code,
                onAuthSuccess = {
                    navController.navigate(RootDestinations.MAIN_APP) {
                        popUpTo(RootDestinations.SPLASH) { inclusive = true }
                    }
                },
                onAuthError = {
                    navController.popBackStack()
                }
            )
        }
    }
}