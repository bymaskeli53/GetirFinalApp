package com.example.getirfinalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
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

    fun fetchData() {
        viewModelScope.launch {

            val result = repository.fetchData()
            _products.emit(ApiResult.Success(result))

        }
    }

    fun fetchSuggestedData() {
        viewModelScope.launch {
            val result = repository.fetchSuggestedData()
            _suggestedProducts.emit(ApiResult.Success(result))
            //_suggestedProducts.value = Resource.Success(result)
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
//        productX.quantity = productX.quantity?.plus(1)!!


        // _totalPrice.value = _totalPrice.value?.plus(productX.price?.times(quantity.value?.toDouble()!!)!!)



        _totalPrice.value = productX.price?.let { _totalPrice.value?.plus(it) }

//        val symbols = DecimalFormatSymbols(Locale("tr", "TR")).apply {
//            decimalSeparator = ','
//            groupingSeparator = '.'
//        }


//        _totalPrice.value = productX.price?.let {
//            val newTotal = _totalPrice.value?.plus(it)
//            // Özel formatlama
//            val decimalFormat = DecimalFormat("#0.00", symbols)
//            decimalFormat.format(newTotal).toDouble()
//        }

//        val symbols = DecimalFormatSymbols(Locale.US).apply {
//            decimalSeparator = '.'
//            groupingSeparator = ','
//        }
//
//        val decimalFormat = DecimalFormat("#,##0.00", symbols)

//        _totalPrice.value = productX.price?.let {
//            val newTotal = _totalPrice.value?.plus(it)
//            // Özel formatlama
//
//            decimalFormat.format(newTotal).replace(',', '.').toDouble()
//        }

//        _totalPrice.value = productX.price?.let {
//            val newTotal = _totalPrice.value?.plus(it)
//            // Değeri iki ondalık hane ile formatla
//            val formattedTotal = DecimalFormat("#.00").format(newTotal)
//            formattedTotal.toDouble()
//        }

    }

    fun increaseQuantity(productX: ProductXX) {
        _quantity.value = _quantity.value!! + 1
        // _totalPrice.value = _totalPrice.value?.plus(productX.price?.times(quantity.value?.toDouble()!!)!!)
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
            //  productDao.updateProduct(productX)
            //productDao.updateProduct(productX.copy(quantity = quantity.value))
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
}