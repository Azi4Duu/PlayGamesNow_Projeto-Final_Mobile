package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator
import pt.atec.playgamesnow_mobile.view.components.PurchaseCard
import pt.atec.playgamesnow_mobile.viewmodel.sale.PurchasesViewModel

@Composable
fun PurchasesScreen(
    onPurchaseClick: (Int) -> Unit,
    purchasesViewModel: PurchasesViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        purchasesViewModel.loadPurchases()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Text(
            text = "My Purchases",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        when {
            purchasesViewModel.loading -> {
                LoadingIndicator(message = "Loading purchases...")
            }

            purchasesViewModel.error != null -> {
                Text(
                    text = "Error: ${purchasesViewModel.error}",
                    modifier = Modifier.padding(16.dp)
                )
            }

            purchasesViewModel.purchases.isEmpty() -> {
                Text(
                    text = "You haven't made any purchases yet.",
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(purchasesViewModel.purchases) { purchase ->

                        PurchaseCard(
                            purchase = purchase,
                            onClick = {
                                onPurchaseClick(purchase.id)
                            }
                        )
                    }
                }
            }
        }
    }
}