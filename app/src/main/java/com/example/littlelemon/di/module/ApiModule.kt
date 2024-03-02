package com.example.littlelemon.di.module

import android.content.Context
import android.telecom.Call
import com.example.littlelemon.screens.home.data.network.ApiService
import com.example.littlelemon.screens.home.data.network.ApiServiceImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android){
            install(Logging){
                level= LogLevel.ALL
            }
            install(DefaultRequest){
                url("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                //header("X-Api-Key", BuildConfig.API_KEY)
            }
            install(ContentNegotiation){
                json(contentType = ContentType.Any)
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService = ApiServiceImpl(httpClient)

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
