# APP Libreria Palabras Radiantes

Una aplicación Android moderna desarrollada en Kotlin con Jetpack Compose, diseñada para proporcionar una experiencia de usuario fluida y responsiva.

## Descripcion

Este proyecto es una aplicación de compras móvil que integra funcionalidades de autenticación, navegación, gestión de carrito de compras y escaneo de códigos de barras mediante visión por computadora.

## Características

- Interfaz moderna construida con Jetpack Compose
- Sistema de autenticación de usuarios
- Gestión de carrito de compras
- Escaneo de códigos de barras con ML Kit
- Navegación fluida entre pantallas
- Consumo de APIs REST con Retrofit
- Carga y visualización de imágenes con Coil
- Soporte para Android 7.0+ (API 24)

## Requisitos Previos

- Android Studio (versión reciente)
- Java 11 o superior
- Android SDK 24 (API mínima)
- Gradle 8.x

## Estructura del Proyecto

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/app_joserodas/
│   │   │   ├── MainActivity.kt              # Actividad principal
│   │   │   ├── Navigation/                  # Configuracion de navegacion
│   │   │   ├── viewmodel/                   # ViewModels (Auth, Home, Cart)
│   │   │   ├── model/                       # Modelos de datos
│   │   │   └── ui/theme/                    # Temas y estilos
│   │   ├── res/                             # Recursos (layouts, strings, etc)
│   │   └── AndroidManifest.xml
│   ├── test/                                # Tests unitarios
│   └── androidTest/                         # Tests de instrumentacion
└── build.gradle.kts                         # Configuracion de compilacion
```

## Dependencias Principales

### Jetpack Compose
- androidx.compose.ui:ui
- androidx.compose.material3:material3
- androidx.activity:activity-compose

### Navegacion
- androidx.navigation:navigation-compose

### Camaras y Vision
- androidx.camera:camera-core
- androidx.camera:camera-camera2
- androidx.camera:camera-lifecycle
- androidx.camera:camera-view
- com.google.mlkit:barcode-scanning

### Networking e Imagenes
- com.squareup.retrofit2:retrofit
- com.squareup.retrofit2:converter-gson
- io.coil-kt:coil-compose

### Ciclo de Vida
- androidx.lifecycle:lifecycle-runtime-compose
- androidx.lifecycle:lifecycle-viewmodel-compose

### Testing
- junit:junit
- io.mockk:mockk
- org.jetbrains.kotlinx:kotlinx-coroutines-test

## Configuracion

### 1. Clonar el Repositorio

```bash
git clone https://github.com/josedrodas/ProyectoAPPMoviles.git
cd ProyectoAPPMoviles
```

### 2. Abrir en Android Studio

1. Abre Android Studio
2. Selecciona "Open an existing project"
3. Navega a la carpeta del proyecto
4. Espera a que se sincronice Gradle

### 3. Configurar el Entorno Local

Si es necesario, configura el archivo `local.properties` con la ruta de tu SDK de Android:

```properties
sdk.dir=/ruta/a/tu/Android/Sdk
```

## Compilacion y Ejecucion

### Compilar el Proyecto

```bash
./gradlew build
```

### Ejecutar en un Dispositivo o Emulador

1. Conecta un dispositivo Android o inicia un emulador
2. Haz clic en "Run" en Android Studio o ejecuta:

```bash
./gradlew installDebug
```

## Ejecucion de Tests

### Tests Unitarios

```bash
./gradlew test
```

### Tests de Instrumentacion

```bash
./gradlew connectedAndroidTest
```

## Arquitectura

La aplicación sigue el patrón MVVM (Model-View-ViewModel):

- **Model**: Modelos de datos que representan la estructura de la aplicación
- **View**: Composables de Jetpack Compose que forman la interfaz de usuario
- **ViewModel**: Gestiona la lógica de negocio y el estado de la aplicación

### ViewModels Principales

- **AuthViewModel**: Gestiona la autenticación de usuarios
- **HomeViewModel**: Maneja los datos de la pantalla de inicio
- **CartViewModel**: Gestiona el carrito de compras

## Configuracion de Compilacion

- **compileSdk**: 35
- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 35
- **jvmTarget**: Java 11

## Contribucion

Las contribuciones son bienvenidas. Para cambios importantes, abre un issue para discutir los cambios propuestos.

## Autores

- Jose Rodas

## Licencia

Este proyecto esta disponible bajo licencia privada.

## Soporte

Para reportar problemas o sugerencias, contacta con el equipo de desarrollo a través de GitHub Issues.
