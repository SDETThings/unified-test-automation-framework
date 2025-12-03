package web.pages;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;

import java.util.function.Supplier;

public class SignUpOrLoginPage extends BasePage {
    private final Supplier<Locator> loginTextField = ()->page.locator("//*[@data-qa='login-email']");
    private final Supplier<Locator> loginPasswordTextField = ()->page.locator("//*[@data-qa='login-password']");
    private final Supplier<Locator> loginbutton = ()->page.locator("//*[@data-qa='login-button']");
    private final Supplier<Locator> signUpTextField = ()->page.locator("//*[@data-qa='signup-name']");
    private final Supplier<Locator> signUpPasswordTextField = ()->page.locator("//*[@data-qa='signup-email']");
    private final Supplier<Locator> signUpbutton = ()->page.locator("//*[@data-qa='signup-button']");

    public HomePage login(JsonObject testData){
        String userEmail = testData.get("username").getAsString();
        String userPassword = testData.get("password").getAsString();
        playwrightActions.typeText(page, loginTextField.get(), userEmail);
        playwrightActions.typeText(page, loginPasswordTextField.get(), userPassword);
        playwrightActions.clickElement(page, loginbutton.get());

        return new HomePage();
    }
}
