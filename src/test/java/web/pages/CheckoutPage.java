package web.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class CheckoutPage extends BasePage {
    private final Supplier<Locator> orderMessageTextField = () -> page.locator("//*[@id='ordermsg']/textarea");
    private final Supplier<Locator> placeOrderButton = () -> page.locator("//*[text()='Place Order']");
    public void enterOrderMessage(String orderMessage){
        playwrightActions.typeText(page,orderMessageTextField.get(),orderMessage);

    }
    public PaymentPage placeOrder(){
        playwrightActions.clickElement(page,placeOrderButton.get());

        return new PaymentPage();
    }

}
