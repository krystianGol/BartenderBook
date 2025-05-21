package com.example.bartenderbook.ui.screen.detail

import android.app.Activity
import Cocktail
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.bartenderbook.data.repository.HardcodedCocktailRepository

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CocktailDetailScreen(
    cocktailId: Int,
    viewModel: TimerViewModel,
    navController: NavController
) {
    val cocktailState = remember { mutableStateOf<Cocktail?>(null) }
    val windowSize = calculateWindowSizeClass(LocalContext.current as Activity)

    val titleSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 30.sp
        WindowWidthSizeClass.Medium -> 36.sp
        WindowWidthSizeClass.Expanded -> 42.sp
        else -> 30.sp
    }

    val sectionTitleSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 20.sp
        WindowWidthSizeClass.Medium -> 24.sp
        WindowWidthSizeClass.Expanded -> 28.sp
        else -> 20.sp
    }

    val bodyTextSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 18.sp
        WindowWidthSizeClass.Medium -> 20.sp
        WindowWidthSizeClass.Expanded -> 24.sp
        else -> 18.sp
    }

    val appBarFontSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 22.sp
        WindowWidthSizeClass.Medium -> 26.sp
        WindowWidthSizeClass.Expanded -> 30.sp
        else -> 22.sp
    }

    val starIconSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 24.dp
        WindowWidthSizeClass.Medium -> 32.dp
        WindowWidthSizeClass.Expanded -> 36.dp
        else -> 24.dp
    }

    LaunchedEffect(cocktailId) {
        cocktailState.value = HardcodedCocktailRepository.getCocktailById(cocktailId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Szczegóły koktajlu",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = appBarFontSize,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                navigationIcon = {
                    Row {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Wróć",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        cocktailState.value?.let { cocktail ->
                            var isFavorite by remember { mutableStateOf(cocktail.isFavorite) }

                            IconButton(onClick = {
                                isFavorite = !isFavorite
                                HardcodedCocktailRepository.toggleFavorite(cocktail.id)
                                cocktailState.value = cocktail.copy(isFavorite = isFavorite)
                            }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                    contentDescription = if (isFavorite) "Usuń z ulubionych" else "Dodaj do ulubionych",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(starIconSize)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        cocktailState.value?.let { cocktail ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = cocktail.name.uppercase(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Składniki",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = sectionTitleSize,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        cocktail.ingredients.forEach {
                            Text(
                                text = "• $it",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = bodyTextSize,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Przygotowanie",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = sectionTitleSize,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = cocktail.preparation,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = bodyTextSize,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TimerScreen(viewModel, timePreparation = cocktail.timePreparation)
            }
        }
    }
}
