package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DroppablePage extends BasePage {

    // -------- SIMPLE (✅ FIXED) --------
    @FindBy(css = "#simpleDropContainer #draggable")
    private WebElement simpleSource;

    @FindBy(css = "#simpleDropContainer #droppable")
    private WebElement simpleTarget;

    // -------- ACCEPT (✅ FIXED) --------
    @FindBy(id = "droppableExample-tab-accept")
    private WebElement acceptTab;

    @FindBy(id = "acceptable")
    private WebElement acceptableSource;

    @FindBy(id = "notAcceptable")   // ✅ replaced xpath
    private WebElement notAcceptableSource;

    // -------- PREVENT --------
    @FindBy(id = "droppableExample-tab-preventPropogation")
    private WebElement preventTab;

    @FindBy(id = "dragBox")
    private WebElement preventSource;

    @FindBy(id = "notGreedyInnerDropBox")
    private WebElement notGreedyInner;

    @FindBy(id = "notGreedyDropBox")
    private WebElement notGreedyOuter;

    @FindBy(id = "greedyDropBoxInner")
    private WebElement greedyInner;

    @FindBy(id = "greedyDropBox")
    private WebElement greedyOuter;

    // -------- REVERT --------
    @FindBy(id = "droppableExample-tab-revertable")
    private WebElement revertTab;

    @FindBy(id = "revertable")
    private WebElement willRevertSource;

    @FindBy(id = "notRevertable")
    private WebElement notRevertSource;

    public DroppablePage(WebDriver driver) {
        super(driver);
    }

    // ✅ NAVIGATION
    public void navigateToDroppablePage() {
        driver.get("https://demoqa.com/droppable");
        
        driver.navigate().refresh();
        disableTextSelection();
    }

    // -------- SIMPLE --------
    public void dragSimpleBox() {
        dragAndDrop(simpleSource, simpleTarget);
    }

    public String getSimpleTargetText() {
        return simpleTarget.getText();
    }

    // -------- ACCEPT (✅ FINAL FIX) --------
    public void clickAcceptTab() {
        clickElement(acceptTab);

        // ✅ wait until correct container visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("acceptDropContainer")
        ));

        // ✅ ensure droppable is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#acceptDropContainer #droppable")
        ));
    }

    // ✅ always fetch latest element
    private WebElement getAcceptTarget() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#acceptDropContainer #droppable")
        ));
    }

    public void dragNotAcceptableBox() {
        dragAndDrop(notAcceptableSource, getAcceptTarget());
    }

    public void dragAcceptableBox() {
        dragAndDrop(acceptableSource, getAcceptTarget());
    }

    public String getAcceptTargetText() {
        return getAcceptTarget().getText();
    }

    // -------- PREVENT --------
    public void clickPreventTab() {
        clickElement(preventTab);
        waitForVisibility(preventSource);
    }

    public void dragToNotGreedyInner() {
        dragAndDrop(preventSource, notGreedyInner);
    }
    

    public void dragToGreedyInner() {
        dragAndDrop(preventSource, greedyInner);
    }

    public String getNotGreedyInnerClass() {
        return notGreedyInner.getAttribute("class");
    }

    public String getNotGreedyOuterClass() {
        return notGreedyOuter.getAttribute("class");
    }
    

    public String getGreedyInnerClass() {
        return greedyInner.getAttribute("class");
    }

    public String getGreedyOuterClass() {
        return greedyOuter.getAttribute("class");
    }

    // -------- REVERT --------
    public void clickRevertTab() {
        clickElement(revertTab);
        waitForVisibility(willRevertSource);
    }

    private WebElement getRevertTarget() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#revertableDropContainer #droppable")
        ));
    }

    public void dragRevertBox(boolean shouldRevert) {
        WebElement source = shouldRevert ? willRevertSource : notRevertSource;

        dragAndDrop(source, getRevertTarget());

        if (shouldRevert) {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
        }
    }

    public Point getRevertSourceLocation() {
        return willRevertSource.getLocation();
    }

    public Point getNotRevertSourceLocation() {
        return notRevertSource.getLocation();
    }
}