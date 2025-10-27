package com.example.app_joserodas.viewmodel

import androidx.compose.runtime.mutableStateListOf
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

class MainViewModel : ViewModel() {

    companion object {
        private var instance: MainViewModel? = null
        fun getInstance(): MainViewModel = instance ?: MainViewModel().also { instance = it }
    }

    // --------- Tipos para Auth ---------
    enum class Rol { ADMIN, USER }
    data class AppUser(val nombre: String, val email: String, val rol: Rol)

    // 2 usuarios fijos (credenciales)
    private data class Cred(val email: String, val pass: String, val user: AppUser)
    private val seededUsers = listOf(
        Cred(
            email = "admin@palabras.com",
            pass  = "Admin1234",
            user  = AppUser(nombre = "Admin Palabras", email = "admin@palabras.com", rol = Rol.ADMIN)
        ),
        Cred(
            email = "usuario@palabras.com",
            pass  = "User1234",
            user  = AppUser(nombre = "Usuario Normal", email = "usuario@palabras.com", rol = Rol.USER)
        )
    )

    private val _currentUser = MutableStateFlow<AppUser?>(null)
    val currentUser: StateFlow<AppUser?> = _currentUser

    fun login(email: String, password: String): Result<Unit> {
        val e = email.trim().lowercase()
        val match = seededUsers.firstOrNull { it.email.equals(e, ignoreCase = true) && it.pass == password }
            ?: return Result.failure(IllegalArgumentException("Credenciales inválidas"))
        _currentUser.value = match.user
        return Result.success(Unit)
    }

    fun logout() { _currentUser.value = null }

    // --------- UI STATE (Home) ---------
    data class HomeUiState(
        val textoBusqueda: String = "",
        val estaBuscando: Boolean = false,
        val productos: List<Libro> = emptyList(),
        val destacado: Libro? = null,
        val errorMsg: String? = null
    )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    // --------- Catálogo estático ---------
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

    // 10 LIBROS (usa tus drawables reales)
    val todosLosProductos: List<Libro> = listOf(
        // 1-3 existentes
        Libro(1, "Palabras Radiantes", autores[0], editoriales[0], 18990, R.drawable.palabrasradiantes, "Segundo libro de El Archivo de las Tormentas."),
        Libro(2, "El Imperio Final",   autores[0], editoriales[1], 15990, R.drawable.imperiofinal,       "Primer libro de Nacidos de la Bruma."),
        Libro(3, "Juramentada",        autores[0], editoriales[0], 21990, R.drawable.juramentada,        "Tercer libro de El Archivo de las Tormentas."),

        // 4-10 nuevos
        Libro(4, "El Camino de los Reyes", autores[0], editoriales[0], 19990, R.drawable.caminoreyes,     "Inicio de El Archivo de las Tormentas."),
        Libro(5, "El Ritmo de la Guerra",  autores[0], editoriales[0], 23990, R.drawable.ritmoguerra,     "Cuarto libro de El Archivo de las Tormentas."),
        Libro(6, "Dune",                   Autor(4, "Frank", "Herbert"), editoriales[2], 17990, R.drawable.dune1,                "Clásico de ciencia ficción."),
        Libro(7, "El Problema de los Tres Cuerpos", Autor(5, "Cixin", "Liu"), editoriales[2], 17990, R.drawable.problema3cuerpos, "Primera entrega de la trilogía."),
        Libro(8, "Fundación",              Autor(6, "Isaac", "Asimov"),  editoriales[1], 15990, R.drawable.fundacion,            "Pilar de la ciencia ficción."),
        Libro(9, "1984",                   Autor(7, "George", "Orwell"), editoriales[2], 12990, R.drawable.libro1984,            "Distopía sobre vigilancia y control."),
        Libro(10, "Harry Potter y la Piedra Filosofal", autores[1], editoriales[1], 13990, R.drawable.piedrafilosofal,           "El comienzo del joven mago.")
    )

    // --------- Carrito ---------
    private val _carrito = mutableStateListOf<Libro>()
    val carrito: List<Libro> get() = _carrito

    // --------- Sistema de Descuentos Simple ---------
    var descuentoAplicado: Int = 0
        private set

    private val codigosDescuento = mapOf(
        "LIBRO10" to 10,
        "LECTOR15" to 15,
        "PALABRAS20" to 20,
        "RADIANTES25" to 25
    )

    init {
        _uiState.update {
            it.copy(
                productos = todosLosProductos,
                destacado = todosLosProductos.firstOrNull(),
                errorMsg = null
            )
        }
    }

    // --------- Búsqueda (StateFlow) ---------
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

    //  Sistemaa DEsc
    fun aplicarDescuento(codigo: String): String {
        val codigoLimpio = codigo.trim().uppercase()
        return when {
            codigoLimpio.isEmpty() -> "Ingresa un código de descuento"
            codigosDescuento.containsKey(codigoLimpio) -> {
                descuentoAplicado = codigosDescuento[codigoLimpio] ?: 0
                "¡Descuento del $descuentoAplicado% aplicado!"
            }
            else -> {
                descuentoAplicado = 0
                "Código inválido o expirado"
            }
        }
    }

    fun limpiarDescuento() { descuentoAplicado = 0 }

    //Cálculos Descuento
    fun obtenerTotalCarrito(): Int = _carrito.sumOf { it.precio }

    fun obtenerTotalConDescuento(): Int {
        val total = obtenerTotalCarrito()
        val descuento = (total * descuentoAplicado) / 100
        return total - descuento
    }

    fun obtenerAhorro(): Int {
        val total = obtenerTotalCarrito()
        return (total * descuentoAplicado) / 100
    }

    // --------- Carrito functions ---------
    fun getProductoPorId(idLibro: Int): Libro? = todosLosProductos.find { it.idLibro == idLibro }
    fun agregarAlCarrito(libro: Libro) = _carrito.add(libro)
    fun eliminarDelCarrito(libro: Libro) = _carrito.remove(libro)
    fun limpiarCarrito() { _carrito.clear(); limpiarDescuento() }
    fun obtenerCantidadTotal(): Int = _carrito.size
}
