package com.turingalan.pokemon.data.model

sealed class LocalizedString {
    data class Res(val resId: Int) : LocalizedString()
    data class Plain(val text: String) : LocalizedString()
}

data class Pokemon(
    val id: Long,
    val name: String,
    val spriteId: LocalizedString,
    val artworkId: LocalizedString,
)