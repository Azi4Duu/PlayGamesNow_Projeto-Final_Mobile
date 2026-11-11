package pt.atec.playgamesnow_mobile.model.cart

data class CartResponse(
    val items: List<CartItem>,
    val coupon: Coupon? = null,
    val subtotal: Double,
    val discount: Double = 0.0,
    val total: Double = 0.0
)