package com.example.app_joserodas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_joserodas.model.Autor
import com.example.app_joserodas.model.Editorial
import com.example.app_joserodas.model.Libro
import com.example.app_joserodas.model.OpenLibraryResponse
import com.example.app_joserodas.model.WorkDetailResponse
import com.google.gson.JsonElement
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String, @Query("limit") limit: Int = 20): OpenLibraryResponse

    @GET("{key}.json")
    suspend fun getWorkDetails(@Path("key", encoded = true) key: String): WorkDetailResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://openlibrary.org/"
    val api: BooksApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApiService::class.java)
    }
}
data class HomeUiState(
    val textoBusqueda: String = "",
    val estaBuscando: Boolean = false,
    val productos: List<Libro> = emptyList(),
    val destacado: Libro? = null,
    val errorMsg: String? = null
)

class HomeViewModel(
    private val api: BooksApiService = RetrofitClient.api
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var librosCargados: List<Libro> = emptyList()
    private var searchJob: Job? = null

    init {
        buscarLibrosEnApi("Brandon Sanderson")
    }

    fun buscarProductos(texto: String) {
        _uiState.update { it.copy(textoBusqueda = texto) }
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(800)
            if (texto.isNotBlank()) {
                buscarLibrosEnApi(texto)
            } else {
                _uiState.update { it.copy(productos = librosCargados, errorMsg = null) }
            }
        }
    }

    private fun buscarLibrosEnApi(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(estaBuscando = true, errorMsg = null) }
            try {
                val response = api.searchBooks(query)
                val docs = response.docs ?: emptyList()

                val librosNuevos = docs.map { doc ->
                    val imageUrl = if (doc.coverId != null) "https://covers.openlibrary.org/b/id/${doc.coverId}-L.jpg" else null
                    val precioRandom = (15000..45000).random()

                    Libro(
                        idLibro = doc.key,
                        titulo = doc.title,
                        autor = Autor(0, doc.authorName?.firstOrNull() ?: "Anónimo", ""),
                        editorial = Editorial(0, doc.publisher?.firstOrNull() ?: "Varios", ""),
                        precio = precioRandom,
                        imagenUrl = imageUrl,
                        descripcion = "Cargando descripción..."
                    )
                }

                librosCargados = librosNuevos

                _uiState.update {
                    it.copy(
                        estaBuscando = false,
                        productos = librosNuevos,
                        destacado = librosNuevos.firstOrNull(),
                        errorMsg = if (librosNuevos.isEmpty()) "No encontrado" else null
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Este error ahora sí será capturado por el Test Unitario
                _uiState.update {
                    it.copy(estaBuscando = false, errorMsg = "Error de red: ${e.message}")
                }
            }
        }
    }

    fun cargarDescripcion(idLibro: String) {
        viewModelScope.launch {
            try {
                val detalle = api.getWorkDetails(idLibro)
                val descripcionReal = parseDescription(detalle.description)

                val listaActualizada = _uiState.value.productos.map { libro ->
                    if (libro.idLibro == idLibro) {
                        libro.copy(descripcion = descripcionReal)
                    } else {
                        libro
                    }
                }
                _uiState.update { it.copy(productos = listaActualizada) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseDescription(json: JsonElement?): String {
        if (json == null) return "Sin descripción disponible."
        return try {
            if (json.isJsonPrimitive) {
                json.asString
            } else if (json.isJsonObject) {
                json.asJsonObject.get("value")?.asString ?: "Sin descripción."
            } else {
                "Sin descripción disponible."
            }
        } catch (e: Exception) {
            "Error al leer descripción."
        }
    }

    fun limpiarBusqueda() {
        _uiState.update { it.copy(textoBusqueda = "") }
        buscarProductos("Fantasía")
    }

    fun setDestacado(libro: Libro?) {
        _uiState.update { it.copy(destacado = libro) }
    }

    fun getProductoPorId(idLibro: String): Libro? =
        uiState.value.productos.find { it.idLibro == idLibro }
}