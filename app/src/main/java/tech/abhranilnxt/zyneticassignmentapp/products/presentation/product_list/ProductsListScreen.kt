package tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.core.utils.getTimeBasedGreeting
import tech.abhranilnxt.zyneticassignmentapp.products.presentation.product_list.components.ProductCard
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.accent
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.background
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.textPrimary
import tech.abhranilnxt.zyneticassignmentapp.ui.theme.textSecondary


@Composable
fun ProductsListScreen(
    viewModel: ProductsListViewModel,
    navController: NavController,
) {
    val errorLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_anim))
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val state by viewModel.productsListState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val greetingCardBackground = Color(0xFFDDEEFF)

    var hasError by remember { mutableStateOf(false) }
    var initialLoadAttempted by remember { mutableStateOf(false) }

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading && !initialLoadAttempted) {
            initialLoadAttempted = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.productsListEvents.collect { event ->
            when (event) {
                is ProductsListEvent.Error -> {
                    hasError = true
                    val result = snackbarHostState.showSnackbar(
                        message = event.error.toString(context),
                        actionLabel = event.retry?.let { "Retry" },
                        duration = SnackbarDuration.Indefinite
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        event.retry?.invoke()
                        hasError = false
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .fillMaxSize()
                        .background(background),
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
            !state.isLoading && state.products.isEmpty() && hasError -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .fillMaxSize()
                        .background(background),
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
            initialLoadAttempted -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues)
                        .fillMaxSize()
                        .background(background),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(background),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = greetingCardBackground
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = getTimeBasedGreeting(),
                                                    fontSize = 16.sp,
                                                    color = Color.Gray
                                                )

                                                Text(
                                                    text = "User",
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black
                                                )
                                            }

                                            Icon(
                                                imageVector = Icons.Default.AccountCircle,
                                                contentDescription = "Profile",
                                                tint = Color.DarkGray,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp,
                                        horizontal = 8.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Our Products",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textPrimary,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Discover Latest Products for You!",
                                    fontSize = 16.sp,
                                    color = textSecondary,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        items(state.products.size) { index ->
                            ProductCard(
                                product = state.products[index],
                                textPrimary,
                                textSecondary,
                                accent,
                                navController
                            )
                        }
                    }
                }
            }

        }
    }
}
