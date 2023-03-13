package com.stanleymesa.megagiga.ui.login

import androidx.lifecycle.*
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.usecase.RegisterUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase): ViewModel() {

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading


    fun register(registerBody: RegisterBody): LiveData<Event<Resource<Register>>> =
        registerUseCase.register(registerBody).asLiveData().map {
            Event(it)
        }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}