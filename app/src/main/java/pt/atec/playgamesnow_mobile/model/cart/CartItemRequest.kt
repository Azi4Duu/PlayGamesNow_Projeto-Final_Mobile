package pt.atec.playgamesnow_mobile.model.cart

data class CartItemRequest(
    val game_platform_id: Int,
    val quantity: Int
)