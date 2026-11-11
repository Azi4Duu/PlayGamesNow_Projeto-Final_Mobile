package pt.atec.playgamesnow_mobile.model.game

class Game(
    val id: Int,
    val title: String,
    val description: String,
    val release_date: String,
    val image: String,
    val categories: List<Category>
)