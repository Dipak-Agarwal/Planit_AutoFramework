package pages;

import org.openqa.selenium.By;

import utilities.ElementUtils;
import utilities.Log;

public class JupiterShopPage extends ElementUtils {
	
	private int timeOut = 10;
	
	public void clickOnBuyButtonForSpecificItem(String itemName) throws Exception {
		By btn_Buy_SpecificItem = By.xpath("//*[text()='" + itemName + "']/following-sibling::p/a[contains(@class,'btn')][text()='Buy']");
		clickOnElement(btn_Buy_SpecificItem, timeOut, "Could not click on buy button for item : "+itemName);
		Log.info("Clicked on Buy button for "+itemName);
	}
	
	public String getPriceForSpecificItem(String itemName) throws Exception {
		By priceOfSpecificItemLocator = By.xpath("//*[text()='" + itemName + "']/following-sibling::p/span[contains(@class,'price')]");
		return getTextFromElement(priceOfSpecificItemLocator, timeOut, "Could not fetch price for item : "+itemName);
	}
	
}

