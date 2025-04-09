package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Double,
    maxRating: Int = 5
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            val starIcon = if (i <= rating) {
                Icons.Filled.Star
            } else if (i - 0.5 <= rating && i > rating) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.Star
            }

            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = if (i <= rating) Color(0xFFFFB800) else Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}