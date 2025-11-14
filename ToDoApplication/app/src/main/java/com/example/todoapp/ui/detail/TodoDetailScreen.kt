package com.example.todoapp.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// ============ PANTALLA DE DETALLES ============
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    // Observar el TODO desde el ViewModel
    val todo by viewModel.todo.collectAsStateWithLifecycle(initialValue = null)

    // Si aún no cargó, mostrar loading
    if (todo == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Si ya cargó, mostrar los detalles
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del TODO") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ============ TÍTULO ============
            Text(
                text = "Título",
                style = MaterialTheme.typography.labelMedium
            )
            Card {
                Text(
                    text = todo!!.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // ============ DESCRIPCIÓN ============
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.labelMedium
            )
            Card {
                Text(
                    text = todo!!.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
