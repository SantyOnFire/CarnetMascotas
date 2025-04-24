package com.example.mascotascarnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mascotascarnet.ui.theme.MascotasCarnetTheme

class MainActivity : ComponentActivity() {
    private val mascotaViewModel: MascotaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MascotasCarnetTheme {
                var mostrarCarnet by remember { mutableStateOf(false) }

                Scaffold { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        if (!mostrarCarnet) {
                            ScreenA { nombre, raza, tamano, edad, fotoUrl ->
                                mascotaViewModel.registrarMascota(
                                    Mascota(
                                        nombre = nombre,
                                        raza = raza,
                                        tamaño = tamano,
                                        edad = edad,
                                        fotoUrl = fotoUrl
                                    )
                                )
                                mostrarCarnet = true
                            }
                        } else {
                            val mascota by mascotaViewModel.mascota.collectAsState()

                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                ScreenB(
                                    raza = mascota.raza,
                                    tamaño = mascota.tamaño,
                                    fotoUrl = mascota.fotoUrl
                                )

                                Button(
                                    onClick = { mostrarCarnet = false },
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text("Volver")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}