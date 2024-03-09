package com.example.littlelemon.screens.home.data

import com.example.littlelemon.screens.home.data.model.MenuItem
import com.example.littlelemon.screens.home.data.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface IMenuItemRepository {
    fun getMenuItemList(query: String? = null): Flow<List<MenuItem>>

    fun fetchNewMenuItemList(): Flow<ApiResult<List<MenuItem>>>
}
