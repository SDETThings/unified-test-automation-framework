package base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import dataHelpers.MasterDataUtils;
import unifiedReports.LoggerFactory;
import unifiedUtils.FileUtils;
import unifiedUtils.JsonOperations;
import web.playwright.browserManager.BrowserManager;
import web.playwright.pageActions.PlaywrightActions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import java.security.SecureRandom;

public class BaseFunctions {
    public static Properties prop;
    public static Properties prop1;
    protected static ThreadLocal<BrowserManager> browserManager = new ThreadLocal<>();
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+<>?";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    MasterDataUtils masterDataUtils;
    public void readProperties() throws InterruptedException {
        prop = new Properties();
        try {
            prop.load(new FileReader("./config.properties"));
        } catch (IOException e) {
            throw new InterruptedException("config.properties file not found in the classpath");
        }
    }
    public void readChainTestProperties() throws InterruptedException {
        prop1 = new Properties();
        try {
            prop1.load(new FileReader("./src/test/resources/chaintest.properties"));
        } catch (IOException e) {
            throw new InterruptedException("config.properties file not found in the classpath");
        }
    }
    public String readClientTestDataJsonFile(String client) {
        String fileName = null;
        switch (client) {
            case "TEST_CLIENT_1":
                fileName =  prop.getProperty("TestClient1DataFilePath").split("/")[prop.getProperty("TestClient1DataFilePath").split("/").length - 1];
                break;
           case "TEST_CLIENT_2": 
               fileName =  prop.getProperty("TestClient2DataFilePath").split("/")[prop.getProperty("TestClient2DataFilePath").split("/").length - 1];
               break;
            case "TEST_CLIENT_3":
               fileName =  prop.getProperty("TestClient2DataFilePath").split("/")[prop.getProperty("TestClient3DataFilePath").split("/").length - 1];
                break;
            default:
                   System.out.println("Invalid client: " + client);
        }
        File testFileUtils = FileUtils.findFile(new File(prop.getProperty("TestDataFolder")),fileName);
        return testFileUtils.getPath();
    }
    public synchronized JsonObject getTestCaseMetaDataBlock(String testCaseNumber) throws FileNotFoundException {
        JsonObject requiredBlock = null;
        FileReader reader = new FileReader(prop.getProperty("TestCaseMetaDataFilePath"));
        JsonObject testDriver = JsonParser.parseReader(reader).getAsJsonObject();
        JsonArray TestCaseRunConfigurationArray = testDriver.get("TestCaseRunConfiguration").getAsJsonArray();
        for (JsonElement testCaseInfo : TestCaseRunConfigurationArray) {
            JsonObject TestCaseRunConfigurationArrayElement = testCaseInfo.getAsJsonObject();
            if (TestCaseRunConfigurationArrayElement.get("test-case-id").getAsString().equalsIgnoreCase(testCaseNumber)) {
                requiredBlock = TestCaseRunConfigurationArrayElement;
            }
        }
        return requiredBlock;
    }
    public String createTestCaseResultsFolder(String destinationPath, String testCaseName, String datePattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        String timestamp = dateFormat.format(new Date());
        String folderName = testCaseName + "_" + timestamp;
        File folder = new File(destinationPath, folderName);
        String testCaseFolderPath = null;

        try {
            if (!folder.exists() && folder.mkdirs()) {
                Path absolutePath = Paths.get(folder.getAbsolutePath());
                Path basePath = Paths.get("").toAbsolutePath();
                Path relativePath = basePath.relativize(absolutePath);
                testCaseFolderPath = relativePath.toString();
                String var12 = testCaseFolderPath.replace("\\", "/").replace("//", "/");
            }
        } catch (Exception var13) {
            System.out.println(var13.getStackTrace());
        }

        return testCaseFolderPath;
    }
    public static String generateRandomChars(int length) {
        return generateRandomString(ALL_CHARS, length);
    }
    public static String generateRandomLetters(int length) {
        return generateRandomString(LETTERS, length);
    }
    public static String generateRandomNumbers(int length) {
        return generateRandomString(NUMBERS, length);
    }
    private static String generateRandomString(String source, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(source.length());
            sb.append(source.charAt(index));
        }
        return sb.toString();
    }
    public JsonObject getPageWiseData(JsonElement testDataSet,String pageIdentifier) {
        masterDataUtils = new MasterDataUtils();
        return masterDataUtils.accessPageWiseWebMobileData(testDataSet, pageIdentifier);
    }
    protected void enableSmartPageReadyWait(Page page) {
        page.onFrameNavigated(frame -> {
            try {
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                page.waitForLoadState(LoadState.NETWORKIDLE);
            } catch (Exception e) {
                System.out.println("Page readiness listener error: " + e.getMessage());
            }
        });
    }
    public void logWebStep(boolean logToConsole, boolean logToReport, String stepDescription, Object driverOrPage, boolean isPassed, boolean captureScreenshot,Exception... e) {
        if (logToReport) {
            LoggerFactory.getWebReportLogger().logStep(stepDescription, driverOrPage, isPassed, captureScreenshot);        }
        if(logToConsole) {
            LoggerFactory.getWebConsoleLogger().logToConsole(isPassed, stepDescription);
        }

    }
}
