package com.example.littlelemon.screens.home.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.R
import com.example.littlelemon.screens.home.data.MenuItemRepository
import com.example.littlelemon.screens.home.data.model.MenuItem
import com.example.littlelemon.screens.home.data.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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

    private val _menuItemsUiState =
        MutableStateFlow<MenuItemListUiState>(MenuItemListUiState.Loading)
    val menuItemsUiState: StateFlow<MenuItemListUiState> get() = _menuItemsUiState.asStateFlow()

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
        menuItemRepository.getMenuItemList()
            .onEach { menuItemList: List<MenuItem> ->
                if (menuItemList.isNotEmpty()) {
                    _menuItemsUiState.update {
                        MenuItemListUiState.Success(menuItemList = menuItemList)
                    }
                } else {
                    fetchMenuItems()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchMenuItems() {
        viewModelScope.launch {
            menuItemRepository.fetchNewMenuItemList()
                .collectLatest { value: ApiResult<List<MenuItem>> ->
                    when (value) {
                        is ApiResult.Loading -> _menuItemsUiState.update {
                            MenuItemListUiState.Loading
                        }

                        is ApiResult.Error -> _menuItemsUiState.update {
                            MenuItemListUiState.Error(errorMessageId = R.string.error_fetching_new_menu_item_list)
                        }

                        is ApiResult.Success -> _menuItemsUiState.update {
                            if (value.data.isNullOrEmpty()) {
                                MenuItemListUiState.Empty(errorMessageId = R.string.error_fetching_empty)
                            } else {
                                MenuItemListUiState.Success(menuItemList = value.data)
                            }
                        }
                    }
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

    sealed class MenuItemListUiState {
        data class Success(val menuItemList: List<MenuItem>) : MenuItemListUiState()
        data class Error(@StringRes val errorMessageId: Int) : MenuItemListUiState()
        data object Loading : MenuItemListUiState()
        data class Empty(@StringRes val errorMessageId: Int) : MenuItemListUiState()
    }
}
