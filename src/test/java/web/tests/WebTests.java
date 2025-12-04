package web.tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Tracing;
import dataHelpers.DataProvider;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import unifiedReports.LoggerFactory;
import web.pages.BasePage;
import web.pages.SignUpOrLoginPage;
import web.playwright.browserManager.BrowserManager;
import web.testImplementation.WebFlows;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebTests extends WebFlows {
    private static final ThreadLocal<String> testCaseId = new ThreadLocal<>();
    private static final ThreadLocal<String> environment = new ThreadLocal<>();
    private static final ThreadLocal<String> client = new ThreadLocal<>();
    private static final ThreadLocal<String> browserName = new ThreadLocal<>();
    private static final ThreadLocal<String> browserVersion = new ThreadLocal<>();
    private static final ThreadLocal<String> osName = new ThreadLocal<>();
    private static final ThreadLocal<String> osVersion = new ThreadLocal<>();
    BasePage basePage ;
    private static ThreadLocal<Map<String,String>> practiceExtendUserJourneytoken = ThreadLocal.withInitial(ConcurrentHashMap::new);

    @BeforeMethod
    public void BeforeWebExecutionStart(ITestResult iTestResult) {
        testCaseId.set(iTestResult.getMethod().getMethodName());
        try {
            environment.set(getTestCaseMetaDataBlock(testCaseId.get()).get("environment").getAsString());
            client.set(getTestCaseMetaDataBlock(testCaseId.get()).get("client-id").getAsString());
            browserName.set(getTestCaseMetaDataBlock(testCaseId.get()).get("browser").getAsString());
            browserVersion.set(getTestCaseMetaDataBlock(testCaseId.get()).get("browser-version").getAsString());
            osName.set(getTestCaseMetaDataBlock(testCaseId.get()).get("os").getAsString());
            osVersion.set(getTestCaseMetaDataBlock(testCaseId.get()).get("os-version").getAsString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Read the test case meta data");
        browserManager.set(BrowserManager.getInstance(browserName.get(),Boolean.parseBoolean(prop.getProperty("headlessMode"))));
        basePage = new BasePage();
        basePage.launchApplication();

        enableSmartPageReadyWait(browserManager.get().getPage());
    }

    @Test(description = "Place Order: Login during Checkout",dataProvider = "getWebDataIterations", dataProviderClass = DataProvider.class, enabled = false)
    public void AE0001(JsonElement testDataSet) throws InterruptedException {
        JsonObject testData = getPageWiseData(testDataSet.getAsJsonObject(),"Universal data");
        addProductsToCart(testData);
        goToCart();
        proceedToCheckout();
        loginWithExistingAccount(testData);
        placeOrder(testData);
        downloadInvoice();
    }
    @Test(description = "Place Order: Login before Checkout",dataProvider = "getWebDataIterations", dataProviderClass = DataProvider.class, enabled = false)
    public void AE0002(JsonElement testDataSet) throws InterruptedException {
        JsonObject testData = getPageWiseData(testDataSet.getAsJsonObject(),"Universal data");
        navigateToDesiredMenuOption(" Signup / Login", new SignUpOrLoginPage());
        loginWithExistingAccount(testData);
        addProductsToCart(testData);
        goToCart();
        proceedToCheckout();
        placeOrder(testData);
    }
    @Test(description = "Place Order: Register before checkout - WIP",dataProvider = "getWebDataIterations", dataProviderClass = DataProvider.class, enabled = false)
    public void AE0003(JsonElement testDataSet) throws InterruptedException {
        JsonObject testData = getPageWiseData(testDataSet.getAsJsonObject(),"Universal data");
        addProductsToCart(testData);
        goToCart();
        proceedToCheckout();
        loginWithExistingAccount(testData);
        placeOrder(testData);
    }

    @AfterMethod
    public void afterWebExecutionEnd() {
        BrowserManager.closeBrowser();
    }

}
