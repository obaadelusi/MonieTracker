package com.oba.monietracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.oba.monietracker.R
import com.oba.monietracker.data.models.Photo

/**
 * A dialog to display photos related to a category name.
 * @param catName The category name.
 * @param photos The photos list to display.
 * @param onDismissRequest The action on dialog dismiss.
 * @param onImageClick The action on image click.
 */
@Composable
fun PhotosDialog(
    catName: String?,
    photos: List<Photo?>,
    onDismissRequest: () -> Unit,
    onImageClick: (imageId: String?, imageUrl: String?) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(640.dp)
                .background(color = colorResource(R.color.blue_100)),
        ) {
            if(photos.isNotEmpty()) {
                Text(
                    text = "Select an image for \"$catName\" category",
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 16.dp)
                )

                LazyColumn(
                ) {
                    items(photos) { item ->
                        PhotoItem(item) { id, url ->
                            onImageClick(id, url)
                        }
                    }
                }
            } else {
                Text(
                    text = "Searching for \"$catName\" image on unsplash",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Magenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}
