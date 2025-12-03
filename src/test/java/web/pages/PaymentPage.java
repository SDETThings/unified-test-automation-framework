package web.pages;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import listeners.TestListener;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class PaymentPage extends BasePage {
    private final Supplier<Locator> paymentPageValidationText = ()->page.locator("//h2[text()='Payment']");
    private final Supplier<Locator> cardNameTextField =  ()->page.locator("//*[@name='name_on_card']");
    private final Supplier<Locator> cardNumberTextField =  ()->page.locator("//*[@name='card_number']");
    private final Supplier<Locator> cvcTextField =  ()->page.locator("//*[@name='cvc']");
    private final Supplier<Locator> expirationTextField =  ()->page.locator("//*[@name='expiry_month']");
    private final Supplier<Locator> expirationYearTextField =  ()->page.locator("//*[@name='expiry_year']");
    private final Supplier<Locator> payAndConfirmOrderButton =  ()->page.locator("//*[@data-qa='pay-button']");
    private final Supplier<Locator> orderConfiramtionMessage =  ()->page.locator("//*[text()='Congratulations! Your order has been confirmed!']");
    private final Supplier<Locator> downloadInvoiceButton =  ()->page.locator("//*[@id='form']/div/div/div/a");

    public void enterCardDetailsAndMakePayment(JsonObject testData){
        validatePageLanding(paymentPageValidationText.get());
        String cardName = testData.get("cardDetails").getAsJsonObject().get("cardName").getAsString();
        String cardNumber = testData.get("cardDetails").getAsJsonObject().get("cardNumber").getAsString();
        String cvc = testData.get("cardDetails").getAsJsonObject().get("cvc").getAsString();
        String expirationMonth = testData.get("cardDetails").getAsJsonObject().get("expiryMonth").getAsString();
        String expirationYear = testData.get("cardDetails").getAsJsonObject().get("expiryYear").getAsString();

        playwrightActions.typeText(page,cardNameTextField.get(),cardName);
        playwrightActions.typeText(page,cardNumberTextField.get(),cardNumber);
        playwrightActions.typeText(page,cvcTextField.get(),cvc);
        playwrightActions.typeText(page,expirationTextField.get(),expirationMonth);
        playwrightActions.typeText(page,expirationYearTextField.get(),expirationYear);
        playwrightActions.clickElement(page,payAndConfirmOrderButton.get());
    }
    public boolean validateOderConfirmation(){
        return playwrightActions.getText(page,orderConfiramtionMessage.get()).trim().contains("confirmed");
    }
    public void downloadInvoicePDF(){
        Download file = page.waitForDownload(() -> {
            playwrightActions.clickElement(page, downloadInvoiceButton.get());
        });
        file.saveAs(Paths.get(TestListener.getCurrentTestResultsFolder()+"/"+"Invoice_"+System.currentTimeMillis()+".pdf"));
    }

}
