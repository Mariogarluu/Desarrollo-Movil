package com.turingalan.pokemon

import android.net.Uri
import android.util.Patterns

fun String.isHttpUrl(): Boolean {
    return try {
        val uri = Uri.parse(this)
        val schemeOk = uri.scheme == "http" || uri.scheme == "https"
        schemeOk && Patterns.WEB_URL.matcher(this).matches()
    } catch (e: Exception) {
        false
    }
}