package pages;

import org.openqa.selenium.By;

import utilities.ElementUtils;
import utilities.Log;

public class JupiterCartPage extends ElementUtils {
	
	private int timeOut = 10;
	
	public void verifyItemNameAndQuantityInCart(String itemName, int quantity) throws Exception {
		By itemAndQuantityLocator = By.xpath("//table[contains(@class,'cart-items')]//td[position()=count(//th[normalize-space(.)='Item']/preceding-sibling::th)+1 and normalize-space(.)='" + itemName + "']/ancestor::tr//td[position()=count(//table[contains(@class,'cart-items')]//th[normalize-space(.)='Quantity']/preceding-sibling::th)+1]/input[@value='" + quantity + "']");
		verifyElementVisible(itemAndQuantityLocator, timeOut, "Could not verify quantity in cart for item "+itemName+" as "+quantity);
		Log.info("Verified quantity in cart for item "+itemName+" as "+quantity);
	}
	
	public void verifyItemNameAndPriceInCart(String itemName, String price) throws Exception {
		By itemAndPriceLocator = By.xpath("//table[contains(@class,'cart-items')]//td[position()=count(//th[normalize-space(.)='Item']/preceding-sibling::th)+1 and normalize-space(.)='" + itemName + "']/ancestor::tr//td[position()=count(//table[contains(@class,'cart-items')]//th[normalize-space(.)='Price']/preceding-sibling::th)+1][text()='" + price + "']");
		verifyElementVisible(itemAndPriceLocator, timeOut, "Could not verify price in cart for item "+itemName+" as "+price);
		Log.info("Verified price in cart for item "+itemName+" as "+price);
	}
	
	public double verifyItemNameAndTotalPriceOfEachItemInCart(String itemName, String price, int quantity) throws Exception {
		double totalPriceOfEachItem = Double.valueOf(price.substring(1)) * quantity;
		By itemAndTotalPriceOfEachItemLocator = By.xpath("//table[contains(@class,'cart-items')]//td[position()=count(//th[normalize-space(.)='Item']/preceding-sibling::th)+1 and normalize-space(.)='" + itemName + "']/ancestor::tr//td[position()=count(//table[contains(@class,'cart-items')]//th[normalize-space(.)='Subtotal']/preceding-sibling::th)+1][text()='$" + totalPriceOfEachItem + "']");
		verifyElementVisible(itemAndTotalPriceOfEachItemLocator, timeOut, "Could not verify total price (quanity * priceOfEach) in cart for item "+itemName+" as "+totalPriceOfEachItem);
		Log.info("Verified subtotal in cart for item "+itemName+" as "+totalPriceOfEachItem);
		return totalPriceOfEachItem;
	}
	
	public void verifyTotalPriceOfAllProducts(double totalPrice) throws Exception {
		By totalPriceLocator = By.xpath("//*[contains(text(),'Total')][contains(text(),'" + String.valueOf(totalPrice) + "')]");
		verifyElementVisible(totalPriceLocator, timeOut, "Could not verify total price of all items as "+totalPrice);
		Log.info("Verified total price for all items in cart "+totalPrice);
	}
}

