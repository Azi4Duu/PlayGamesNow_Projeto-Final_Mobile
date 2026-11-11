
package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.atec.playgamesnow_mobile.model.cart.Coupon
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun CouponSection(
    coupon: Coupon?,
    couponCode: String,
    couponLoading: Boolean,
    couponError: String?,
    couponSuccess: String?,
    onCouponCodeChange: (String) -> Unit,
    onApplyCoupon: () -> Unit,
    onRemoveCoupon: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Coupon",
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = coupon?.code ?: "None applied",
                    color = if (coupon != null)
                        Color(0xFF0071F8)
                    else
                        Color.Gray
                )

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded)
                        "Collapse coupon"
                    else
                        "Expand coupon"
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (coupon == null) {
                    RoundedTextField(
                        value = couponCode,
                        onValueChange = onCouponCodeChange,
                        label = "Coupon Code",
                        placeholder = "Enter your coupon code",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onApplyCoupon,
                        enabled = !couponLoading &&
                                couponCode.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEE626B)
                        )
                    ) {
                        Text(
                            if (couponLoading) "Applying..."
                            else "Apply Coupon"
                        )
                    }
                } else {
                    Text(
                        text = if (
                            coupon.discount_type == "percentage"
                        ) {
                            "${coupon.discount_value}% off"
                        } else {
                            "${coupon.discount_value} € off"
                        },
                        color = Color.Gray
                    )

                    OutlinedButton(
                        onClick = onRemoveCoupon,
                        enabled = !couponLoading,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            if (couponLoading) "Removing..."
                            else "Remove Coupon"
                        )
                    }
                }

                couponError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                couponSuccess?.let {
                    Text(
                        text = it,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }
    }
}
