package tech.abhranilnxt.zyneticassignmentapp.products.domain

import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.NetworkError
import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.Result

interface ProductsDataSource {
    suspend fun getProducts(): Result<List<Products>, NetworkError>

    suspend fun getProductDetails(productId: Int): Result<Products, NetworkError>
}