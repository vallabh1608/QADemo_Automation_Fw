package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SortablePage extends BasePage {

    @FindBy(id = "demo-tab-list")
    private WebElement listTab;

    @FindBy(xpath = "//div[@id='demo-tabpane-list']//div[contains(@class, 'list-group-item')]")
    private List<WebElement> listItems;
    
    @FindBy(id = "demo-tab-grid")
    private WebElement gridTab;

    @FindBy(xpath = "//div[@id='demo-tabpane-grid']//div[contains(@class, 'list-group-item')]")
    private List<WebElement> gridItems;

    public SortablePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToSortablePage() {
        driver.get("https://demoqa.com/sortable");
        disableTextSelection();
    }

    public void clickListTab() { clickElement(listTab); }
    public void clickGridTab() { clickElement(gridTab); }

    public void sortRandomListElements() {
        sortRandom(listItems);
    }

    public void sortRandomGridElements() {
        sortRandom(gridItems);
    }

    private void sortRandom(List<WebElement> elements) {

        List<WebElement> visibleElements = waitForAllVisible(elements);
        Random random = new Random();

        int sourceIndex = random.nextInt(visibleElements.size());
        int targetIndex = random.nextInt(visibleElements.size());

        while (sourceIndex == targetIndex) {
            targetIndex = random.nextInt(visibleElements.size());
        }

        WebElement source = visibleElements.get(sourceIndex);
        WebElement target = visibleElements.get(targetIndex);

        scrollIntoViewCenter(target);

        // ✅ IMPORTANT: get size of target
        int height = target.getSize().getHeight();

        // ✅ choose direction (top or bottom)
        int yOffset = (random.nextBoolean()) ? -height/2 : height/2;

        actions.clickAndHold(source)
                .pause(Duration.ofMillis(500))
                .moveToElement(target, 0, yOffset)  // ✅ move ABOVE or BELOW center
                .pause(Duration.ofMillis(700))      // wait for UI detection
                .release()
                .perform();
    }
    
}