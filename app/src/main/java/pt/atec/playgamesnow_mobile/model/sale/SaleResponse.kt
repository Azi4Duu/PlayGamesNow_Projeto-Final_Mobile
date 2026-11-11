package pt.atec.playgamesnow_mobile.model.sale

data class SaleResponse(
    val message: String,
    val sale: SaleCheckout
)

data class SaleCheckout(
    val id: Int,
    val subtotal: Double,
    val discount: Double,
    val total: Double,
    val status: String,
    val payment_method: String,
    val items: List<SaleCheckoutItem>
)

data class SaleCheckoutItem(
    val title: String,
    val platform: String,
    val quantity: Int,
    val unit_price: Double,
    val item_total: Double,
    val keys: List<SaleKey>
)

data class SaleKey(
    val code: String
)