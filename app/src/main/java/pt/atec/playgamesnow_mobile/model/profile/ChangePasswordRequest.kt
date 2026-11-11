package pt.atec.playgamesnow_mobile.model.profile

data class ChangePasswordRequest(
    val current_password: String,
    val password: String,
    val password_confirmation: String
)