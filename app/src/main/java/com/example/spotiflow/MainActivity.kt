package com.example.spotiflow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.spotiflow.ui.theme.SpotiflowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController // 2. Mude para lateinit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotiflowTheme {
                navController = rememberNavController() // 3. Inicialize aqui
                SpotiflowApp(navController = navController)
            }
        }

        // 4. LIDE COM O DEEP LINK NO ONCREATE (Para apps recriados)
        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 5. LIDE COM O DEEP LINK NO ONNEWINTENT (Para apps pausados)
        handleDeepLink(intent)
    }

    /**
     * Função centralizada para entregar o deep link ao NavHost.
     */
    private fun handleDeepLink(intent: Intent?) {
        if (intent != null && intent.action == Intent.ACTION_VIEW) {
            Log.d("MainActivity", "Recebendo Deep Link: ${intent.data}")
            // 6. Verifique se o navController já foi inicializado
            if (::navController.isInitialized) {
                navController.handleDeepLink(intent)
            } else {
                // Isso pode acontecer se o setContent ainda não rodou.
                // O NavHost em SpotiflowApp.kt vai pegar o intent da activity
                // quando ele for criado.
                Log.w("MainActivity", "NavController ainda não inicializado para handleDeepLink.")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
