package com.example.app_joserodas.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.R
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.CatalogViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    catalogVM: CatalogViewModel,
    cartVM: CartViewModel,
    onOpenCart: () -> Unit,
    onOpenProduct: (Int) -> Unit,
    onOpenLogin: () -> Unit,
    onOpenPerfil: () -> Unit,
    onOpenFAQ: () -> Unit,
    onOpenTerminos: () -> Unit,
    onOpenEncontrarnos: () -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val state by catalogVM.uiState.collectAsState()

    var libroDestacado by remember { mutableStateOf(state.destacado) }
    LaunchedEffect(catalogVM.todosLosProductos) {
        while (true) {
            delay(3000)
            if (catalogVM.todosLosProductos.isNotEmpty()) {
                libroDestacado = catalogVM.todosLosProductos.random()
            }
        }
    }

    // Esta lista es para las sugerencias de la búsqueda
    val resultadosProgresivos = remember { mutableStateListOf<com.example.app_joserodas.model.Libro>() }

    LaunchedEffect(state.textoBusqueda) {
        resultadosProgresivos.clear()
        val q = state.textoBusqueda.trim().lowercase()
        if (q.isNotBlank()) {
            val matches = catalogVM.todosLosProductos.filter { libro ->
                libro.titulo.lowercase().contains(q) ||
                        libro.autor.nombre.lowercase().contains(q) ||
                        libro.autor.apellido.lowercase().contains(q) ||
                        libro.editorial.nombre.lowercase().contains(q)
            }
            for (item in matches) {
                resultadosProgresivos.add(item)
                delay(120)
            }
        }
    }

    Scaffold(
        topBar = {
            // usamos Box para poder poner la barra + el popup flotante de resultados
            Box(
                modifier = Modifier
                    .background(Color(0xFFDE4954))
            ) {
                Column {
                    // fila superior logo / carrito / menú
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_palabras_radiantes),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .height(55.dp)
                                .offset(x = (-8).dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(Modifier.weight(1f))

                        // carrito
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { onOpenCart() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.carrito),
                                contentDescription = "Carrito",
                                modifier = Modifier.size(26.dp),
                                contentScale = ContentScale.Fit
                            )
                            val cant = cartVM.obtenerCantidadTotal()
                            if (cant > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .background(Color.Red, shape = CircleShape)
                                        .align(Alignment.TopEnd),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cant.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.width(12.dp))

                        // menú hamburguesa
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { menuAbierto = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("☰", color = Color.White, fontSize = 22.sp)

                            DropdownMenu(
                                expanded = menuAbierto,
                                onDismissRequest = { menuAbierto = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Mi Perfil") },
                                    onClick = {
                                        menuAbierto = false
                                        onOpenPerfil()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Iniciar sesión") },
                                    onClick = {
                                        menuAbierto = false
                                        onOpenLogin()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Preguntas frecuentes") },
                                    onClick = {
                                        menuAbierto = false
                                        onOpenFAQ()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Términos y condiciones") },
                                    onClick = {
                                        menuAbierto = false
                                        onOpenTerminos()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Encuéntranos") },
                                    onClick = {
                                        menuAbierto = false
                                        onOpenEncontrarnos()
                                    }
                                )
                            }
                        }
                    }

                    // barra de búsqueda
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = state.textoBusqueda,
                            onValueChange = { catalogVM.buscarProductos(it) },
                            placeholder = { Text("Buscar libros, autores...", fontSize = 16.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingIcon = {
                                if (state.estaBuscando) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.Gray
                                    )
                                } else if (state.textoBusqueda.isNotEmpty()) {
                                    IconButton(
                                        onClick = {
                                            catalogVM.limpiarBusqueda()
                                            keyboardController?.hide()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Limpiar",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.Gray,
                            ),
                            singleLine = true
                        )
                    }
                }

                // dropdown flotante con los resultados de búsqueda
                AnimatedVisibility(
                    visible = state.textoBusqueda.isNotBlank(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        // se posiciona "pegado" debajo de la barra de búsqueda
                        .offset(y = 140.dp) // 70dp header + 70dp search aprox
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 320.dp)
                            .shadow(8.dp, ambientColor = Color.Black.copy(alpha = 0.2f))
                            .background(Color.White)
                            .verticalScroll(rememberScrollState())
                            .padding(12.dp)
                    ) {
                        resultadosProgresivos.forEach { libro ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onOpenProduct(libro.idLibro)
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = libro.imagenRes),
                                    contentDescription = libro.titulo,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .padding(end = 12.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        libro.titulo,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        color = Color.Black
                                    )
                                    Text(
                                        "por ${libro.autor.nombre} ${libro.autor.apellido}",
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        maxLines = 1
                                    )
                                }
                                Text(
                                    "$${libro.precio}",
                                    fontSize = 13.sp,
                                    color = Color(0xFFDE4954),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDE4954))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "© 2024 Palabras Radiantes. Todos los derechos reservados.",
                    color = Color.White,
                    fontSize = 10.sp
                )
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.instagramlogo),
                    contentDescription = "Instagram Antártica Libros",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val url = "https://www.instagram.com/antarticalibros/"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                )
            }
        }
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // loading "Buscando..."
            AnimatedVisibility(
                visible = state.estaBuscando,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFFDE4954))
                        Spacer(Modifier.height(8.dp))
                        Text("Buscando...", color = Color.Gray, fontSize = 16.sp)
                    }
                }
            }

            // slider/destacado solo si NO está buscando nada
            AnimatedVisibility(
                visible = state.textoBusqueda.isBlank(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Crossfade(targetState = libroDestacado, label = "destacado") { libro ->
                    libro?.let {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(20.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .clickable { onOpenProduct(libro.idLibro) }
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = libro.imagenRes),
                                        contentDescription = "Portada de ${libro.titulo}",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(300.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(libro.titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        "por ${libro.autor.nombre} ${libro.autor.apellido}",
                                        fontSize = 12.sp, color = Color.Gray
                                    )
                                    Text(
                                        "$${libro.precio}",
                                        fontSize = 14.sp,
                                        color = Color(0xFFDE4954)
                                    )
                                }
                            }

                            Spacer(Modifier.height(16.dp))
                            Text("Bienvenido a Palabras Radiantes", fontSize = 22.sp)
                            Text("Descubre libros increíbles", fontSize = 14.sp, color = Color.Gray)
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }

            if (state.textoBusqueda.isNotEmpty() && !state.estaBuscando) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Resultados de búsqueda: ${state.productos.size} libro${if (state.productos.size != 1) "s" else ""} encontrado${if (state.productos.size != 1) "s" else ""}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }

            Text(
                text = when {
                    state.estaBuscando -> "Buscando..."
                    state.textoBusqueda.isNotEmpty() -> "Libros Encontrados"
                    else -> "Nuestros Libros"
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))

            when {
                state.estaBuscando -> { }

                state.productos.isEmpty() && state.textoBusqueda.isNotEmpty() -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No hay resultados", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(8.dp))
                            Text("No se encontraron libros con esa búsqueda", fontSize = 14.sp, color = Color.Gray)
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = { catalogVM.limpiarBusqueda() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954))
                            ) {
                                Text("Ver todos los libros")
                            }
                        }
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.productos, key = { it.idLibro }) { libro ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onOpenProduct(libro.idLibro) },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(id = libro.imagenRes),
                                        contentDescription = libro.titulo,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        Text(
                                            text = libro.titulo,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 2
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            text = "$${libro.precio}",
                                            fontSize = 13.sp,
                                            color = Color(0xFFDE4954),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}
