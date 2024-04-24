package com.oba.monietracker.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oba.monietracker.R
import com.oba.monietracker.data.db.AppDataManager
import com.oba.monietracker.data.models.TransactionRecord
import com.oba.monietracker.ui.components.RecordCard
import com.oba.monietracker.ui.components.RecordDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * The all records content screen.
 * @param navController The navigation controller.
 * @param appDataManager The app database manager.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecordsScreen(
    navController: NavHostController,
    appDataManager: AppDataManager
) {

    val context = LocalContext.current

    var records by remember { mutableStateOf<List<TransactionRecord>>(emptyList()) }

    var openRecordDialog by remember { mutableStateOf(false) }
    var selectedRecordIndex by remember { mutableStateOf(0) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        initialDisplayMode = DisplayMode.Picker)
    var dateOpenDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter
        .ofPattern("MMM yyyy", Locale.getDefault())
    val selectedDate = datePickerState.selectedDateMillis?.let {
        dateFormatter.format(
            Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
            //Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.blue_100))
        ){
            Text(text = "All Records",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp))
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(
                value = selectedDate ?: "No date",
                onValueChange = {  },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { dateOpenDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "select a month"
                        )
                    }
                },
                shape = RectangleShape,
                modifier = Modifier
                    .width(150.dp)
                    .padding(end = 8.dp),
            )
            if (dateOpenDialog) {
                DatePickerDialog(
                    onDismissRequest = { dateOpenDialog = false },
                    confirmButton = {
                        TextButton(onClick = { dateOpenDialog = false }) {
                            Text(text = "OK")
                        }
                        GlobalScope.launch {
                            records = appDataManager.getTransactionsByDate("$selectedDate")
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

//            OutlinedTextField(
//                value = "All",
//                onValueChange = {},
//                singleLine = true,
//                readOnly = true,
//                shape = RectangleShape,
//                trailingIcon = {
//                    IconButton(onClick = { /* Handle icon click */ }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowDropDown,
//                            contentDescription = "View more record types"
//                        )
//                    }
//                },
//                modifier = Modifier.width(90.dp)
//            )
        }

        // TODO: Get all the records for date
        LaunchedEffect(Unit) {
            records = appDataManager.getTransactionsByDate(selectedDate!!)
//            records = appDataManager.getAllTransactionRecords()
        }

        if(records.isEmpty()) {
            Row (
                modifier = Modifier.weight(1f)
                    .padding(top = 120.dp)
                    .align(Alignment.CenterHorizontally)
            ){
                Text(text = "No transactions recorded for $selectedDate")
            }
        }

        LazyColumn {
            items(records) { item ->
                RecordCard(item) {
                    openRecordDialog = true
                    selectedRecordIndex = it
                }
            }
        }

        // dialog logic
        if (openRecordDialog) {
            RecordDialog(
                t = records.find { it.id?.toInt() == selectedRecordIndex }!!,
                appDataManager,
                navController,
                onDismissRequest = { openRecordDialog = false }
            )
        }

        Spacer(modifier = Modifier
            .weight(1f)
            .height(120.dp))
    }
}