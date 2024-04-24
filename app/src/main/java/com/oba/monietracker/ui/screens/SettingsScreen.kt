package com.oba.monietracker.ui.screens

import android.content.Intent
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.oba.monietracker.Destination
import com.oba.monietracker.R
import com.oba.monietracker.ui.activities.SignInActivity
import com.oba.monietracker.ui.theme.Blue10
import com.oba.monietracker.ui.theme.Crimson

/**
 * The all settings screen.
 * @param navController The app navigation controller.
 */
@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val state = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(state)
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Blue10)
        ) {
            Text(
                text = "Settings",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Monie",
                Modifier
                    .padding(bottom = 30.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(6f, TextUnitType.Em),
                color = Color.Black,
            )
            Text(
                text = "Tracker",
                Modifier
                    .padding(bottom = 30.dp),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(6f, TextUnitType.Em),
                color = colorResource(R.color.green)
            )
        }

        Column (
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "MonieTracker is a mobile application for keeping track of income and " +
                        "expense transaction data recorded by the user, and provides insights.",
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        Column {
            Button(
                onClick = { navController.navigate(Destination.Account.route) },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)) {
                    Icon(Icons.Default.Person, contentDescription = null,
                        tint = colorResource(id = R.color.lime_green),
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Text(
                        text = Destination.Account.title,
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                    )
                }
            }

            Button(
                onClick = { navController.navigate(Destination.AddRecord.route)},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)) {
                    Icon(Icons.Default.Add, contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Text(
                        text = "Add record",
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                    )
                }
            }

            Button(
                onClick = { navController.navigate(Destination.Records.route)},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)) {
                    Icon(Icons.Default.Info, contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Text(
                        text = "View transaction records",
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                    )
                }
            }

            Button(
                onClick = { navController.navigate(Destination.AddCategory.route)},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)) {
                    Icon(Icons.Default.Add, contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Text(
                        text = "Add category",
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                    )
                }
            }

            Button(
                onClick = { navController.navigate(Destination.Categories.route)},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)) {
                    Icon(Icons.Default.Info, contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Text(
                        text = "View categories",
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                    )
                }
            }

            Button(
                onClick = {
                    val intent = Intent(context, SignInActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
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
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}