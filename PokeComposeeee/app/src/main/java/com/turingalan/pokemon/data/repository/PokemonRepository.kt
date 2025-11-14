package com.turingalan.pokemon.data.repository

import com.turingalan.pokemon.data.model.LocalizedString
import com.turingalan.pokemon.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun readOne(id:Long): Flow<Pokemon?>
    suspend fun readAll(): Flow<List<Pokemon>>
    suspend fun createOne(name: String, spriteId: LocalizedString.Plain, artworkId: LocalizedString.Plain): Boolean
    suspend fun deleteOne(id: Long): Boolean
}