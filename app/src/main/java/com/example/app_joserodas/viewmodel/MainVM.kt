package com.example.app_joserodas.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_joserodas.R
import com.example.app_joserodas.model.Libro

class MainViewModel : ViewModel() {

    // TODO: Reemplaza imagenRes por portadas reales en drawable (ej: urlimagen.png)
    val productos: List<Libro> = listOf(
        Libro(1, "Palabras Radiantes", "Brandon Sanderson", 18990, R.drawable.logo_palabras_radiantes, "Segundo libro de la saga."),
        Libro(2, "El Imperio Final", "Brandon Sanderson", 15990, R.drawable.carrito, "Primer libro de Nacidos de la Bruma."),
        Libro(3, "Juramentada", "Brandon Sanderson", 21990, R.drawable.instagramlogo, "Tercer libro del Archivo de las Tormentas.")
    )
    //destacado slider toma ya chavale
    val destacados: List<Libro> = listOf(productos[0], productos[1], productos[2])
//buscar producto x id
    fun getProductoporId(id: Int): Libro? = productos.find { it.id == id }
}