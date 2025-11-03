package com.example.app_joserodas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_joserodas.Navigation.AppNav
import com.example.app_joserodas.ui.theme.theme.APP_JoseRodasTheme
import com.example.app_joserodas.viewmodel.AuthViewModel
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel = HomeViewModel()
    private val cartViewModel = CartViewModel()
    private val authViewModel = AuthViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APP_JoseRodasTheme {
                AppNav(
                    homeViewModel = homeViewModel,
                    cartViewModel = cartViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
