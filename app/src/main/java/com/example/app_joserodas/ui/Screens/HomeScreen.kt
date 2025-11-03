package com.example.app_joserodas.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.app_joserodas.R
import com.example.app_joserodas.model.Libro
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    onOpenCart: () -> Unit,
    onOpenProduct: (Int) -> Unit,
    onOpenLogin: () -> Unit,
    onOpenPerfil: () -> Unit,
    onOpenFAQ: () -> Unit,
    onOpenTerminos: () -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val activity = context as Activity
    val state by homeViewModel.uiState.collectAsState()

    var startVoiceRecognition: () -> Unit by remember { mutableStateOf({}) }

    val voiceInputLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = matches?.firstOrNull() ?: ""
            if (spokenText.isNotBlank()) {
                homeViewModel.buscarProductos(spokenText)
            }
        }
    }

    startVoiceRecognition = {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Di qué libro quieres buscar")
        }
        voiceInputLauncher.launch(intent)
    }

    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            startVoiceRecognition()
        }
    }

    fun onMicClick() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            startVoiceRecognition()
        } else {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    var libroDestacado by remember { mutableStateOf(state.destacado) }
    LaunchedEffect(homeViewModel.todosLosProductos) {
        while (true) {
            delay(3000)
            if (homeViewModel.todosLosProductos.isNotEmpty()) {
                libroDestacado = homeViewModel.todosLosProductos.random()
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(Color(0xFFDE4954))
            ) {
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

                        val cant = cartViewModel.obtenerCantidadTotal()
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
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clickable { menuAbierto = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "☰",
                            color = Color.White,
                            fontSize = 22.sp
                        )

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
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val kb = keyboardController

                    OutlinedTextField(
                        value = state.textoBusqueda,
                        onValueChange = { homeViewModel.buscarProductos(it) },
                        placeholder = {
                            Text(
                                "Buscar libros, autores...",
                                fontSize = 16.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        trailingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (state.textoBusqueda.isNotEmpty()) {
                                    IconButton(
                                        onClick = {
                                            homeViewModel.limpiarBusqueda()
                                            kb?.hide()
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

                                IconButton(
                                    onClick = {
                                        onMicClick()
                                    }
                                ) {
                                    Text(
                                        text = "🎤",
                                        color = Color.Gray,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = { kb?.hide() }
                        ),
                        modifier = Modifier
                            .weight(1f)
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

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 16.dp)
            ) {
                Crossfade(
                    targetState = libroDestacado,
                    label = "destacado"
                ) { libro ->
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
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = libro.imagenRes),
                                        contentDescription = "Portada de ${libro.titulo}",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(300.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        libro.titulo,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "por ${libro.autor.nombre} ${libro.autor.apellido}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        "$${libro.precio}",
                                        fontSize = 14.sp,
                                        color = Color(0xFFDE4954)
                                    )
                                }
                            }

                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Bienvenido a Palabras Radiantes",
                                fontSize = 22.sp
                            )
                            Text(
                                "Descubre libros increíbles",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }

                Text(
                    text = "Nuestros Libros",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        homeViewModel.todosLosProductos,
                        key = { it.idLibro }
                    ) { libro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenProduct(libro.idLibro) },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
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
                                Column(
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
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

                Spacer(Modifier.height(20.dp))
            }

            val resultadosProgresivos = remember { mutableStateListOf<Libro>() }

            LaunchedEffect(state.textoBusqueda) {
                resultadosProgresivos.clear()
                val q = state.textoBusqueda.trim().lowercase()
                if (q.isNotBlank()) {
                    val matches = homeViewModel.todosLosProductos.filter { libro ->
                        libro.titulo.lowercase().contains(q) ||
                                libro.autor.nombre.lowercase().contains(q) ||
                                libro.autor.apellido.lowercase().contains(q) ||
                                libro.editorial.nombre.lowercase().contains(q)
                    }
                    resultadosProgresivos.addAll(matches)
                }
            }

            AnimatedVisibility(
                visible = state.textoBusqueda.isNotBlank(),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 0.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .heightIn(max = 320.dp)
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    resultadosProgresivos.forEach { libro ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenProduct(libro.idLibro) }
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = libro.imagenRes),
                                    contentDescription = libro.titulo,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .padding(end = 12.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        libro.titulo,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
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

                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = Color(0xFFE0E0E0))
                        }
                    }
                }
            }
        }
    }
}
