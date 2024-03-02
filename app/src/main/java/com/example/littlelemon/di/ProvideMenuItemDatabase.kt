package com.example.littlelemon.di

import android.content.Context
import androidx.room.Room
import com.example.littlelemon.screens.home.data.local.MenuItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): MenuItemDatabase = Room.databaseBuilder(
        context,
        MenuItemDatabase::class.java,
        "menu-item-database",
    ).build()
}
