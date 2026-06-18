package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
	// protected WebDriver driver;
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	@BeforeMethod
	public void setUp() {
		driver.set(DriverFactory.initDriver());
	}

	public WebDriver getDriver() {
		return driver.get();
	}

	public String captureBase64Screenshot() {

	    try {
	        ((JavascriptExecutor) getDriver())
	            .executeScript("window.scrollTo(0, 0);");

	        return ((TakesScreenshot) getDriver())
	                .getScreenshotAs(OutputType.BASE64);

	    } catch (Exception e) {
	        return null;
	    }
	}

	@AfterMethod
	public void tearDown() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

}
