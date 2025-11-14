package com.turingalan.pokemon.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.turingalan.pokemon.data.model.LocalizedString
import com.turingalan.pokemon.isHttpUrl

@Composable
fun PokemonListItemScreen(
    id: Long,
    name: String,
    spriteId: LocalizedString,
    onClickItem: (Long) -> Unit
) {
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth().clickable(true, onClick = {
            onClickItem(id)
        }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //si es un resource se usa image, si es texto se usa AsyncImage y se pone por medio de url
        when (spriteId) {
            is LocalizedString.Plain -> {
                val text = spriteId.text
                if (text.isHttpUrl()) {
                    AsyncImage(
                        model = text,
                        modifier = Modifier.size(128.dp).padding(8.dp).clip(CircleShape),
                        contentDescription = "Pokemon image",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            is LocalizedString.Res -> {
                Image(
                    painter = painterResource(spriteId.resId),
                    modifier = Modifier.size(128.dp).padding(8.dp).clip(CircleShape),
                    contentDescription = "Pokemon image",
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}