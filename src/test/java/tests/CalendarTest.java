package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class CalendarTest {

    private AndroidDriver driver;
    private long tiempoInicio;

    @BeforeEach
    public void setUp() throws Exception {

        tiempoInicio = System.currentTimeMillis();

        System.out.println("\n========================================");
        System.out.println("       AUTOMATIZACION MOBILE QA");
        System.out.println("========================================");
        System.out.println("[INICIO] Configurando Google Calendar...");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:deviceName", "Pixel 5");
        capabilities.setCapability("appium:udid", "emulator-5554");
        capabilities.setCapability("appium:platformVersion", "11");

        capabilities.setCapability(
                "appium:appPackage",
                "com.google.android.calendar"
        );

        capabilities.setCapability(
                "appium:appActivity",
                "com.android.calendar.AllInOneActivity"
        );

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                capabilities
        );

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        imprimirPaso("Aplicacion iniciada correctamente");
    }

    @Test
    public void crearEventoEnGoogleCalendar() {

        WebDriverWait espera = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        try {

            System.out.println("\n--- CREACION DE EVENTO ---");

            // 1. Avanzar por las pantallas de bienvenida
            By siguientePagina = By.xpath(
                    "//android.widget.ImageView[@content-desc='next page']"
            );

            By siguientePaginaContenedor = By.id(
                    "com.google.android.calendar:id/next_arrow_touch"
            );

            By botonListo = By.id(
                    "com.google.android.calendar:id/done_button"
            );

            for (int i = 0; i < 5; i++) {
                try {
                    WebElement flecha = new WebDriverWait(
                            driver, Duration.ofSeconds(2)
                    ).until(d -> {

                        var elementos = d.findElements(siguientePagina);

                        if (!elementos.isEmpty()
                                && elementos.get(0).isDisplayed()) {
                            return elementos.get(0);
                        }

                        elementos = d.findElements(
                                siguientePaginaContenedor
                        );

                        if (!elementos.isEmpty()
                                && elementos.get(0).isDisplayed()) {
                            return elementos.get(0);
                        }

                        return null;
                    });

                    flecha.click();

                } catch (TimeoutException e) {
                    break;
                }
            }

            try {
                WebElement listo = new WebDriverWait(
                        driver, Duration.ofSeconds(2)
                ).until(
                        ExpectedConditions.elementToBeClickable(botonListo)
                );

                listo.click();

            } catch (TimeoutException e) {
                System.out.println(
                        "[INFO] No aparecio la pantalla de bienvenida."
                );
            }

            // 2. Pulsar el boton +
            espera.until(
                    ExpectedConditions.elementToBeClickable(
                            AppiumBy.accessibilityId(
                                    "Create new event and more"
                            )
                    )
            ).click();

            imprimirPaso("Menu de creacion abierto");

            // 3. Seleccionar Evento
            espera.until(
                    ExpectedConditions.elementToBeClickable(
                            AppiumBy.accessibilityId("Event button")
                    )
            ).click();

            imprimirPaso("Formulario de evento abierto");

            // 4. Escribir el titulo
            String tituloEvento = "Prueba de automatizacion QA";

            WebElement campoTitulo = espera.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("com.google.android.calendar:id/title")
                    )
            );

            campoTitulo.sendKeys(tituloEvento);

            imprimirPaso("Titulo ingresado: " + tituloEvento);

            // 5. Ocultar teclado
            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            imprimirPaso("Teclado ocultado");

            // 6. Activar All-day
            By switchAllDay = By.xpath("//android.widget.Switch");

            WebElement allDay = espera.until(
                    ExpectedConditions.elementToBeClickable(switchAllDay)
            );

            if (!allDay.isSelected()) {
                allDay.click();
            }

            imprimirPaso("Opcion All-day activada");

            // 7. Desplazarse hacia abajo
            driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))"
                                    + ".scrollForward()"
                    )
            );

            imprimirPaso("Formulario desplazado hacia abajo");

            // 8. Añadir descripcion
            By campoDescripcion = By.xpath(
                    "//android.widget.EditText[@text='Add description']"
            );

            WebElement descripcion = espera.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            campoDescripcion
                    )
            );

            String textoDescripcion =
                    "Evento creado mediante una prueba automatizada de QA.";

            descripcion.click();
            descripcion.sendKeys(textoDescripcion);

            imprimirPaso("Descripcion ingresada");

            // 9. Ocultar teclado
            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            imprimirPaso("Teclado ocultado nuevamente");

            // 10. Guardar evento
            By botonGuardar = By.xpath(
                    "//android.widget.Button"
                            + "[@resource-id='com.google.android.calendar:id/save']"
            );

            espera.until(
                    ExpectedConditions.elementToBeClickable(botonGuardar)
            ).click();

            imprimirPaso("Boton Guardar pulsado");

            // 11. Comprobar que aparece la pantalla posterior al guardado
            By pantallaCalendario = By.xpath(
                    "//android.support.v7.widget.RecyclerView/android.view.View[3]"
            );

            espera.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            pantallaCalendario
                    )
            );

            imprimirPaso(
                    "Elemento esperado visible despues de guardar"
            );

            long duracion = (System.currentTimeMillis()
                    - tiempoInicio) / 1000;

            System.out.println("\n========================================");
            System.out.println("       RESULTADO: PRUEBA FINALIZADA");
            System.out.println("       DURACION: " + duracion + " segundos");
            System.out.println("========================================\n");

        } catch (Exception e) {

            System.out.println("\n========================================");
            System.out.println("       RESULTADO: PRUEBA FALLIDA");
            System.out.println("       ERROR: " + e.getMessage());
            System.out.println("========================================\n");

            throw e;
        }
    }

    private void imprimirPaso(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("[INFO] Sesion de Appium cerrada");
        }
    }
}
