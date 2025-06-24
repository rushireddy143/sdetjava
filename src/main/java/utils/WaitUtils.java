package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class WaitUtils {
    public static WebElement waitForElementVisible(WebDriver driver, By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForPageLoaded(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
            wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}