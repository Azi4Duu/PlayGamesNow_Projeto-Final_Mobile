package pt.atec.playgamesnow_mobile.model.sale

data class Purchase(
    val id: Int,
    val title: String,
    val status: String,
    val payment_method: String,
    val total: String,
    val created_at: String
)