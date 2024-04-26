package com.oba.monietracker.ui.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.oba.monietracker.MainActivity
import com.oba.monietracker.auth.SignUpScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme

/**
 * The sign up activity page.
 */
class SignUpActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonieTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val keyboardController = LocalSoftwareKeyboardController.current

                    SignUpScreen(
                        context = applicationContext,
                        performSignUp = { name, email, pass ->
                            performSignUp(name, email, pass, keyboardController)
                        } )
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.email)
            applicationContext.startActivity(intent)
        } else {
            return
        }
    }

    /**
     * Implements the FirebaseAuth sign up process.
     * @param name = The name of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param context The application context.
     * @param keyboardController The keyboard control options.
     */
    @OptIn(ExperimentalComposeUiApi::class)
    private fun performSignUp(
        name: String,
        email: String,
        password: String,
        keyboardController: SoftwareKeyboardController?
    ) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        baseContext,
                        "Successful. You are now signed in.",
                        Toast.LENGTH_LONG,
                    ).show()

                    val user = auth.currentUser
                    // Update user profile with username
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Profile update successful
                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                // You can navigate to another activity or perform any other action here
                            } else {
                                // Profile update failed
                                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                            }
                        }

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.email)
                    applicationContext.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Account creation failed!",
                        Toast.LENGTH_LONG,
                    ).show()
                }
                keyboardController?.hide()
            }
    }
}
