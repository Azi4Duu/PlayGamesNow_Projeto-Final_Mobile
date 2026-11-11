package pt.atec.playgamesnow_mobile.model.profile

data class ProfileUpdateRequest(
    val name: String,
    val username: String,
    val email: String
)