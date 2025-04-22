package com.example.mascotascarnet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Mascota(
    val nombre: String = "",
    val raza: String = "",
    val tamaño: String = "",
    val edad: String = "",
    val fotoUrl: String = ""
)

class MascotaViewModel : ViewModel() {
    private val _mascota = MutableStateFlow(Mascota())
    val mascota: StateFlow<Mascota> = _mascota

    fun registrarMascota(nuevaMascota: Mascota) {
        _mascota.value = nuevaMascota
    }
}
