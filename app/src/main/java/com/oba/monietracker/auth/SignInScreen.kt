package com.oba.monietracker.auth


// import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.oba.monietracker.MainActivity
import com.oba.monietracker.R
import com.oba.monietracker.ui.activities.SignUpActivity

/**
 * The sign in screen.
 * @param context The application context.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    context: Context,
    canAuthenticate: () -> Boolean,
    authenticateUser: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String>("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Monie",
                modifier = Modifier.padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(8f, TextUnitType.Em),
                color = Color.Black,
            )
            Text(
                text = "Tracker",
                modifier = Modifier.padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(8f, TextUnitType.Em),
                color = colorResource(R.color.green)
            )
        }

        Text(text = "Sign In",
            Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge)

        // if mobile supports biometric sign in
        if(canAuthenticate()) {
            Button(onClick = {
                authenticateUser()
            }) {
                Text(text = "BIOMETRIC SIGN IN", Modifier.padding(end = 12.dp))
                Icon(painterResource(R.drawable.ic_baseline_fingerprint), null)
            }
            Text(text = "-- OR --", Modifier.padding(vertical = 12.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done)
        )

        if (error.isNotBlank()) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        OutlinedButton(
            onClick = {
                // validate inputs
                if (email.isBlank()) {
                    error = "Please enter an email"
                } else if (password.isBlank()) {
                    error = "Please enter your password"
                } else {
                    // Perform Firebase authentication
                    performSignIn(email, password, context, keyboardController)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "SIGN IN")
        }

        Row {
            Text(text = "Don't have an account?",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            ClickableText(text = AnnotatedString("Create account"),
                maxLines = 1,
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                val intent = Intent(context, SignUpActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }
}

/**
 * Implements the FirebaseAuth sign in process.
 * @param email The email of the user.
 * @param password The password of the user.
 * @param context The application context.
 * @param keyboardController The keyboard control options.
 */
@OptIn(ExperimentalComposeUiApi::class)
private fun performSignIn(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController?
) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, navigate to the next screen or perform desired action
                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("userEmail", FirebaseAuth.getInstance().currentUser?.email)
                context.startActivity(intent)

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }

            //loading = false
            keyboardController?.hide()
        }
}

