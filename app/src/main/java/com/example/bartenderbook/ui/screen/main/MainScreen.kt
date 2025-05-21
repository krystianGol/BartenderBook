package com.example.bartenderbook.ui.screen.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bartenderbook.R
import com.example.bartenderbook.ui.screen.category.CategoryGridScreen
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(navController: NavHostController, onCocktailSelected: (Int) -> Unit) {
    val tabs = listOf(
        "Start" to "",
        "Alkoholowe" to "alcohol",
        "Bezalkoholowe" to "non_alcohol"
    )
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    val windowSize = calculateWindowSizeClass(LocalContext.current as Activity)
    val topBarFontSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 26.sp
        WindowWidthSizeClass.Medium -> 30.sp
        WindowWidthSizeClass.Expanded -> 32.sp
        else -> 24.sp
    }

    val tabFontSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 15.sp
        WindowWidthSizeClass.Medium -> 22.sp
        WindowWidthSizeClass.Expanded -> 26.sp
        else -> 18.sp
    }

    val iconSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 45.dp
        WindowWidthSizeClass.Medium -> 70.dp
        WindowWidthSizeClass.Expanded -> 72.dp
        else -> 45.dp
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Drinkopedia",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = topBarFontSize,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.cocktail_icon),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(iconSize),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, (label, _) ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = label,
                                fontSize = tabFontSize,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> HomeScreen()
                    else -> CategoryGridScreen(
                        category = tabs[page].second,
                        onCocktailSelected = onCocktailSelected,
                        onAddClicked = {
                            navController.navigate("add")
                        }
                    )
                }
            }
        }
    }
}
