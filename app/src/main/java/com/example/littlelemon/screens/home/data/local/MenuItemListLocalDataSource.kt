package com.example.littlelemon.screens.home.data.local

import com.example.littlelemon.screens.home.data.local.dao.MenuItemListDao
import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity
import kotlinx.coroutines.CoroutineDispatcher
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

    fun getMenuItemList() = menuItemDao.getAllMenuItemList()
}
