package com.example.littlelemon.screens.home.data.network

sealed class ApiResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(menuItem: T) : ApiResult<T>(data = menuItem)
    class Error<T>(error: String) : ApiResult<T>(error = error)
    class Loading<T> : ApiResult<T>()
}
