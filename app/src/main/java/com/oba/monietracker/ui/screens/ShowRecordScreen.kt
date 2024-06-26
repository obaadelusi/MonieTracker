package com.oba.monietracker.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oba.monietracker.R
import com.oba.monietracker.data.models.TransactionRecord
import com.oba.monietracker.ui.theme.DarkSeaGreen
import com.oba.monietracker.ui.theme.Salmon

@Composable
fun ShowRecordScreen(
    navController: NavHostController,
    item: TransactionRecord
) {
    Column(modifier = Modifier.padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            item.category?.name?.let {
                Text(text = it,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black)
            }

            Spacer(modifier = Modifier.weight(1f))
            Canvas(
                modifier = Modifier
                    .size(14.dp)
                    .padding(end = 4.dp)
            ) {
                drawCircle(color = if(item.type == "income") DarkSeaGreen
                else Salmon
                )
            }
            Text(text = "${item.type}".uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray)
        }

        Text(text = "${item.description}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "${item.transactionDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = String.format("$ %.2f", item?.amount),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = if (item?.type == "income") colorResource(R.color.dark_green)
                else colorResource(R.color.crimson)
            )
        }
    }
}