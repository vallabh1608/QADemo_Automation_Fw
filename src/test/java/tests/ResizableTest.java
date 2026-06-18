package tests;

import base.BaseTest;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResizablePage;

public class ResizableTest extends BaseTest {

    @Test(priority = 1)
    public void testResizableBoxWithRestriction() {
        ResizablePage resizablePage = new ResizablePage(getDriver());
        
        resizablePage.navigateToResizablePage();
        
        Dimension initialSize = resizablePage.getRestrictedBoxSize();
        resizablePage.resizeRestrictedBox(100, 50);
        Dimension newSize = resizablePage.getRestrictedBoxSize();
        
        Assert.assertTrue(newSize.getWidth() > initialSize.getWidth(), "The restricted box width did not increase!");
        Assert.assertTrue(newSize.getHeight() > initialSize.getHeight(), "The restricted box height did not increase!");
    }

    @Test(priority = 2)
    public void testResizableBoxWithoutRestriction() {
        ResizablePage resizablePage = new ResizablePage(getDriver());
        
        resizablePage.navigateToResizablePage();
        
        Dimension initialSize = resizablePage.getUnrestrictedBoxSize();
        resizablePage.resizeUnrestrictedBox(150, 150);
        Dimension newSize = resizablePage.getUnrestrictedBoxSize();
        
        Assert.assertTrue(newSize.getWidth() > initialSize.getWidth(), "The unrestricted box width did not increase!");
        Assert.assertTrue(newSize.getHeight() > initialSize.getHeight(), "The unrestricted box height did not increase!");
    }
}