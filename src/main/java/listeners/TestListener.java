package listeners;

import base.BaseFunctions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.gson.*;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;
import unifiedReports.LoggerFactory;
import unifiedReports.webLogger.WebReportLogger;
import utils.RetryAnalyzer;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener extends BaseFunctions implements IAnnotationTransformer, ITestListener {

    public static ThreadLocal<String> currentTestResultsFolder = new ThreadLocal<>();

    public static String getReportNameWithTimeStamp(String testcase, String pattern) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String timeStamp = now.format(formatter);
        String reportName = testcase + "_REPORT_" + timeStamp + ".html";
        return reportName;
    }

    public synchronized static String getCurrentTestResultsFolder() {
        return currentTestResultsFolder.get();
    }

    public synchronized boolean shouldBeSkipped(ITestAnnotation annotation, Method methodName) throws IOException, InterruptedException {
        readProperties();
        readChainTestProperties();
        boolean isPresent = false;
        FileReader reader = new FileReader("./src/test/resources/TestCaseDriver/TestCaseMetadata.json");
        try {
            JsonObject testDriver = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray TestCaseRunConfigurationArray = testDriver.get("TestCaseRunConfiguration").getAsJsonArray();
            for (JsonElement testCaseInfo : TestCaseRunConfigurationArray) {
                JsonObject TestCaseRunConfigurationArrayElements = testCaseInfo.getAsJsonObject();
                String testCaseNumber = TestCaseRunConfigurationArrayElements.get("test-case-id").getAsString();
                if (testCaseNumber.equals(methodName.getName())) {
                    if (!annotation.getEnabled()) {
                        isPresent = true;
                        break;
                    }
                }
            }
        } finally {
            reader.close();
        }
        return isPresent;
    }

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        try {
            if (shouldBeSkipped(iTestAnnotation, method)) {
                iTestAnnotation.setEnabled(true);
                //iTestAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onStart(ITestContext iTestContext) {
    }

    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        String testName = iTestResult.getName();
        if(!testName.startsWith("PE")) {
            String fullReportPath = createTestCaseResultsFolder(prop.getProperty("TestCaseEvidenceFolder"), testName, "yyyyMMddHHmmss");
            currentTestResultsFolder.set(fullReportPath);
        }
        if(!testName.startsWith("PE")) {
            browserManager.get().getContext().tracing()
                    .start(new Tracing.StartOptions()
                            .setScreenshots(true)
                            .setSnapshots(true)
                            .setSources(false));
        }
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String testName = iTestResult.getName();
        if(!testName.startsWith("PE")) {
            browserManager.get().getContext().tracing()
                    .stop(new Tracing.StopOptions()
                            .setPath(Paths.get(getCurrentTestResultsFolder() + "/" + iTestResult.getName() + "_trace.zip")));
            currentTestResultsFolder.remove();
        }
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        browserManager.get().getContext().tracing()
                .stop(new Tracing.StopOptions()
                        .setPath(Paths.get(getCurrentTestResultsFolder() + "/" + iTestResult.getName() + "trace.zip")));
        currentTestResultsFolder.remove();
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        browserManager.get().getContext().tracing()
                .stop(new Tracing.StopOptions()
                        .setPath(Paths.get(getCurrentTestResultsFolder() + "/" + "trace.zip")));
        currentTestResultsFolder.remove();
    }

    @Override
    public synchronized void onFinish(ITestContext iTestContext) {
    }
}
