package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import pt.atec.playgamesnow_mobile.model.cart.CartItem

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onClick: () -> Unit,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = cartItem.game_platform.game.image
                ),
                contentDescription = cartItem.game_platform.game.title,
                modifier = Modifier
                    .size(90.dp, 110.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.size(14.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            ) {

                Text(
                    text = cartItem.game_platform.game.title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = cartItem.game_platform.platform.name
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "${cartItem.game_platform.price} €"
                )

                Spacer(
                    modifier = Modifier.height(1.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = onDecrease,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAAAAA))
                    ) {
                        Text("-")
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 14.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = onIncrease,
                        enabled = cartItem.quantity < cartItem.max_quantity,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAAAAA))
                    ) {
                        Text("+")
                    }

                    Spacer(modifier = Modifier.width(60.dp))

                    Text(
                        text = "Total: %.2f €".format(cartItem.item_total),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}