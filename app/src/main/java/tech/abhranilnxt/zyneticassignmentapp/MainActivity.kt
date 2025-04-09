package tech.abhranilnxt.zyneticassignmentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.navigation.ZyneticAssignmentAppNavigation
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.ZyneticAssignmentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZyneticAssignmentAppTheme {
                ZyneticAssignmentAppNavigation()
            }
        }
    }
}