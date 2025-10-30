package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_joserodas.viewmodel.AuthViewModel

@Composable
fun PerfilScreen(
    authVM: AuthViewModel,
    onBack: () -> Unit
) {
    val user by authVM.currentUser.collectAsState()

    Column(Modifier.padding(20.dp)) {
        Text("Mi Perfil", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(18.dp))

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
                onClick = {
                    authVM.logout()
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
