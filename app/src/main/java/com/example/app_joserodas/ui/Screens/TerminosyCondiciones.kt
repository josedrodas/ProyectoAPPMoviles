package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TerminosScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDE4954))
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
                    text = "Términos y condiciones",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Términos y Condiciones de Palabras Radiantes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "1. Identificación del vendedor\n" +
                            "Razón social: Palabras Radiantes SpA · RUT: 76.123.456-7 · Dirección: Av. Libros 123, Santiago · Contacto: contacto@palabrasradiantes.com / +56 9 1234 5678.\n" +
                            "\n" +
                            "2. Uso del sitio\n" +
                            "Al comprar en palabrasradiantes.com, aceptas estos Términos y Condiciones. Nos reservamos el derecho de realizar cambios de contenido y precios sin aviso previo.\n" +
                            "\n" +
                            "3. Precios y stock\n" +
                            "Precios en pesos chilenos (CLP) incluyen IVA. El stock está sujeto a confirmación. Si existe algún error de precio o stock, te contactaremos y procederemos al reembolso si corresponde.\n" +
                            "\n" +
                            "4. Compras y pagos\n" +
                            "Aceptamos: Webpay/Transbank, transferencia bancaria y tarjetas de crédito/débito. Los pedidos pagados por transferencia se procesan una vez acreditado el pago.\n" +
                            "\n" +
                            "5. Envíos y retiro\n" +
                            "Despachamos a todo Chile por Starken y Chilexpress. Plazos estimados: Región Metropolitana [24–72 horas hábiles], regiones [2–6 días hábiles]. Retiro en tienda disponible en Av. Libros 123, Santiago, previa confirmación.\n" +
                            "\n" +
                            "6. Cambios y devoluciones\n" +
                            "\n" +
                            "Garantía legal: 6 meses por productos con falla (Ley 19.496).\n" +
                            "\n" +
                            "Derecho de retracto (compras online): 10 días corridos desde la recepción del producto (sin uso, en su empaque original).\n" +
                            "\n" +
                            "Para gestionar cambios o devoluciones, escribe a cambios@palabrasradiantes.com incluyendo el número de pedido y fotografías si aplica.\n" +
                            "\n" +
                            "7. Preventas y productos \"a pedido\"\n" +
                            "Las fechas estimadas de entrega para preventas pueden variar según la editorial. Si mezclas productos en preventa con productos en stock, el envío se realizará completo cuando todos los productos estén disponibles, salvo que solicites despachos separados (puede tener costo adicional).\n" +
                            "\n" +
                            "8. Propiedad intelectual\n" +
                            "Todos los textos, logos e imágenes del sitio pertenecen a Palabras Radiantes o a sus respectivos titulares. Queda prohibido cualquier uso no autorizado.\n" +
                            "\n" +
                            "9. Protección de datos personales\n" +
                            "Utilizamos tus datos exclusivamente para procesar tu compra, gestionar el despacho y brindarte soporte. Implementamos medidas de seguridad avanzadas (HTTPS, encriptación). Más información en nuestra Política de Privacidad.\n" +
                            "\n" +
                            "10. Limitación de responsabilidad\n" +
                            "No nos hacemos responsables por interrupciones del servicio causadas por factores externos (problemas del courier, fallas de terceros, fuerza mayor).\n" +
                            "\n" +
                            "11. Soporte y reclamos\n" +
                            "Puedes contactarnos en contacto@palabrasradiantes.com o al +56 9 1234 5678 (WhatsApp). También puedes dirigirte al SERNAC en caso de corresponder.\n" +
                            "\n" +
                            "12. Vigencia\n" +
                            "Estos Términos y Condiciones están vigentes desde el 1 de enero de 2024 y pueden actualizarse sin previo aviso. La versión vigente siempre estará publicada en nuestro sitio web.\n" +
                            "\n" +
                            "13. Medios de contacto\n" +
                            "- Email: contacto@palabrasradiantes.com\n" +
                            "- WhatsApp: +56 9 1234 5678\n" +
                            "- Dirección: Av. Libros 123, Santiago, Chile\n" +
                            "- Horario de atención: Lunes a Viernes 9:00 - 18:00 hrs\n" +
                            "\n" +
                            "14. Ley aplicable\n" +
                            "Estos términos se rigen por la legislación chilena y cualquier disputa será resuelta en los tribunales de Santiago, Chile.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Última actualización: 15 de Enero, 2024",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}