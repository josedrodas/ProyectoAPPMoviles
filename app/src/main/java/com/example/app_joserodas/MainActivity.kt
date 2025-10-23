package com.example.app_joserodas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.ui.theme.theme.APP_JoseRodasTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { APP_JoseRodasTheme { Home() } }
    }
}

@Composable
fun Home() {
    var menu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDE4954))
                    .statusBarsPadding()
                    .height(120.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_palabras_radiantes),
                    contentDescription = "Logo Palabras Radiantes",
                    modifier = Modifier.height(96.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clickable {},
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.carrito),
                        contentDescription = "Carrito de compras",
                        modifier = Modifier.height(32.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clickable { menu = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text("☰", color = Color.Black, fontSize = 35.sp)

                    DropdownMenu(
                        expanded = menu,
                        onDismissRequest = { menu = false },
                        offset = DpOffset(x = 0.dp, y = 8.dp)
                    ) {
                        DropdownMenuItem(text = { Text("Preguntas frecuentes") }, onClick = { menu = false })
                        DropdownMenuItem(text = { Text("Mi Perfil") }, onClick = { menu = false })
                        DropdownMenuItem(text = { Text("Iniciar sesión") }, onClick = { menu = false })
                        DropdownMenuItem(text = { Text("Términos y condiciones") }, onClick = { menu = false })
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDE4954))
                    .navigationBarsPadding()
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
                    contentDescription = "Logochikitoinstagram",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { inner ->
        val imageList = listOf(
            R.drawable.logo_palabras_radiantes,
            R.drawable.carrito,
            R.drawable.instagramlogo
        )
        var currentIndex by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(3000) // 3 segundos
                currentIndex = (currentIndex + 1) % imageList.size
            }
        }

        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))

            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    slideInHorizontally { width -> width } togetherWith
                            slideOutHorizontally { width -> -width }
                },
                label = "ImageCarousel",
                modifier = Modifier.fillMaxWidth(0.7f)
            ) { targetIndex ->
                Image(
                    painter = painterResource(id = imageList[targetIndex]),
                    contentDescription = "Imagen de carrusel",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Bienvenido a Palabras Radiantes",
                fontSize = 22.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHome() { APP_JoseRodasTheme { Home() } }
