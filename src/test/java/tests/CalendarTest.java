
package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
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

    @BeforeEach
    public void setUp() throws Exception {

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

        // Abrir Google Calendar
        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                capabilities
        );

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    @Test
    public void crearEventoEnGoogleCalendar() {

        WebDriverWait espera = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

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

        // 2. Pulsar Done si aparece
        try {
            WebElement listo = new WebDriverWait(
                    driver, Duration.ofSeconds(2)
            ).until(
                    ExpectedConditions.elementToBeClickable(botonListo)
            );

            listo.click();

        } catch (TimeoutException e) {
            System.out.println("No apareció Done; continuando.");
        }

        // 3. Pulsar el botón +
        espera.until(
                ExpectedConditions.elementToBeClickable(
                        AppiumBy.accessibilityId(
                                "Create new event and more"
                        )
                )
        ).click();

        // 4. Seleccionar Evento
        espera.until(
                ExpectedConditions.elementToBeClickable(
                        AppiumBy.accessibilityId("Event button")
                )
        ).click();

        // 5. Escribir el título
        String tituloEvento = "Prueba de automatizacion QA";

        WebElement campoTitulo = espera.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("com.google.android.calendar:id/title")
                )
        );

        campoTitulo.sendKeys(tituloEvento);

        // 6. Guardar el evento
        espera.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("com.google.android.calendar:id/save")
                )
        ).click();

        System.out.println(
                "Se completaron los pasos hasta pulsar Guardar."
        );
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
