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


    private val _data = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val data: StateFlow<Resource<List<Product>>> = _data

    fun fetchData() {
        viewModelScope.launch {

            val result = repository.fetchData()
            _data.value = result

        }
    }
}