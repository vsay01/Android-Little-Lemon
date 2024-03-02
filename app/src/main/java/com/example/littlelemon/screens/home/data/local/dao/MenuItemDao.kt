package com.example.littlelemon.screens.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {
    @Insert
    fun insertMenuItem(menuItem: MenuItemEntity)

    @Query("SELECT * from menu_table")
    fun getMenuItem(): Flow<List<MenuItemEntity>>
}
