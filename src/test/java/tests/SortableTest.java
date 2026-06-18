package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.SortablePage;

public class SortableTest extends BaseTest {

    @Test(priority = 1)
    public void testSortableList() {
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickListTab();
        
        // The framework executes a dynamic drag and drop of random list elements
        sortablePage.sortRandomListElements();
        
        }

    @Test(priority = 2)
    public void testSortableGrid() {
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickGridTab();
        
        // Execute dynamic drag and drop on the grid
        sortablePage.sortRandomGridElements();
    }
}