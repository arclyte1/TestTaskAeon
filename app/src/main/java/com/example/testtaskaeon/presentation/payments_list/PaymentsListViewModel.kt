package com.example.testtaskaeon.presentation.payments_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskaeon.common.Resource
import com.example.testtaskaeon.domain.usecase.GetPaymentsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PaymentsListViewModel @Inject constructor(
    private val getPaymentsListUseCase: GetPaymentsListUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(PaymentsListState())
    val screenState: StateFlow<PaymentsListState> = _screenState

    private val _effect = MutableSharedFlow<PaymentsListEffect>()
    val effect = _effect.asSharedFlow()

    init {
        refreshPaymentList()
    }

    fun refreshPaymentList() {
        if (!_screenState.value.isLoading) {
            getPaymentsListUseCase().onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _effect.emit(PaymentsListEffect.Error(resource.message))
                        _screenState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
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
                                isLoading = false,
                                paymentList = resource.data
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}