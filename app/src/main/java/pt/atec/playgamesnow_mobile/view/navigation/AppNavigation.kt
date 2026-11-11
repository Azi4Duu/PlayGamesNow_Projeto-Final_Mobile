package pt.atec.playgamesnow_mobile.view.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.atec.playgamesnow_mobile.data.TokenManager
import pt.atec.playgamesnow_mobile.view.components.BottomNavigationBar
import pt.atec.playgamesnow_mobile.view.screens.CartScreen
import pt.atec.playgamesnow_mobile.view.screens.ChangePasswordScreen
import pt.atec.playgamesnow_mobile.view.screens.CheckoutScreen
import pt.atec.playgamesnow_mobile.view.screens.EditProfileScreen
import pt.atec.playgamesnow_mobile.view.screens.GameDetailScreen
import pt.atec.playgamesnow_mobile.view.screens.HomeScreen
import pt.atec.playgamesnow_mobile.view.screens.LoginScreen
import pt.atec.playgamesnow_mobile.view.screens.ProfileScreen
import pt.atec.playgamesnow_mobile.view.screens.PurchaseDetailScreen
import pt.atec.playgamesnow_mobile.view.screens.PurchaseSuccessScreen
import pt.atec.playgamesnow_mobile.view.screens.PurchasesScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = {
                    navController.navigate("home")
                },
                onCartClick = {
                    if (TokenManager(context).getToken() != null) {
                        navController.navigate("cart")
                    } else {
                        navController.navigate("login")
                    }
                },
                onProfileClick = {
                    if (TokenManager(context).getToken() != null) {
                        navController.navigate("profile")
                    } else {
                        navController.navigate("login")
                    }
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {

            composable("login") {
                LoginScreen(onLoginClick = {navController.popBackStack()})
            }

            composable("home") {
                HomeScreen(
                    onGameClick = { gamePlatformId ->
                        navController.navigate(
                            "gameDetail/$gamePlatformId"
                        )
                    }
                )
            }

            composable("gameDetail/{gamePlatformId}") { backStackEntry ->

                val gamePlatformId =
                    backStackEntry.arguments
                        ?.getString("gamePlatformId")
                        ?.toIntOrNull()

                if (gamePlatformId != null) {
                    GameDetailScreen(
                        gamePlatformId = gamePlatformId,
                        onLoginRequired = {navController.navigate("login")}
                    )
                }
            }

            composable("cart") {
                CartScreen(
                    onGameClick = { gamePlatformId ->
                        navController.navigate(
                            "gameDetail/$gamePlatformId"
                        )
                    },
                    onCheckoutClick = {
                        navController.navigate("checkout")
                    },
                    onBrowseGames = {navController.navigate("home"){ launchSingleTop = true} }
                )
            }

            composable("checkout") {
                CheckoutScreen(
                    onPurchaseSuccess = { saleId ->
                        navController.navigate("purchaseSuccess/$saleId") {
                            popUpTo("cart") {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = "purchaseSuccess/{saleId}", arguments = listOf(
                    navArgument("saleId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val saleId = backStackEntry.arguments?.getInt("saleId") ?: 0

                PurchaseSuccessScreen(
                    saleId = saleId,

                    onContinueShopping = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("profile") {
                ProfileScreen(
                    onPurchasesClick = { navController.navigate("purchases") },
                    onEditProfileClick = { navController.navigate("editProfile") },
                    onChangePasswordClick = { navController.navigate("changePassword") },
                    onLogoutClick = {
                        TokenManager(context).clearToken()
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("purchases") {
                PurchasesScreen(
                    onPurchaseClick = { purchaseId ->
                        navController.navigate("purchaseDetail/$purchaseId")
                    }
                )
            }

            composable(route = "purchaseDetail/{purchaseId}", arguments = listOf(
                    navArgument("purchaseId") {
                        type = NavType.IntType
                    })
            ) { backStackEntry ->
                val purchaseId =
                    backStackEntry.arguments?.getInt("purchaseId") ?: 0
                PurchaseDetailScreen(
                    purchaseId = purchaseId
                )
            }

            composable("editProfile") {

                EditProfileScreen(
                    onProfileUpdated = {
                        navController.popBackStack()
                    }
                )
            }

            composable("changePassword") {

                ChangePasswordScreen(
                    onPasswordUpdated = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}