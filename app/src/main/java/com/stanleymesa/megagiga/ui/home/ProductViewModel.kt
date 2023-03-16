package com.stanleymesa.megagiga.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.usecase.ProductUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCase: ProductUseCase): ViewModel() {

    private val _getProductResponse = MutableLiveData<Event<PagingData<Product>>>()
    val getProductResponse: LiveData<Event<PagingData<Product>>> = _getProductResponse

    private val _getTokenResponse = MutableLiveData<Event<String>>()
    val getTokenResponse: LiveData<Event<String>> = _getTokenResponse

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading!!

    init {
        getToken()
    }

    fun getProduct(token: String) = viewModelScope.launch(Dispatchers.IO) {
        productUseCase.getProduct(token).collect {
            _getProductResponse.postValue(Event(it))
        }
    }

    fun deleteProduct(token: String, productId: Int): LiveData<Event<Resource<String>>> =
        productUseCase.deleteProduct(token, productId).asLiveData().map {
            Event(it)
        }

    fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        productUseCase.getToken().collect {
            _getTokenResponse.postValue(Event(it))
        }
    }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}