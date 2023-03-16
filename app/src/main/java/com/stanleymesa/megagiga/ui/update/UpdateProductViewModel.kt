package com.stanleymesa.megagiga.ui.update

import androidx.lifecycle.*
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.UpdateProductBody
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.usecase.UpdateProductUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(private val updateProductUseCase: UpdateProductUseCase) :
    ViewModel() {

    private val _getTokenResponse = MutableLiveData<Event<String>>()
    val getTokenResponse: LiveData<Event<String>> = _getTokenResponse

    private val _getProductByIdResponse = MutableLiveData<Event<Resource<Product>>>()
    val getProductByIdResponse: LiveData<Event<Resource<Product>>> = _getProductByIdResponse

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    init {
        getToken()
    }

    private fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        updateProductUseCase.getToken().collect {
            _getTokenResponse.postValue(Event(it))
        }
    }

    fun getProductById(token: String, productId: Int) = viewModelScope.launch(Dispatchers.IO) {
        updateProductUseCase.getProductById(token, productId).collect {
            _getProductByIdResponse.postValue(Event(it))
        }
    }

    fun updateProduct(
        token: String,
        productId: Int,
        updateProductBody: UpdateProductBody,
    ): LiveData<Event<Resource<String>>> =
        updateProductUseCase.updateProduct(token, productId, updateProductBody).asLiveData().map {
            Event(it)
        }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}