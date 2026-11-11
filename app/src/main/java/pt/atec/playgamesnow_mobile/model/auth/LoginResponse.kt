package pt.atec.playgamesnow_mobile.model.auth

data class LoginResponse (
    val message: String,
    val token: String,
    val user: LoginUser
)