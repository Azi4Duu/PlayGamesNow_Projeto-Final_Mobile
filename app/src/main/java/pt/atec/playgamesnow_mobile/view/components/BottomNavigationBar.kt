package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.atec.playgamesnow_mobile.R

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))

    ) {

        Image(
            painter = painterResource(
                id = R.drawable.pageheading
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxSize()
        ) {

            NavigationBarItem(
                selected = currentRoute == "cart",
                onClick = onCartClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFFEE626B)
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart"
                    )
                },
                label = {
                    Text("Cart")
                }
            )

            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = onHomeClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFFEE626B)
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                },
                label = {
                    Text("Home")
                }
            )

            NavigationBarItem(
                selected = currentRoute == "profile",
                onClick = onProfileClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFFEE626B)
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile"
                    )
                },
                label = {
                    Text("Profile")
                }
            )
        }
    }

}