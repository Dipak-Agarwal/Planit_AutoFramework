package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utilities.ElementUtils;
import utilities.Log;

public class JupiterMainTabs extends ElementUtils {
	
	private int timeOut = 10;
	
	private By tab_Home=By.xpath("//a[contains(text(),'Home')]");
	private By tab_Contact=By.xpath("//a[contains(text(),'Contact')]");
	private By tab_Shop=By.xpath("//a[contains(text(),'Shop')]");
	private By icon_Cart = By.xpath("//a[contains(text(),'Cart')]");
	
//	public JupiterMainTabs(WebDriver driver) {
//		PageFactory.initElements(driver, JupiterMainTabs.class);
//	}
	
	public JupiterHomePage clickOnHomeTab() throws Exception {
		clickOnElement(tab_Home, timeOut, "Could not click on Home tab");
		Log.info("Clicked on Home tab");
		return new JupiterHomePage();
	}
	
	public JupiterContactPage clickOnContactTab() throws Exception {
		clickOnElement(tab_Contact, timeOut, "Could not click on Contact tab");
		Log.info("Clicked on Contact tab");
		return new JupiterContactPage();
	}
	
	public JupiterShopPage clickOnShopTab() throws Exception {
		clickOnElement(tab_Shop, timeOut, "Could not click on Shop tab");
		Log.info("Clicked on Shop tab");
		return new JupiterShopPage();
	}
	
	public JupiterCartPage clickOnCartIcon() throws Exception {
		clickOnElement(icon_Cart, timeOut, "Could not click on Cart Icon");
		Log.info("Clicked on Cart icon");
		return new JupiterCartPage();
	}
	
}

