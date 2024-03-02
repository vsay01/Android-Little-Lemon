package com.example.littlelemon.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.screens.home.data.MenuItemRepository
import com.example.littlelemon.screens.home.data.network.ApiResult
import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItem
import com.example.littlelemon.screens.home.data.network.models.NetworkMenuItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val menuItemRepository: MenuItemRepository
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _categories = MutableStateFlow(menuItemRepository.getMenuItemCategories())
    val categories = _categories.asStateFlow()

    private val _menuItems = MutableStateFlow<ApiResult<NetworkMenuItemList>>(ApiResult.Loading())
    val menuItems = _menuItems.asStateFlow()

    /*    val dishes = searchText
            .debounce(200L)
            .onEach { _isSearching.update { true } }
            .combine(_dishes) { text, dishes ->
                if (text.isBlank()) {
                    dishes
                } else {
                    delay(500L)
                    dishes.filter {
                        it.doesMatchSearchQuery(text)
                    }
                }
            }
            .onEach { _isSearching.update { false } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(1000),
                _dishes.value
            )*/

    init {
        getMenuItems()
    }

    private fun getMenuItems() {
        viewModelScope.launch {
            menuItemRepository.getAllMenuItems()
                .catch {
                    _menuItems.value = ApiResult.Error(it.message ?: "Something went wrong")
                }
                .collect {
                    _menuItems.value = it
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onCategorySelected(category: String) {
        // update isSelect state of each item in category
        /*DishRepository.categories.forEach {
            it.isSelected = it.name.contentEquals(category)
        }
        _categories.value = DishRepository.categories

        // filter dishes result based on the selected category
        _dishes.value = if (_searchText.value.isEmpty() || dishes.value.isEmpty()) {
            DishRepository.dishes.filter {
                it.category.contentEquals(category.lowercase())
            }
        } else {
            dishes.value.filter {
                it.category.contentEquals(category.lowercase())
            }
        }*/
    }
}
