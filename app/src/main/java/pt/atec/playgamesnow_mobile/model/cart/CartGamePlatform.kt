package pt.atec.playgamesnow_mobile.model.cart

data class CartGamePlatform(
    val id: Int,
    val game: CartGame,
    val platform: CartPlatform,
    val price: String
)