package tech.abhranilnxt.zyneticassignmentapp.products.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details.ProductDetailsScreen
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details.ProductDetailsViewModel
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list.ProductsListScreen
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list.ProductsListViewModel

@Composable
fun ZyneticAssignmentAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ZyneticAssignmentAppScreens.ProductListScreen.route) {

        composable(ZyneticAssignmentAppScreens.ProductListScreen.route) {
            val viewModel : ProductsListViewModel = koinViewModel()
            ProductsListScreen(viewModel, navController)
        }

        val detailScreen = ZyneticAssignmentAppScreens.ProductDetailsScreen.route
        composable("$detailScreen/{productId}", arguments = listOf(navArgument("productId") {
            type = NavType.IntType
        })) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId == null) {
                Log.e("NavArgs", "productId is null — skipping screen")
                return@composable
            }
            val viewModel: ProductDetailsViewModel = koinViewModel(
                parameters = { parametersOf(productId) }
            )
            ProductDetailsScreen(viewModel, navController)
        }
    }
}