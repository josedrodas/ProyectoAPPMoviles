package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_joserodas.R
import com.example.app_joserodas.model.Libro
import com.example.app_joserodas.viewmodel.HomeViewModel

@Composable
fun Producto( // <--- AHORA SE LLAMA PRODUCTO
    idLibro: String,
    viewModel: HomeViewModel, // Necesitamos el ViewModel para observar cambios
    onAddToCart: (Libro) -> Unit,
    onBack: () -> Unit
) {
    // 1. ESCUCHAMOS EL ESTADO EN VIVO (Crucial para que aparezca la descripción)
    val uiState by viewModel.uiState.collectAsState()

    // 2. Buscamos el libro en la lista actualizada
    val libro = uiState.productos.find { it.idLibro == idLibro }

    // 3. Al iniciar, pedimos la descripción a la API
    LaunchedEffect(idLibro) {
        viewModel.cargarDescripcion(idLibro)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Si el libro no aparece (ej. cargando inicial), mostramos aviso
        if (libro == null) {
            Text("Cargando información...", modifier = Modifier.padding(16.dp))
            Button(onClick = onBack) { Text("Volver") }
            return
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- IMAGEN ---
                Box(modifier = Modifier.fillMaxWidth().height(320.dp), contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = libro.imagenUrl,
                        contentDescription = libro.titulo,
                        modifier = Modifier.fillMaxWidth(0.7f).height(300.dp),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.logo_palabras_radiantes),
                        error = painterResource(R.drawable.logo_palabras_radiantes)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // --- TITULO Y AUTOR ---
                Text(libro.titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("por ${libro.autor.nombre}", fontSize = 12.sp, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                Text("$${libro.precio}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDE4954))

                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFE0E0E0))
                Spacer(Modifier.height(16.dp))

                // --- DESCRIPCIÓN ---
                Text("Descripción", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(6.dp))

                // Aquí el texto cambiará automáticamente de "Cargando..." a la sinopsis real
                Text(
                    text = if (libro.descripcion == "Cargando descripción...") "Buscando sinopsis..." else libro.descripcion,
                    fontSize = 14.sp,
                    color = Color(0xFF444444),
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(24.dp))

                // --- BOTONES ---
                Button(
                    onClick = { onAddToCart(libro) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar al carrito", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(12.dp))
                TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                    Text("Volver", fontSize = 15.sp, color = Color.Gray)
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}