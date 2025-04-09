package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import tech.abhranilnxt.zyneticassignmentapp.R
import tech.abhranilnxt.zyneticassignmentapp.core.presentation.util.toString
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.core.components.ProductImageWithSkeletonLoading
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_details.components.RatingBar
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.accent
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.background
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.textPrimary
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.textSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    navController: NavController
) {
    val errorLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_anim))
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val state by viewModel.productDetailsState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    var initialLoadAttempted by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading && !initialLoadAttempted) {
            initialLoadAttempted = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.productDetailsEvents.collect { event ->
            when (event) {
                is ProductDetailsEvent.Error -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.error.toString(context),
                        actionLabel = event.retry?.let { "Retry" },
                        duration = SnackbarDuration.Indefinite
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        event.retry?.invoke()
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details",
                    color = accent,
                    fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = accent)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background,
                    titleContentColor = textPrimary,
                    actionIconContentColor = Color.Black
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .background(background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = loadingLottie,
                        modifier = Modifier.size(360.dp),
                        contentScale = ContentScale.Fit,
                        iterations = LottieConstants.IterateForever
                    )
                }
            }
            state.product != null -> {
                state.product?.let { product ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(background),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(accent),
                                contentAlignment = Alignment.Center
                            ) {
                                ProductImageWithSkeletonLoading(product.images[selectedImageIndex])
                            }
                        }

                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(product.images.size) { index ->
                                    Box(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (selectedImageIndex == index) accent
                                                else accent.copy(alpha = 0.2f)
                                            )
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Card(
                                            onClick = { selectedImageIndex = index },
                                            modifier = Modifier.fillMaxSize(),
                                            shape = RoundedCornerShape(8.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.White
                                            ),
                                            elevation = CardDefaults.cardElevation(
                                                defaultElevation = if (selectedImageIndex == index) 4.dp else 0.dp
                                            )
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                ProductImageWithSkeletonLoading(product.images[index])
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = product.title,
                                    fontSize = 28.sp,
                                    lineHeight = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textPrimary
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 2.dp
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = "Description",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textPrimary
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = product.description,
                                            fontSize = 16.sp,
                                            color = textSecondary,
                                            lineHeight = 24.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = accent.copy(alpha = 0.1f)
                                        )
                                    ) {
                                        Text(
                                            text = product.category.replaceFirstChar {
                                                it.uppercase()
                                            },
                                            fontSize = 14.sp,
                                            color = accent,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    RatingBar(rating = product.rating)

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = product.rating.toString(),
                                        fontSize = 14.sp,
                                        color = textSecondary
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = "$ ${product.price}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = accent
                                    )
                                }
                            }
                        }

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Description",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textPrimary
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = product.description,
                                        fontSize = 16.sp,
                                        color = textSecondary,
                                        lineHeight = 24.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = errorLottie,
                        modifier = Modifier.size(300.dp),
                        contentScale = ContentScale.Fit,
                        iterations = LottieConstants.IterateForever
                    )
                }
            }
        }
    }
}