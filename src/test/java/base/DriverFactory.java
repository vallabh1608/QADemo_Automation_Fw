package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import testUtils.ConfigReader;

import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver() {

        String browser = ConfigReader.getProperty("browser").toLowerCase();
        
        // Read headless from properties, but allow Jenkins to override it via Environment Variables
        String envHeadless = System.getenv("HEADLESS");
        boolean isHeadless = envHeadless != null ? Boolean.parseBoolean(envHeadless) : Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        
        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }

                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--no-sandbox"); // Critical for Edge in Jenkins

                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                } else {
                    edgeOptions.addArguments("--start-maximized");
                }

                tlDriver.set(new EdgeDriver(edgeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }

                tlDriver.set(new FirefoxDriver(firefoxOptions));
                
                if (!isHeadless) {
                    tlDriver.get().manage().window().maximize();
                }
                break;

            default:
                throw new RuntimeException("Invalid browser: " + browser);
        }

        // ✅ Common setup
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicitWait")))
        );

        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }
}