package com.oba.monietracker.ui.screens

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
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.group.GroupedBarChart
import com.himanshoe.charty.group.model.GroupBarData
import com.oba.monietracker.ui.theme.Blue10
import com.oba.monietracker.ui.theme.DarkSeaGreen
import com.oba.monietracker.ui.theme.Salmon

/**
 * The all insights content screen.
 * @param navController The app navigation controller.
 */
@Composable
fun InsightsScreen(
    navController: NavHostController
){
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

    val date_options = listOf("Apr 2024", "Mar 2024", "Feb 2024", "Jan 2024")
    var date_expanded by remember { mutableStateOf(false) }
    var date_selectedOption by remember { mutableStateOf("Mar 2024") }

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
                modifier = Modifier
                    .width(150.dp)
                    .padding(end = 8.dp),
            )
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
                    Text(text = "Mar 2024",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "$ 3020.94",
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
                        text = "Mar 2024",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "$ 2589.10",
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
                    text = "$ 431.84",
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
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
                )

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
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}