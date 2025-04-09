package tech.abhranilnxt.zyneticassignmentapp.di

import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tech.abhranilnxt.zyneticassignmentapp.core.data.networking.HttpClientFactory
import tech.abhranilnxt.zyneticassignmentapp.products.data.networking.RemoteProductsDataSource
import tech.abhranilnxt.zyneticassignmentapp.products.domain.ProductsDataSource
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details.ProductDetailsViewModel
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list.ProductsListViewModel

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteProductsDataSource).bind<ProductsDataSource>()
    viewModelOf(::ProductsListViewModel)
    viewModel { (productId: Int) -> ProductDetailsViewModel(productId, get()) }
}