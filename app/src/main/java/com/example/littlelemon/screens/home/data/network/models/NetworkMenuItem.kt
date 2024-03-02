package com.example.littlelemon.screens.home.data.network.models

import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity
import com.example.littlelemon.screens.home.data.model.MenuItem
import kotlinx.serialization.Serializable

@Serializable
class NetworkMenuItemList(
    val menu: List<NetworkMenuItem>
)

/**
 * Network representation of [MenuItem]
 */
@Serializable
class NetworkMenuItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)

/**
 * Converts the network model to the local model for persisting
 * by the local data source
 */
fun NetworkMenuItem.asEntity() = MenuItemEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category
)

fun NetworkMenuItem.asModel() = MenuItem(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category
)
