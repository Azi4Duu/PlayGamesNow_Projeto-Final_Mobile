package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import pt.atec.playgamesnow_mobile.viewmodel.game.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.FilterPanel
import pt.atec.playgamesnow_mobile.view.components.GameCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator
import pt.atec.playgamesnow_mobile.view.components.SearchBar

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), onGameClick: (Int) -> Unit){

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {
                homeViewModel.loadGamePlatforms()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HomeHeader()

        SearchBar(
            search = homeViewModel.search,
            onSearchChange = {homeViewModel.updateSearch(it)},
            onFilterClick = {homeViewModel.toggleFilters()}
        )

        AnimatedVisibility(
            visible = homeViewModel.showFilters
        ) {
            FilterPanel(
                activeFilter = homeViewModel.activeFilter,
                platforms = homeViewModel.platforms,
                onOpenFilter = { homeViewModel.openFilter(it) },
                onBack = { homeViewModel.closeFilter() },
                selectedPlatforms = homeViewModel.selectedPlatforms,
                onPlatformChange = {homeViewModel.togglePlatform(it)},
                categories = homeViewModel.categories,
                selectedCategories = homeViewModel.selectedCategories,
                onCategoryChange = { homeViewModel.toggleCategory(it) },
                selectedStock = homeViewModel.selectedStock,
                onStockChange = { homeViewModel.toggleStock(it) },
                minPrice = homeViewModel.minPrice,
                maxPrice = homeViewModel.maxPrice,
                onMinPriceChange = { homeViewModel.updateMinPrice(it) },
                onMaxPriceChange = { homeViewModel.updateMaxPrice(it) },
                onClearFilters = { homeViewModel.clearFilters() }
            )
        }

        when{
            homeViewModel.loading->{
                LoadingIndicator(message = "Loading Games...")
            }
            homeViewModel.error != null->{
                Text("Error: ${homeViewModel.error}")
            }
            else->{

                val filteredGamePlatforms = homeViewModel.gamePlatforms.filter { gamePlatform ->

                    val matchesSearch = gamePlatform.game.title.contains(
                        homeViewModel.search,
                        ignoreCase = true
                    )

                    val matchesPlatform =
                        homeViewModel.selectedPlatforms.isEmpty() ||
                        homeViewModel.selectedPlatforms.contains(gamePlatform.platform.id)

                    val matchesCategory =
                        homeViewModel.selectedCategories.isEmpty() ||
                        gamePlatform.game.categories.any { category ->
                            homeViewModel.selectedCategories.contains(category.id)
                        }

                    val matchesStock =
                        homeViewModel.selectedStock.isEmpty() ||
                        (homeViewModel.selectedStock.contains("available") && gamePlatform.stock > 0) ||
                        (homeViewModel.selectedStock.contains("unavailable") && gamePlatform.stock == 0)

                    val price = gamePlatform.price.toDoubleOrNull() ?: 0.0

                    val minPrice = homeViewModel.minPrice.toDoubleOrNull()
                    val maxPrice = homeViewModel.maxPrice.toDoubleOrNull()

                    val matchesPrice =
                        (minPrice == null || price >= minPrice) &&
                        (maxPrice == null || price <= maxPrice)

                    matchesPlatform && matchesSearch && matchesCategory && matchesStock && matchesPrice
                }

                LazyColumn(
                    modifier = Modifier.weight(1f).padding(top = 6.dp)
                ) {
                    items(filteredGamePlatforms){
                        gamePlatform ->
                        GameCard(gamePlatform = gamePlatform, onClick = {onGameClick(gamePlatform.id)})
                    }
                }
            }
        }
    }
}

