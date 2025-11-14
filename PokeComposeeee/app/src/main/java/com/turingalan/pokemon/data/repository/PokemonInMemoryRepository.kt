package com.turingalan.pokemon.data.repository

import com.turingalan.pokemon.R
import com.turingalan.pokemon.data.model.LocalizedString
import com.turingalan.pokemon.data.model.Pokemon
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonInMemoryRepository @Inject constructor(): PokemonRepository {

    private val _pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(
        listOf(
            Pokemon(
                id = 1,
                name = "Bulbasaur",
                spriteId = LocalizedString.Res(R.drawable.sprite_1),
                artworkId = LocalizedString.Res(R.drawable.artwork_1)
            ),
            Pokemon(
                id = 4,
                name = "Charmander",
                spriteId = LocalizedString.Res(R.drawable.sprite_4),
                artworkId = LocalizedString.Res(R.drawable.artwork_4)
            ),
            Pokemon(
                id = 7,
                name = "Squirtle",
                spriteId = LocalizedString.Res(R.drawable.sprite_7),
                artworkId = LocalizedString.Res(R.drawable.artwork_7)
            ),
            Pokemon(
                id = 10,
                name = "Caterpie",
                spriteId = LocalizedString.Res(R.drawable.sprite_10),
                artworkId = LocalizedString.Res(R.drawable.artwork_10)
            ),
            Pokemon(
                id = 25,
                name = "Pikachu",
                spriteId = LocalizedString.Res(R.drawable.sprite_25),
                artworkId = LocalizedString.Res(R.drawable.artwork_25)
            ),
            Pokemon(
                id = 39,
                name = "JigglyPuff",
                spriteId = LocalizedString.Res(R.drawable.sprite_39),
                artworkId = LocalizedString.Res(R.drawable.artwork_39)
            ),
            Pokemon(
                id = 133,
                name = "Eeve",
                spriteId = LocalizedString.Res(R.drawable.sprite_133),
                artworkId = LocalizedString.Res(R.drawable.artwork_133)
            ),
            Pokemon(
                id = 143,
                name = "Snorlax",
                spriteId = LocalizedString.Res(R.drawable.sprite_143),
                artworkId = LocalizedString.Res(R.drawable.artwork_143)
            )
        )
    )

    private val pokemonList = _pokemonList.asStateFlow()

    override suspend fun readOne(id: Long): Flow<Pokemon?> {
        return pokemonList.map { pokemons ->
            pokemons.find { it.id == id }
        }
    }

    override suspend fun readAll(): Flow<List<Pokemon>> {
        delay(2500L)
        return this.pokemonList
    }

    override suspend fun createOne(name: String, spriteId: LocalizedString.Plain, artworkId: LocalizedString.Plain): Boolean {
        val currentPokemons = _pokemonList.value.toMutableList()
        val nextId = (currentPokemons.maxOfOrNull { it.id } ?: 0) + 1
        val newPokemon = Pokemon(
            id = nextId,
            name = name,
            spriteId = spriteId,
            artworkId = artworkId,
        )
        val added = currentPokemons.add(newPokemon)
        _pokemonList.value = currentPokemons
        return added
    }

    override suspend fun deleteOne(id: Long): Boolean {
        val currentPokemons = _pokemonList.value.toMutableList()
        val removed = currentPokemons.removeIf { it.id == id }
        _pokemonList.value = currentPokemons
        return removed
    }
}