package web.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

public class CartPage extends BasePage {
    private final Supplier<Locator> productDescription = () -> page.locator("//tbody/tr/td[@class='cart_description']/h4/a");
    private final Supplier<Locator> proccedToCheckoutButton = () -> page.locator("//*[@id='do_action']/div/div/div/a");
    private final Supplier<Locator> registerOrLoginLink = () -> page.locator("//*[@id='checkoutModal']/div/div/div/p/a");

    public void validateCorrectItemsAreAddedToCart(JsonObject testData){
        List<String> expectedProductNames = StreamSupport
                .stream(testData.get("productsToBeAddedToCart").getAsJsonArray().spliterator(), false)
                .map(e -> e.getAsJsonObject().get("productName").getAsString())
                .toList();
        List<String> actualNames = productDescription.get()
                .allInnerTexts()
                .stream()
                .map(String::trim)
                .toList();
        actualNames.stream()
                .filter(actual -> !expectedProductNames.contains(actual))
                .findAny()
                .ifPresent(unexpected -> {
                    throw new AssertionError("Unexpected item found in cart: " + unexpected);
                });
        logWebStep(true,true,"All items are added to cart",page,true,true);

    }
    public CheckoutPage procceedToCheckout(){
        playwrightActions.clickElement(page,proccedToCheckoutButton.get());

        return new CheckoutPage();
    }
    public SignUpOrLoginPage goToRegisterOrLogin(){
        playwrightActions.clickElement(page,registerOrLoginLink.get());
        return new SignUpOrLoginPage();
    }


}
