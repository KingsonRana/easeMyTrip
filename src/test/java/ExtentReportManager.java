import Base.BaseTest;
import Utility.ScreenshotUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;  //handles ui of the report
    public ExtentReports extent; //populate common info on the report
    public ExtentTest test; //creating test case enteries of the report and update status of the test method
    public BaseTest base = new BaseTest();
    @Override
    public void onStart(ITestContext context) {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("EaseMyTrip");
        sparkReporter.config().setReportName("EaseMyTrip Test Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter); // <-- if this fails, ExceptionInInitializerError
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Tester", "Kingson rana");
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        String testDescription = result.getMethod().getDescription();
        test = extent.createTest(result.getName());
        test.log(Status.PASS, testDescription);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testDescription = result.getMethod().getDescription();
        test = extent.createTest(result.getName());
        test.log(Status.FAIL, testDescription);
        test.log(Status.FAIL,"Failed due to " + result.getThrowable());
        WebDriver driver = base.getDriver();
        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
        System.out.println(screenshotPath);
        test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testDescription = result.getMethod().getDescription();
        test = extent.createTest(result.getName());
        test.log(Status.SKIP, testDescription);

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("===> Test Suite Finished: " + context.getName());
    }
}
