package pages;

import org.openqa.selenium.By;

import utilities.ElementUtils;
import utilities.Log;

public class JupiterHomePage extends ElementUtils {
	
	private int timeOut = 10;
	
	private By header_JupiterToys_HomeSection=By.xpath("//h1[text()='Jupiter Toys']");
	
	public void verifyHeaderDisplayed() throws Exception {
		verifyElementVisible(header_JupiterToys_HomeSection, timeOut, "Jupiter Toys header is not displayed on home screen");
		Log.info("Verified header is available on Home Page");
	}
	
}

