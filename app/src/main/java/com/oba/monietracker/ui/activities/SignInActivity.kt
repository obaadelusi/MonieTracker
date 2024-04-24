package com.oba.monietracker.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.oba.monietracker.MainActivity
import com.oba.monietracker.auth.SignInScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme
import java.util.concurrent.Executor


/**
 * The sign in activity page.
 */
@SuppressLint("RestrictedApi")
class SignInActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var promptInfo: PromptInfo
    private lateinit var biometricPrompt : BiometricPrompt

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonieTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                ) {
                    val context = applicationContext
                    val biometricManager = BiometricManager.from(this)

                    SignInScreen(context = context,
                        canAuthenticate = {
                            when (biometricManager.canAuthenticate(
                                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                )) {
                                BiometricManager.BIOMETRIC_SUCCESS -> true
                                else -> false
                            }
                        }, authenticateUser = {
                            authenticateUser(context)
                        }
                    )
                }
            }
        }
    }


    /**
     * Authenticates user with biometric information.
     * @param context The application context.
     */
    private fun authenticateUser(
        context: Context
    ) {
        executor = ContextCompat.getMainExecutor(this)
//        executor = context.mainExecutor

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int,
                                               errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext,
                    "Authentication error: $errString", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext,
                    "Authentication succeeded!", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }

        biometricPrompt = BiometricPrompt(this, executor, callback)

        promptInfo = PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use email instead")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
