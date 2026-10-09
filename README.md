# Mobile Calendar Automation 📱

## 1. Descripción del proyecto

Proyecto de automatización de pruebas móviles utilizando Java, Gradle, Appium y Appium Inspector.

La aplicación seleccionada para realizar las pruebas es Google Calendar, ejecutada en un emulador Android.

El proyecto contempla dos casos de prueba principales:

- **Create Event:** automatizar la creación y el guardado de un evento.
- **Verify Event:** verificar que el evento exista y que sus datos sean correctos.

El desarrollo se realiza como parte de las actividades prácticas del Diplomado QA.

## 2. Tecnologías utilizadas

- Java JDK 21
- Gradle
- Appium 3
- Appium Java Client 10.1.1
- Appium Inspector
- Android Emulator
- Android SDK
- UiAutomator2
- JUnit 5
- Git y GitHub
- IntelliJ IDEA

## 3. Configuración del entorno

### 3.1. Dispositivo de prueba

- **Dispositivo:** Pixel 5 (emulador Android).
- **Identificador del dispositivo:** `emulator-5554`.
- **Plataforma:** Android.
- **Versión de Android:** 11.
- **Android API Level:** 30.
- **Aplicación:** Google Calendar.
- **Package:** `com.google.android.calendar`.
- **Activity:** `com.android.calendar.AllInOneActivity`.
- **Servidor Appium:** `http://127.0.0.1:4723/`.

### 3.2. Capacidades de Appium

La sesión de automatización utiliza las siguientes capacidades:

```json
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:deviceName": "Pixel 5",
  "appium:udid": "emulator-5554",
  "appium:platformVersion": "11",
  "appium:appPackage": "com.google.android.calendar",
  "appium:appActivity": "com.android.calendar.AllInOneActivity"
}
```

Estas capacidades permiten identificar el dispositivo, seleccionar la plataforma Android e iniciar Google Calendar para ejecutar las pruebas.

## 4. Funcionalidades implementadas

### 4.1. Create Event

**Archivo:** `src/test/java/tests/CalendarTest.java`

El caso de prueba automatiza las siguientes acciones:

1. Configuración de las capacidades del dispositivo.
2. Conexión con el servidor Appium.
3. Gestión de las pantallas iniciales de bienvenida.
4. Acceso al botón de creación de eventos.
5. Selección de la opción **Evento**.
6. Ingreso del título `Prueba de automatizacion QA`.
7. Guardado del evento.
8. Cierre de la sesión de automatización.

**Estado actual:** implementado hasta la acción de guardar el evento. La verificación independiente de que el evento se haya guardado correctamente está pendiente.

### 4.2. Verify Event

Este caso de prueba corresponde a la segunda parte del proyecto.

Se contempla implementar las siguientes validaciones:

- Confirmar que el evento creado exista en Google Calendar.
- Verificar que el título coincida con el esperado.
- Validar la fecha y las horas de inicio y finalización.
- Comprobar la ubicación y la descripción, cuando correspondan.
- Evitar confundir el evento con registros anteriores o duplicados.
- Mostrar un resultado claro de aprobación o fallo.

**Estado actual:** pendiente de implementación e integración.

Los datos utilizados en ambos casos de prueba deben estar coordinados para asegurar que la creación y la verificación correspondan al mismo evento.

## 5. Evidencias

### 5.1. Conexión inicial con Appium Inspector

Se estableció una sesión de Appium Inspector para comprobar la comunicación con el emulador Android e inspeccionar los elementos de Google Calendar.

**Evidencia:** captura de la sesión conectada y de la aplicación abierta en el emulador.

### 5.2. Ejecución de Create Event

La automatización navega por Google Calendar, ingresa el título del evento y ejecuta la acción de guardado.

**Evidencia:** captura de la ejecución del caso de prueba.

### 5.3. Verificación del evento

En esta sección se incorporará la evidencia de la ejecución de `Verify Event` una vez implementada.

**Evidencia pendiente:** captura de las validaciones y del resultado de la prueba.

## 6. Requisitos previos

Antes de ejecutar las pruebas, se requiere:

- Java JDK 21.
- Android SDK y herramientas de plataforma.
- Un emulador Android configurado.
- Google Calendar instalado en el emulador.
- Appium Server instalado y en ejecución.
- Driver UiAutomator2 instalado en Appium.
- IntelliJ IDEA u otro IDE compatible con Java.

## 7. Ejecución del proyecto

### Paso 1. Iniciar el emulador

Iniciar el emulador Pixel 5 con Android 11 y API Level 30.

### Paso 2. Iniciar Appium Server

Verificar que el servidor esté disponible en:

```text
http://127.0.0.1:4723/
```

### Paso 3. Ejecutar Create Event

Desde la raíz del proyecto, ejecutar en PowerShell:

```powershell
.\gradlew clean test --tests "tests.CalendarTest"
```

### Paso 4. Consultar los resultados

Los resultados se muestran en la terminal. Gradle genera los reportes en las siguientes rutas:

```text
build/reports/tests/test/
build/test-results/test/
```

**Importante:** una ejecución sin excepciones confirma que los pasos automatizados finalizaron según lo programado. No demuestra por sí sola que el evento se haya persistido correctamente en Google Calendar.

## 8. Estructura del proyecto

```text
Mobile-Calendar-Automation/
├── gradle/
├── src/
│   └── test/
│       └── java/
│           └── tests/
│               └── CalendarTest.java
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

## 9. Repositorio

Código fuente del proyecto:

[Mobile Calendar Automation en GitHub](https://github.com/marielaandrade15/Mobile-Calendar-Automation)

## 10. Estado actual y próximos pasos

El proyecto cuenta con la configuración inicial de Appium, la conexión al emulador Android y la automatización de la creación de eventos hasta la acción de guardado.

Los siguientes pasos son:

1. Completar el caso de prueba `Verify Event`.
2. Validar los datos del evento creado.
3. Ejecutar ambos casos de prueba y revisar sus resultados.
4. Incorporar las capturas finales como evidencia.
5. Actualizar esta documentación conforme se completen las funcionalidades.
