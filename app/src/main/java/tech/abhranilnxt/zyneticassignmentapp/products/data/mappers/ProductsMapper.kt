package tech.abhranilnxt.zyneticassignmentapp.products.data.mappers

import tech.abhranilnxt.zyneticassignmentapp.products.data.networking.dto.ProductsDto
import tech.abhranilnxt.zyneticassignmentapp.products.domain.Products

fun ProductsDto.toProducts(): Products {
    return Products(
        id = id,
        title = title,
        description = description,
        images = images,
        rating = rating,
        category = category,
        price = price
    )
}