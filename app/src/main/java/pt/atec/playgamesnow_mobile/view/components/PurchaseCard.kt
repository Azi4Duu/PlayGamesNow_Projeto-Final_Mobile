package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.playgamesnow_mobile.model.sale.Purchase
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun PurchaseCard(
    purchase: Purchase,
    onClick: () -> Unit
) {

    val formattedDate = try {
        val parser = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
            Locale.US
        )
        parser.timeZone = TimeZone.getTimeZone("UTC")

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.UK)
        parser.parse(purchase.created_at)?.let {
            formatter.format(it)
        } ?: purchase.created_at
    } catch (e: Exception) {
        purchase.created_at
    }

    CheckoutCard {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = purchase.title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = purchase.status.replaceFirstChar {
                        it.uppercase()
                    },
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = formattedDate,
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Payment: ${purchase.payment_method.uppercase()}",
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    color = Color.Gray,
                    fontSize = 15.sp
                )

                Text(
                    text = "%.2f €".format(purchase.total.toDouble()),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                color = Color(0xFFEAEAEA)
            )

            Text(
                text = "View Details",
                color = Color(0xFF0071F8),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}