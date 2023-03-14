package com.stanleymesa.megagiga.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.domain.usecase.SupplierUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(private val supplierUseCase: SupplierUseCase): ViewModel() {

    private val _getSupplierResponse = MutableLiveData<Event<PagingData<Supplier>>>()
    val getProductResponse: LiveData<Event<PagingData<Supplier>>> = _getSupplierResponse

    fun getSupplier(token: String) = viewModelScope.launch(Dispatchers.IO) {
        supplierUseCase.getSupplier(token).collect {
            _getSupplierResponse.postValue(Event(it))
        }
    }

    fun getToken(): LiveData<Event<String>> =
        supplierUseCase.getToken().asLiveData().map {
            Event(it)
        }


}