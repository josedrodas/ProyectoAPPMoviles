package com.example.app_joserodas.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_joserodas.ui.screens.CarritoScreen
import com.example.app_joserodas.ui.screens.EncuentranosScreen
import com.example.app_joserodas.ui.screens.HomeScreen
import com.example.app_joserodas.ui.screens.LoginScreen
import com.example.app_joserodas.ui.screens.PerfilScreen
import com.example.app_joserodas.ui.screens.PagoScreen
import com.example.app_joserodas.ui.screens.PreguntasScreen
import com.example.app_joserodas.ui.screens.ProductoScreen
import com.example.app_joserodas.ui.screens.QrScannerScreen
import com.example.app_joserodas.ui.screens.RegisterScreen
import com.example.app_joserodas.ui.screens.TerminosScreen
import com.example.app_joserodas.viewmodel.AuthViewModel
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.CatalogViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    catalogVM: CatalogViewModel,
    cartVM: CartViewModel,
    authVM: AuthViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                catalogVM = catalogVM,
                cartVM = cartVM,
                onOpenCart = { navController.navigate("carrito") },
                onOpenProduct = { id -> navController.navigate("producto/$id") },
                onOpenLogin = { navController.navigate("login") },
                onOpenPerfil = { navController.navigate("perfil") },
                onOpenFAQ = { navController.navigate("faq") },
                onOpenTerminos = { navController.navigate("terminos") },
                onOpenEncontrarnos = { navController.navigate("encuentranos") }
            )
        }

        composable("login") {
            LoginScreen(
                authVM = authVM,
                onBack = { navController.popBackStack() },
                onGoRegister = { navController.navigate("register") },
                onLogged = { navController.popBackStack() }
            )
        }

        composable("register") {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegistered = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            PerfilScreen(
                authVM = authVM,
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

        composable("encuentranos") {
            EncuentranosScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("carrito") {
            CarritoScreen(
                cartVM = cartVM,
                onBack = { navController.popBackStack() },
                onIrPago = { navController.navigate("pago") }
            )
        }

        composable("producto/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
            ProductoScreen(
                catalogVM = catalogVM,
                cartVM = cartVM,
                idLibro = id,
                onBack = {
                    catalogVM.limpiarBusqueda()
                    navController.popBackStack()
                }
            )
        }

        composable("pago") {
            PagoScreen(
                cartVM = cartVM,
                onBack = { navController.popBackStack() },
                onPagoExitoso = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onIrScannerQR = { navController.navigate("qr") }
            )
        }

        composable("qr") {
            QrScannerScreen(
                cartVM = cartVM,
                onBack = { navController.popBackStack() },
                onPagoExitoso = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
    }
}
