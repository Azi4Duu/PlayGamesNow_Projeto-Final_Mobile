package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.CartItemCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.viewmodel.cart.CartViewModel
import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.CouponSection
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator

@Composable
fun CartScreen(
    onGameClick: (Int) -> Unit,
    onCheckoutClick: () -> Unit,
    onBrowseGames:() -> Unit,
    cartViewModel: CartViewModel = viewModel(),
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        LaunchedEffect(Unit) {
            cartViewModel.loadCart()
        }

        when {

            cartViewModel.loading -> {
                LoadingIndicator(message = "Loading cart...")
            }

            cartViewModel.error != null -> {
                Text("Error: ${cartViewModel.error}")
            }

            else -> {
                if (cartViewModel.cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CheckoutCard {

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    tint = Color(0xFF0071F8),
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    text = "Your Cart Is Empty",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = "Looks like you haven't added any games yet.",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(Modifier.height(24.dp))
                                Button(
                                    onClick = onBrowseGames,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEE626B)
                                    )
                                ) {
                                    Text("Browse Games")
                                }
                            }
                        }
                    }
                } else {

                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn (modifier = Modifier.weight(1f)) {
                            items(cartViewModel.cartItems) { cartItem ->
                                CartItemCard(
                                    cartItem = cartItem,
                                    onClick = { onGameClick(cartItem.game_platform.id) },
                                    onDecrease = {cartViewModel.decreaseQuantity(cartItem)},
                                    onIncrease = {cartViewModel.increaseQuantity(cartItem)}
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 8.dp,
                                bottom = 12.dp
                            )
                        ){
                            CheckoutCard {


                                CouponSection(
                                    coupon = cartViewModel.coupon,
                                    couponCode = cartViewModel.couponCode,
                                    couponLoading = cartViewModel.couponLoading,
                                    couponError = cartViewModel.couponError,
                                    couponSuccess = cartViewModel.couponSuccess,
                                    onCouponCodeChange = cartViewModel::updateCouponCode,
                                    onApplyCoupon = cartViewModel::applyCoupon,
                                    onRemoveCoupon = cartViewModel::removeCoupon
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 14.dp),
                                    color = Color(0xFFEAEAEA)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Subtotal",
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = "%.2f €".format(cartViewModel.subtotal),
                                        fontSize = 16.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Discount",
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = "-%.2f €".format(cartViewModel.discount),
                                        fontSize = 16.sp,
                                        color = Color(0xFF2E7D32)
                                    )
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 14.dp),
                                    color = Color(0xFFEAEAEA)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = onCheckoutClick,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEE626B)
                                    )
                                ) {
                                    Text("Checkout")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}