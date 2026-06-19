package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;

        PageFactory.initElements(driver, this);
    }

    // ✅ Wait for single element visibility
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // ✅ Wait for multiple elements
    protected List<WebElement> waitForAllVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    // ✅ Click with wait
    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // ✅ NEW: Wait for clickable (very important)
    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // ✅ Scroll helpers
    protected void scrollIntoViewCenter(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected void scrollIntoViewBottom(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    // ✅ Disable text selection
    protected void disableTextSelection() {
        js.executeScript("document.body.style.userSelect = 'none';");
        js.executeScript("document.body.style.webkitUserSelect = 'none';");
    }

    // ✅ ✅ 🔥 NEW: Stable Drag & Drop (COMMON METHOD)
//    protected void dragAndDrop(WebElement source, WebElement target) {
//
//        waitForVisibility(source);
//        waitForVisibility(target);
//
//        scrollIntoViewCenter(source);
//        scrollIntoViewCenter(target);
//
//        actions.moveToElement(source)
//                .pause(Duration.ofMillis(300))
//                .clickAndHold()
//                .pause(Duration.ofMillis(500))
//                .moveToElement(target)  // ✅ no offset first
//                .pause(Duration.ofMillis(500))
//                .moveByOffset(10, 10)   // ✅ small nudge to trigger drop
//                .pause(Duration.ofMillis(500))
//                .release()
//                .perform();
//    }
    protected void dragAndDrop(WebElement source, WebElement target) {

        waitForVisibility(source);
        waitForVisibility(target);

        scrollIntoViewCenter(source);
        scrollIntoViewCenter(target);

        actions.moveToElement(source)
                .pause(Duration.ofMillis(300))
                .clickAndHold()
                .pause(Duration.ofMillis(400))
                .moveToElement(target)
                .pause(Duration.ofMillis(400))
                .moveByOffset(10, 10)
                .pause(Duration.ofMillis(400))
                .release()
                .perform();
    }

    // ✅ ✅ 🔥 NEW: JS fallback (for flaky UI like DemoQA)
    protected void dragAndDropJS(WebElement source, WebElement target) {
        js.executeScript(
                "arguments[1].appendChild(arguments[0]);",
                source, target
        );
    }
}