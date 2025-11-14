package com.turingalan.pokemon.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turingalan.pokemon.R
import com.turingalan.pokemon.ui.create.PokemonCreateScreen
import com.turingalan.pokemon.ui.detail.PokemonDetailScreen
import com.turingalan.pokemon.ui.list.PokemonListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    var showFab by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            )
        },
        floatingActionButton = {
            if (showFab) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "CREATE",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    icon = { Icon(Icons.Filled.Add, null) },
                    onClick = {
                        navController.navigate(Destinations.Create)
                    },
                    shape = CircleShape
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.List
        ) {
            val hostModifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding).padding(vertical = 8.dp, horizontal = 10.dp)
            composable<Destinations.List> {
                SideEffect { showFab = true }
                PokemonListScreen(
                    modifier = hostModifier,
                    onNavigateToDetail = {
                            id -> navController.navigate(Destinations.Details(id))
                    }
                )
            }
            composable<Destinations.Create> {
                SideEffect { showFab = false }
                PokemonCreateScreen(
                    modifier = hostModifier,
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }
            composable<Destinations.Details> {
                SideEffect { showFab = false }
                PokemonDetailScreen(
                    modifier = hostModifier,
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}