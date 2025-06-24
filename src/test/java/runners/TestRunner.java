package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;
import listeners.ExtentTestListener;
import listeners.EmailSuiteListener;

@Listeners({ExtentTestListener.class, EmailSuiteListener.class})
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions"},
    plugin = {"pretty", "html:target/cucumber-reports"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}