package com.example.littlelemon.nav

interface Destinations {
    val route: String
}

object Onboarding : Destinations {
    override val route = "Onboarding"
}

object Home : Destinations {
    override val route = "Home"
}

object Profile : Destinations {
    override val route = "Profile"
}

object DishDetails : Destinations {
    override val route = "Menu"
    const val argDishId = "dishId"
}
