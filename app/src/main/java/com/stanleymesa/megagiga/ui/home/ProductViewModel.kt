package com.stanleymesa.megagiga.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.usecase.ProductUseCase
import com.stanleymesa.core.utlis.Event
import com.stanleymesa.core.utlis.LOGIN_FAILED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCase: ProductUseCase): ViewModel() {

    private val _getProductResponse = MutableLiveData<Event<PagingData<Product>>>()
    val getProductResponse: LiveData<Event<PagingData<Product>>> = _getProductResponse

    fun getProduct(token: String) = viewModelScope.launch(Dispatchers.IO) {
        productUseCase.getProduct(token).collect {
            _getProductResponse.postValue(Event(it))
        }
    }

    fun getToken(): LiveData<Event<String>> =
        productUseCase.getToken().asLiveData().map {
            Event(it)
        }


}