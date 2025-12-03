package web.pages;

import base.BaseFunctions;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import web.playwright.pageActions.PlaywrightActions;

public class BasePage extends BaseFunctions {
    protected final String navigationMenuOptions = "//*[@class='shop-menu pull-right']/ul/li/a";
    protected PlaywrightActions playwrightActions;
    protected BrowserContext browserContext;
    protected Browser browser;
    protected Page page;


    public BasePage() {
        this.browser = browserManager.get().getBrowser();
        this.browserContext = browserManager.get().getContext();
        this.page = browserManager.get().getPage();
        this.page.setDefaultNavigationTimeout(60000);
        this.playwrightActions = PlaywrightActions.getInstance();
        //autoPageReadyWait();
    }
    private void autoPageReadyWait() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForLoadState(LoadState.LOAD);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void launchApplication() {
        String url = prop.getProperty("applicationUrl");
        String title = "Automation Exercise";
        page.navigate(url);
        String actualTitle = playwrightActions.getTitle(page);
        if (actualTitle.equals(title)) {
            System.out.println("Successfully launched the application. Title: " + actualTitle);
        } else {
            throw new RuntimeException("Failed to launch the application. Expected title: " + title + ", Actual title : " + actualTitle);
        }
    }

    public void validatePageLanding(Locator uniqueLocator) {
        uniqueLocator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10 * 1000));
    }
    public Object navigateToDesiredMenuOption(String menuOption, Object pageObjectToReturn) {
        // 1. Wait until DOM is fully loaded
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        // 2. Wait until all network calls finish (optional but useful)
        //page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator navigationMenu = page.locator(navigationMenuOptions);
        for (int i = 0; i < navigationMenu.count(); i++) {
            String actualMenuOption = playwrightActions.getText(page, navigationMenu.nth(i)).trim();
            if (actualMenuOption.trim().toLowerCase().contains(menuOption.trim().toLowerCase())) {
                playwrightActions.clickElement(page, navigationMenu.nth(i));
                break;
            }
        }
        return pageObjectToReturn;
    }
}
