//package base;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.edge.EdgeOptions;
//
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//
//import testUtils.ConfigReader;
//
//import java.time.Duration;
//
//public class DriverFactory {
//
//    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
//
//    public static WebDriver initDriver() {
//
//        String browser = ConfigReader.getProperty("browser").toLowerCase();
//
//        boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
//        
//        switch (browser) {
//
//            case "chrome":
//                ChromeOptions chromeOptions = new ChromeOptions();
//
//                if (isHeadless) {
//                    chromeOptions.addArguments("--headless=new");
//                    chromeOptions.addArguments("--disable-gpu");
//                    chromeOptions.addArguments("--window-size=1920,1080");
//                }
//
//                // ✅ Jenkins stability flags
//                chromeOptions.addArguments("--no-sandbox");
//                chromeOptions.addArguments("--disable-dev-shm-usage");
//                chromeOptions.addArguments("--start-maximized");
//
//                tlDriver.set(new ChromeDriver(chromeOptions));
//                break;
//
//            case "edge":
//                EdgeOptions edgeOptions = new EdgeOptions();
//
//               if (isHeadless) {
//                    edgeOptions.addArguments("--headless=new");
//                    edgeOptions.addArguments("--disable-gpu");
//                    edgeOptions.addArguments("--window-size=1920,1080");
//                }
//
//                edgeOptions.addArguments("--start-maximized");
//                edgeOptions.addArguments("--disable-dev-shm-usage");
//
//                tlDriver.set(new EdgeDriver(edgeOptions));
//                break;
//                
//            case "edge":
//                EdgeOptions edgeOptions = new EdgeOptions();
//
//                edgeOptions.addArguments("--disable-dev-shm-usage");
//                edgeOptions.addArguments("--no-sandbox");
//
//                if (isHeadless) {
//                    edgeOptions.addArguments("--headless=new");
//                    edgeOptions.addArguments("--window-size=1920,1080");
//                } else {
//                    edgeOptions.addArguments("--start-maximized");
//                }
//
//                tlDriver.set(new EdgeDriver(edgeOptions));
//                break;
//            case "firefox":
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//
//                if (isHeadless) {
//                    firefoxOptions.addArguments("--headless");
//                }
//
//                tlDriver.set(new FirefoxDriver(firefoxOptions));
//                tlDriver.get().manage().window().maximize();
//                break;
//
//            default:
//                throw new RuntimeException("Invalid browser: " + browser);
//        }
//
//        // ✅ Common setup
//        getDriver().manage().deleteAllCookies();
//        getDriver().manage().timeouts().implicitlyWait(
//                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicitWait")))
//        );
//
//        return getDriver();
//    }
//
//    public static WebDriver getDriver() {
//        return tlDriver.get();
//    }
//
//    public static void quitDriver() {
//        if (getDriver() != null) {
//            getDriver().quit();
//            tlDriver.remove();
//        }
//    }
//}
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

        // ✅ CRITICAL JENKINS FIX: Check Maven argument first, fallback to config.properties
        String sysHeadless = System.getProperty("headless");
        boolean isHeadless = sysHeadless != null ? Boolean.parseBoolean(sysHeadless) : Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        
        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                // Jenkins stability flags
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

                // Jenkins SYSTEM account stability flags
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--no-sandbox");
                
                // --- CRITICAL FIX FOR DEVTOOLS ACTIVE PORT ---
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--user-data-dir=C:\\Windows\\Temp\\EdgeProfile");

                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu"); // Add this for Windows SYSTEM
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
                
                // Only maximize if NOT running headless
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