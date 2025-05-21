package com.example.bartenderbook.ui.screen.add

import Cocktail
import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.bartenderbook.R
import com.example.bartenderbook.data.repository.HardcodedCocktailRepository

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AddCocktailScreen(navController: NavHostController) {
    val windowSize = calculateWindowSizeClass(LocalContext.current as Activity)

    val textSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 20.sp
        WindowWidthSizeClass.Medium -> 22.sp
        WindowWidthSizeClass.Expanded -> 26.sp
        else -> 18.sp
    }

    var name by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var preparation by remember { mutableStateOf("") }
    var timePreparation by remember { mutableStateOf("") }
    var isAlcoholic by remember { mutableStateOf(true) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    val repository = HardcodedCocktailRepository

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dodaj nowy koktajl",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Wróć",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nazwa koktajlu", fontSize = textSize) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = textSize)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Składniki (oddzielone przecinkami)", fontSize = textSize) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = textSize)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = preparation,
                onValueChange = { preparation = it },
                label = { Text("Sposób przygotowania", fontSize = textSize) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = textSize)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = timePreparation,
                onValueChange = { timePreparation = it },
                label = { Text("Czas przygotowania (sekundy)", fontSize = textSize) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = textSize)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Alkoholowy:", fontSize = textSize)
                Switch(
                    checked = isAlcoholic,
                    onCheckedChange = { isAlcoholic = it },
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Zdjęcie:", fontSize = textSize)
            Spacer(modifier = Modifier.height(8.dp))

            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Wybierz zdjęcie z galerii", fontSize = textSize)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && timePreparation.toIntOrNull() != null) {
                        repository.addCocktail(
                            Cocktail(
                                id = (repository.getCocktails().maxOfOrNull { it.id } ?: 0) + 1,
                                name = name,
                                ingredients = ingredients.split(",").map { it.trim() },
                                preparation = preparation,
                                timePreparation = timePreparation.toInt(),
                                imageResId = null,
                                imageUri = selectedImageUri?.toString(),
                                isAlcoholic = isAlcoholic
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zapisz", fontSize = textSize)
            }
        }
    }
}
