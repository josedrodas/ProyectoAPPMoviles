package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TerminosScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDE4954))
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(end = 12.dp)
            )
            Text(
                text = "Términos y Condiciones",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Términos y Condiciones de Palabras Radiantes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text =
                    "1. Identificación del vendedor\n" +
                            "Razón social: Palabras Radiantes SpA · RUT: 76.123.456-7 · Dirección: Av. Libros 123, Santiago · " +
                            "Contacto: contacto@palabrasradiantes.com / +56 9 1234 5678.\n\n" +

                            "2. Uso del sitio\n" +
                            "Al comprar en palabrasradiantes.com, aceptas estos Términos y Condiciones. " +
                            "Nos reservamos el derecho de realizar cambios de contenido y precios sin aviso previo.\n\n" +

                            "3. Precios y stock\n" +
                            "Precios en CLP incluyen IVA. El stock está sujeto a confirmación. " +
                            "Si existe algún error de precio o stock, te contactaremos y haremos reembolso si aplica.\n\n" +

                            "4. Compras y pagos\n" +
                            "Aceptamos Webpay/Transbank, transferencia bancaria y tarjetas de crédito/débito. " +
                            "Los pedidos por transferencia se procesan una vez acreditado el pago.\n\n" +

                            "5. Envíos y retiro\n" +
                            "Despachamos a todo Chile. RM: 24–72 hrs hábiles. Regiones: 2–6 días hábiles. " +
                            "Retiro en tienda disponible en Av. Libros 123, Santiago, previa confirmación.\n\n" +

                            "6. Cambios y devoluciones\n" +
                            "Garantía legal: 6 meses por productos con falla.\n" +
                            "Derecho de retracto online: 10 días corridos desde la recepción (sin uso, empaque original).\n\n" +

                            "7. Preventas y productos a pedido\n" +
                            "Las fechas estimadas pueden variar según editorial. Si tu carrito mezcla preventa + stock, " +
                            "el envío se hace cuando todo esté disponible (a menos que pidas despachos separados).\n\n" +

                            "8. Propiedad intelectual\n" +
                            "Textos, logos e imágenes pertenecen a Palabras Radiantes o sus titulares. Uso no autorizado prohibido.\n\n" +

                            "9. Datos personales\n" +
                            "Usamos tus datos para procesar compras, despachos y soporte. " +
                            "Aplicamos medidas de seguridad (HTTPS, cifrado).\n\n" +

                            "10. Limitación de responsabilidad\n" +
                            "No somos responsables por demoras externas (courier, fallas de terceros, fuerza mayor).\n\n" +

                            "11. Soporte y reclamos\n" +
                            "contacto@palabrasradiantes.com · +56 9 1234 5678 · También puedes acudir al SERNAC.\n\n" +

                            "12. Vigencia\n" +
                            "Estos Términos están vigentes desde el 1 de enero de 2024 y pueden actualizarse.\n\n" +

                            "13. Contacto\n" +
                            "Email: contacto@palabrasradiantes.com · WhatsApp: +56 9 1234 5678 · " +
                            "Dirección: Av. Libros 123, Santiago, Chile · Horario: Lun-Vie 9:00 - 18:00.\n\n" +

                            "14. Ley aplicable\n" +
                            "Legislación chilena. Jurisdicción: tribunales de Santiago, Chile.\n\n" +

                            "Última actualización: 15 de enero de 2024",
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}
