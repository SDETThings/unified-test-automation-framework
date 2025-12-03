package web.pages;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import unifiedReports.LoggerFactory;

import java.util.function.Supplier;

public class ProductsPage extends BasePage {
    private final Supplier<Locator> itemDetails =  ()->page.locator("//*[@class='single-products']/div");
    private final Supplier<Locator> productAddedToCartDialogTexts = ()->page.locator("//*[@id='cartModal']/div/div/div");

    public void addItemToCart(String productName,String productPrice ){
        Locator items = itemDetails.get();
        for(int i=0;i<items.count();i++){
            String actualProductName = playwrightActions.getText(page,items.locator("xpath=.//p[1]").nth(i)).trim();
            String actualProductPrice = playwrightActions.getText(page,items.locator("xpath=.//h2[1]").nth(i)).trim();
            if(actualProductName.equalsIgnoreCase(productName.trim()) && actualProductPrice.equalsIgnoreCase(productPrice.trim())){
                Locator addToCartButton = items.locator("xpath=.//a").nth(i);
                playwrightActions.clickElement(page,addToCartButton);
                Assert.assertTrue(isItemAddedToCart("Your product has been added to cart."), "Item: "+productName+" is not added to cart");
                break;
            }
        }
    }
    public boolean isItemAddedToCart(String confirmationMessage) {
        Locator validationTexts = productAddedToCartDialogTexts.get();
        String actualText = playwrightActions.getText(page, validationTexts.nth(1)).trim();
        return actualText.equalsIgnoreCase(confirmationMessage) || actualText.contains(confirmationMessage);
    }
    public void continueShoppingAfterAddingItem(){
        Locator validationTexts =productAddedToCartDialogTexts.get();
        playwrightActions.clickElement(page,validationTexts.nth(2).locator("xpath=.//button"));
    }
    public CartPage goToCart(){
        Locator validationTexts =productAddedToCartDialogTexts.get();
        playwrightActions.clickElement(page,validationTexts.nth(1).locator("xpath=.//p/a"));

        return new CartPage();
    }

}
