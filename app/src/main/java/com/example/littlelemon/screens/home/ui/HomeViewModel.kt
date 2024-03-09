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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val menuItemRepository: MenuItemRepository
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _categories = MutableStateFlow(menuItemRepository.getMenuItemCategories())
    val categories = _categories.asStateFlow()

    private val _menuItemsUiState =
        MutableStateFlow<MenuItemListUiState>(MenuItemListUiState.Loading)
    val menuItemsUiState: StateFlow<MenuItemListUiState> get() = _menuItemsUiState.asStateFlow()

    init {
        getMenuItems()
    }

    private fun getMenuItems(query: String? = null, category: String? = null) {
        menuItemRepository.getMenuItemList(query = query, category = category)
            .onEach { menuItemList: List<MenuItem> ->
                if (menuItemList.isNotEmpty()) {
                    _menuItemsUiState.update {
                        MenuItemListUiState.Success(menuItemList = menuItemList)
                    }
                } else {
                    // only fetch query from remote if query and category null or empty
                    if (query.isNullOrEmpty() && category.isNullOrEmpty()) {
                        fetchMenuItems()
                    } else {
                    // query and search scenario with category or query null/empty
                        category?.let {
                            _menuItemsUiState.update {
                                MenuItemListUiState.Empty(R.string.error_fetching_empty_by_category)
                            }
                        }
                        query?.let {
                            // if query is empty then show all list instead of error message
                            if (it.isNotEmpty()) {
                                _menuItemsUiState.update {
                                    MenuItemListUiState.Empty(R.string.error_fetching_empty_by_query)
                                }
                            }
                        }
                    }
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

    fun onSearchTextChange(query: String) {
        _searchText.value = query
        searchText
            .onEach { text ->
                getMenuItems(query = text)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                MenuItemListUiState.Loading
            )
    }

    fun onCategorySelected(selectedCategory: String) {
        var isCategorySelected = false
        _categories.update {
            _categories.value.map { menuItemCategory ->
                // check if category is not yet selected and select again then it is select
                // otherwise it is deselect
                var selectedStatus = false
                if (!menuItemCategory.isSelected
                    && menuItemCategory.name.contentEquals(
                        selectedCategory
                    )
                ) {
                    selectedStatus = true
                    isCategorySelected = true
                }
                menuItemCategory.copy(
                    isSelected = selectedStatus
                )
            }
        }
        // if it is a select case and not deselect then get menu item with category text
        if (isCategorySelected) {
            getMenuItems(category = selectedCategory)
        } else {
            getMenuItems(category = null)
        }
    }

    sealed class MenuItemListUiState {
        data class Success(val menuItemList: List<MenuItem>) : MenuItemListUiState()
        data class Error(@StringRes val errorMessageId: Int) : MenuItemListUiState()
        data object Loading : MenuItemListUiState()
        data class Empty(@StringRes val errorMessageId: Int) : MenuItemListUiState()
    }
}
