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

    val todosLosProductos: List<Libro> = listOf(
        Libro(1, "Palabras Radiantes", autores[0], editoriales[0], 18990, R.drawable.palabrasradiantes, "Segundo libro de la saga El Archivo de las Tormentas."),
        Libro(2, "El Imperio Final", autores[0], editoriales[1], 15990, R.drawable.imperiofinal, "Primer libro de Nacidos de la Bruma."),
        Libro(3, "Juramentada", autores[0], editoriales[0], 21990, R.drawable.juramentada, "Tercer libro del Archivo de las Tormentas.")
    )

    // --------- Carrito (igual que ya tenías) ---------
    private val _carrito = mutableStateListOf<Libro>()
    val carrito: List<Libro> get() = _carrito

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

    // --------- Carrito functions ---------
    fun getProductoPorId(idLibro: Int): Libro? = todosLosProductos.find { it.idLibro == idLibro }
    fun agregarAlCarrito(libro: Libro) = _carrito.add(libro)
    fun eliminarDelCarrito(libro: Libro) = _carrito.remove(libro)
    fun limpiarCarrito() = _carrito.clear()
    fun obtenerTotalCarrito(): Int = _carrito.sumOf { it.precio }
    fun obtenerCantidadTotal(): Int = _carrito.size
}
