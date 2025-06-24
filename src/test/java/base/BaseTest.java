package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ExtentManager;
import org.apache.logging.log4j.*;
import utils.ConfigManager;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    protected Logger logger = LogManager.getLogger(this.getClass());

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        extent = ExtentManager.getInstance();
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {
        logger.info("Starting test on browser: " + (browser != null ? browser : ConfigManager.get("browser")));
        driver = DriverFactory.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Quitting driver");
        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        extent.flush();
    }
}