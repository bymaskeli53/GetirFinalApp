package com.example.getirfinalapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.network.ApiResult
import com.example.getirfinalapp.network.ApiResult.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {
    private val _products = MutableStateFlow<ApiResult<List<ProductModelItem>>>(ApiResult.Loading())
    val products: StateFlow<ApiResult<List<ProductModelItem>>> = _products

    private val _suggestedProducts = MutableStateFlow<ApiResult<List<ProductItem>>>(ApiResult.Loading())
    val suggestedProducts: StateFlow<ApiResult<List<ProductItem>>> = _suggestedProducts

    fun fetchProductList() {
        viewModelScope.launch {
            _products.emit(ApiResult.Loading())
            val result = repository.fetchProductList()
            handleApiCall(result, _products)
        }
    }

    fun fetchSuggestedProductList() {
        viewModelScope.launch {
            _suggestedProducts.emit(ApiResult.Loading())
            val result = repository.fetchSuggestedProductList()
            handleApiCall(result, _suggestedProducts)
        }
    }

    private suspend fun <T> handleApiCall(
        result: ApiResult<T>,
        stateFlow: MutableStateFlow<ApiResult<T>>
    ) {
        when (result) {
            is Success -> stateFlow.emit(Success(result.data))
            is Error -> stateFlow.emit(Error(result.message))
            is NetworkError -> stateFlow.emit(NetworkError)
            is UnknownError -> stateFlow.emit(UnknownError)
            else -> {}
        }
    }
}
