package com.example.littlelemon.screens.home.data

import com.example.littlelemon.screens.home.data.model.MenuItemCategory
import com.example.littlelemon.screens.home.data.network.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MenuItemRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllMenuItems() = apiService.getMenuItems()

    fun getMenuItemCategories() = listOf(
        MenuItemCategory("Starters"),
        MenuItemCategory("Mains"),
        MenuItemCategory("Desserts"),
        MenuItemCategory("Drinks"),
        MenuItemCategory("Side")
    )
}
