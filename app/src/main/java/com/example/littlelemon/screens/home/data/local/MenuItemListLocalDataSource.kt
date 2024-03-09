package com.example.littlelemon.screens.home.data.local

import com.example.littlelemon.screens.home.data.local.dao.MenuItemListDao
import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuItemListLocalDataSource @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val menuItemDao: MenuItemListDao,
) {
    suspend fun insertMenuItemList(menuItemList: List<MenuItemEntity>) {
        withContext(coroutineDispatcher) {
            menuItemDao.insertMenuItemList(menuItemList)
        }
    }

    fun getMenuItemList(
        query: String? = null,
        category: String? = null
    ): Flow<List<MenuItemEntity>> {
        return if (!category.isNullOrEmpty()) {
            menuItemDao.getMenuItemByCategory(category)
        } else if (!query.isNullOrEmpty()) {
            menuItemDao.searchMenuItemByText(query)
        } else {
            menuItemDao.getAllMenuItemList()
        }
    }
}
