package com.example.littlelemon.screens.home.data.network

import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItem
import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItemList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override suspend fun getMenuItems(): Flow<ApiResult<NetworkMenuItemList>> = flow{
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get("").body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }
}