package com.oba.monietracker.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.oba.monietracker.Destination
import com.oba.monietracker.R
import com.oba.monietracker.data.db.AppDataManager
import com.oba.monietracker.data.models.Category
import com.oba.monietracker.data.models.TransactionRecord
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * The add new transaction record form screen.
 * @param navController The app navigation controller.
 * @param appDataManager The app data manager.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    navController: NavHostController,
    appDataManager: AppDataManager
) {
    var typeSelected by remember { mutableStateOf("expense") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli())
    var dateOpenDialog by remember { mutableStateOf(false) }
    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
    }

    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var catOptions by remember { mutableStateOf<List<String>>(emptyList()) }
    var catSelectedOption by remember { mutableStateOf<String?>("Uncategorized") }
    var catExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        categories = appDataManager.getAllCategories()
        val cats = mutableListOf<String>()
        categories.forEach { c ->
            cats.add("${c.name}")
        }
        catOptions = cats.sorted()
        //catSelectedOption = catOptions.firstOrNull()
    }

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var showAmountError by remember { mutableStateOf(false) }
    var showDescError by remember { mutableStateOf(false) }

//    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.green_100))
        ) {
            Text(text = "Record a transaction",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp))
        }

        // Type
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Type",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = typeSelected == "expense",
                    modifier = Modifier.padding(0.dp),
                    onClick = { typeSelected = "expense" }
                )
                Text("Expense", fontSize = 16.sp, color = Color.Black)
                
                Spacer(modifier = Modifier.width(24.dp))

                RadioButton(
                    selected = typeSelected == "income",
                    modifier = Modifier.padding(0.dp),
                    onClick = { typeSelected = "income" }
                )
                Text("Income", fontSize = 16.sp, color = Color.Black)
            }
        }

        // Date
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Select date",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 3.dp)
            )
            OutlinedTextField(
                value = selectedDate
                    ?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
                    ?: "No selection",
                onValueChange = { },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { dateOpenDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "icon calendar"
                        )
                    }
                },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
            )
            if (dateOpenDialog) {
                DatePickerDialog(
                    onDismissRequest = { dateOpenDialog = false },
                    confirmButton = {
                        TextButton(onClick = { dateOpenDialog = false }) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { dateOpenDialog = false }) {
                            Text(text = "CANCEL")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }

        // Amount
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Amount $",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    showAmountError = false
                },
                trailingIcon = { Icons.Default.Edit },
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            if(showAmountError) {
                Text(text = "Enter an amount",
                    color = Color.Red, fontSize = 13.sp)
            }
        }

        // Category
        Column(Modifier.padding(8.dp)) {
            Row {
                Text(
                    text = "Category",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                ClickableText(text = AnnotatedString("+ Add new category"),
                    maxLines = 1,
                    style = TextStyle(
                        color = colorResource(R.color.blue),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    navController.navigate(Destination.AddCategory.route)
                }
            }

            ExposedDropdownMenuBox(
                expanded = catExpanded,
                onExpandedChange = {
                    catExpanded = !catExpanded
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                OutlinedTextField(
                    value = catSelectedOption ?: "",
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                ExposedDropdownMenu(
                    expanded = catExpanded,
                    onDismissRequest = { catExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    catOptions.forEach { cat_option ->
                        Log.i("AddScreen-option", cat_option)
                        DropdownMenuItem(text = {
                            Text(text = cat_option)
                        }, onClick = {
                            catSelectedOption = cat_option
                            //onValueChange(option)
                            catExpanded = false
                        },
                            modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }

        // Description
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Description",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    showDescError = false
                },
                trailingIcon = { Icons.Default.Edit },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            if(showDescError) {
                Text(text = "Enter a description",
                    color = Color.Red, fontSize = 13.sp)
            }
        }
        
        // Save Button
        Button(
            onClick = {
                if (amount.isBlank()) {
                    showAmountError = true
                } else if(description.isBlank()) {
                    showDescError = true
                }
                else {
                    val tDate = DateTimeFormatter
                        .ofPattern("dd MMM yyyy", Locale.getDefault())
                        .format(selectedDate)
                    val t = TransactionRecord(
                        tDate,
                        amount.toDouble(),
                        typeSelected,
                        description,
                        Category(catSelectedOption)
                    )
                    GlobalScope.launch { appDataManager.saveTransaction(t) }
                    navController.navigate(Destination.Records.route)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dark_green)
            ),
            shape = RectangleShape,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
        ) {
            Text(text = "SAVE RECORD",
                fontSize = 16.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}