package pt.atec.playgamesnow_mobile.model.profile

data class ProfileResponse(
    val data: Profile
)

data class Profile(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)