package web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;

import java.util.function.Supplier;

public class HomePage extends BasePage {
    private final Supplier<Locator> categoryList = () -> page.locator("//*[@id='accordian']/div/div/h4/a");
    private final Supplier<Locator> subcategoriesList = () -> page.locator("//*[@id='accordian']/div/div[2]/div/ul/li/a");
    public void selectCategory(String categoryName){
        //page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator categories = categoryList.get();
        for(int i=0;i<categories.count();i++){
            String actualText = playwrightActions.getText(page,categories.nth(i)).trim();
            System.out.println("Category : "+actualText);
            if(actualText.equalsIgnoreCase(categoryName.trim().toUpperCase())){
                playwrightActions.clickElement(page,categories.nth(i));
                System.out.println("Selected category: "+categoryName);
                break;
            }
        }
    }
    public ProductsPage selectSubCategory(String subcategoryName){
        Locator subCategories = subcategoriesList.get();
        for(int i=0;i<subCategories.count();i++){
            String actualText = playwrightActions.getText(page,subCategories.nth(i)).trim();
            System.out.println("Sub-category : "+actualText);
            System.out.println("Expected sub-category : "+subcategoryName);
            if(actualText.equalsIgnoreCase(subcategoryName.trim())){
                playwrightActions.clickElement(page,subCategories.nth(i));
                System.out.println("Selected sub-category: "+subcategoryName);
                break;
            }
        }
        return new ProductsPage();
    }
}
