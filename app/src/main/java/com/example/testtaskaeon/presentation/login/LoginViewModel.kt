package com.example.testtaskaeon.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskaeon.common.Resource
import com.example.testtaskaeon.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(LoginScreenState())
    val screenState: StateFlow<LoginScreenState> = _screenState

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect: SharedFlow<LoginEffect> = _effect

    fun login(login: String, password: String) {
        if (!_screenState.value.isLoading) {
            loginUseCase(login, password).onEach { resource ->
                when(resource) {
                    is Resource.Error -> {
                        _screenState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _effect.emit(LoginEffect.Error(resource.message))
                    }
                    Resource.Loading -> {
                        _screenState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _screenState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _effect.emit(LoginEffect.NavigateForward)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}