package com.example.app_joserodas.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    enum class Rol { ADMIN, USER }
    data class AppUser(val nombre: String, val email: String, val rol: Rol)

    private data class Cred(val email: String, val pass: String, val user: AppUser)

    private val seededUsers = listOf(
        Cred(
            email = "admin@palabras.com",
            pass  = "Admin1234",
            user  = AppUser(
                nombre = "EL fokin Admin",
                email = "admin@palabras.com",
                rol   = Rol.ADMIN
            )
        ),
        Cred(
            email = "usuario@palabras.com",
            pass  = "User1234",
            user  = AppUser(
                nombre = "Usuario Normal",
                email = "usuario@palabras.com",
                rol   = Rol.USER
            )
        )
    )

    private val _currentUser = MutableStateFlow<AppUser?>(null)
    val currentUser: StateFlow<AppUser?> = _currentUser

    fun login(email: String, password: String): Result<Unit> {
        val e = email.trim().lowercase()
        val match = seededUsers.firstOrNull {
            it.email.equals(e, ignoreCase = true) && it.pass == password
        } ?: return Result.failure(IllegalArgumentException("Credenciales inválidas"))

        _currentUser.value = match.user
        return Result.success(Unit)
    }

    fun logout() {
        _currentUser.value = null
    }
}
