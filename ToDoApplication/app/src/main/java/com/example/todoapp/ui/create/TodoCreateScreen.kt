package com.example.todoapp.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// ============ PANTALLA DE CREAR TODO ============
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreateScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoCreateViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    // Observar el estado de la UI
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Observar título y descripción
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()

    // ============ EFECTO: Navegar cuando se crea ============
    // LaunchedEffect = Ejecuta algo cuando el estado cambia
    LaunchedEffect(uiState) {
        when (uiState) {
            // Si se creó exitosamente, volver a la lista
            CreateUiState.Created -> {
                onNavigateBack()
                viewModel.resetState()
            }
            // Si canceló, volver a la lista
            CreateUiState.Canceled -> {
                onNavigateBack()
                viewModel.resetState()
            }
            // Si está editando o hay error, no hacer nada
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear TODO") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.cancel() }) {
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

            // ============ CAMPO DE TÍTULO ============
            Text("Título", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.updateTitle(it) },
                placeholder = { Text("Mi nuevo TODO") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ============ CAMPO DE DESCRIPCIÓN ============
            Text("Descripción", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.updateDescription(it) },
                placeholder = { Text("Detalles...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                minLines = 4
            )

            // ============ MOSTRAR ERRORES ============
            if (uiState is CreateUiState.Error) {
                Text(
                    text = (uiState as CreateUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ============ BOTONES ============
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón Cancelar
                OutlinedButton(
                    onClick = { viewModel.cancel() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                // Botón Crear
                Button(
                    onClick = { viewModel.createTodo() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Crear")
                }
            }
        }
    }
}
