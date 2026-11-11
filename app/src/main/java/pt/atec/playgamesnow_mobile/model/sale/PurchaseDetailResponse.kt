package pt.atec.playgamesnow_mobile.model.sale

import pt.atec.playgamesnow_mobile.model.cart.Coupon

data class PurchaseDetailResponse(
    val data: PurchaseDetail
)

data class PurchaseDetail(
    val id: Int,
    val subtotal: String,
    val discount: String,
    val coupon: Coupon? = null,
    val total: String,
    val status: String,
    val payment_method: String,
    val created_at: String,
    val items: List<PurchaseDetailItem>
)

data class PurchaseDetailItem(
    val id: Int,
    val quantity: Int,
    val unit_price: String,
    val game: PurchaseGame,
    val platform: PurchasePlatform,
    val keys: List<PurchaseGameKey>
)

data class PurchaseGame(
    val id: Int,
    val title: String,
    val image: String?
)

data class PurchasePlatform(
    val id: Int,
    val name: String
)

data class PurchaseGameKey(
    val id: Int,
    val code: String
)