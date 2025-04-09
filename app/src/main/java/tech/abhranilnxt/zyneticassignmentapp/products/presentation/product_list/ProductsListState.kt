package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list

import androidx.compose.runtime.Immutable
import tech.abhranilnxt.zyneticassignmentapp.products.domain.Products

@Immutable
data class ProductsListState (
    val isLoading: Boolean = false,
    val products: List<Products> = emptyList()
)