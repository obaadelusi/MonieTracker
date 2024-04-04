package com.oba.monietracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oba.monietracker.ui.components.BottomNavBar
import com.oba.monietracker.ui.screens.AccountScreen
import com.oba.monietracker.ui.screens.AddCategoryScreen
import com.oba.monietracker.ui.screens.AddRecordScreen
import com.oba.monietracker.ui.screens.InsightsScreen
import com.oba.monietracker.ui.screens.RecordsScreen
import com.oba.monietracker.ui.screens.SettingsScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme

sealed class Destination(val route: String, val title: String) {
    data object Records: Destination("records", "Records")

    data object AddRecord: Destination("add-record", "Add record")

    data object Categories: Destination("categories", "Categories")

    data object AddCategory: Destination("add-category", "Add new category")

    data object Insights: Destination("insights", "Insights")

    data object Settings: Destination("settings", "Settings")

    data object Account: Destination("account", "My Account")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonieTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // initialize cloud fireStore
                    // val fs_db = Firebase.firestore

                    val navController = rememberNavController()
                    val context = LocalContext.current

                    MainScaffold(
                        navController,
                        context
                        //, fs_db
                    )
                }
            }
        }
    }
}

@Composable
fun MainScaffold(
    navHostController: NavHostController,
    context: Context
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navHostController)
        },
        floatingActionButton = {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            when (currentRoute) {
                Destination.AddRecord.route -> {
                    FloatingActionButton(
                        onClick = {
                            /* TODO: Get receipt image from gallery */
                        },
                        containerColor = colorResource(R.color.black),
                        contentColor = colorResource(R.color.white),
                        content = {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp, 10.dp)) {
                                Icon(painterResource(R.drawable.ic_camera),
                                    contentDescription = "Add Image")
                                Text(text = "Upload Receipt",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        })
                }
                Destination.Records.route, Destination.Insights.route -> {
                    FloatingActionButton(
                        onClick = {
                            navHostController.navigate(Destination.AddRecord.route)
                        },
                        containerColor = colorResource(R.color.orange_red),
                        contentColor = colorResource(R.color.white),
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp, 10.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Record")
                                Text(
                                    text = "Add Record",
                                )
                            }
                        })

                }
                else -> { null }
            }
        }
    ){
            paddingValues ->
        NavHost(navController = navHostController,
            startDestination = Destination.Records.route,
            modifier = Modifier.padding(paddingValues))
        {
            composable(Destination.Records.route){
                RecordsScreen(navController = navHostController)
            }

            composable(Destination.AddRecord.route){
                AddRecordScreen(navController = navHostController)
            }

            composable(Destination.AddCategory.route){
                AddCategoryScreen(navController = navHostController)
            }

            composable(Destination.Insights.route){
                InsightsScreen(navController = navHostController)
            }

            composable(Destination.Settings.route){
                SettingsScreen(navController = navHostController)
            }

            composable(Destination.Account.route){
                AccountScreen(navController = navHostController)
            }
        }
    }
}