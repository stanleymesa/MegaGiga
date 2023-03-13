package com.stanleymesa.megagiga.ui.login

import androidx.lifecycle.*
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.usecase.LoginUseCase
import com.stanleymesa.core.utlis.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun login(loginBody: LoginBody): LiveData<Event<Resource<Login>>> =
        loginUseCase.login(loginBody).asLiveData().map {
            Event(it)
        }

    fun setLoading(isLoading: Boolean) =
        _isLoading.postValue(Event(isLoading))

}