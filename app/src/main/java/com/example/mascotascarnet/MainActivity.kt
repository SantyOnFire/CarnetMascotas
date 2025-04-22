package com.example.mascotascarnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                            ScreenA { nombre, raza, edad, peso ->
                                val nuevaMascota = Mascota(
                                    nombre = nombre,
                                    raza = raza,
                                    edad = edad,
                                    tamaño = peso, // Aquí usamos 'peso' como tamaño por compatibilidad con tu ViewModel
                                    fotoUrl = "https://cdn-icons-png.flaticon.com/512/616/616408.png" // Puedes cambiarlo luego
                                )
                                mascotaViewModel.registrarMascota(nuevaMascota)
                                mostrarCarnet = true
                            }
                        } else {
                            val mascota by mascotaViewModel.mascota.collectAsState()

                            ScreenB(
                                nombre = mascota.nombre,
                                raza = mascota.raza,
                                edad = mascota.edad,
                                fotoUrl = mascota.fotoUrl
                            )

                            Button(
                                onClick = { mostrarCarnet = false },
                                modifier = Modifier.padding(16.dp)
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
