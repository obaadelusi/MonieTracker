package com.oba.monietracker.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.oba.monietracker.Destination
import com.oba.monietracker.R
import com.oba.monietracker.data.api.PhotoViewModel
import com.oba.monietracker.ui.components.PhotosDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun AddCategoryScreen(
    navController: NavController
){
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var showNameError by remember { mutableStateOf(false) }

    var openPhotosDialog by remember { mutableStateOf(false) }

    val viewModel: PhotoViewModel = viewModel()

    Log.d("PhotoVM", viewModel.photosResponse.value.toString())

    Column(
        Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.alice_blue))
        ) {
            Text(
                text = Destination.AddCategory.title,
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp)
            )
        }

        // Category name
        Column(Modifier.padding(all = 8.dp)) {
            Text(
                text = "Name",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = {
                    showNameError = name.isBlank()
                    name = it
                },
                trailingIcon = { Icons.Default.Edit },
                shape = RectangleShape,
                placeholder = { Text(text = "ex. Housing - Utilities") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            if(showNameError) {
                Text(text = "Enter a category name", color = Color.Red, fontSize = 13.sp)
            }
        }

        // Category description
        Column(Modifier.padding(all = 8.dp)) {
            Text(
                text = "Description",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it},
                trailingIcon = { Icons.Default.Edit },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        // Category image
        Column(Modifier.padding(all = 8.dp)) {
            Text(
                text = "Add image",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .border(1.dp, Color.Gray, shape = RectangleShape)
            ){
                Button(
                    onClick = {
                        if (name.isBlank()) showNameError = true
                            else openPhotosDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Gray

                    )
                ) {
                    Text(text = "Click to add image from unsplash")
                }

                // dialog logic
                if (openPhotosDialog) {
                    LaunchedEffect(Unit) {
                        val job = GlobalScope.launch {
                            viewModel.getPhotosBySearchQuery(name)
                        }
                        job.join()
                    }
                    PhotosDialog(
                        catName = name,
                        photos = viewModel.photosResponse.value
                    ) {
                        openPhotosDialog = false
                    }
                }
            }
        }

        Button(
            onClick = {
                if (name.isBlank()) showNameError = true
                    else navController.navigate(Destination.Records.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dark_slate_blue)
            ),
            shape = RectangleShape,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
        ) {
            Text(text = "SAVE CATEGORY",
                fontSize = 16.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}