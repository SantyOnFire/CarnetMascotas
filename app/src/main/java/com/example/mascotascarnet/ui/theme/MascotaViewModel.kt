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
    private val _listaMascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val listaMascotas: StateFlow<List<Mascota>> = _listaMascotas.asStateFlow()

    fun registrarMascota(mascota: Mascota) {
        _listaMascotas.value = _listaMascotas.value + mascota
    }

    fun eliminarMascota(mascota: Mascota) {
        _listaMascotas.value = _listaMascotas.value - mascota
    }
}



