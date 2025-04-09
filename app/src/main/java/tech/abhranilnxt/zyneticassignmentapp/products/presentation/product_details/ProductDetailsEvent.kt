package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details

import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.NetworkError

sealed interface ProductDetailsEvent {
    data class Error(
        val error: NetworkError,
        val retry: (() -> Unit)? = null
    ): ProductDetailsEvent
}