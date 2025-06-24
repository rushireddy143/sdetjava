package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentTestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test failed: " + result.getThrowable());
        // Screenshot capture
        Object currentClass = result.getInstance();
        WebDriver driver = null;
        try {
            driver = (WebDriver) currentClass.getClass().getDeclaredField("driver").get(currentClass);
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Unable to get driver for screenshot: " + e.getMessage());
        }
        if (driver != null) {
            String screenshotPath = getScreenshot(driver, result.getMethod().getMethodName());
            test.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String getScreenshot(WebDriver driver, String methodName) {
        String dir = System.getProperty("user.dir") + "/target/screenshots/";
        new File(dir).mkdirs();
        String path = dir + methodName + "_" + System.currentTimeMillis() + ".png";
        try {
            byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(path), src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}