package pt.atec.playgamesnow_mobile.model.cart

data class CartItem(
    val id: Int,
    val quantity: Int,
    val max_quantity: Int,
    val game_platform: CartGamePlatform,
    val item_total: Double
)