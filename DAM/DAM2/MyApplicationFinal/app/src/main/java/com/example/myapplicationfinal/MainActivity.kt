package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadybugTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyDropDownMenu()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyDropDownMenu(modifier: Modifier = Modifier) {
        var selectedText: String by remember { mutableStateOf("") }
        var expanded: Boolean by remember { mutableStateOf(false) }
        val options = listOf(
            "Add" to Icons.Default.Add,
            "Call" to Icons.Default.Call,
            "Email" to Icons.Default.Email,
            "Star" to Icons.Default.Star,
            "Home" to Icons.Default.Home,
            "Search" to Icons.Default.Search,
            "Settings" to Icons.Default.Settings,
            "Favorite" to Icons.Default.Favorite,
            "Location" to Icons.Default.LocationOn,
            "Person" to Icons.Default.Person
        )

        var minText by remember { mutableStateOf(TextFieldValue("0")) }
        var maxText by remember { mutableStateOf(TextFieldValue("100")) }
        var sliderValue by remember { mutableStateOf(50f) }

        Column(modifier = Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable { expanded = true }
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary),
                placeholder = { Text("Selecciona un icono") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Seleccionar"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { (text, icon) ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = icon, contentDescription = text)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = text)
                            }
                        },
                        onClick = {
                            expanded = false
                            selectedText = text
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = minText,
                    onValueChange = { minText = it },
                    label = { Text("Min") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = maxText,
                    onValueChange = { maxText = it },
                    label = { Text("Max") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }

            val min = minText.text.toIntOrNull() ?: 0
            val max = maxText.text.toIntOrNull() ?: 100

            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = min.toFloat()..max.toFloat(),
                steps = (max - min) - 1,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { /* Acción del botón */ },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Enviar")
            }

            if (selectedText.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Blue)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = options.find { it.first == selectedText }?.second ?: Icons.Default.Check,
                        contentDescription = selectedText,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Valor del Slider: ${sliderValue.toInt()}",
                        color = Color.White
                    )
                }
            }
        }
    }
    @Composable
    fun LadybugTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (darkTheme) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }

        MaterialTheme(
            colorScheme = colors,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }

    @Preview (showBackground = true, showSystemUi = true)
    @Composable
    fun preview2 (modifier: Modifier = Modifier) {
        MyDropDownMenu()
    }
}