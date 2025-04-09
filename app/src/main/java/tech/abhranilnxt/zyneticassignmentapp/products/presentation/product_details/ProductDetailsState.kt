package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details

import androidx.compose.runtime.Immutable
import tech.abhranilnxt.zyneticassignmentapp.products.domain.Products

@Immutable
data class ProductDetailsState (
    val isLoading: Boolean = false,
    val product: Products? = null
)