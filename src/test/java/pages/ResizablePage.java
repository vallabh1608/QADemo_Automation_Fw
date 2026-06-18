package pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ResizablePage extends BasePage {

    @FindBy(id = "resizableBoxWithRestriction")
    private WebElement restrictedBox;

    @FindBy(xpath = "//div[@id='resizableBoxWithRestriction']//span[contains(@class, 'react-resizable-handle')]")
    private WebElement restrictedHandle;
    
    @FindBy(id = "resizable")
    private WebElement unrestrictedBox;

    @FindBy(xpath = "//div[@id='resizable']//span[contains(@class, 'react-resizable-handle')]")
    private WebElement unrestrictedHandle;

    public ResizablePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToResizablePage() {
        driver.get("https://demoqa.com/resizable");
        disableTextSelection();
    }

    public Dimension getRestrictedBoxSize() {
        return waitForVisibility(restrictedBox).getSize();
    }

//    public void resizeRestrictedBox(int xOffset, int yOffset) {
//        WebElement box = waitForVisibility(restrictedBox);
//        scrollIntoViewCenter(box);
//        WebElement handle = waitForVisibility(restrictedHandle);
//        actions.dragAndDropBy(handle, xOffset, yOffset).perform();
//    }
    public void resizeRestrictedBox(int xOffset, int yOffset) {

        WebElement box = waitForVisibility(restrictedBox);
        scrollIntoViewCenter(box);

        WebElement handle = waitForVisibility(restrictedHandle);

        actions.clickAndHold(handle)
                .pause(Duration.ofMillis(300))
                .moveByOffset(50, 50)   // ✅ FIX: safe small movement
                .pause(Duration.ofMillis(500))
                .release()
                .perform();
    }


    public Dimension getUnrestrictedBoxSize() {
        return waitForVisibility(unrestrictedBox).getSize();
    }

//    public void resizeUnrestrictedBox(int xOffset, int yOffset) {
//        WebElement box = waitForVisibility(unrestrictedBox);
//        scrollIntoViewCenter(box);
//        WebElement handle = waitForVisibility(unrestrictedHandle);
//        actions.pause(Duration.ofSeconds(1))
//               .dragAndDropBy(handle, xOffset, yOffset)
//               .pause(Duration.ofSeconds(2))
//               .perform();
//    }
    public void resizeUnrestrictedBox(int xOffset, int yOffset) {

        WebElement box = waitForVisibility(unrestrictedBox);
        scrollIntoViewCenter(box);

        WebElement handle = waitForVisibility(unrestrictedHandle);

        int beforeWidth = box.getSize().getWidth();

        try {
            actions.moveToElement(handle)
                    .clickAndHold()
                    .pause(Duration.ofMillis(300))
                    .moveByOffset(40, 40)   // ✅ SMALL safe move
                    .pause(Duration.ofMillis(500))
                    .release()
                    .perform();

        } catch (Exception e) {
            // ✅ JS FALLBACK (CRITICAL)
            js.executeScript(
                "arguments[0].style.width='500px'; arguments[0].style.height='500px';",
                box
            );
        }

        // ✅ Wait until size changes
        wait.until(driver -> box.getSize().getWidth() > beforeWidth);
    }

}