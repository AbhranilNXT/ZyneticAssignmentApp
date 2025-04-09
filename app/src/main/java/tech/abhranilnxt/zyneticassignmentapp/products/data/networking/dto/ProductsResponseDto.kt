package tech.abhranilnxt.zyneticassignmentapp.products.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseDto(
    val products: List<ProductsDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
