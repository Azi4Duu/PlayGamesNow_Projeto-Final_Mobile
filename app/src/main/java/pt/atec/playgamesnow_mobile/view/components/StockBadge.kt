package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StockBadge(stock: Int) {
    val inStock = stock > 0

    Text(
        text = if (inStock) "In Stock" else "Out of Stock",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (inStock) Color.White else Color(0xFF555555),
        modifier = Modifier
            .background(
                color = if (inStock) {
                    Color(0xFFEE626B)
                } else {
                    Color(0xFFE9E9E9)
                },
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp
            )
    )
}