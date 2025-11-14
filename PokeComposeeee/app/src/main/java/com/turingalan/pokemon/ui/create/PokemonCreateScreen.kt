package com.turingalan.pokemon.ui.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.turingalan.pokemon.data.model.LocalizedString
import com.turingalan.pokemon.isHttpUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCreateScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonCreateViewModel = hiltViewModel(),
    onCancel: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val spriteId by viewModel.spriteId.collectAsStateWithLifecycle()
    val artworkId by viewModel.artworkId.collectAsStateWithLifecycle()

    // --- FLAGS LOCALES: touched por campo + intento de submit ---
    val nameTouched = remember { mutableStateOf(false) }
    val spriteTouched = remember { mutableStateOf(false) }
    val artworkTouched = remember { mutableStateOf(false) }
    val submitAttempt = remember { mutableStateOf(false) }

    // Validaciones por campo (solo mensaje, no bloqueo aquí)
    val nameError: String? = if (name.isBlank()) "Name is mandatory" else null
    val spriteError: String? = run {
        val t = spriteId.text
        if (t.isBlank()) "Sprite is Mandatory"
        else if (!t.isHttpUrl()) "Sprite isn't a valid URL"
        else null
    }
    val artworkError: String? = run {
        val t = artworkId.text
        if (t.isBlank()) "Artwork is mandatory"
        else if (!t.isHttpUrl()) "Artwork isn't a valid URL"
        else null
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is CreateUiState.Created, CreateUiState.Canceled -> {
                onCancel()
                viewModel.resetState()
                // limpiar flags locales por si vuelve a esta pantalla
                submitAttempt.value = false
                nameTouched.value = false
                spriteTouched.value = false
                artworkTouched.value = false
            }
            else -> {}
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            val artText = artworkId.text
            if (artText.isNotBlank() && artText.isHttpUrl()) {
                AsyncImage(
                    model = artText,
                    contentDescription = name,
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = name.ifBlank { "Preview" }, style = MaterialTheme.typography.headlineSmall)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NAME
            Text("Name", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = name,
                onValueChange = {
                    nameTouched.value = true
                    viewModel.updateName(it)
                },
                placeholder = { Text("Pokemon name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = (nameError != null) && (nameTouched.value || submitAttempt.value),
                supportingText = {
                    if ((nameError != null) && (nameTouched.value || submitAttempt.value)) {
                        Text(nameError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // SPRITE
            Text("Sprite (URL)", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = spriteId.text,
                onValueChange = {
                    spriteTouched.value = true
                    viewModel.updateSpriteId(LocalizedString.Plain(it))
                },
                placeholder = { Text("https://...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = (spriteError != null) && (spriteTouched.value || submitAttempt.value),
                supportingText = {
                    if ((spriteError != null) && (spriteTouched.value || submitAttempt.value)) {
                        Text(spriteError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ARTWORK
            Text("Artwork (URL)", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = artworkId.text,
                onValueChange = {
                    artworkTouched.value = true
                    viewModel.updateArtworkId(LocalizedString.Plain(it))
                },
                placeholder = { Text("https://...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = (artworkError != null) && (artworkTouched.value || submitAttempt.value),
                supportingText = {
                    if ((artworkError != null) && (artworkTouched.value || submitAttempt.value)) {
                        Text(artworkError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            // Error general del ViewModel (si lo hay)
            if (uiState is CreateUiState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (uiState as CreateUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        // reset local touched flags si cancela manualmente
                        viewModel.cancel()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("CANCEL")
                }
                Button(
                    onClick = {
                        // marcar intento de submit para forzar mostrar errores si existen
                        submitAttempt.value = true
                        viewModel.createPokemon()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("CREATE")
                }
            }
        }
    }
}
