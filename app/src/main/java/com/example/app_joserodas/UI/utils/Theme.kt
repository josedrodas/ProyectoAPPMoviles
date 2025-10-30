package com.example.app_joserodas.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFDE4954),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5),
    tertiary = androidx.compose.ui.graphics.Color(0xFF3700B3)
)

@Composable
fun APP_JoseRodasTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColors
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.primary.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
