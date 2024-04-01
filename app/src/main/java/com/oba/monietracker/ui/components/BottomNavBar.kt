package com.oba.monietracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oba.monietracker.Destination
import com.oba.monietracker.R


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
                Icon(Icons.Default.List, contentDescription = "transaction records")
            },
            label = { Text(text = Destination.Records.title) },
            selected = currentDestination?.route == Destination.Records.route,
            onClick = {
                navController.navigate(Destination.Records.route){
                    popUpTo(Destination.Records.route)
                    launchSingleTop = true
                }
            },

            )

        NavigationBarItem(
            icon = {
                Icon(ic_bar_graph, contentDescription = "insights")
            },
            label = { Text(text = Destination.Insights.title) },
            selected = currentDestination?.route == Destination.Insights.route,
            onClick = {
                navController.navigate(Destination.Insights.route){
                    popUpTo(Destination.Insights.route)
                    launchSingleTop = true
                }
            },

            )

        NavigationBarItem(
            icon = {
                Icon(Icons.Default.Settings, contentDescription = "profile")
            },
            label = { Text(text = Destination.Settings.title) },
            selected = currentDestination?.route == Destination.Settings.route,
            onClick = {
                navController.navigate(Destination.Settings.route){
                    popUpTo(Destination.Settings.route)
                    launchSingleTop = true
                }
            },

            )
    }

}