package com.example.littlelemon.screens.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.littlelemon.screens.home.data.local.dao.MenuItemListDao
import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class MenuItemDatabase : RoomDatabase() {
    abstract fun menuItemListDao(): MenuItemListDao
}
