package com.oba.monietracker.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oba.monietracker.data.models.TransactionRecord
import com.oba.monietracker.ui.theme.DarkSeaGreen
import com.oba.monietracker.ui.theme.Salmon

@Composable
fun RecordsScreen(
    navController: NavHostController
) {
    val records = listOf(
        TransactionRecord("1 Mar 2024", 650.00f,
            "expense", "Rent & Utilities - Princess St", "Housing"),
        TransactionRecord("3 Mar 2024", 125.92f,
            "expense", "Gift - Mom", "Giving"),
        TransactionRecord("15 Mar 2024", 1130.39f,
            "income", "Pay - CargoJet (Part-time)", "Job Pay"),
        TransactionRecord("16 Mar 2024", 230.52f,
            "expense", "Groceries - Walmart", "Groceries"),
        TransactionRecord("24 Mar 2024", 1444.60f,
            "income", "Tax return - CRA", "Tax Return"),
        TransactionRecord("26 Mar 2024", 14.55f,
            "expense", "Subscription - YT Premium", "Entertainment"),
    )

    val date_options = listOf("Apr 2024", "Mar 2024", "Feb 2024", "Jan 2024")
    var date_expanded by remember { mutableStateOf(false) }
    var date_selectedOption by remember { mutableStateOf("Mar 2024") }

    //val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            //.verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                //.background(Color(R.color.greenYellow))
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
                value = date_selectedOption,
                onValueChange = { date_selectedOption = it },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { /* TODO: Handle icon click */ }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "View more dates"
                        )
                    }
                },
                shape = RectangleShape,
                modifier = Modifier.width(150.dp).padding(end = 8.dp),
            )
            OutlinedTextField(
                value = "All",
                onValueChange = {},
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { /* Handle icon click */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "View more record types"
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier.width(90.dp)
            )
        }

        LazyColumn {
            items(records) { item ->
                Box(modifier = Modifier
                    .padding(all = 8.dp)
                    .border(1.dp, Color.Gray, shape = RectangleShape)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.category,
                                style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.weight(1f))
                            Canvas(
                                modifier = Modifier
                                    .size(14.dp)
                                    .padding(end = 4.dp)
                            ) {
                                drawCircle(color = if(item.type == "income") DarkSeaGreen
                                                    else Salmon)
                            }
                            Text(text = item.type.uppercase(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray)
                        }

                        Text(text = item.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp))

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = item.date,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "$ ${item.amount}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}