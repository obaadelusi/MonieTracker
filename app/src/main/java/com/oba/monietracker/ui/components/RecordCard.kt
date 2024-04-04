package com.oba.monietracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oba.monietracker.data.models.TransactionRecord
import com.oba.monietracker.ui.theme.DarkSeaGreen
import com.oba.monietracker.ui.theme.Salmon

@Composable
fun RecordCard(item: TransactionRecord) {
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
                    else Salmon
                    )
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