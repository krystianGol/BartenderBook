package com.example.bartenderbook.data.repository

import Cocktail
import com.example.bartenderbook.R

object HardcodedCocktailRepository {

    private val cocktails = mutableListOf(
        Cocktail(
            id = 1,
            name = "Mojito",
            ingredients = listOf("Rum", "Limonka", "Mięta", "Cukier", "Woda gazowana"),
            preparation = "Ugnieć miętę z cukrem, dodaj limonkę i rum, dopełnij wodą gazowaną.",
            timePreparation = 300,
            imageResId = R.drawable.mojito,
            isAlcoholic = false
        ),
        Cocktail(
            id = 2,
            name = "Pina Colada",
            ingredients = listOf("Rum", "Śmietanka kokosowa", "Sok ananasowy"),
            preparation = "Zmiksuj wszystkie składniki z lodem do uzyskania gładkiej konsystencji.",
            timePreparation = 240,
            imageResId = R.drawable.pina_colada,
            isAlcoholic = true
        ),
        Cocktail(
            id = 3,
            name = "Espresso Martini",
            ingredients = listOf("Wódka", "Espresso", "Likier kawowy"),
            preparation = "Wstrząśnij wszystkie składniki z lodem i przecedź do kieliszka koktajlowego.",
            timePreparation = 180,
            imageResId = R.drawable.espresso_martini,
            isAlcoholic = true
        ),
        Cocktail(
            id = 4,
            name = "Blue Lagoon",
            ingredients = listOf("Wódka", "Blue Curaçao", "Lemoniada"),
            preparation = "Wlej składniki do szklanki z lodem i delikatnie wymieszaj.",
            timePreparation = 150,
            imageResId = R.drawable.blue_lagoon,
            isAlcoholic = true
        ),
        Cocktail(
            id = 5,
            name = "Sunset Kiss",
            ingredients = listOf("Sok pomarańczowy", "Grenadyna", "Sok z cytryny"),
            preparation = "Wlej składniki warstwowo do szklanki z lodem i lekko zamieszaj.",
            timePreparation = 120,
            imageResId = R.drawable.sunset_kiss,
            isAlcoholic = false
        ),
        Cocktail(
            id = 6,
            name = "Cuba Libre",
            ingredients = listOf("Rum", "Cola", "Limonka"),
            preparation = "Wlej rum do szklanki z lodem, dodaj colę i sok z limonki.",
            timePreparation = 90,
            imageResId = R.drawable.cuba_libre,
            isAlcoholic = true
        ),
        Cocktail(
            id = 7,
            name = "Owocowy Raj",
            ingredients = listOf("Sok jabłkowy", "Sok pomarańczowy", "Sok z granatu"),
            preparation = "Wymieszaj wszystkie soki i podawaj z kostkami lodu.",
            timePreparation = 60,
            imageResId = R.drawable.owocowy_raj,
            isAlcoholic = false
        )
    )


    fun getCocktails(): List<Cocktail> = cocktails

    fun getCocktailsByCategory(category: String): List<Cocktail> =
        when (category.lowercase()) {
            "alcohol" -> cocktails.filter { it.isAlcoholic }
            "non_alcohol" -> cocktails.filter { !it.isAlcoholic }
            else -> cocktails
        }

    fun getCocktailById(id: Int): Cocktail? =
        cocktails.find { it.id == id }

    fun addCocktail(cocktail: Cocktail) {
        cocktails.add(cocktail)
    }

    fun toggleFavorite(cocktailId: Int) {
        val cocktail = cocktails.find { it.id == cocktailId }
        cocktail?.isFavorite = !(cocktail?.isFavorite ?: false)
    }

    fun getFavoriteCocktails(): List<Cocktail> {
        return cocktails.filter { it.isFavorite }
    }

}
