package com.oba.monietracker.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oba.monietracker.R
import com.oba.monietracker.data.models.Category

@Composable
fun CategoryCard(
    item: Category
) {
    val url = "https://source.unsplash.com/${item.image}"
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.alice_blue)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, color = Color.Gray)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)){
            AsyncImage(
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(url)
                    .build(),
                contentDescription = item.description,
                contentScale = ContentScale.FillWidth
            )
        }

        Text(text = item.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, bottom = 4.dp))

        item.description?.let {
            Text(text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp))
        }

        Text(text = item.dateCreated.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 8.dp))
    }
}