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
                var pantallaActual by remember { mutableStateOf("ScreenA") }
                var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
                var mostrarDialogo by remember { mutableStateOf(false) }

                val listaMascotas by mascotaViewModel.listaMascotas.collectAsState()

                Scaffold { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        when (pantallaActual) {
                            "ScreenA" -> ScreenA { nombre, raza, tamano, edad, fotoUrl ->
                                val nuevaMascota = Mascota(nombre, raza, tamano, edad, fotoUrl)
                                mascotaViewModel.registrarMascota(nuevaMascota)
                                pantallaActual = "ScreenC"
                            }

                            "ScreenC" -> ScreenC(
                                mascotas = listaMascotas,
                                onMascotaClick = { mascota ->
                                    mascotaSeleccionada = mascota
                                    pantallaActual = "ScreenB"
                                }
                            )

                            "ScreenB" -> {
                                mascotaSeleccionada?.let { mascota ->
                                    Column(modifier = Modifier.fillMaxSize()) {
                                        ScreenB(
                                            nombre = mascota.nombre,
                                            raza = mascota.raza,
                                            tamaño = mascota.tamaño,
                                            edad = mascota.edad,
                                            fotoUrl = mascota.fotoUrl
                                        )

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Button(onClick = {
                                                pantallaActual = "ScreenC"
                                            }) {
                                                Text("Volver")
                                            }

                                            Button(
                                                onClick = { mostrarDialogo = true },
                                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                            ) {
                                                Text("Eliminar")
                                            }
                                        }

                                        if (mostrarDialogo) {
                                            AlertDialog(
                                                onDismissRequest = { mostrarDialogo = false },
                                                title = { Text("Confirmar eliminación") },
                                                text = { Text("¿Estás seguro de que deseas eliminar esta mascota?") },
                                                confirmButton = {
                                                    TextButton(onClick = {
                                                        mascotaSeleccionada?.let {
                                                            mascotaViewModel.eliminarMascota(it)
                                                        }
                                                        mostrarDialogo = false
                                                        pantallaActual = "ScreenC"
                                                    }) {
                                                        Text("Sí")
                                                    }
                                                },
                                                dismissButton = {
                                                    TextButton(onClick = {
                                                        mostrarDialogo = false
                                                    }) {
                                                        Text("No")
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
