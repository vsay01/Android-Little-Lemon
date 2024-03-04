package com.example.littlelemon.screens.home.data.network

import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItemList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MenuItemListRemoteDataSource @Inject constructor(private val httpClient: HttpClient) :
    ApiService {
    override suspend fun getRemoteMenuItemList(): NetworkMenuItemList =
        httpClient.get("").body()
}