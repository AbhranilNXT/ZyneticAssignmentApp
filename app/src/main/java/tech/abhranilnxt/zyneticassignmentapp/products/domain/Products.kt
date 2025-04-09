package tech.abhranilnxt.zyneticassignmentapp.products.domain

data class Products(
    val id: Int,
    val title: String,
    val description: String,
    val images: List<String>,
    val rating: Double,
    val category: String,
    val price: Double
)
