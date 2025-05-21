package com.example.bartenderbook.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bartenderbook.ui.screen.add.AddCocktailScreen
import com.example.bartenderbook.ui.screen.detail.CocktailDetailScreen
import com.example.bartenderbook.ui.screen.detail.TimerViewModel
import com.example.bartenderbook.ui.screen.main.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("detail/{cocktailId}") { backStackEntry ->
                val cocktailId = backStackEntry.arguments?.getString("cocktailId")?.toIntOrNull()
                val viewModel: TimerViewModel = viewModel(backStackEntry)
                cocktailId?.let {
                    CocktailDetailScreen(it, viewModel, navController)
                }
            }
            composable("main") {
                MainScreen(navController = navController) { id ->
                    navController.navigate("detail/$id")
                }
            }
            composable("add") {
                AddCocktailScreen(navController = navController)
            }
        }
    }
}
