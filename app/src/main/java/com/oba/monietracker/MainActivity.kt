package com.oba.monietracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.oba.monietracker.data.db.AppDataManager
import com.oba.monietracker.data.db.AppDatabase
import com.oba.monietracker.ui.components.BottomNavBar
import com.oba.monietracker.ui.screens.AccountScreen
import com.oba.monietracker.ui.screens.AddCategoryScreen
import com.oba.monietracker.ui.screens.AddRecordScreen
import com.oba.monietracker.ui.screens.CategoriesScreen
import com.oba.monietracker.ui.screens.InsightsScreen
import com.oba.monietracker.ui.screens.RecordsScreen
import com.oba.monietracker.ui.screens.SettingsScreen
import com.oba.monietracker.ui.theme.MonieTrackerTheme

sealed class Destination(val route: String, val title: String) {
    data object Records: Destination("records", "Records")
    data object ShowRecord: Destination("record", "A Record")
    data object AddRecord: Destination("add-record", "Add record")
    data object Categories: Destination("categories", "Categories")
    data object AddCategory: Destination("add-category", "Add new category")
    data object Insights: Destination("insights", "Insights")
    data object Settings: Destination("settings", "Settings")
    data object Account: Destination("account", "My Account")
}

/***
 * The app main activity.
 */
class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth

    @SuppressLint("CoroutineCreationDuringComposition")
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
                    auth = Firebase.auth
                    val currentUser = auth.currentUser

                    val navController = rememberNavController()
                    val context = LocalContext.current

                    val database = AppDatabase.getInstance(context)
                    val appDataManager = AppDataManager(database)

                    MainScaffold(
                        context,
                        navController,
                        appDataManager,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    context: Context,
    navHostController: NavHostController,
    appDataManager: AppDataManager,
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navHostController)
        },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                    modifier = Modifier
                        .padding(top = 40.dp)
                ) {
                    Text(
                        text = "Monie",
                        Modifier
                            .padding(bottom = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 5.em,
                        color = Color.Black,
                    )
                    Text(
                        text = "Tracker",
                        Modifier
                            .padding(bottom = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 5.em,
                        color = colorResource(R.color.green)
                    )
                } },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_money),
                        contentDescription = "logo")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.green_100)
                ),
                actions = {
                    IconButton(
                        onClick = { navHostController.navigate(Destination.Account.route) }
                    ) {
                        Icon(Icons.Default.AccountCircle, "user account", tint = Color.Black)
                    }
                }
            )
        },
        floatingActionButton = {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            when (currentRoute) {
                Destination.AddRecord.route -> {
                    FloatingActionButton(
                        onClick = {
                            /* TODO: Get receipt image from gallery */
                                  Toast
                                      .makeText(context,
                                          "Receipt feature coming soon...",
                                          Toast.LENGTH_LONG)
                                      .show()
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
                RecordsScreen(navHostController, appDataManager)
            }

            composable(Destination.ShowRecord.route){
//                ShowRecordScreen(navHostController)
            }

            composable(Destination.AddRecord.route){
                AddRecordScreen(navHostController, appDataManager)
            }

            composable(Destination.AddCategory.route){
                AddCategoryScreen(navHostController, appDataManager)
            }

            composable(Destination.Categories.route){
                CategoriesScreen(navHostController, appDataManager)
            }

            composable(Destination.Insights.route){
                InsightsScreen(navHostController, appDataManager)
            }

            composable(Destination.Settings.route){
                SettingsScreen(navHostController)
            }

            composable(Destination.Account.route){
                AccountScreen(navHostController)
            }
        }
    }
}