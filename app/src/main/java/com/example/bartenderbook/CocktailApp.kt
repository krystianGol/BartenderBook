package com.example.bartenderbook

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.bartenderbook.navigation.AppNavigation
import com.example.bartenderbook.ui.theme.BartenderBookTheme

@Composable
fun CocktailApp() {
    BartenderBookTheme {
        Surface {
            AppNavigation()
        }
    }
}
