package com.example.app_joserodas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_joserodas.ui.screens.CarritoScreen
import com.example.app_joserodas.ui.screens.LoginScreen
import com.example.app_joserodas.ui.screens.PerfilScreen
import com.example.app_joserodas.ui.screens.PreguntasScreen
import com.example.app_joserodas.ui.screens.ProductoScreen
import com.example.app_joserodas.ui.screens.RegisterScreen
import com.example.app_joserodas.ui.screens.TerminosScreen
import com.example.app_joserodas.ui.theme.theme.APP_JoseRodasTheme
import com.example.app_joserodas.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    // ViewModel global para toda la app
    private val viewModel = MainViewModel.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APP_JoseRodasTheme {
                AppRoot(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AppRoot(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onOpenCart = { navController.navigate("carrito") },
                onOpenProduct = { id -> navController.navigate("producto/$id") },
                onOpenLogin = { navController.navigate("login") },
                onOpenRegister = { navController.navigate("registro") },
                onOpenPerfil = { navController.navigate("perfil") },
                onOpenFAQ = { navController.navigate("faq") },
                onOpenTerminos = { navController.navigate("terminos") }
            )
        }

        composable("login") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onGoRegister = { navController.navigate("registro") },
                onLogged = { navController.popBackStack() }
            )
        }

        composable("registro") {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegistered = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            PerfilScreen(onBack = { navController.popBackStack() })
        }

        composable("faq") {
            PreguntasScreen(onBack = { navController.popBackStack() })
        }

        composable("terminos") {
            TerminosScreen(onBack = { navController.popBackStack() })
        }

        composable("carrito") {
            CarritoScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("producto/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
            ProductoScreen(
                viewModel = viewModel,
                idLibro = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onOpenCart: () -> Unit,
    onOpenProduct: (Int) -> Unit,
    onOpenLogin: () -> Unit,
    onOpenRegister: () -> Unit,
    onOpenPerfil: () -> Unit,
    onOpenFAQ: () -> Unit,
    onOpenTerminos: () -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }
    val libros = remember { viewModel.productos }
    var libroActual by remember { mutableStateOf(libros.first()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            libroActual = libros.random()
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDE4954))
                    .height(120.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_palabras_radiantes),
                    contentDescription = "Logo",
                    modifier = Modifier.height(96.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clickable { onOpenCart() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carrito),
                        contentDescription = "Carrito",
                        modifier = Modifier.height(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    // Mostrar cantidad en el carrito
                    if (viewModel.obtenerCantidadTotal() > 0) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color.Red, shape = CircleShape)
                                .align(Alignment.TopEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = viewModel.obtenerCantidadTotal().toString(),
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clickable { menuAbierto = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text("☰", color = Color.Black, fontSize = 35.sp)

                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = { menuAbierto = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Mi Perfil") },
                            onClick = { menuAbierto = false; onOpenPerfil() }
                        )
                        DropdownMenuItem(
                            text = { Text("Iniciar sesión") },
                            onClick = { menuAbierto = false; onOpenLogin() }
                        )
                        DropdownMenuItem(
                            text = { Text("Registro") },
                            onClick = { menuAbierto = false; onOpenRegister() }
                        )
                        DropdownMenuItem(
                            text = { Text("Preguntas frecuentes") },
                            onClick = { menuAbierto = false; onOpenFAQ() }
                        )
                        DropdownMenuItem(
                            text = { Text("Términos y condiciones") },
                            onClick = { menuAbierto = false; onOpenTerminos() }
                        )
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
                    fontSize = 8.sp
                )
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.instagramlogo),
                    contentDescription = "Instagram",
                    modifier = Modifier.size(24.dp)
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clickable { onOpenProduct(libroActual.idLibro) }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = libroActual.imagenRes),
                            contentDescription = "Portada de ${libroActual.titulo}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(libroActual.titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("por ${libroActual.autor.nombre} ${libroActual.autor.apellido}",
                            fontSize = 12.sp, color = Color.Gray)
                        Text("$${libroActual.precio}", fontSize = 14.sp, color = Color(0xFFDE4954))
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("Bienvenido a Palabras Radiantes", fontSize = 22.sp)
                Text("Descubre libros increíbles", fontSize = 14.sp, color = Color.Gray)
                Spacer(Modifier.height(16.dp))
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
                items(libros, key = { it.idLibro }) { libro ->
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

            Spacer(Modifier.height(20.dp))
        }
    }
}