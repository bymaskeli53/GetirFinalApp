package com.example.getirfinalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetirViewModel @Inject constructor(val repository: GetirRepository,val productDao: ProductDao) : ViewModel() {




    private val _products = MutableStateFlow<Resource<List<ProductModelItem>>>(Resource.Loading())
    val products: StateFlow<Resource<List<ProductModelItem>>> = _products

    private val _quantity = MutableLiveData<Int>( 0)
    val quantity: LiveData<Int> get() =  _quantity

    private val _totalPrice = MutableLiveData<Double>( 0.0)
    val totalPrice: LiveData<Double> get() =  _totalPrice

    private val _productsInBasket = MutableLiveData<List<ProductXX>>()
    val productsInBasket: LiveData<List<ProductXX>> get() =  _productsInBasket



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
            _productsInBasket.value = productDao.getProducts()



        }

    }

    fun deleteProductsFromLocal() {
        viewModelScope.launch {
            productDao.deleteAllProducts()


        }
    }
}