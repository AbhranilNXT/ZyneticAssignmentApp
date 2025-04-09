package tech.abhranilnxt.zyneticassignmentapp.products.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDto(
    val id: Int,
    val title: String,
    val description: String,
    val images: List<String>,
    val rating: Double,
    val category: String,
    val price: Double
)
