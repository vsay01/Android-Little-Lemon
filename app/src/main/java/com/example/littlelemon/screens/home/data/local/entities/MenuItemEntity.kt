package com.example.littlelemon.screens.home.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.littlelemon.screens.home.data.model.MenuItem

/**
 * Defines a menu item entity
 */
@Entity(tableName = "menu_table")
data class MenuItemEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)

/**
 * Converts the local model to the external model for use
 * by layers external to the data layer
 */
fun MenuItemEntity.asExternalModel() = MenuItem(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category
)
