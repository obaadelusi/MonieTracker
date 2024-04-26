package com.oba.monietracker.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth

    private var canAuthenticateWithBiometric by mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user is signed in (non-null) and update UI accordingly.
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // create shared preferences
            sharedPreferences = getSharedPreferences("MonieTracker", MODE_PRIVATE)

            // Write to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("userId", currentUser.uid)
            editor.apply()
        }

        setContent {
            MonieTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SignInScreen(context = applicationContext,
                        canAuthenticate = { canAuthenticateWithBiometric }, authenticateUser = {
                            authenticateUserWithBiometric(applicationContext)
                        }
                    )
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        val currentUid = currentUser?.uid?: ""

        sharedPreferences = getSharedPreferences("MonieTracker", MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")!!
        val displayName = sharedPreferences.getString("displayName", "")

        Log.d(">onStart", "Shared Pref UserId: $displayName: $userId")
        Log.d(">onStart", "Firebase uid: $currentUid")

        if (currentUser != null && userId.isNotBlank()) {
            if(currentUser.uid == userId){
                val biometricManager = BiometricManager.from(this)

                canAuthenticateWithBiometric =
                    when (biometricManager.canAuthenticate(
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                    ) {
                        BIOMETRIC_SUCCESS -> true
                        else -> false
                    }
            }
        }
    }

    /**
     * Authenticates user with biometric information.
     * @param context The application context.
     */
    private fun authenticateUserWithBiometric(context: Context) {
        executor = ContextCompat.getMainExecutor(this)

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
                // TODO: perform sign in with data from shared preferences

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
