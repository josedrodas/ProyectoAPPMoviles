package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TerminosScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().background(Color(0xFFDE4954))
                    .statusBarsPadding().height(56.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("←", color = Color.White, fontSize = 22.sp,
                    modifier = Modifier.clickable { onBack() }.padding(end = 12.dp))
                Text("Términos y condiciones", color = Color.White, fontSize = 18.sp)
            }
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Términos y Condiciones" +
                    "\n" +
                    "1. Identificación del vendedor\n" +
                    "Razón social: [Nombre Legal] · RUT: [XX.XXX.XXX-X] · Dirección: [Dirección] · Contacto: [correo] / [WhatsApp].\n" +
                    "\n" +
                    "2. Uso del sitio\n" +
                    "Al comprar en [dominio.com], aceptas estos T&C. Nos reservamos cambios de contenido y precios sin aviso.\n" +
                    "\n" +
                    "3. Precios y stock\n" +
                    "Precios en CLP con/ sin IVA [indicar]. Stock sujeto a confirmación. Si hay error de precio o stock, te avisamos y reembolsamos.\n" +
                    "\n" +
                    "4. Compras y pagos\n" +
                    "Aceptamos: [Webpay/Transbank], transferencia y [otros]. Pedidos por transferencia se procesan al acreditar el pago.\n" +
                    "\n" +
                    "5. Envíos y retiro\n" +
                    "Despachamos a todo Chile por [Courier]. Plazos estimados: RM [24–72 h hábiles], regiones [2–6 días]. Retiro en tienda en [dirección] con confirmación previa.\n" +
                    "\n" +
                    "6. Cambios y devoluciones\n" +
                    "\n" +
                    "Garantía legal: 6 meses por productos con falla (Ley 19.496).\n" +
                    "\n" +
                    "Arrepentimiento (compras online): [X] días corridos desde la recepción (sin uso, sellado).\n" +
                    "\n" +
                    "Para gestionar, escribe a [correo] con #Pedido y fotos si aplica.\n" +
                    "\n" +
                    "7. Preventas y “a pedido”\n" +
                    "Fechas estimadas pueden variar por editorial. Si mezclas preventa con stock disponible, se envía todo junto, salvo que pidas despachos separados (puede tener costo).\n" +
                    "\n" +
                    "8. Propiedad intelectual\n" +
                    "Textos, logos e imágenes del sitio pertenecen a [Palabras Radiantes] o a sus titulares. Prohibido uso no autorizado.\n" +
                    "\n" +
                    "9. Datos personales\n" +
                    "Usamos tus datos solo para procesar la compra, despacho y soporte. Implementamos medidas de seguridad (HTTPS). Más info en Política de Privacidad ([link]).\n" +
                    "\n" +
                    "10. Limitación de responsabilidad\n" +
                    "No respondemos por interrupciones del servicio por causas externas (courier, fallas de terceros, fuerza mayor).\n" +
                    "\n" +
                    "11. Soporte y reclamos\n" +
                    "Escríbenos a [correo] o [WhatsApp]. También puedes usar el SERNAC si corresponde.\n" +
                    "\n" +
                    "12. Vigencia\n" +
                    "Estos T&C rigen desde [fecha] y pueden actualizarse sin previo aviso. La versión vigente estará publicada en el sitio.")
        }
    }
}
