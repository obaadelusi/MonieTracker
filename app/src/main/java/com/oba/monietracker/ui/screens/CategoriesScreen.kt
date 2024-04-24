package com.oba.monietracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oba.monietracker.data.db.AppDataManager
import com.oba.monietracker.data.models.Category
import com.oba.monietracker.ui.components.CategoryCard
import com.oba.monietracker.ui.theme.Blue10

/**
 * The all categories content screen.
 *
 * @param navController The app navigation controller.
 */
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    appDataManager: AppDataManager
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Blue10)
        ) {
            Text(
                text = "All categories",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp)
            )
        }

        // TODO: Get all the records
        var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
        LaunchedEffect(Unit) {
            categories = appDataManager.getAllCategories()
        }

        if(categories.isEmpty()) {
            Text(text = "Loading transaction categories...",
                modifier = Modifier.padding(24.dp, 6.dp))
        }

        LazyColumn {
            items(categories) { item ->
                CategoryCard(item)
            }
        }
    }
}