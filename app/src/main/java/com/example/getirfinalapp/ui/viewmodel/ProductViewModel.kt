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

    private val _suggestedProducts =
        MutableStateFlow<ApiResult<List<ProductItem>>>(ApiResult.Loading())
    val suggestedProducts: StateFlow<ApiResult<List<ProductItem>>> = _suggestedProducts

    fun fetchProductList() {
        viewModelScope.launch {
            when (val result = repository.fetchProductList()) {
                is Success -> {
                    _products.emit(Success(result.data))
                }

                is Error -> {
                    _products.emit(Error(result.message))
                }


                is NetworkError -> _products.emit(NetworkError)
                is UnknownError -> _products.emit(UnknownError)
                else -> {}
            }
        }

    }


    fun fetchSuggestedProductList() {
        viewModelScope.launch {
            when (val result = repository.fetchSuggestedProductList()) {
                is Error -> _suggestedProducts.emit(Error(result.message))
                is Loading -> {}
                is NetworkError -> _suggestedProducts.emit(NetworkError)
                is Success -> _suggestedProducts.emit(Success(result.data))
                is UnknownError -> _suggestedProducts.emit(UnknownError)
            }
        }
    }


}