package com.example.littlelemon.home.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.littlelemon.home.data.DishRepository

@Composable
fun HomeScreen(navController: NavHostController, homeBodyScreenState: HomeBodyScreenState) {
    Column {
        TopAppBar()
        BodyPanel(navController, homeBodyScreenState)
    }
}
