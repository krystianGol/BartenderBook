package com.example.bartenderbook.ui.screen.category

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.windowsizeclass.*
import coil.compose.rememberAsyncImagePainter
import com.example.bartenderbook.R
import com.example.bartenderbook.data.repository.HardcodedCocktailRepository

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CategoryGridScreen(
    category: String,
    onCocktailSelected: (Int) -> Unit,
    onAddClicked: () -> Unit
) {
    val cocktails = HardcodedCocktailRepository.getCocktailsByCategory(category)
    val windowSize = calculateWindowSizeClass(LocalContext.current as Activity)

    val titleFontSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 18.sp
        WindowWidthSizeClass.Medium -> 22.sp
        WindowWidthSizeClass.Expanded -> 26.sp
        else -> 18.sp
    }

    val imageHeight = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 120.dp
        WindowWidthSizeClass.Medium -> 160.dp
        WindowWidthSizeClass.Expanded -> 200.dp
        else -> 120.dp
    }

    val starIconSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 28.dp
        WindowWidthSizeClass.Medium -> 36.dp
        WindowWidthSizeClass.Expanded -> 42.dp
        else -> 28.dp
    }

    val columns = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 3
        else -> 2
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            items(cocktails) { cocktail ->
                val imagePainter = when {
                    cocktail.imageUri != null -> rememberAsyncImagePainter(cocktail.imageUri)
                    cocktail.imageResId != null -> painterResource(id = cocktail.imageResId)
                    else -> painterResource(id = R.drawable.placeholder)
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onCocktailSelected(cocktail.id) },
                    elevation = CardDefaults.cardElevation()
                ) {
                    Box {
                        Column {
                            Image(
                                painter = imagePainter,
                                contentDescription = cocktail.name,
                                modifier = Modifier
                                    .height(imageHeight)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(vertical = 8.dp, horizontal = 12.dp)
                            ) {
                                Text(
                                    text = cocktail.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = titleFontSize,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }

                        if (cocktail.isFavorite) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Ulubiony",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(starIconSize)
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddClicked,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Dodaj koktajl")
        }
    }
}
