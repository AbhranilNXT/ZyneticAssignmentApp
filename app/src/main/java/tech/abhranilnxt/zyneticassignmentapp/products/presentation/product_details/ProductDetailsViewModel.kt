package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.onError
import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.onSuccess
import tech.abhranilnxt.zyneticassignmentapp.products.domain.ProductsDataSource

class ProductDetailsViewModel(
    private val productId: Int,
    private val productsDataSource: ProductsDataSource
): ViewModel() {

    private val _productDetailsState = MutableStateFlow(ProductDetailsState())
    val productDetailsState = _productDetailsState
        .onStart {
            loadProductDetails()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ProductDetailsState()
        )

    private val _productDetailsEvents = MutableSharedFlow<ProductDetailsEvent>()
    val productDetailsEvents = _productDetailsEvents.asSharedFlow()

    private fun loadProductDetails() {
        viewModelScope.launch {
            _productDetailsState.update { it.copy(
                isLoading = true
            ) }

            productsDataSource
                .getProductDetails(productId)
                .onSuccess { product ->
                    _productDetailsState.update { it.copy(
                        isLoading = false,
                        product = product
                    ) }
                }
                .onError { error ->
                    _productDetailsState.update { it.copy(isLoading = false) }
                    _productDetailsEvents.emit(ProductDetailsEvent.Error(
                        error = error,
                        retry = ::loadProductDetails
                    ))
                }
        }
    }
}