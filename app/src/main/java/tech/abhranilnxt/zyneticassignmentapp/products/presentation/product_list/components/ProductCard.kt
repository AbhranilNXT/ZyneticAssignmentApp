package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tech.abhranilnxt.zyneticassignmentapp.products.domain.Products
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.core.components.ProductImageWithSkeletonLoading
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.navigation.ZyneticAssignmentAppScreens

@Composable
fun ProductCard(
    product: Products,
    textPrimary: Color,
    textSecondary: Color,
    accentColor: Color,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
                .clip(RoundedCornerShape(16.dp))
                .background(accentColor),
            contentAlignment = Alignment.Center
        ) {
            ProductImageWithSkeletonLoading(product.images[0])
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = textPrimary,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.description,
            fontSize = 17.sp,
            color = textSecondary,
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(
                    "${ZyneticAssignmentAppScreens.ProductDetailsScreen.route}/${product.id}"
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .height(36.dp)
        ) {
            Text(
                text = "Learn More >",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        HorizontalDivider(
            color = Color(0xFF060623),
            thickness = 0.5.dp,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
        )
    }
}