
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
    val BAR_HEIGHT = 120.dp
    val LOGO_HEIGHT = 96.dp
    val MENU_SIZE = 40.sp
    val MENU_TOUCH = 56.dp

    var menu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0D47A1))
                    .statusBarsPadding()
                    .height(BAR_HEIGHT)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_palabras_radiantes),
                    contentDescription = null,
                    modifier = Modifier.height(LOGO_HEIGHT),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(MENU_TOUCH)
                        .clickable { menu = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text("☰", color = Color.White, fontSize = MENU_SIZE)
                }

                DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                    DropdownMenuItem(text = { Text("Iniciar sesión") }, onClick = { menu = false })
                    DropdownMenuItem(text = { Text("Preguntas frecuentes") }, onClick = { menu = false })
                    DropdownMenuItem(text = { Text("Términos y condiciones") }, onClick = { menu = false })
                }
            }
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text("Bienvenido a Palabras Radiantes") }
    }
}
