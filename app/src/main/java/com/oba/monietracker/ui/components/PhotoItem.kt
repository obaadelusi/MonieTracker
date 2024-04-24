package com.oba.monietracker.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oba.monietracker.data.models.Photo

/**
 * The photo item card component.
 * @param item The photo data object.
 */
@Composable
fun PhotoItem(
    item: Photo?,
    onImageClick: (imageId: String?, imageUrl: String?) -> Unit
) {
    Card(
        Modifier.padding(8.dp, 6.dp)
            .border(width = 1.dp, color = Color.Gray)
            .clickable { onImageClick(item?.id, item?.urls?.regular) },
        shape = RectangleShape,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(
                LocalContext.current
            ).data(item?.urls?.regular)
                .build(),
            contentDescription = item?.altDescription
        )

        Text(text = "Photo by ${item?.user?.name}",
            fontSize = 13.sp, color = Color.DarkGray,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(8.dp, 4.dp))

//        item?.let {
//            Text(text = "Description: ${it.description}")
//        }
    }
}