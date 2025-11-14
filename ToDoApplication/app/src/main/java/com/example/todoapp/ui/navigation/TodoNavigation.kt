package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.ui.create.TodoCreateScreen
import com.example.todoapp.ui.detail.TodoDetailScreen
import com.example.todoapp.ui.list.TodoListScreen

// ============ RUTAS DE NAVEGACIÓN ============
object TodoRoutes {
    const val LIST = "list"                    // Pantalla de lista
    const val CREATE = "create"                // Pantalla de crear
    const val DETAIL = "detail/{todoId}"       // Pantalla de detalles con ID

    // Función para generar la ruta de detalles
    fun detail(todoId: Long) = "detail/$todoId"
}

// ============ CONFIGURACIÓN DE NAVEGACIÓN ============
@Composable
fun TodoNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TodoRoutes.LIST,  // Pantalla inicial
        modifier = modifier
    ) {
        // ============ PANTALLA DE LISTA ============
        composable(route = TodoRoutes.LIST) {
            TodoListScreen(
                onNavigateToCreate = {
                    navController.navigate(TodoRoutes.CREATE)
                },
                onNavigateToDetail = { todoId ->
                    navController.navigate(TodoRoutes.detail(todoId))
                }
            )
        }

        // ============ PANTALLA DE CREAR ============
        composable(route = TodoRoutes.CREATE) {
            TodoCreateScreen(
                onNavigateBack = {
                    navController.popBackStack()  // Volver a la lista
                }
            )
        }

        // ============ PANTALLA DE DETALLES ============
        composable(
            route = TodoRoutes.DETAIL,
            // Pasar el todoId como parámetro
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.LongType
                }
            )
        ) {
            TodoDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()  // Volver a la lista
                }
            )
        }
    }
}
