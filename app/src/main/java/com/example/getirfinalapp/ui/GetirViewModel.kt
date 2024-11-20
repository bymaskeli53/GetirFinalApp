package com.example.getirfinalapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirfinalapp.data.database.ProductDao
import com.example.getirfinalapp.data.repository.GetirRepository
import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.data.model.ProductX
import com.example.getirfinalapp.data.model.ProductXX
import com.example.getirfinalapp.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetirViewModel @Inject constructor(
    val repository: GetirRepository,
    val productDao: ProductDao
) : ViewModel() {


    private val _products = MutableStateFlow<ApiResult<List<ProductModelItem>>>(ApiResult.Loading())
    val products: StateFlow<ApiResult<List<ProductModelItem>>> = _products

    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> get() = _quantity

    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> get() = _totalPrice

    private val _productsInBasket = MutableLiveData<List<ProductXX>>()
    val productsInBasket: LiveData<List<ProductXX>> get() = _productsInBasket

    private val _formattedTotalPrice = MutableLiveData<String>()
    val formattedTotalPrice: LiveData<String> get() = _formattedTotalPrice


    private val _suggestedProducts =
        MutableStateFlow<ApiResult<List<ProductItem>>>(ApiResult.Loading())
    val suggestedProducts: StateFlow<ApiResult<List<ProductItem>>> = _suggestedProducts

    fun fetchProductList() {
        viewModelScope.launch {

            try {
                val result = repository.fetchData()
                _products.emit(ApiResult.Success(result))
            } catch (e: Exception){
                _products.emit(ApiResult.Error(e.localizedMessage))
            }


        }
    }

    fun fetchSuggestedProductList() {
        viewModelScope.launch {

            try {
                val result = repository.fetchSuggestedData()
                _suggestedProducts.emit(ApiResult.Success(result))
            } catch (e: Exception){
                _suggestedProducts.emit(ApiResult.Error(message = e.localizedMessage))
            }
        }
    }

    fun increaseQuantity(productX: ProductX) {
        viewModelScope.launch {
            val existingProduct = productDao.getProductById(productX.id)
            if (existingProduct != null) {
                existingProduct.quantity += 1
                productDao.updateProduct(existingProduct)
            } else {
                productX.quantity = 1
                productDao.insertProduct(productX)
            }
        }

        getProductsFromLocal()



        _quantity.value = _quantity.value!! + 1

        _totalPrice.value = productX.price?.let { _totalPrice.value?.plus(it) }

    }

    fun increaseQuantity(productX: ProductXX) {
        _quantity.value = _quantity.value!! + 1
        _totalPrice.value = productX.price?.let { _totalPrice.value?.plus(it) }
    }

    fun insertProductToLocal(productX: ProductX) {
        viewModelScope.launch {
            productDao.insertProduct(productX)
        }
    }

    fun insertProductToLocal(productXX: ProductXX) {
        viewModelScope.launch {
            productDao.insertProduct(productXX)
        }
    }

    fun updateProductToLocal(productX: ProductX) {
        viewModelScope.launch {
            productDao.updateProduct(productX)

        }
    }

    fun updateProductToLocal(productXX: ProductXX) {
        viewModelScope.launch {
            productDao.updateProduct(productXX.copy(quantity = (quantity.value)?.plus(1)))

        }
    }

    fun getProductsFromLocal() {

        viewModelScope.launch {
            productDao.getProducts().collect{
                _productsInBasket.value = it
            }

        }

    }

    fun deleteProductsFromLocal() {
        viewModelScope.launch {
            productDao.deleteAllProducts()
        }
    }

    fun clearBasket() {
        viewModelScope.launch {
            deleteProductsFromLocal() // Mevcut fonksiyonunuz
            _totalPrice.value = 0.0  // Total price'ı sıfırla
        }
    }
}