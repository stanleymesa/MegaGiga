package com.stanleymesa.megagiga.ui.create

import androidx.lifecycle.*
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.SupplierBody
import com.stanleymesa.core.domain.usecase.CreateSupplierUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSupplierViewModel @Inject constructor(private val createSupplierUseCase: CreateSupplierUseCase): ViewModel() {

    private val _getTokenResponse = MutableLiveData<Event<String>>()
    val getTokenResponse: LiveData<Event<String>> = _getTokenResponse

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    init {
        getToken()
    }

    private fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        createSupplierUseCase.getToken().collect {
            _getTokenResponse.postValue(Event(it))
        }
    }

    fun createSupplier(token: String, supplierBody: SupplierBody): LiveData<Event<Resource<String>>> =
        createSupplierUseCase.createSupplier(token, supplierBody).asLiveData().map {
            Event(it)
        }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}