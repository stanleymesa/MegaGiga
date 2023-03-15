package com.stanleymesa.megagiga.ui.create

import androidx.lifecycle.*
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.usecase.CreateProductUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(private val createProductUseCase: CreateProductUseCase): ViewModel() {

    private val _getTokenResponse = MutableLiveData<Event<String>>()
    val getTokenResponse: LiveData<Event<String>> = _getTokenResponse

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    init {
        getToken()
    }

    private fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        createProductUseCase.getToken().collect {
            _getTokenResponse.postValue(Event(it))
        }
    }

    fun createProduct(token: String, createProductBody: CreateProductBody): LiveData<Event<Resource<String>>> =
        createProductUseCase.createProduct(token, createProductBody).asLiveData().map {
            Event(it)
        }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}