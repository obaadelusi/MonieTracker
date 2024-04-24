package com.oba.monietracker.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.group.GroupedBarChart
import com.himanshoe.charty.group.model.GroupBarData
import com.oba.monietracker.data.db.AppDataManager
import com.oba.monietracker.data.models.TransactionRecord
import com.oba.monietracker.ui.theme.Blue10
import com.oba.monietracker.ui.theme.DarkSeaGreen
import com.oba.monietracker.ui.theme.Salmon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * The all insights content screen.
 * @param navController The app navigation controller.
 */
//@SuppressLint("CoroutineCreationDuringComposition")
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    navController: NavHostController,
    appDataManager: AppDataManager
){
    var records by remember { mutableStateOf<List<TransactionRecord>>(emptyList()) }
    var incomeTotal by remember { mutableFloatStateOf(0.0f) }
    var expenseTotal by remember { mutableFloatStateOf(0.0f) }
    var balance by remember { mutableFloatStateOf(0.0f) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        initialDisplayMode = DisplayMode.Picker)
    var dateOpenDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter
        .ofPattern("MMM yyyy", Locale.getDefault())
    val selectedDate = datePickerState.selectedDateMillis?.let {//
        dateFormatter.format(
            Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
            //Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        )
    }

    val year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"))
    val months = listOf (
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )


    val chartColors = listOf(
        DarkSeaGreen,
        Salmon
    )

    val groupData = listOf(
        GroupBarData(
            label = "Nov 2023",
            dataPoints = listOf(3000f, 1800f),
            chartColors
        ),
        GroupBarData(
            label = "Dec 2023",
            dataPoints = listOf(2300f, 3150f),
            chartColors
        ),
        GroupBarData(
            label = "Jan 2024",
            dataPoints = listOf(2800f, 2900f),
            chartColors
        ),
        GroupBarData(
            label = "Feb 2024",
            dataPoints = listOf(3400f, 2200f),
            chartColors
        ),
        GroupBarData(
            label = "Mar 2024",
            dataPoints = listOf(3700f, 1950f),
            chartColors
        ),
    )

    /**
     * Get all transactions for a specified month.
     * @param date The specified month date.
     */
    fun getTransactionsByDate(date: String?) {
        /* TODO: populate income, expense and balance.
        *   [x] Get all transactions for specific month.
        *   [x] Loop through and aggregate income and expense
        *   [x] Subtract result to get the balance
        */
        GlobalScope.launch {
            records = appDataManager.getTransactionsByDate("$date")
            incomeTotal = 0.0f
            expenseTotal = 0.0f
            records.forEach { r ->
                if (r.type.lowercase() == "income") {
                    incomeTotal += r.amount.toFloat()
                } else if(r.type.lowercase() == "expense") {
                    expenseTotal += r.amount.toFloat()
                }
            }
            balance = incomeTotal - expenseTotal
        }
    }

    val state = rememberScrollState()

    Column(
        Modifier.verticalScroll(state)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Blue10)
        ) {
            Text(
                text = "Insights",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp)
            )
        }

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
        }
        if (dateOpenDialog) {
            DatePickerDialog(
                onDismissRequest = { dateOpenDialog = false },
                confirmButton = {
                    TextButton(onClick = { dateOpenDialog = false }) {
                        Text(text = "OK")
                    }
                    GlobalScope.launch { getTransactionsByDate(selectedDate) }
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

        if(records.isEmpty()) {
            LaunchedEffect(Unit) {
                getTransactionsByDate(selectedDate)
            }
//                GlobalScope.launch{  }
        }

        Box(modifier = Modifier
            .padding(all = 8.dp)
            .border(1.dp, Color.Gray, shape = RectangleShape)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Income",
                        style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.width(6.dp))
                    Canvas(
                        modifier = Modifier
                            .size(14.dp)
                    ) {
                        drawCircle(color = DarkSeaGreen)
                    }
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = "$selectedDate",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "$ ${String.format("%.2f", incomeTotal)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(all = 8.dp)
                .border(1.dp, Color.Gray, shape = RectangleShape)
        ){
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Expenses",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Canvas(
                        modifier = Modifier
                            .size(14.dp)
                    ) {
                        drawCircle(color = Salmon)
                    }
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$selectedDate",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = String.format("$ %.2f", expenseTotal),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(all = 8.dp)
                .border(1.dp, Color.Gray, shape = RectangleShape)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = String.format("$ %.2f", balance),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        ){
            Column {
                Text(
                    text = "Income vs Expenses",
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 0.sp,
                    modifier = Modifier.padding(top = 18.dp, bottom = 8.dp))
                Text(text = "$selectedDate",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp))

                // CHART
                GroupedBarChart(
                    groupBarDataCollection = groupData.toComposeList(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(12.dp)
                    ) {
                        drawCircle(color = DarkSeaGreen)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Canvas(
                        modifier = Modifier
                            .size(12.dp)
                    ) {
                        drawCircle(color = Salmon)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Expenses",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}