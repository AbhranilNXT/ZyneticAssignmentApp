package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list

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

class ProductsListViewModel(
    private val productsDataSource: ProductsDataSource
): ViewModel() {

    private val _productsListState = MutableStateFlow(ProductsListState())
    val productsListState = _productsListState
        .onStart {
            loadProducts()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ProductsListState()
        )

    private val _productsListEvents = MutableSharedFlow<ProductsListEvent>()
    val productsListEvents = _productsListEvents.asSharedFlow()

    private fun loadProducts() {
        viewModelScope.launch {
            _productsListState.update { it.copy(
                isLoading = true
            ) }

            productsDataSource.getProducts()
                .onSuccess { products ->
                    _productsListState.update { it.copy(
                        isLoading = false,
                        products = products
                    ) }
                }
                .onError { error ->
                    _productsListState.update { it.copy(isLoading = false) }
                    _productsListEvents.emit(ProductsListEvent.Error(
                        error = error,
                        retry = ::loadProducts
                    ))
                }
        }
    }
}