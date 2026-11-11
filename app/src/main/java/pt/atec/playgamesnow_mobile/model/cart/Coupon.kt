package pt.atec.playgamesnow_mobile.model.cart

data class Coupon(
    val id: Int,
    val code: String,
    val discount_type: String,
    val discount_value: String
)
