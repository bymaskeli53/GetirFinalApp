package com.example.getirfinalapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.database.ProductDao
import com.example.getirfinalapp.data.model.GeneralProductItem
import com.example.getirfinalapp.data.model.SuggestedProductItem
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

    private val _productsInBasket = MutableLiveData<List<GeneralProductItem>>()
    val productsInBasket: LiveData<List<GeneralProductItem>> get() = _productsInBasket

    private val _formattedTotalPrice = MutableLiveData<String>()
    val formattedTotalPrice: LiveData<String> get() = _formattedTotalPrice

    fun increaseQuantity(suggestedProductItem: SuggestedProductItem) {
        viewModelScope.launch {
            val existingProduct = productDao.getProductById(suggestedProductItem.id)
            if (existingProduct != null) {
                existingProduct.quantity += 1
                productDao.updateProduct(existingProduct)
            } else {
                suggestedProductItem.quantity = 1
                productDao.insertProduct(suggestedProductItem)
            }
        }

        getProductsFromLocal()

        _quantity.value = _quantity.value!! + 1

        _totalPrice.value = suggestedProductItem.price?.let { _totalPrice.value?.plus(it) }
    }

    fun increaseQuantity(productX: GeneralProductItem) {
        _quantity.value = _quantity.value!! + 1
        _totalPrice.value = productX.price?.let { _totalPrice.value?.plus(it) }
    }

    fun insertProductToLocal(suggestedProductItem: SuggestedProductItem) {
        viewModelScope.launch {
            productDao.insertProduct(suggestedProductItem)
        }
    }

    fun insertProductToLocal(generalProductItem: GeneralProductItem) {
        viewModelScope.launch {
            productDao.insertProduct(generalProductItem)
        }
    }

    fun updateProductToLocal(suggestedProductItem: SuggestedProductItem) {
        viewModelScope.launch {
            productDao.updateProduct(suggestedProductItem)
        }
    }

    fun updateProductToLocal(generalProductItem: GeneralProductItem) {
        viewModelScope.launch {
            productDao.updateProduct(generalProductItem.copy(quantity = (quantity.value)?.plus(1)))
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
            _totalPrice.value = 0.0 // Total price'ı sıfırla
        }
    }
}
