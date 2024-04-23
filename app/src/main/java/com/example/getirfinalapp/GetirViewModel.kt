package com.example.getirfinalapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetirViewModel @Inject constructor(val repository: GetirRepository) : ViewModel() {


    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val products: StateFlow<Resource<List<Product>>> = _products

    private val _suggestedProducts = MutableStateFlow<Resource<List<ProductItem>>>(Resource.Loading())
    val suggestedProducts: StateFlow<Resource<List<ProductItem>>> = _suggestedProducts

    fun fetchData() {
        viewModelScope.launch {

            val result = repository.fetchData()
            _products.emit(Resource.Success(result))

        }
    }

    fun fetchSuggestedData() {
        viewModelScope.launch {
            val result = repository.fetchSuggestedData()
           _suggestedProducts.emit(Resource.Success(result))
            //_suggestedProducts.value = Resource.Success(result)
        }
    }
}