package com.example.app_joserodas.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Login : Screen("login")

    data object Perfil : Screen("perfil")

    data object FAQ : Screen("faq")

    data object Terminos : Screen("terminos")

    data object Carrito : Screen("carrito")


    data object Producto : Screen("producto/{id}") {
        fun createRoute(id: Int): String = "producto/$id"
    }
}
