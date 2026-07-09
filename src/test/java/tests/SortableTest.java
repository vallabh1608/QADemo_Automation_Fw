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
        
        sortablePage.sortRandomListElements();
        
        }

    @Test(priority = 2)
    public void testSortableGrid() {
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickGridTab();
        
        sortablePage.sortRandomGridElements();
    }
}