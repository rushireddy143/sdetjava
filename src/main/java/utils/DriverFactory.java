package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;
import java.util.HashMap;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        String browser = ConfigManager.get("browser");
        String runMode;
        runMode = ConfigManager.get("run.mode");
        System.out.println("runMode = " + runMode);
        if (driverThread.get() == null) {
            try {
                System.out.println("runMode = " + runMode);
                if ("sauce".equalsIgnoreCase(runMode)) {
                    driverThread.set(initSauceDriver(browser));
                } else {
                    driverThread.set(initLocalDriver(browser));
                }
            } catch (Exception e) {
                throw new RuntimeException("WebDriver init failed", e);
            }
        }
        return driverThread.get();
    }

    private static WebDriver initLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new org.openqa.selenium.firefox.FirefoxDriver(new FirefoxOptions());
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new org.openqa.selenium.edge.EdgeDriver(new EdgeOptions());
            case "safari":
                return new org.openqa.selenium.safari.SafariDriver(new SafariOptions());
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return new org.openqa.selenium.chrome.ChromeDriver(new ChromeOptions());
        }
    }

    private static WebDriver initSauceDriver(String browser) throws Exception {
        String user = ConfigManager.get("sauce.username");
        String key = ConfigManager.get("sauce.accessKey");
        String url = "https://" + user + ":" + key + "@ondemand.eu-central-1.saucelabs.com/wd/hub";
        MutableCapabilities caps = null;
        switch (browser.toLowerCase()) {
            case "firefox":
                caps = new FirefoxOptions();
                break;
            case "edge":
                caps = new EdgeOptions();
                break;
            case "safari":
                caps = new SafariOptions();
                break;
            case "chrome":
            default:
                caps = new ChromeOptions();
        }
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("platformName", "Windows 11");
        HashMap<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("build", "Build-" + System.currentTimeMillis());
        sauceOptions.put("name", browser + "-Suite");
        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL(url), caps);
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}