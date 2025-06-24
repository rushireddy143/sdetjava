package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.EmailUtils;

public class EmailSuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        // Send email before suite starts
        EmailUtils.sendEmail(
            "Regression Suite Execution Started",
            "Regression suite is about to start.",
            "lrushikeswarreddy@gmail.com",
            null // no attachment before execution
        );
    }

    @Override
    public void onFinish(ISuite suite) {
        // Path to your test report (e.g., TestNG or Cucumber HTML report)
        String reportPath = "target/cucumber-reports";
        EmailUtils.sendEmail(
            "Regression Suite Execution Completed",
            "Regression suite has completed. Please find the report attached.",
            "lrushikeswarreddy@gmail.com",
            reportPath
        );
    }
}