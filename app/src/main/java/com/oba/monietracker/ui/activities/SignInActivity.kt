package com.oba.monietracker.ui.activities

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.oba.monietracker.auth.SignInScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme
import java.util.concurrent.Executor

/**
 * The sign in activity page.
 */
class SignInActivity : ComponentActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonieTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                ) {
                    val context = applicationContext
                    val biometricManager = BiometricManager.from(context)

                    SignInScreen(context = context, canAuthenticate = {
                        when (biometricManager
                            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or
                                    BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                            BiometricManager.BIOMETRIC_SUCCESS -> true
                            else -> false
                        }
                    }, authenticateUser = {
                        authenticateUser(context)
                    })
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
//        executor = ContextCompat.getMainExecutor(this)

        executor = context.mainExecutor

        val callback = object : android.hardware.biometrics.BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int,
                                               errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext,
                    "Authentication error: $errString", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationSucceeded(
                result: android.hardware.biometrics.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext,
                    "Authentication succeeded!", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }


        val bioPrompt = android.hardware.biometrics.BiometricPrompt.Builder(context)
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButton("Use account password",
                executor) { _, _ ->
                    // Handle cancel action here
                    // For example: close the authentication dialog

                }
            .build()

        bioPrompt.authenticate(
            CancellationSignal(), executor, callback
        )


        //val frag = Fragment(R.layout.custom_dialog)
        //biometricPrompt = BiometricPrompt(frag,mainExecutor, callback)

//        promptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Biometric login for my app")
//            .setSubtitle("Log in using your biometric credential")
//            .setNegativeButtonText("Use account password")
//            .build()
//
//        biometricPrompt.authenticate(promptInfo)
    }
}
