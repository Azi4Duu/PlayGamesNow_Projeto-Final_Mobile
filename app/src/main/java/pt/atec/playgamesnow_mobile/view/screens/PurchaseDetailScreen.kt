package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator
import pt.atec.playgamesnow_mobile.viewmodel.sale.PurchaseDetailViewModel

@Composable
fun PurchaseDetailScreen(
    purchaseId: Int,
    purchaseDetailViewModel: PurchaseDetailViewModel = viewModel()
) {

    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(purchaseId) {
        purchaseDetailViewModel.loadPurchaseDetail(purchaseId)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        when {

            purchaseDetailViewModel.loading -> {
                LoadingIndicator("Loading order details...")
            }

            purchaseDetailViewModel.error != null -> {
                Text(
                    text = purchaseDetailViewModel.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            purchaseDetailViewModel.purchase != null -> {

                val purchase = purchaseDetailViewModel.purchase!!

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Order Details",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    CheckoutCard {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Order #${purchase.id}",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = purchase.status.replaceFirstChar {
                                    it.uppercase()
                                },
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Payment Method: ${purchase.payment_method.uppercase()}",
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = "Date: ${purchase.created_at.take(10)}",
                            color = Color.Gray
                        )
                    }

                    CheckoutCard {

                        Text(
                            text = "Purchased Games",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(30.dp))

                        purchase.items.forEachIndexed { index, item ->

                            Text(
                                text = item.game.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = "${item.platform.name} · Quantity: ${item.quantity}",
                                color = Color.Gray,
                                fontSize = 15.sp
                            )

                            Spacer(Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Unit Price")
                                Text("${item.unit_price} €")
                            }

                            Spacer(Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Item Total")
                                Text(
                                    text = "%.2f €".format(
                                        (item.unit_price.toDoubleOrNull() ?: 0.0) *
                                                item.quantity
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(14.dp))

                            Text(
                                text = "Game Keys",
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(8.dp))

                            item.keys.forEach { gameKey ->

                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color(0xFFF5F5F5)
                                ) {

                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {

                                        Text(
                                            text = gameKey.code,
                                            modifier = Modifier.weight(1f),
                                            fontSize = 14.sp,
                                            lineHeight = 18.sp
                                        )

                                        IconButton(
                                            onClick = {
                                                clipboardManager.setText(
                                                    AnnotatedString(gameKey.code)
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ContentCopy,
                                                contentDescription = "Copy game key",
                                                tint = Color(0xFF0071F8)
                                            )
                                        }
                                    }
                                }
                            }

                            if (index < purchase.items.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = Color(0xFFEAEAEA)
                                )
                            }
                        }
                    }

                    CheckoutCard {

                        Text(
                            text = "Order Summary",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal")
                            Text("${purchase.subtotal} €")
                        }

                        Spacer(modifier = Modifier.height(10.dp))


                        purchase.coupon?.let { coupon ->

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Coupon")

                                Text(
                                    text = coupon.code,
                                    color = Color(0xFF0071F8),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                        }


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Discount")
                            Text("${purchase.discount} €")
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 14.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Total",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "${purchase.total} €",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}