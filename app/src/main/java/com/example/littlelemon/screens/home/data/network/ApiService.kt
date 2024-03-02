package com.example.littlelemon.screens.home.data.network

import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItem
import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItemList
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getMenuItems(): Flow<ApiResult<NetworkMenuItemList>>
}
