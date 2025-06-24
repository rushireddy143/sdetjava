package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = ConfigManager.get("report.path");
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}