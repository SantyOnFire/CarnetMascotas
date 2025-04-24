package com.example.mascotascarnet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Mascota(
    val nombre: String = "",
    val raza: String = "",
    val tamaño: String = "",
    val edad: String = "",
    val fotoUrl: String = ""
)

class MascotaViewModel : ViewModel() {
    private val _mascota = MutableStateFlow(Mascota())
    val mascota: StateFlow<Mascota> = _mascota.asStateFlow()

    fun registrarMascota(nuevaMascota: Mascota) {
        viewModelScope.launch {
            _mascota.emit(nuevaMascota)
        }
    }
}