package com.example.app_joserodas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.ui.theme.APP_JoseRodasTheme

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
                    .background(Color(0xFFDE4954)) // barra
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
                    Text("☰", color = Color.White, fontSize = 40.sp)

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
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Bienvenido a Palabras Radiantes")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() { APP_JoseRodasTheme { Home() } }
