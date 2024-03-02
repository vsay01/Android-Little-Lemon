package com.example.littlelemon.di.module

import android.content.Context
import androidx.room.Room
import com.example.littlelemon.screens.home.data.local.MenuItemDatabase
import com.example.littlelemon.screens.home.data.local.dao.MenuItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: MenuItemDatabase): MenuItemDao {
        return appDatabase.menuItemDao()
    }
}
