package Utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
           return 0;
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
    public static void waitForLoaderToDisappear(WebDriverWait wait) {
        wait.until(driver -> {
            WebElement loader = driver.findElement(By.xpath("//div[@id='Loader']"));
            String displayStyle = loader.getAttribute("style");
            return displayStyle != null && displayStyle.contains("display: none");
        });
    }
}
