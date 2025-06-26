package Utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CommonMethods {
    public static void waitUntilPageIsFullyLoaded(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(60)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }
    public static int calculatePercentage(int part, int total) {
        if (total == 0) {
            throw new IllegalArgumentException("Total cannot be zero.");
        }
        return (part * 100) / total;
    }
    public static boolean isSortedByHighest(List<Integer> list) throws InterruptedException {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
