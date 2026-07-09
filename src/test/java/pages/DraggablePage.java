package pages;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class DraggablePage extends BasePage {

	@FindBy(id = "draggableExample-tab-simple")
	private WebElement simpleTab;
	@FindBy(id = "dragBox")
	private WebElement dragBox;

	@FindBy(id = "draggableExample-tab-axisRestriction")
	private WebElement axisTab;
	@FindBy(id = "restrictedX")
	private WebElement restrictedXBox;
	@FindBy(id = "restrictedY")
	private WebElement restrictedYBox;

	@FindBy(id = "draggableExample-tab-cursorStyle")
	private WebElement cursorTab;
	@FindBy(id = "cursorCenter")
	private WebElement cursorCenter;
	@FindBy(id = "cursorTopLeft")
	private WebElement cursorTopLeft;
	@FindBy(id = "cursorBottom")
	private WebElement cursorBottom;

	@FindBy(id = "draggableExample-tab-containerRestriction")
	private WebElement containerTab;
	@FindBy(xpath = "//div[@id='containmentWrapper']//div[contains(@class, 'ui-widget-content')]")
	private WebElement boxDraggable;
	@FindBy(xpath = "//span[contains(text(), \"I'm contained within my parent\")]")
	private WebElement parentDraggable;

	public DraggablePage(WebDriver driver) {
		super(driver);
	}

	public void navigateToDraggablePage() {
		driver.get("https://demoqa.com/dragabble");
		disableTextSelection();
	}

	private void performDrag(WebElement element, int xOffset, int yOffset) {
		waitForVisibility(element);
		scrollIntoViewCenter(element);
		actions.moveToElement(element).perform();
		actions.pause(Duration.ofMillis(200)).perform();
		actions.clickAndHold(element).perform();
		actions.pause(Duration.ofMillis(200)).perform();
		actions.moveByOffset(xOffset, yOffset).perform();
		actions.pause(Duration.ofMillis(200)).perform();
		actions.release().perform();
	}

	private void performSlowGrabDrag(WebElement element, int xOffset, int yOffset) {
		waitForVisibility(element);
		scrollIntoViewCenter(element);
		actions.moveToElement(element).perform();
		actions.pause(Duration.ofMillis(500)).perform();
		actions.clickAndHold(element).perform();
		actions.pause(Duration.ofMillis(500)).perform();
		actions.moveByOffset(xOffset, yOffset).perform();
		actions.pause(Duration.ofMillis(500)).perform();
		actions.release().perform();
	}

	// Tab Methods
	public void clickSimpleTab() {
		clickElement(simpleTab);
		actions.pause(Duration.ofMillis(300)).perform();
	}

	public Point getSimpleBoxLocation() {
		return waitForVisibility(dragBox).getLocation();
	}

	public void dragSimpleBox(int x, int y) {
		performDrag(dragBox, x, y);
	}

	public void clickAxisTab() {
		clickElement(axisTab);
		actions.pause(Duration.ofMillis(300)).perform();
	}

	public Point getRestrictedXBoxLocation() {
		return waitForVisibility(restrictedXBox).getLocation();
	}

	public void dragRestrictedXBox(int x, int y) {
		performDrag(restrictedXBox, x, y);
	}

	public Point getRestrictedYBoxLocation() {
		return waitForVisibility(restrictedYBox).getLocation();
	}

	public void dragRestrictedYBox(int x, int y) {
		performDrag(restrictedYBox, x, y);
	}

	public void clickCursorTab() {
		clickElement(cursorTab);
		actions.pause(Duration.ofMillis(300)).perform();
	}

	public Point getCursorCenterLocation() {
		return waitForVisibility(cursorCenter).getLocation();
	}

	public void dragCursorCenter(int x, int y) {
		performSlowGrabDrag(cursorCenter, x, y);
	}

	public Point getCursorTopLeftLocation() {
		return waitForVisibility(cursorTopLeft).getLocation();
	}

	public void dragCursorTopLeft(int x, int y) {
		performSlowGrabDrag(cursorTopLeft, x, y);
	}

	public Point getCursorBottomLocation() {
		return waitForVisibility(cursorBottom).getLocation();
	}

	public void dragCursorBottom(int x, int y) {
		performSlowGrabDrag(cursorBottom, x, y);
	}

	public void clickContainerTab() {
		clickElement(containerTab);
		actions.pause(Duration.ofMillis(300)).perform();
	}

	public Point getBoxDraggableLocation() {
		return waitForVisibility(boxDraggable).getLocation();
	}

	public void dragBoxInsideContainer(int x, int y) {
		performSlowGrabDrag(boxDraggable, x, y);
	}

	public Point getParentDraggableLocation() {
		return waitForVisibility(parentDraggable).getLocation();
	}

	public void dragSpanInsideParent(int x, int y) {
		performSlowGrabDrag(parentDraggable, x, y);
	}
}