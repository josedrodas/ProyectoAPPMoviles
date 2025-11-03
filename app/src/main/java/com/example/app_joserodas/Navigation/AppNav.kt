package com.example.app_joserodas.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_joserodas.ui.screens.CarritoScreen
import com.example.app_joserodas.ui.screens.HomeScreen
import com.example.app_joserodas.ui.screens.LoginScreen
import com.example.app_joserodas.ui.screens.PagoScreen
import com.example.app_joserodas.ui.screens.PerfilScreen
import com.example.app_joserodas.ui.screens.PreguntasScreen
import com.example.app_joserodas.ui.screens.ProductoScreen
import com.example.app_joserodas.ui.screens.TerminosScreen
import com.example.app_joserodas.viewmodel.AuthViewModel
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.HomeViewModel

@Composable
fun AppNav(
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                homeViewModel = homeViewModel,
                cartViewModel = cartViewModel,
                onOpenCart = { navController.navigate("carrito") },
                onOpenProduct = { id -> navController.navigate("producto/$id") },
                onOpenLogin = { navController.navigate("login") },
                onOpenPerfil = { navController.navigate("perfil") },
                onOpenFAQ = { navController.navigate("faq") },
                onOpenTerminos = { navController.navigate("terminos") }
            )
        }

        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onBack = { navController.popBackStack() },
                onGoRegister = { },
                onLogged = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            PerfilScreen(
                viewModel = authViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("faq") {
            PreguntasScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("terminos") {
            TerminosScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("carrito") {
            CarritoScreen(
                viewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onProcederPago = { navController.navigate("pago") }
            )
        }

        composable("pago") {
            PagoScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onConfirmarPagoTarjeta = { }
            )
        }

        composable(
            route = "producto/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idLibro = backStackEntry.arguments?.getInt("id") ?: -1
            ProductoScreen(
                idLibro = idLibro,
                getLibro = { libroId -> homeViewModel.getProductoPorId(libroId) },
                onAddToCart = { libro -> cartViewModel.agregarAlCarrito(libro) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
