package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator
import pt.atec.playgamesnow_mobile.view.components.StockBadge
import pt.atec.playgamesnow_mobile.viewmodel.game.GameDetailViewModel

@Composable
fun GameDetailScreen(
    gamePlatformId: Int,
    onLoginRequired: () -> Unit,
    gameDetailViewModel: GameDetailViewModel = viewModel()
)
{
    var descriptionExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(gamePlatformId) {
            gameDetailViewModel.loadGamePlatform(gamePlatformId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeHeader()

        when{
            gameDetailViewModel.loading ->{
                LoadingIndicator(message = "Loading Game...")
            }

            gameDetailViewModel.error != null -> {
                Text("Error: ${gameDetailViewModel.error}")
            }

            gameDetailViewModel.gamePlatform != null -> {
                val gamePlatform = gameDetailViewModel.gamePlatform!!

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(15.dp)
                ) {

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(22.dp)
                            )
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color.White)
                            .padding(15.dp)
                    ) {
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Image(
                            painter = rememberAsyncImagePainter(
                                model = gamePlatform.game.image
                            ),
                            contentDescription = gamePlatform.game.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(428.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = gamePlatform.game.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = gamePlatform.platform.name,
                            fontSize = 16.sp
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF5F5F5))
                                .clickable {
                                    descriptionExpanded = !descriptionExpanded
                                }
                                .padding(14.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Description",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    imageVector = if (descriptionExpanded) {
                                        Icons.Default.KeyboardArrowUp
                                    } else {
                                        Icons.Default.KeyboardArrowDown
                                    },
                                    contentDescription = "Expand description"
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            if (!descriptionExpanded) {

                                Text(
                                    text = gamePlatform.game.description,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            AnimatedVisibility(
                                visible = descriptionExpanded
                            ) {

                                Text(
                                    text = gamePlatform.game.description
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                Text(
                                    text = "${gamePlatform.price} €",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                val currentPrice = gamePlatform.price.toDoubleOrNull()
                                val oldPrice = gamePlatform.old_price?.toDoubleOrNull()

                                if (
                                    oldPrice != null &&
                                    currentPrice != null &&
                                    oldPrice > currentPrice
                                ) {
                                    Text(
                                        text = "%.2f €".format(oldPrice),
                                        fontSize = 15.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.Gray,
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            StockBadge(
                                stock = gamePlatform.stock
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 20.dp),
                            color = Color(0xFFEAEAEA)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Button(
                                    onClick = {
                                        gameDetailViewModel.decreaseQuantity()
                                    },
                                    enabled = gameDetailViewModel.quantity > 1,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAAAAA))
                                ) {
                                    Text("-")
                                }

                                Text(
                                    text = gameDetailViewModel.quantity.toString(),
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Button(
                                    onClick = {
                                        gameDetailViewModel.increaseQuantity()
                                    },
                                    enabled = gameDetailViewModel.quantity < gamePlatform.stock,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAAAAA))
                                ) {
                                    Text("+")
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (gameDetailViewModel.addedToCart) {

                                Text(
                                    text = "Added to cart successfully!",
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    if (gameDetailViewModel.isLoggedIn()) {
                                        gameDetailViewModel.addToCart(gamePlatform.id)
                                    } else {
                                        onLoginRequired()
                                    }
                                },
                                enabled = gamePlatform.stock > 0 || !gameDetailViewModel.isLoggedIn(),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE626B))
                            ) {
                                Text(
                                    text = if (!gameDetailViewModel.isLoggedIn()) {
                                        "Login"
                                    } else if (gamePlatform.stock > 0) {
                                        "Add to Cart"
                                    } else {
                                        "No stock"
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}