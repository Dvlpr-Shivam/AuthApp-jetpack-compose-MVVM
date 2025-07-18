package com.example.simpleloginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simpleloginapp.Screens.SplashScreen
import com.example.simpleloginapp.Screens.Auth.LoginScreen
import com.example.simpleloginapp.Screens.Auth.OTPScreen
import com.example.simpleloginapp.Screens.Auth.SignupScreen
import com.example.simpleloginapp.ui.theme.SimpleLoginAppTheme
import com.example.simpleloginapp.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleLoginAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("signup") {
            SignupScreen { navController.navigate("login") }
        }
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("dashboard") },
                redirectSignUp = { navController.navigate("signup") },
                onOtpRequested = { contact -> navController.navigate("otp/$contact") }
            )

        }
        composable(
            route = "otp/{contact}",
            arguments = listOf(navArgument("contact") { type = NavType.StringType })
        ) { backStackEntry ->
            val contact = backStackEntry.arguments?.getString("contact") ?: ""
            OTPScreen(contact = contact) { navController.navigate("dashboard") }
        }
        composable("dashboard") {
            val context = LocalContext.current
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
               Text("Welcome to Dashboard Click this to Clear Session", modifier = Modifier.clickable { SessionManager.clearSession(context)
                   navController.navigate("login")
               })
            }
        }
    }
}
