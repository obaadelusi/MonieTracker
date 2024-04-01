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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oba.monietracker.ui.components.BottomNavBar
import com.oba.monietracker.ui.screens.AddRecordScreen
import com.oba.monietracker.ui.screens.DashboardScreen
import com.oba.monietracker.ui.screens.InsightsScreen
import com.oba.monietracker.ui.screens.RecordsScreen
import com.oba.monietracker.ui.screens.SettingsScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme

sealed class Destination(val route: String, val title: String) {
    data object Dashboard: Destination("dash", "Dashboard")

    data object AddRecord: Destination("add-record", "Add Record")

    data object Records: Destination("records", "Records")

    data object Insights: Destination("insights", "Insights")

    data object Settings: Destination("settings", "Settings")
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
            if(currentRoute == Destination.AddRecord.route) {
                FloatingActionButton(
                    onClick = {
                        /* TODO: Get receipt image from gallery */
                    },
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp, 10.dp)) {
                            Icon(painterResource(R.drawable.ic_camera), contentDescription = "Add Image")
                            Text(text = "Image",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    })
            } else if (currentRoute == Destination.Settings.route){
                null
            } else {
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(Destination.AddRecord.route)
                    },
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
        }
    ){
            paddingValues ->
        NavHost(navController = navHostController,
            startDestination = Destination.Records.route,
            modifier = Modifier.padding(paddingValues))
        {
            composable(Destination.Dashboard.route){
                DashboardScreen(navController = navHostController)
            }

            composable(Destination.AddRecord.route){
                AddRecordScreen(navController = navHostController)
            }

            composable(Destination.Records.route){
                RecordsScreen(navController = navHostController)
            }

            composable(Destination.Insights.route){
                InsightsScreen(navController = navHostController)
            }

            composable(Destination.Settings.route){
                SettingsScreen(navController = navHostController)
            }
        }
    }
}