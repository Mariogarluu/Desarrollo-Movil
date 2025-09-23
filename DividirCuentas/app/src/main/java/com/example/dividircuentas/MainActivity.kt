package com.example.dividircuentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dividircuentas.ui.theme.DividirCuentasTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Switch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DividirCuentasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var cantidad by remember { mutableStateOf("") }
    var comensales by remember { mutableStateOf("") }
    var redondear by remember { mutableStateOf(false) }

    TextField(
        value = cantidad,
        onValueChange = { cantidad = it },
        label = { Text("Cantidad") }
    )

    TextField(
        value = comensales,
        onValueChange = { comensales = it },
        label = { Text("Comensales") }
    )

    Switch(
        checked = redondear,
        onCheckedChange = {
            redondear = it
        }
    )

    Slider(

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DividirCuentasTheme {
        Greeting("Android")
    }
}