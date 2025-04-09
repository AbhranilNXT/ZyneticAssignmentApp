package tech.abhranilnxt.zyneticassignmentapp.products.presentation.navigation

sealed class ZyneticAssignmentAppScreens(val route: String){
    object ProductListScreen: ZyneticAssignmentAppScreens("product_list_screen")
    object ProductDetailsScreen: ZyneticAssignmentAppScreens("product_details_screen")
}