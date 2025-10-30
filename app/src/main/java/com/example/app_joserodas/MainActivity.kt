package com.example.app_joserodas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.app_joserodas.ui.theme.APP_JoseRodasTheme
import com.example.app_joserodas.viewmodel.AuthViewModel
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.CatalogViewModel

class MainActivity : ComponentActivity() {

    private val catalogVM: CatalogViewModel by viewModels()
    private val cartVM: CartViewModel by viewModels()
    private val authVM: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            APP_JoseRodasTheme {
                val navController = rememberNavController()
                MainContent(navController, catalogVM, cartVM, authVM)
            }
        }
    }
}

@Composable
fun MainContent(
    navController: NavHostController,
    catalogVM: CatalogViewModel,
    cartVM: CartViewModel,
    authVM: AuthViewModel
) {
    AppNav(
        navController = navController,
        catalogVM = catalogVM,
        cartVM = cartVM,
        authVM = authVM
    )
}
