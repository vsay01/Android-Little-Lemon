package com.example.littlelemon.screens.home.data.model

/**
 * External data layer representation of a MenuItem
 */
data class MenuItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            title,
            title.lowercase(),
            title.uppercase(),
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
