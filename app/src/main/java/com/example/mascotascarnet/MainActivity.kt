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
                                pantallaActual = "ScreenC" // Cambia a la pantalla C después de agregar la mascota
                            }

                            "ScreenC" -> ScreenC(
                                mascotas = listaMascotas,
                                onMascotaClick = { mascota ->
                                    mascotaSeleccionada = mascota
                                    pantallaActual = "ScreenB" // Cambia a la pantalla B al seleccionar una mascota
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
                                            fotoUrl = mascota.fotoUrl,
                                            onGuardarClick = {
                                                // Lógica para guardar si es necesario
                                                pantallaActual = "ScreenC" // Volver a la pantalla C después de guardar
                                            },
                                            onEliminarClick = {
                                                // Lógica para eliminar la mascota
                                                mascotaViewModel.eliminarMascota(mascota)
                                                pantallaActual = "ScreenC" // Volver a la pantalla C después de eliminar
                                            }
                                        )

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Button(onClick = {
                                                pantallaActual = "ScreenC" // Volver a la pantalla C
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
                                                            pantallaActual = "ScreenC" // Volver a la pantalla C después de eliminar
                                                        }
                                                        mostrarDialogo = false
                                                    }) {
                                                        Text("Sí")
                                                    }
                                                },
                                                dismissButton = {
                                                    TextButton(onClick = {
                                                        mostrarDialogo = false // Solo cierra el diálogo si se cancela
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
