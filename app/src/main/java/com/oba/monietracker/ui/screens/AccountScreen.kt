package com.oba.monietracker.ui.screens

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.oba.monietracker.Destination
import com.oba.monietracker.R
import com.oba.monietracker.ui.activities.SignInActivity
import com.oba.monietracker.ui.theme.Blue10
import com.oba.monietracker.ui.theme.Crimson

/**
 * The user account or profile detail screen.
 * @param navController The app navigation controller.
 */
@Composable
fun AccountScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    lateinit var sharedPreferences: SharedPreferences

    // User object will be gotten from Firebase Authentication
    val user = FirebaseAuth.getInstance().currentUser

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Blue10)
        ) {
            Text(
                text = Destination.Account.title,
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp)
            )
        }

        Text(
            text = "Hello ${user?.displayName?: user?.email?.split("@")?.first()},",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 8.dp)
        )

        Log.d(">AccountScreen", "Email: ${user?.email}")
        Log.d(">AccountScreen", "Display name: ${user?.displayName}")

        Card (
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = Blue10,
                contentColor = colorResource(R.color.black)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 2.dp)
        ) {
            Text(
                text = "Email address: ${user?.email?: "Not logged in"}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = "Verified: ${user?.isEmailVerified }",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(60.dp).weight(2f))

        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(context, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)) {
                Icon(
                    Icons.Default.ExitToApp, contentDescription = null,
                    tint = Crimson,
                    modifier = Modifier.align(Alignment.CenterVertically))
                Text(
                    text = "SIGN OUT",
                    color = Crimson,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}