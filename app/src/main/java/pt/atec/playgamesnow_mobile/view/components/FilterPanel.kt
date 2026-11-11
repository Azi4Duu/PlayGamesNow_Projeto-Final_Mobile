package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.playgamesnow_mobile.model.game.Category
import pt.atec.playgamesnow_mobile.model.game.Platform

@Composable
fun FilterPanel(
    activeFilter: String?,
    onOpenFilter: (String) -> Unit,
    onBack: () -> Unit,
    platforms: List<Platform>,
    onPlatformChange: (Int) -> Unit,
    selectedPlatforms: Set<Int>,
    categories: List<Category>,
    selectedCategories: Set<Int>,
    onCategoryChange: (Int) -> Unit,
    selectedStock: Set<String>,
    onStockChange: (String) -> Unit,
    minPrice: String,
    maxPrice: String,
    onMinPriceChange: (String) -> Unit,
    onMaxPriceChange: (String) -> Unit,
    onClearFilters: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        if (activeFilter == null) {

            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = "Filters",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Limpar filtros",
                    modifier = Modifier
                        .clickable {
                            onClearFilters()
                        },
                    color = Color(0xFF0071F8),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            FilterMenuItem(
                text = if (selectedPlatforms.isEmpty()){
                    "Platforms"
                }else{
                    "Platforms (${selectedPlatforms.size})"
                },
                onClick = {
                    onOpenFilter("platform")
                }
            )

            FilterMenuItem(
                text = if (selectedCategories.isEmpty()){
                    "Genres"
                }else{
                    "Genres (${selectedCategories.size})"
                },
                onClick = {
                    onOpenFilter("genre")
                }
            )

            FilterMenuItem(
                text = if (selectedStock.isEmpty()){
                    "Stock"
                }else{
                    "Stock (${selectedStock.size})"
                },
                onClick = {
                    onOpenFilter("stock")
                }
            )

            FilterMenuItem(
                text = "Price",
                onClick = {
                    onOpenFilter("price")
                }
            )
        }else{

            when (activeFilter) {

                "platform" -> {
                    PlatformFilterList(
                        platforms = platforms,
                        onBack = onBack,
                        onPlatformClick = onPlatformChange,
                        selectedPlatforms = selectedPlatforms
                    )
                }

                "genre" -> {
                    CategoryFilterList(
                        categories = categories,
                        selectedCategories = selectedCategories,
                        onBack = onBack,
                        onCategoryClick = onCategoryChange
                    )
                }

                "stock" -> {
                    StockFilterList(
                        selectedStock = selectedStock,
                        onBack = onBack,
                        onStockClick = onStockChange
                    )
                }

                "price" -> {
                    PriceFilterList(
                        onBack = onBack,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                        onMinPriceChange = onMinPriceChange,
                        onMaxPriceChange = onMaxPriceChange
                    )
                }
            }
        }

    }
}

@Composable
fun FilterMenuItem(
    text: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            fontSize = 16.sp
        )

        Text(
            text = ">"
        )
    }
}

@Composable
fun PlatformFilterList(
    onBack: () -> Unit,
    platforms: List<Platform>,
    onPlatformClick: (Int)-> Unit,
    selectedPlatforms: Set<Int>
) {

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onBack()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "", modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Platforms",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        platforms.forEach { platform ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onPlatformClick(platform.id)},
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedPlatforms.contains(platform.id),
                    onCheckedChange = {
                        onPlatformClick(platform.id)
                    }
                )
                Text(text = platform.name)
            }
        }
    }
}

@Composable
fun CategoryFilterList(
    onBack: () -> Unit,
    categories: List<Category>,
    selectedCategories: Set<Int>,
    onCategoryClick: (Int) -> Unit
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onBack()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Voltar",
                modifier = Modifier.size(18.dp)
            )
            Spacer(
                modifier = Modifier.width(6.dp)
            )
            Text(
                text = "Géneros",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        categories.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onCategoryClick(category.id)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedCategories.contains(category.id),
                    onCheckedChange = {
                        onCategoryClick(category.id)
                    }
                )
                Text(
                    text = category.name
                )
            }
        }
    }
}

@Composable
fun StockFilterList(
    onBack: () -> Unit,
    selectedStock: Set<String>,
    onStockClick: (String) -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onBack()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Voltar",
                modifier = Modifier.size(18.dp)
            )
            Spacer(
                modifier = Modifier.width(6.dp)
            )
            Text(
                text = "Stock",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onStockClick("available")
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selectedStock.contains("available"),
                onCheckedChange = {
                    onStockClick("available")
                }
            )
            Text("In stock")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onStockClick("unavailable")
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selectedStock.contains("unavailable"),
                onCheckedChange = {
                    onStockClick("unavailable")
                }
            )
            Text("No stock")
        }
    }
}

@Composable
fun PriceFilterList(
    onBack: () -> Unit,
    minPrice: String,
    maxPrice: String,
    onMinPriceChange: (String) -> Unit,
    onMaxPriceChange: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onBack()
                }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Voltar",
                modifier = Modifier.size(18.dp)
            )
            Spacer(
                modifier = Modifier.width(6.dp)
            )
            Text(
                text = "Price (EUR)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = minPrice,
                onValueChange = {
                    onMinPriceChange(it)
                },
                label = {
                    Text("Min.")
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Text(
                text = " - ",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            OutlinedTextField(
                value = maxPrice,
                onValueChange = {
                    onMaxPriceChange(it)
                },
                label = {
                    Text("Max.")
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
        }
    }
}