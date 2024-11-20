package com.example.getirfinalapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.database.ProductDao
import com.example.getirfinalapp.data.model.ProductX
import com.example.getirfinalapp.data.model.ProductXX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    val repository: ProductsRepository,
    val productDao: ProductDao
) : ViewModel() {

    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> get() = _quantity

    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> get() = _totalPrice

    private val _productsInBasket = MutableLiveData<List<ProductXX>>()
    val productsInBasket: LiveData<List<ProductXX>> get() = _productsInBasket

    private val _formattedTotalPrice = MutableLiveData<String>()
    val formattedTotalPrice: LiveData<String> get() = _formattedTotalPrice


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
            productDao.getProducts().collect {
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