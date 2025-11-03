package com.example.app_joserodas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_joserodas.R
import com.example.app_joserodas.model.Autor
import com.example.app_joserodas.model.Editorial
import com.example.app_joserodas.model.Libro
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val textoBusqueda: String = "",
    val estaBuscando: Boolean = false,
    val productos: List<Libro> = emptyList(),
    val destacado: Libro? = null,
    val errorMsg: String? = null
)

class HomeViewModel : ViewModel() {

    // catálogo base
    private val autores = listOf(
        Autor(1, "Brandon", "Sanderson"),
        Autor(2, "J. K.", "Rowling"),
        Autor(3, "Isabel", "Allende")
    )

    private val editoriales = listOf(
        Editorial(1, "Planeta", "Chile"),
        Editorial(2, "Santillana", "España"),
        Editorial(3, "Anagrama", "España")
    )

    val todosLosProductos: List<Libro> = listOf(
        Libro(
            1,
            "Palabras Radiantes",
            autores[0],
            editoriales[0],
            18990,
            R.drawable.palabrasradiantes,
            "Segundo libro de El Archivo de las Tormentas."
        ),
        Libro(
            2,
            "El Imperio Final",
            autores[0],
            editoriales[1],
            15990,
            R.drawable.imperiofinal,
            "Primer libro de Nacidos de la Bruma."
        ),
        Libro(
            3,
            "Juramentada",
            autores[0],
            editoriales[0],
            21990,
            R.drawable.juramentada,
            "Tercer libro de El Archivo de las Tormentas."
        ),

        Libro(
            4,
            "El Camino de los Reyes",
            autores[0],
            editoriales[0],
            19990,
            R.drawable.caminoreyes,
            "Inicio de El Archivo de las Tormentas."
        ),
        Libro(
            5,
            "El Ritmo de la Guerra",
            autores[0],
            editoriales[0],
            23990,
            R.drawable.ritmoguerra,
            "Cuarto libro de El Archivo de las Tormentas."
        ),
        Libro(
            6,
            "Dune",
            Autor(4, "Frank", "Herbert"),
            editoriales[2],
            17990,
            R.drawable.dune1,
            "Clásico de ciencia ficción."
        ),
        Libro(
            7,
            "El Problema de los Tres Cuerpos",
            Autor(5, "Cixin", "Liu"),
            editoriales[2],
            17990,
            R.drawable.problema3cuerpos,
            "Primera entrega de la trilogía."
        ),
        Libro(
            8,
            "Fundación",
            Autor(6, "Isaac", "Asimov"),
            editoriales[1],
            15990,
            R.drawable.fundacion,
            "Pilar de la ciencia ficción."
        ),
        Libro(
            9,
            "1984",
            Autor(7, "George", "Orwell"),
            editoriales[2],
            12990,
            R.drawable.libro1984,
            "Distopía sobre vigilancia y control."
        ),
        Libro(
            10,
            "Harry Potter y la Piedra Filosofal",
            autores[1],
            editoriales[1],
            13990,
            R.drawable.piedrafilosofal,
            "El comienzo del joven mago."
        )
    )


    private val _uiState = MutableStateFlow(
        HomeUiState(
            productos = todosLosProductos,
            destacado = todosLosProductos.firstOrNull(),
            errorMsg = null
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState

    fun buscarProductos(texto: String) {
        _uiState.update { it.copy(textoBusqueda = texto, estaBuscando = true) }

        viewModelScope.launch {
            delay(300)
            val query = texto.trim().lowercase()

            val resultados = if (query.isBlank()) {
                todosLosProductos
            } else {
                todosLosProductos.filter { libro ->
                    libro.titulo.lowercase().contains(query) ||
                            libro.autor.nombre.lowercase().contains(query) ||
                            libro.autor.apellido.lowercase().contains(query) ||
                            libro.editorial.nombre.lowercase().contains(query)
                }
            }

            _uiState.update {
                it.copy(
                    estaBuscando = false,
                    productos = resultados,
                    errorMsg = if (resultados.isEmpty()) "Sin resultados" else null
                )
            }
        }
    }

    fun limpiarBusqueda() {
        _uiState.update {
            it.copy(
                textoBusqueda = "",
                estaBuscando = false,
                productos = todosLosProductos,
                errorMsg = null
            )
        }
    }

    fun setDestacado(libro: Libro?) {
        _uiState.update { it.copy(destacado = libro) }
    }

    fun getProductoPorId(idLibro: Int): Libro? =
        todosLosProductos.find { it.idLibro == idLibro }
}
