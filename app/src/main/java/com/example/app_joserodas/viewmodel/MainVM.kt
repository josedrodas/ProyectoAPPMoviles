package com.example.app_joserodas.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.app_joserodas.R
import com.example.app_joserodas.model.Autor
import com.example.app_joserodas.model.Editorial
import com.example.app_joserodas.model.Libro

class MainViewModel : ViewModel() {

    companion object {
        // Instancia única del ViewModel
        private var instance: MainViewModel? = null

        fun getInstance(): MainViewModel {
            return instance ?: MainViewModel().also { instance = it }
        }
    }

    private val autores = listOf(
        Autor(idAutor = 1, nombre = "Brandon", apellido = "Sanderson"),
        Autor(idAutor = 2, nombre = "J. K.", apellido = "Rowling"),
        Autor(idAutor = 3, nombre = "Isabel", apellido = "Allende")
    )

    private val editoriales = listOf(
        Editorial(idEditorial = 1, nombre = "Planeta", pais = "Chile"),
        Editorial(idEditorial = 2, nombre = "Santillana", pais = "España"),
        Editorial(idEditorial = 3, nombre = "Anagrama", pais = "España")
    )

    val productos: List<Libro> = listOf(
        Libro(
            idLibro = 1,
            titulo = "Palabras Radiantes",
            autor = autores[0],
            editorial = editoriales[0],
            precio = 18990,
            imagenRes = R.drawable.palabrasradiantes,
            descripcion = "Segundo libro de la saga El Archivo de las Tormentas."
        ),
        Libro(
            idLibro = 2,
            titulo = "El Imperio Final",
            autor = autores[0],
            editorial = editoriales[1],
            precio = 15990,
            imagenRes = R.drawable.imperiofinal,
            descripcion = "Primer libro de Nacidos de la Bruma."
        ),
        Libro(
            idLibro = 3,
            titulo = "Juramentada",
            autor = autores[0],
            editorial = editoriales[0],
            precio = 21990,
            imagenRes = R.drawable.juramentada,
            descripcion = "Tercer libro del Archivo de las Tormentas."
        )
    )

    // Carrito de compras
    private val _carrito = mutableStateListOf<Libro>()
    val carrito: List<Libro> get() = _carrito

    fun getProductoPorId(idLibro: Int): Libro? = productos.find { it.idLibro == idLibro }

    // Funciones del carrito
    fun agregarAlCarrito(libro: Libro) {
        _carrito.add(libro)
    }

    fun eliminarDelCarrito(libro: Libro) {
        _carrito.remove(libro)
    }

    fun limpiarCarrito() {
        _carrito.clear()
    }

    fun obtenerTotalCarrito(): Int {
        return _carrito.sumOf { it.precio }
    }

    fun obtenerCantidadTotal(): Int {
        return _carrito.size
    }
}