package com.oba.monietracker.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.oba.monietracker.auth.SignUpScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme

/**
 * The sign up activity page.
 */
class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonieTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                ) {
                    //val navController = rememberNavController()

                    val context = applicationContext
                    SignUpScreen(context = context)

                }
            }
        }
    }
}
