package com.example.littlelemon.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.home.data.DishRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class HomeViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _categories = MutableStateFlow(DishRepository.categories)
    val categories = _categories.asStateFlow()

    private val _dishes = MutableStateFlow(DishRepository.dishes)
    val dishes = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_dishes) { text, dishes ->
            if (text.isBlank()) {
                dishes
            } else {
                delay(1000L)
                dishes.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _dishes.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}