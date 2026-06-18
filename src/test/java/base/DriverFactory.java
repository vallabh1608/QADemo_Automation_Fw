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

        //boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        
        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

//                if (isHeadless) {
//                    chromeOptions.addArguments("--headless=new");
//                    chromeOptions.addArguments("--disable-gpu");
//                    chromeOptions.addArguments("--window-size=1920,1080");
//                }

                // ✅ Jenkins stability flags
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--start-maximized");

                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();

//                if (isHeadless) {
//                    edgeOptions.addArguments("--headless=new");
//                    edgeOptions.addArguments("--disable-gpu");
//                    edgeOptions.addArguments("--window-size=1920,1080");
//                }

                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--disable-dev-shm-usage");

                tlDriver.set(new EdgeDriver(edgeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

//                if (isHeadless) {
//                    firefoxOptions.addArguments("--headless");
//                }

                tlDriver.set(new FirefoxDriver(firefoxOptions));
                tlDriver.get().manage().window().maximize();
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