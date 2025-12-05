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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import coil.compose.AsyncImage
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
    onOpenProduct: (String) -> Unit,
    onOpenLogin: () -> Unit,
    onOpenPerfil: () -> Unit,
    onOpenFAQ: () -> Unit,
    onOpenTerminos: () -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val state by homeViewModel.uiState.collectAsState()

    var startVoiceRecognition: () -> Unit by remember { mutableStateOf({}) }
    val voiceInputLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = matches?.firstOrNull() ?: ""
            if (spokenText.isNotBlank()) homeViewModel.buscarProductos(spokenText)
        }
    }
    startVoiceRecognition = {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Di qué libro quieres buscar")
        }
        voiceInputLauncher.launch(intent)
    }
    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> if (granted) startVoiceRecognition() }

    fun onMicClick() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecognition()
        } else {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    var libroDestacado by remember { mutableStateOf(state.destacado) }
    LaunchedEffect(state.productos) {
        if (state.productos.isNotEmpty()) libroDestacado = state.productos.first()
        while (true) {
            delay(5000)
            if (state.productos.isNotEmpty()) {
                libroDestacado = state.productos.random()
            }
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color(0xFFDE4954))) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(70.dp).padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_palabras_radiantes),
                        contentDescription = "Logo",
                        modifier = Modifier.height(55.dp).offset(x = (-8).dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier.size(44.dp).clickable { onOpenCart() },
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
                                modifier = Modifier.size(18.dp).background(Color.Red, shape = CircleShape).align(Alignment.TopEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(cant.toString(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Box(
                        modifier = Modifier.size(44.dp).clickable { menuAbierto = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("☰", color = Color.White, fontSize = 22.sp)
                        DropdownMenu(expanded = menuAbierto, onDismissRequest = { menuAbierto = false }) {
                            DropdownMenuItem(text = { Text("Mi Perfil") }, onClick = { menuAbierto = false; onOpenPerfil() })
                            DropdownMenuItem(text = { Text("Iniciar sesión") }, onClick = { menuAbierto = false; onOpenLogin() })
                            DropdownMenuItem(text = { Text("Preguntas frecuentes") }, onClick = { menuAbierto = false; onOpenFAQ() })
                            DropdownMenuItem(text = { Text("Términos y condiciones") }, onClick = { menuAbierto = false; onOpenTerminos() })
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().height(70.dp).padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = state.textoBusqueda,
                        onValueChange = { homeViewModel.buscarProductos(it) },
                        placeholder = { Text("Buscar libros, autores...", fontSize = 16.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, "Buscar", tint = Color.Gray) },
                        trailingIcon = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (state.textoBusqueda.isNotEmpty()) {
                                    IconButton(onClick = { homeViewModel.limpiarBusqueda(); keyboardController?.hide() }) {
                                        Icon(Icons.Default.Close, "Limpiar", tint = Color.Gray, modifier = Modifier.size(20.dp))
                                    }
                                }
                                IconButton(onClick = { onMicClick() }) { Text("🎤", fontSize = 18.sp) }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Gray
                        ),
                        singleLine = true
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFDE4954)).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("© 2024 Palabras Radiantes", color = Color.White, fontSize = 10.sp)
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.instagramlogo),
                    contentDescription = "IG",
                    modifier = Modifier.size(24.dp).clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
                        context.startActivity(intent)
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 16.dp)) {

                // --- DESTACADO ---
                Crossfade(targetState = libroDestacado, label = "destacado") { libro ->
                    libro?.let {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(20.dp))
                            // [FIX 1] Reemplazar / por _
                            Box(modifier = Modifier.fillMaxWidth(0.8f).clickable {
                                onOpenProduct(libro.idLibro.replace("/", "_"))
                            }) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AsyncImage(
                                        model = libro.imagenUrl,
                                        contentDescription = libro.titulo,
                                        modifier = Modifier.fillMaxWidth().height(300.dp),
                                        contentScale = ContentScale.Fit,
                                        placeholder = painterResource(R.drawable.logo_palabras_radiantes),
                                        error = painterResource(R.drawable.logo_palabras_radiantes)
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(libro.titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text("por ${libro.autor.nombre}", fontSize = 12.sp, color = Color.Gray)
                                    Text("$${libro.precio}", fontSize = 14.sp, color = Color(0xFFDE4954))
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                            Text("Bienvenido a Palabras Radiantes", fontSize = 22.sp)
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }

                Text("Nuestros Libros", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(12.dp))

                // --- GRILLA DE LIBROS ---
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth().height(500.dp).padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.productos) { libro ->
                        // [FIX 2] Reemplazar / por _
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable {
                                onOpenProduct(libro.idLibro.replace("/", "_"))
                            },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column {
                                AsyncImage(
                                    model = libro.imagenUrl,
                                    contentDescription = libro.titulo,
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(R.drawable.logo_palabras_radiantes),
                                    error = painterResource(R.drawable.logo_palabras_radiantes)
                                )
                                Spacer(Modifier.height(8.dp))
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(libro.titulo, fontSize = 14.sp, maxLines = 2)
                                    Text("$${libro.precio}", fontSize = 13.sp, color = Color(0xFFDE4954), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            // --- RESULTADOS DE BÚSQUEDA FLOTANTES ---
            AnimatedVisibility(
                visible = state.textoBusqueda.isNotBlank() && state.productos.isNotEmpty(),
                modifier = Modifier.align(Alignment.TopStart).fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth().heightIn(max = 320.dp).background(Color.White).padding(8.dp)
                ) {
                    state.productos.take(5).forEach { libro ->
                        // [FIX 3] Reemplazar / por _
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable {
                                onOpenProduct(libro.idLibro.replace("/", "_"))
                            }.padding(vertical = 8.dp)
                        ) {
                            AsyncImage(
                                model = libro.imagenUrl,
                                contentDescription = libro.titulo,
                                modifier = Modifier.size(56.dp).padding(end = 12.dp),
                                contentScale = ContentScale.Crop,
                                error = painterResource(R.drawable.logo_palabras_radiantes)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(libro.titulo, fontSize = 14.sp, maxLines = 1)
                                Text(libro.autor.nombre, fontSize = 12.sp, color = Color.Gray)
                            }
                            Text("$${libro.precio}", fontSize = 13.sp, color = Color(0xFFDE4954))
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}