package com.example.littlelemon.screens.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenuItemList(menuItemList: List<MenuItemEntity>): List<Long>

    @Query("SELECT * from menu_table")
    fun getAllMenuItemList(): Flow<List<MenuItemEntity>>

    @Query(
        """
          SELECT *
          FROM menu_table
          WHERE title LIKE '%' || :query || '%' OR
                description LIKE '%' || :query || '%'
        """
    )
    fun searchMenuItemByText(query: String): Flow<List<MenuItemEntity>>

    @Query(
        """
          SELECT *
          FROM menu_table
          WHERE category LIKE :category
        """
    )
    fun getMenuItemByCategory(category: String): Flow<List<MenuItemEntity>>
}
