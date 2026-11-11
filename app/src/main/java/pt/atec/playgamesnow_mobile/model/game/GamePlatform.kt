package pt.atec.playgamesnow_mobile.model.game

class GamePlatform(
    val id: Int,
    val game: Game,
    val platform: Platform,
    val price: String,
    val old_price: String?,
    val active: Int,
    val stock: Int
)