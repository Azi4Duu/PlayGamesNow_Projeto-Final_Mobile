package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import pt.atec.playgamesnow_mobile.model.game.GamePlatform

@Composable
fun GameCard(gamePlatform: GamePlatform, onClick: () -> Unit){
    Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ), onClick = onClick) {

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = gamePlatform.game.image
                ),
                contentDescription = gamePlatform.game.title,
                modifier = Modifier
                    .size(90.dp, 110.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.size(14.dp))

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = gamePlatform.game.title,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = gamePlatform.platform.name)
                Spacer(modifier = Modifier.height(6.dp))
                Row() {

                    val currentPrice = gamePlatform.price.toDoubleOrNull()
                    val oldPrice = gamePlatform.old_price?.toDoubleOrNull()

                    Text(
                        text = "${gamePlatform.price} €",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    if (
                        oldPrice != null &&
                        currentPrice != null &&
                        oldPrice > currentPrice
                    ) {
                        Text(
                            text = "%.2f €".format(oldPrice),
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                StockBadge(stock = gamePlatform.stock)
            }

        }
    }
}