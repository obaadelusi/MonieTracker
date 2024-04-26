package com.oba.monietracker.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
 * @param appDataManager The app data manager.
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
    val selectedDate = datePickerState.selectedDateMillis?.let {
        dateFormatter.format(
            Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
            //Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        )
    }

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

        // Chart

        /* TODO: To display data with grouped bar chart
         *  1. Data -> date: String [MMM yyyy], dataPoints: listOf(f, f)
         *  2. Get current date, query the records. Save in variable
         *  3. Get previous month, query the records. Save in variable
         *  4. Display
         */
        var prevMonth1Records by remember { mutableStateOf<List<TransactionRecord>>(emptyList()) }
        var prevMonth1IncomeTotal by remember { mutableFloatStateOf(0.0f) }
        var prevMonth1ExpenseTotal by remember { mutableFloatStateOf(0.0f) }

        var prevMonth2Records by remember { mutableStateOf<List<TransactionRecord>>(emptyList()) }
        var prevMonth2IncomeTotal by remember { mutableFloatStateOf(0.0f) }
        var prevMonth2ExpenseTotal by remember { mutableFloatStateOf(0.0f) }

        // use current month to get previous month
        val year = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy")).toInt()
        val months = listOf (
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val thisMonth = selectedDate?.split(" ")?.first()
        Log.d("Screen", "This month: $thisMonth")

        var prevMonth1 by remember { mutableStateOf("") }
        var prevMonth2 by remember { mutableStateOf("") }
        for(index in months.indices) {
            //Log.d("Screen", "Element at index: " + months.elementAt(index))
            if (index > 1 && months.elementAt(index) == thisMonth) {
                prevMonth1 = months.elementAt(index-1) + " $year"
                prevMonth2 = months.elementAt(index-2) + " $year"

                Log.d("Screen", "Element at index-1: " + months.elementAt(index-1))
                break
            } else if (index == 1 && months.elementAt(index) == thisMonth) {
                prevMonth1 = "Jan $year"
                prevMonth2 = "Dec ${year-1}"
                break
            }
            else if (index == 0 && months.elementAt(index) == thisMonth){
                prevMonth1 = "Dec ${year-1}"
                prevMonth2 = "Nov ${year-1}"
                break
            }
        }

        LaunchedEffect(Unit) {
            prevMonth1Records = appDataManager.getTransactionsByDate(prevMonth1)
            prevMonth1IncomeTotal = 0.0f
            prevMonth1ExpenseTotal = 0.0f
            prevMonth1Records.forEach { r ->
                if (r.type.lowercase() == "income") {
                    prevMonth1IncomeTotal += r.amount.toFloat()
                } else if(r.type.lowercase() == "expense") {
                    prevMonth1ExpenseTotal += r.amount.toFloat()
                }
            }

            prevMonth2Records = appDataManager.getTransactionsByDate(prevMonth2)
            prevMonth2IncomeTotal = 0.0f
            prevMonth2ExpenseTotal = 0.0f
            prevMonth2Records.forEach { r ->
                if (r.type.lowercase() == "income") {
                    prevMonth2IncomeTotal += r.amount.toFloat()
                } else if(r.type.lowercase() == "expense") {
                    prevMonth2ExpenseTotal += r.amount.toFloat()
                }
            }

        }


        val chartColors = listOf(
            DarkSeaGreen,
            Salmon
        )

        val groupData = listOf(
            GroupBarData(
                label = prevMonth2,
                dataPoints = listOf(prevMonth2IncomeTotal, prevMonth2ExpenseTotal),
                chartColors
            ),
            GroupBarData(
                label = prevMonth1,
                dataPoints = listOf(prevMonth1IncomeTotal, prevMonth1ExpenseTotal),
                chartColors
            ),
            GroupBarData(
                label = "$selectedDate",
                dataPoints = listOf(incomeTotal, expenseTotal),
                chartColors
            ),
        )

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