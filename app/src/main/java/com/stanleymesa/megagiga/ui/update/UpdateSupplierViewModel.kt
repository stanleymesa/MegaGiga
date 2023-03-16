package com.stanleymesa.megagiga.ui.update

import android.util.Log
import androidx.lifecycle.*
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.SupplierBody
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.domain.usecase.UpdateSupplierUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateSupplierViewModel @Inject constructor(private val updateSupplierUseCase: UpdateSupplierUseCase) :
    ViewModel() {

    private val _getTokenResponse = MutableLiveData<Event<String>>()
    val getTokenResponse: LiveData<Event<String>> = _getTokenResponse

    private val _getSupplierByIdResponse = MutableLiveData<Event<Resource<Supplier>>>()
    val getSupplierByIdResponse: LiveData<Event<Resource<Supplier>>> = _getSupplierByIdResponse

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    init {
        getToken()
    }

    private fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        updateSupplierUseCase.getToken().collect {
            _getTokenResponse.postValue(Event(it))
        }
    }

    fun getSupplierById(token: String, supplierId: Int) = viewModelScope.launch(Dispatchers.IO) {
        Log.e("VIEWMODEL", "TES BLOK")
        updateSupplierUseCase.getSupplierById(token, supplierId).collect {
            _getSupplierByIdResponse.postValue(Event(it))
        }
    }

    fun updateSupplier(
        token: String,
        supplierId: Int,
        supplierBody: SupplierBody,
    ): LiveData<Event<Resource<String>>> =
        updateSupplierUseCase.updateSupplier(token, supplierId, supplierBody).asLiveData().map {
            Event(it)
        }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}