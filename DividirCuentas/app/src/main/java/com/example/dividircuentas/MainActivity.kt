package com.example.dividircuentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Switch
import androidx.compose.runtime.produceState
import kotlin.math.ceil
import kotlin.math.round


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DividirCuentasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var totalConPropina by remember { mutableStateOf(0f) }
    var montoPorPersona by remember { mutableStateOf(0f) }

    var cantidad by remember { mutableStateOf("") }
    var comensales by remember { mutableStateOf("") }
    var propina by remember { mutableStateOf(0f) }
    var redondear by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
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

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Redondear propina")
            Switch(
                checked = redondear,
                onCheckedChange = {
                    redondear = it
                    if (!it) {
                        propina = 0f
                    }
                }
            )
        }

        Text(text = "Porcentaje de propina: ${propina.toInt()}%")
        Slider(
            value = if (redondear) propina else 0f,
            onValueChange = { if (redondear) propina = it },
            valueRange = 0f..25f,
            steps = 4,
            enabled = redondear
        )

        Button(
            onClick = {
                val cantidadFloat = cantidad.toFloatOrNull() ?: 0f
                val comensalesInt = comensales.toIntOrNull() ?: 1

                val (total, porPersona) = calcularDivision(
                    montoTotal = cantidadFloat,
                    numeroDePersonas = comensalesInt,
                    porcentajePropina = propina,
                    redondear = redondear
                )

                totalConPropina = total
                montoPorPersona = porPersona
            }
        ) {
            Text("Calcular")
        }

        Text(text = "Total a pagar: $${String.format("%.2f", totalConPropina)}")
        Text(text = "Cada uno paga: $${String.format("%.2f", montoPorPersona)}")

    }

}

private fun calcularDivision(
    montoTotal: Float,
    numeroDePersonas: Int,
    porcentajePropina: Float,
    redondear: Boolean
): Pair<Float, Float> {
    if (montoTotal <= 0f || numeroDePersonas <= 0) {
        return Pair(0f, 0f)
    }

    val multiplicadorPropina = 1 + porcentajePropina / 100
    var totalConPropina = montoTotal * multiplicadorPropina

    if (redondear) {
        totalConPropina = ceil(totalConPropina)
    }

    val montoPorPersona = totalConPropina / numeroDePersonas

    return Pair(totalConPropina, montoPorPersona)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DividirCuentasTheme {
        Greeting()
    }
}