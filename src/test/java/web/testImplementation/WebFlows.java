package web.testImplementation;

import base.BaseFunctions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.testng.Assert;
import web.pages.*;


public class WebFlows extends BaseFunctions {

    public void addProductsToCart(JsonObject testData) throws InterruptedException {
        HomePage homePage = new HomePage();
        ProductsPage productsPage = null;
        JsonArray productName = testData.get("productsToBeAddedToCart").getAsJsonArray();
        int numberOfItemsAdded = 0;
        int totalNumberOfItemsToBeAdded = productName.size();
        for (JsonElement product : productName) {
            String categoryName = product.getAsJsonObject().get("Category").getAsString();
            String subCategoryName = product.getAsJsonObject().get("SubCategory").getAsString();
            String prodName = product.getAsJsonObject().get("productName").getAsString();
            String prodPrice = product.getAsJsonObject().get("productPrice").getAsString();
            homePage.selectCategory(categoryName);
            productsPage = homePage.selectSubCategory(subCategoryName);
            productsPage.addItemToCart(prodName, prodPrice);
            numberOfItemsAdded++;
            if(numberOfItemsAdded<totalNumberOfItemsToBeAdded){
                productsPage.continueShoppingAfterAddingItem();
            }
        }
        CartPage cartPage = productsPage.goToCart();
        cartPage.validateCorrectItemsAreAddedToCart(testData);
    }
    public void goToCart(){
        ProductsPage productsPage = new ProductsPage();
        productsPage.goToCart();
    }
    public void proceedToCheckout(){
        CartPage cartPage = new CartPage();
        cartPage.procceedToCheckout();
    }
    public void loginWithExistingAccount(JsonObject testData){
        CartPage cartPage = new CartPage();
        SignUpOrLoginPage signUpOrLoginPage = cartPage.goToRegisterOrLogin();
        signUpOrLoginPage.login(testData);
    }
    public void placeOrder(JsonObject testData){
        BasePage basePage = new BasePage();
        CartPage cartPage = (CartPage) basePage.navigateToDesiredMenuOption(" Cart", new CartPage());
        CheckoutPage checkoutPage = cartPage.procceedToCheckout();
        checkoutPage.enterOrderMessage("Please deliver between 9 AM to 5 PM");
        PaymentPage paymentPage = checkoutPage.placeOrder();
        paymentPage.enterCardDetailsAndMakePayment(testData);
        Assert.assertTrue(paymentPage.validateOderConfirmation());
    }
    public void navigateToDesiredMenuOption(String menuOption, Object pageObject){
        BasePage basePage = new BasePage();
        basePage.navigateToDesiredMenuOption(menuOption, pageObject);
    }
    public void downloadInvoice(){
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.downloadInvoicePDF();
    }

}
