package com.oba.monietracker.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.oba.monietracker.Destination
import com.oba.monietracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    navController: NavHostController
) {
    val options = listOf("Expense", "Income")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.firstOrNull()) }

    val cat_options = listOf("Groceries",
        "Income - Job",
        "Income - Business",
        "Income - Other")
    var cat_expanded by remember { mutableStateOf(false) }
    var cat_selectedOption by remember { mutableStateOf(cat_options.firstOrNull()) }
    //var cat_searchText by remember { mutableStateOf("") }

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            //.padding(top = 0.dp, end = 8.dp, bottom = 8.dp, start = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(R.color.orange_red))
        ) {
            Text(text = "Record a transaction",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp))
        }

        // Type
        Column(Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
            Text(
                text = "Type",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                modifier = Modifier.fillMaxWidth(),
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = selectedOption!!,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedOption = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }

        // Amount
        Column(Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
            Text(
                text = "Amount $",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it},
                trailingIcon = { Icons.Default.Edit },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }

        // Category
        Column(Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
            Text(
                text = "Category",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            ExposedDropdownMenuBox(
                expanded = cat_expanded,
                onExpandedChange = {
                    cat_expanded = !cat_expanded
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                OutlinedTextField(
                    value = cat_selectedOption ?: "",
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cat_expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                ExposedDropdownMenu(
                    expanded = cat_expanded,
                    onDismissRequest = { cat_expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    cat_options.forEach { cat_option ->
                        Log.i("AddScreen-option", cat_option)
                        DropdownMenuItem(text = {
                            Text(text = cat_option)
                        }, onClick = {
                            cat_selectedOption = cat_option
                            //onValueChange(option)
                            cat_expanded = false
                        },
                            modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }

        // Description
        Column(Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }
        
        // Save Button
        Button(
            onClick = { navController.navigate(Destination.Records.route) },
            modifier = Modifier.padding(6.dp).fillMaxWidth(),
            shape = RectangleShape
        ) {
            Text(text = "SAVE",
                fontSize = 16.sp,
                color = Color.White)
        }
    }
}