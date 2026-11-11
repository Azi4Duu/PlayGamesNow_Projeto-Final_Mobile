package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.model.sale.SaleRequest
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.viewmodel.cart.CartViewModel
import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.RoundedTextField
import pt.atec.playgamesnow_mobile.viewmodel.sale.CheckoutViewModel

@Composable
fun CheckoutScreen(
    onPurchaseSuccess: (Int) -> Unit,
    cartViewModel: CartViewModel = viewModel(),
    checkoutViewModel: CheckoutViewModel = viewModel()
) {

    var paymentMethod by remember { mutableStateOf("card") }

    var cardholderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var paypalEmail by remember { mutableStateOf("") }
    var mbwayPhone by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        cartViewModel.loadCart()
    }

    val completedSaleId = checkoutViewModel.saleResponse?.sale?.id

    LaunchedEffect(completedSaleId) {
        if (completedSaleId != null) {
            onPurchaseSuccess(completedSaleId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                text = "Checkout",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            CheckoutCard {

                Text(
                    text = "Payment Details",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Choose how you want to complete your purchase.",
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "Payment Method",
                    fontWeight = FontWeight.Bold
                )

                listOf(
                    "card" to "Credit / Debit Card",
                    "paypal" to "PayPal",
                    "mbway" to "MB WAY"
                ).forEach { (value, label) ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = paymentMethod == value,
                                onClick = { paymentMethod = value }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = paymentMethod == value,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF0071F8)
                            )
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(label)
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            CheckoutCard {

                AnimatedVisibility(
                    visible = paymentMethod == "card"
                ) {

                    Column {

                        Text(
                            text = "Card Details",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = "Test payment details only.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )

                        Spacer(Modifier.height(16.dp))

                        RoundedTextField(
                            value = cardholderName,
                            onValueChange = { cardholderName = it },
                            label = "Cardholder Name",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(10.dp))

                        RoundedTextField(
                            value = cardNumber,
                            onValueChange = { cardNumber = it },
                            label = "Test Card Number",
                            modifier = Modifier.fillMaxWidth(),
                            keyboardType = KeyboardType.Number
                        )

                        Spacer(Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            RoundedTextField(
                                value = expiry,
                                onValueChange = { expiry = it },
                                label = "MM/YY",
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Number
                            )

                            RoundedTextField(
                                value = cvv,
                                onValueChange = { cvv = it },
                                label = "Test CVV",
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = paymentMethod == "paypal"
                ) {

                    Column {

                        Text(
                            text = "PayPal Details",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Enter your PayPal email for this simulated payment.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )

                        Spacer(Modifier.height(16.dp))

                        RoundedTextField(
                            value = paypalEmail,
                            onValueChange = { paypalEmail = it },
                            label = "PayPal Email",
                            modifier = Modifier.fillMaxWidth(),
                            keyboardType = KeyboardType.Email
                        )
                    }
                }

                AnimatedVisibility(
                    visible = paymentMethod == "mbway"
                ) {

                    Column {

                        Text(
                            text = "MB WAY Details",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Enter a test phone number for this simulated payment.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )

                        Spacer(Modifier.height(16.dp))

                        RoundedTextField(
                            value = mbwayPhone,
                            onValueChange = { mbwayPhone = it },
                            label = "Phone Number",
                            modifier = Modifier.fillMaxWidth(),
                            keyboardType = KeyboardType.Phone
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            CheckoutCard {

                Text(
                    text = "Order Summary",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                cartViewModel.cartItems.forEach { cartItem ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = cartItem.game_platform.game.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )

                            Text(
                                text = cartItem.game_platform.platform.name,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )

                            Text(
                                text = "Quantity: ${cartItem.quantity}",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(Modifier.width(10.dp))

                        Text(
                            text = "%.2f €".format(cartItem.item_total),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFEAEAEA)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal")
                    Text("%.2f €".format(cartViewModel.subtotal))
                }

                Spacer(Modifier.height(10.dp))


                cartViewModel.coupon?.let { coupon ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Coupon",
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        Text(
                            text = coupon.code,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0071F8)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Discount")
                    Text("-%.2f €".format(cartViewModel.discount))
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
                        text = "%.2f €".format(cartViewModel.total),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(16.dp))

                if (checkoutViewModel.loading) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(12.dp))
                }

                checkoutViewModel.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(12.dp))
                }

                Button(
                    onClick = {
                        val request = when (paymentMethod) {

                            "card" -> {

                                val expiryParts = expiry.split("/")

                                val formattedExpiry = if (expiryParts.size == 2) {
                                    "20${expiryParts[1]}-${expiryParts[0]}"
                                } else {
                                    expiry
                                }

                                SaleRequest(
                                    payment_method = "card",
                                    cardholder_name = cardholderName,
                                    card_number = cardNumber,
                                    expiry_date = formattedExpiry,
                                    cvv = cvv
                                )
                            }

                            "paypal" -> SaleRequest(
                                payment_method = "paypal",
                                paypal_email = paypalEmail
                            )

                            else -> SaleRequest(
                                payment_method = "mbway",
                                mbway_phone = mbwayPhone
                            )
                        }

                        checkoutViewModel.createSale(request)
                    },
                    enabled = !cartViewModel.loading &&
                            !checkoutViewModel.loading &&
                            cartViewModel.error == null &&
                            cartViewModel.cartItems.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEE626B)
                    )
                ) {
                    Text("Pay Now")
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
