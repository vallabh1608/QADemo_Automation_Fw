package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SelectablePage;

import java.util.Set;

public class SelectableTest extends BaseTest {

    @Test(priority = 1)
    public void testSelectableList() {
        SelectablePage selectablePage = new SelectablePage(getDriver());
        
        selectablePage.navigateToSelectablePage();
        selectablePage.clickListTab();
        
        // Act: Select 2 random items
        Set<WebElement> selectedItems = selectablePage.selectRandomItemsFromList(2);
        
        // Assert: Verify all selected items received the 'active' class
        for(WebElement item : selectedItems) {
            Assert.assertTrue(item.getAttribute("class").contains("active"), "List Item '" + item.getText() + "' was not selected!");
        }
    }

    @Test(priority = 2)
    public void testSelectableGrid() {
        SelectablePage selectablePage = new SelectablePage(getDriver());
        
        selectablePage.navigateToSelectablePage();
        selectablePage.clickGridTab();
        
        // Act: Select 2 random items
        Set<WebElement> selectedItems = selectablePage.selectRandomItemsFromGrid(2);
        
        // Assert: Verify all selected items received the 'active' class
        for(WebElement item : selectedItems) {
            Assert.assertTrue(item.getAttribute("class").contains("active"), "Grid Item '" + item.getText() + "' was not selected!");
        }
    }
}