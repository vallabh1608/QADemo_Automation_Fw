package testUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // Initialize report
    	ExtentReportManger.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create test in report
        String testName = result.getMethod().getMethodName();
        ExtentReportManger.startTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	ExtentReportManger.getTest().pass("✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = ExtentReportManger.getTest();

        test.fail("❌ Test Failed");
        test.fail(result.getThrowable());

        try {
            String base64 = ((base.BaseTest) result.getInstance())
                    .captureBase64Screenshot();

            if (base64 != null) {
                test.addScreenCaptureFromBase64String(base64, "Failure Screenshot");
            }

        } catch (Exception e) {
            test.warning("⚠️ Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    	ExtentReportManger.getTest().skip("⚠️ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush report
    	ExtentReportManger.flushReport();
    }
}