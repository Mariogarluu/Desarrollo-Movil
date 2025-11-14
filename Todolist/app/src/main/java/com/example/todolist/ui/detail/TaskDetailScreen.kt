package com.example.todolist.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist.viewmodel.ToDoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Long,
    navController: NavController,
    viewModel: ToDoViewModel
) {
    val task = remember(taskId) { viewModel.getTaskById(taskId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = task?.title ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (task != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Descripción", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = task.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Activa", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    // Switch deshabilitado (gris) — no hace nada funcional
                    var checked by remember { mutableStateOf(task.completed) }
                    Switch(
                        checked = checked,
                        onCheckedChange = { /* no-op: UI solo */ },
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "El switch está deshabilitado (solo UI).",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                Text("Tarea no encontrada")
            }
        }
    }
}
