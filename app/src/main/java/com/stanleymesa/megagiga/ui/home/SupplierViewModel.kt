package com.stanleymesa.megagiga.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.domain.usecase.SupplierUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(private val supplierUseCase: SupplierUseCase): ViewModel() {

    private val _getTokenResponse = MutableLiveData<Event<String>>()
    val getTokenResponse: LiveData<Event<String>> = _getTokenResponse

    private val _getSupplierResponse = MutableLiveData<Event<PagingData<Supplier>>>()
    val getProductResponse: LiveData<Event<PagingData<Supplier>>> = _getSupplierResponse

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    init {
        getToken()
    }

    fun getSupplier(token: String) = viewModelScope.launch(Dispatchers.IO) {
        supplierUseCase.getSupplier(token).collect {
            _getSupplierResponse.postValue(Event(it))
        }
    }

    fun deleteSupplier(token: String, supplierId: Int): LiveData<Event<Resource<String>>> =
        supplierUseCase.deleteSupplier(token, supplierId).asLiveData().map {
            Event(it)
        }

    private fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        supplierUseCase.getToken().collect {
            _getTokenResponse.postValue(Event(it))
        }
    }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}