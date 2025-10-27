package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_joserodas.viewmodel.MainViewModel

@Composable
fun PerfilScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text("Mi Perfil", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        if (user == null) {
            Text("No has iniciado sesión.")
            Spacer(Modifier.height(12.dp))
            Text("Inicia sesión desde el menú para ver tu información.")
        } else {
            Text("Nombre: ${user!!.nombre}")
            Text("Email: ${user!!.email}")
            Text("Rol: ${user!!.rol.name}")
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.logout(); onBack() },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Cerrar sesión") }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}
