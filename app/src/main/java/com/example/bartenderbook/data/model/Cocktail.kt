data class Cocktail(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val preparation: String,
    val timePreparation: Int,
    val imageResId: Int? = null,
    val imageUri: String? = null,
    val isAlcoholic: Boolean,
    var isFavorite: Boolean = false
)
