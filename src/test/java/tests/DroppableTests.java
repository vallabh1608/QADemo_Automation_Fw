package tests;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DroppablePage;

public class DroppableTests extends BaseTest {

//    @Test(priority = 1)
//    public void simpleDragAndDrop() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.dragSimpleBox();
//
//        Assert.assertEquals(page.getSimpleTargetText(), "Dropped!");
//    }
//
//    @Test(priority = 2)
//    public void acceptableDragAndDrop() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.clickAcceptTab();
//
//        page.dragNotAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Drop here");
//
//        page.dragAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Dropped!");
//    }

    @Test(priority = 3)
    public void preventPropagationDroppable() {
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.clickPreventTab();

        page.dragToNotGreedyInner();
        Assert.assertTrue(page.getNotGreedyInnerClass().contains("ui-state-highlight"));
        Assert.assertTrue(page.getNotGreedyOuterClass().contains("ui-state-highlight"));

        page.dragToGreedyInner();
        Assert.assertTrue(page.getGreedyInnerClass().contains("ui-state-highlight"));
        Assert.assertFalse(page.getGreedyOuterClass().contains("ui-state-highlight"));
    }

    @Test(priority = 4)
    public void testRevertDraggable() {
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.clickRevertTab();

        // ✅ Revertable
        Point start = page.getRevertSourceLocation();
        page.dragRevertBox(true);
        Point end = page.getRevertSourceLocation();

        Assert.assertEquals(end, start);

        // ✅ Not Revertable
        Point start2 = page.getNotRevertSourceLocation();
        page.dragRevertBox(false);
        Point end2 = page.getNotRevertSourceLocation();

        Assert.assertNotEquals(end2, start2);
    }
}