# Desarrollo Móvil

Repositorio de prácticas y proyectos del curso de Desarrollo Móvil. Contiene ejercicios en Kotlin tanto para línea de comandos como aplicaciones Android.

## 📱 Proyectos

### 1. HolaMundo
**Aplicación Android con Jetpack Compose**

Primera aplicación Android que demuestra los conceptos básicos de Jetpack Compose y el manejo de estado.

**Características:**
- Interfaz de usuario con Jetpack Compose
- Sistema de contador interactivo
- Botones para incrementar y reiniciar el contador
- Diseño moderno con Material Design 3
- Soporte para temas claro/oscuro

**Tecnologías:**
- Kotlin
- Jetpack Compose
- Material Design 3
- Android SDK (minSdk: 24, targetSdk: 36)

**Cómo ejecutar:**
1. Abrir el proyecto `HolaMundo` en Android Studio
2. Sincronizar Gradle
3. Ejecutar en un emulador o dispositivo Android

---

### 2. ParkingJava
**Sistema de gestión de parking (Programación Procedural)**

Aplicación de consola para gestionar un parking de vehículos utilizando programación procedural.

**Características:**
- Menú interactivo de consola
- Agregar vehículos por matrícula
- Remover vehículos del parking
- Mostrar lista de vehículos estacionados
- Validación de matrículas duplicadas

**Funcionalidades:**
- `Meter vehículo`: Añade un vehículo al parking
- `Sacar vehículo`: Retira un vehículo del parking
- `Mostrar vehículos`: Lista todos los vehículos estacionados

**Cómo ejecutar:**
```bash
cd ParkingJava/src
kotlinc Main.kt -include-runtime -d Parking.jar
java -jar Parking.jar
```

---

### 3. ParkingPoo
**Sistema de gestión de parking (Programación Orientada a Objetos)**

Versión mejorada del sistema de parking utilizando principios de POO.

**Características:**
- Arquitectura orientada a objetos
- Gestión de diferentes tipos de vehículos (Car, Bike, Van)
- Sistema de slots de parking con límite de capacidad
- Control de tiempo de ocupación
- Manejo de excepciones personalizadas
- Prevención de vehículos duplicados

**Estructura de clases:**
- `Vehicule`: Clase base para vehículos
- `Car`, `Bike`, `Van`: Tipos específicos de vehículos
- `ParkingSlot`: Representa una plaza de parking con vehículo y tiempo
- `Parking`: Gestiona las plazas y operaciones del parking
- `VehiculesAlreadyParkedExceptions`: Excepción para vehículos duplicados

**Cómo ejecutar:**
```bash
cd ParkingPoo/src
kotlinc -d Parking.jar Main.kt model/*.kt exceptions/*.kt
kotlin -classpath Parking.jar MainKt
```

---

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje principal de desarrollo
- **Android Studio**: IDE para desarrollo Android
- **Jetpack Compose**: Framework moderno de UI para Android
- **Material Design 3**: Sistema de diseño de Google
- **IntelliJ IDEA**: IDE para desarrollo Kotlin

## 📋 Requisitos

### Para proyectos Android:
- Android Studio Arctic Fox o superior
- JDK 11 o superior
- Android SDK (API 24+)

### Para proyectos Kotlin (consola):
- Kotlin Compiler (kotlinc)
- JRE 8 o superior

## 📁 Estructura del Repositorio

```
Desarrollo-Movil/
├── HolaMundo/              # Aplicación Android con Jetpack Compose
├── ParkingJava/            # Sistema de parking (procedural)
├── ParkingPoo/             # Sistema de parking (POO)
├── DividirCuentas/         # Git submodule - Aplicación de dividir cuentas
├── DividirCuentasV2/       # Git submodule - Versión 2 de dividir cuentas
├── app-lol/                # Git submodule - Aplicación League of Legends
└── README.md               # Este archivo
```

## 📦 Submódulos (Git Submodules)

Este repositorio utiliza git submodules para incluir proyectos adicionales. Los submodules son repositorios independientes enlazados a este proyecto principal.

### Submodules Incluidos:

1. **DividirCuentas** - [Dividir-Cuenta](https://github.com/Mariogarluu/Dividir-Cuenta.git)
2. **DividirCuentasV2** - [Dividir-CuentaV2](https://github.com/Mariogarluu/Dividir-CuentaV2.git)
3. **app-lol** - [App-Lol](https://github.com/Mariogarluu/App-Lol.git)

### Cómo trabajar con los submodules:

**Inicializar los submodules después de clonar el repositorio:**
```bash
git submodule init
git submodule update
```

**O clonar el repositorio con todos los submodules:**
```bash
git clone --recurse-submodules https://github.com/Mariogarluu/Desarrollo-Movil.git
```

**Actualizar todos los submodules a sus últimas versiones:**
```bash
git submodule update --remote --merge
```

**Ver el estado de los submodules:**
```bash
git submodule status
```

## 👨‍💻 Autor

Mario García

## 📝 Notas

Este repositorio contiene ejercicios prácticos del curso de Desarrollo Móvil, enfocado en el aprendizaje de Kotlin y desarrollo Android.
