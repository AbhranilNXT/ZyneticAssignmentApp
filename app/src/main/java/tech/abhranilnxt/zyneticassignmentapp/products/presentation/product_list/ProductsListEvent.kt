package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list

import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.NetworkError

sealed interface ProductsListEvent {
    data class Error(
        val error: NetworkError,
        val retry: (() -> Unit)? = null
    ): ProductsListEvent
}