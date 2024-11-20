package com.example.getirfinalapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.network.ApiResult
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

    private val _suggestedProducts =
        MutableStateFlow<ApiResult<List<ProductItem>>>(ApiResult.Loading())
    val suggestedProducts: StateFlow<ApiResult<List<ProductItem>>> = _suggestedProducts

    fun fetchProductList() {
        viewModelScope.launch {
            try {
                val result = repository.fetchProductList()
                _products.emit(ApiResult.Success(result))
            } catch (e: Exception) {
                _products.emit(ApiResult.Error(e.localizedMessage))
            }
        }
    }

    fun fetchSuggestedProductList() {
        viewModelScope.launch {
            try {
                val result = repository.fetchSuggestedProductList()
                _suggestedProducts.emit(ApiResult.Success(result))
            } catch (e: Exception) {
                _suggestedProducts.emit(ApiResult.Error(e.localizedMessage))
            }
        }
    }

}