package com.oba.monietracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oba.monietracker.Destination
import com.oba.monietracker.R

/**
 * The app-wide bottom navigation bar component.
 */
@Composable
fun BottomNavBar(navController: NavController){
    BottomAppBar(tonalElevation = 7.dp) {
        val navBackStackEntry = navController.currentBackStackEntry
        val currentDestination = navBackStackEntry?.destination //maybe comeback?

        val ic_bar_graph = painterResource(id = R.drawable.ic_bar_graph)

//        NavigationBarItem(
//            icon = {
//                Icon(Icons.Default.Home, contentDescription = "dashboard")
//            },
//            label = { Text(text = Destination.Dashboard.title) },
//            selected = currentDestination?.route == Destination.Dashboard.route,
//            onClick = {
//                navController.navigate(Destination.Dashboard.route){
//                    popUpTo(Destination.Dashboard.route) {inclusive= true}
//                    launchSingleTop = true
//                }
//            }
//
//        )

        NavigationBarItem(
            icon = {
                Icon(Icons.Default.List, contentDescription = "icon list")
            },
            label = { Text(text = Destination.Records.title) },
            selected = currentDestination?.route == Destination.Records.route,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = colorResource(R.color.dark_green),
                selectedIconColor = colorResource(R.color.white)
            ),
            onClick = {
                navController.navigate(Destination.Records.route){
                    popUpTo(Destination.Records.route)
                    launchSingleTop = true
                }
            },

            )

        NavigationBarItem(
            icon = {
                Icon(ic_bar_graph, contentDescription = "icon graph")
            },
            label = { Text(text = Destination.Insights.title) },
            selected = currentDestination?.route == Destination.Insights.route,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = colorResource(R.color.dark_green),
                selectedIconColor = colorResource(R.color.white)
            ),
            onClick = {
                navController.navigate(Destination.Insights.route){
                    popUpTo(Destination.Insights.route)
                    launchSingleTop = true
                }
            },

            )

        NavigationBarItem(
            icon = {
                Icon(Icons.Default.Settings, contentDescription = "icon clog")
            },
            label = { Text(text = Destination.Settings.title) },
            selected = currentDestination?.route == Destination.Settings.route,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = colorResource(R.color.dark_green),
                selectedIconColor = colorResource(R.color.white)
            ),
            onClick = {
                navController.navigate(Destination.Settings.route){
                    popUpTo(Destination.Settings.route)
                    launchSingleTop = true
                }
            },

            )
    }

}