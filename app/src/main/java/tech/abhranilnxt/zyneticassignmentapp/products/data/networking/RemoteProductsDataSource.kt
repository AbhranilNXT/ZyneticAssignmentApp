package tech.abhranilnxt.zyneticassignmentapp.products.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import tech.abhranilnxt.zyneticassignmentapp.core.data.networking.constructUrl
import tech.abhranilnxt.zyneticassignmentapp.core.data.networking.safeCall
import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.NetworkError
import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.Result
import tech.abhranilnxt.zyneticassignmentapp.core.domain.util.map
import tech.abhranilnxt.zyneticassignmentapp.products.data.mappers.toProducts
import tech.abhranilnxt.zyneticassignmentapp.products.data.networking.dto.ProductsDto
import tech.abhranilnxt.zyneticassignmentapp.products.data.networking.dto.ProductsResponseDto
import tech.abhranilnxt.zyneticassignmentapp.products.domain.Products
import tech.abhranilnxt.zyneticassignmentapp.products.domain.ProductsDataSource

class RemoteProductsDataSource(
    private val httpClient: HttpClient
): ProductsDataSource {
    override suspend fun getProducts(): Result<List<Products>, NetworkError> {
        return safeCall<ProductsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/products")
            ) {
                parameter("select", "id,title,description,images,rating,category,price")
            }
        }.map { response ->
            response.products.map { it.toProducts() }
        }
    }

    override suspend fun getProductDetails(productId: Int): Result<Products, NetworkError> {
        return safeCall<ProductsDto> {
            httpClient.get(
                urlString = constructUrl("/products/$productId")
            ) {
                parameter("select", "id,title,description,images,rating,category,price")
            }
        }.map { response ->
            response.toProducts()
        }
    }
}