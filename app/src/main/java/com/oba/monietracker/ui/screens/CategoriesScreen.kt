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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oba.monietracker.data.models.Category
import com.oba.monietracker.ui.components.CategoryCard
import com.oba.monietracker.ui.theme.Blue10

/**
 * Displays content of all categories.
 *
 * @param navController Stores app navigation data.
 */
@Composable
fun CategoriesScreen(
    navController: NavHostController
) {
    // This data will come from database - Room DB.
    val categories = listOf(
        Category("Income - Job", "Income from full- part-time jobs", "lCPhGxs7pww"),
        Category("Income - Business", "Income from side businesses", "6dW3xyQvcYE"),
        Category("Housing", "Rent, utilities and home maintenance", "r3WAWU5Fi5Q"),
        Category("Groceries", "Food, fruits, snacks and everything nice.", "ivfp_yxZuYQ"),
    )


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

        LazyColumn {
            items(categories) { item ->
                CategoryCard(item)
            }
        }
    }
}