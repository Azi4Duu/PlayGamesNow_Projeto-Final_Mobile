package pt.atec.playgamesnow_mobile.model.sale

data class SaleRequest(
    val payment_method: String,
    val cardholder_name: String? = null,
    val card_number: String? = null,
    val expiry_date: String? = null,
    val cvv: String? = null,
    val paypal_email: String? = null,
    val mbway_phone: String? = null
)