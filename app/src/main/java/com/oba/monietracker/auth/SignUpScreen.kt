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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.oba.monietracker.MainActivity
import com.oba.monietracker.R
import com.oba.monietracker.ui.activities.SignInActivity

/**
 * The sign up screen.
 * @param context The application context.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    context: Context
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
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
                Modifier.padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(10f, TextUnitType.Em),
                color = Color.Black,
            )
            Text(
                text = "Tracker",
                Modifier.padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(10f, TextUnitType.Em),
                color = colorResource(R.color.green)
            )
        }

        Text(text = "Create Your Account",
            Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Check if password and confirm password match
                    if (password == confirmPassword) {
                        // Passwords match, notify the caller
                        Toast.makeText(context, "Match!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Passwords don't match, show error (or handle as needed)
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        )

        if (error != null) {
            Text(
                text = error!!,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(
            onClick = {
                // Perform Firebase authentication
                performSignUp(email, password, context, keyboardController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "CREATE ACCOUNT",
                fontSize = 18.sp)
        }

        Row {
            Text(text = "Already have an account?",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            ClickableText(text = AnnotatedString("Sign In"),
                maxLines = 1,
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                val intent = Intent(context, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }
}

/**
 * Implements the FirebaseAuth sign up process.
 * @param email The email of the user.
 * @param password The password of the user.
 * @param context The application context.
 * @param keyboardController The keyboard control options.
 */
@OptIn(ExperimentalComposeUiApi::class)
private fun performSignUp(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController?
) {

    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)

   // val auth = FirebaseAuth.getInstance()

//    auth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Sign in success, navigate to the next screen or perform desired action
//                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.email)
//                context.startActivity(intent)
//
//            } else {
//                // If sign in fails, display a message to the user.
//                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
//            }
//
//            //loading = false
//            keyboardController?.hide()
//        }
}