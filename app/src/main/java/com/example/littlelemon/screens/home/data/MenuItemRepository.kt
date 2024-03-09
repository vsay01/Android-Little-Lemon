package com.example.littlelemon.screens.home.data

import com.example.littlelemon.screens.home.data.local.MenuItemListLocalDataSource
import com.example.littlelemon.screens.home.data.local.entities.MenuItemEntity
import com.example.littlelemon.screens.home.data.local.entities.asExternalModel
import com.example.littlelemon.screens.home.data.model.MenuItem
import com.example.littlelemon.screens.home.data.model.MenuItemCategory
import com.example.littlelemon.screens.home.data.network.ApiResult
import com.example.littlelemon.screens.home.data.network.MenuItemListRemoteDataSource
import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItem
import com.example.littlelemon.screens.home.data.network.models.asEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class MenuItemRepository @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val menuItemListLocalDataSource: MenuItemListLocalDataSource,
    private val menuItemListRemoteDataSource: MenuItemListRemoteDataSource,
) : IMenuItemRepository {

    fun getMenuItemCategories() = listOf(
        MenuItemCategory("Starters"),
        MenuItemCategory("Mains"),
        MenuItemCategory("Desserts"),
        MenuItemCategory("Drinks"),
        MenuItemCategory("Side")
    )

    override fun getMenuItemList(query: String?): Flow<List<MenuItem>> {
        return menuItemListLocalDataSource.getMenuItemList(query)
            .map { localData -> localData.map { it.asExternalModel() } }
    }

    override fun fetchNewMenuItemList(): Flow<ApiResult<List<MenuItem>>> = flow {
        emit(ApiResult.Loading())
        try {
            val networkMenuItemList = menuItemListRemoteDataSource.getRemoteMenuItemList()
            if (networkMenuItemList.menu.isNotEmpty()) {
                val menuItemListEntity = networkMenuItemList.menu.map(NetworkMenuItem::asEntity)
                menuItemListLocalDataSource.insertMenuItemList(menuItemListEntity)
                emit(
                    ApiResult.Success(
                        menuItemListEntity.map(MenuItemEntity::asExternalModel)
                    )
                )
            } else {
                emit(ApiResult.Error("networkMenuItemList menu is null or empty"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }.flowOn(coroutineDispatcher)
}
