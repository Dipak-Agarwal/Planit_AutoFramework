package testRunners;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pages.JupiterCartPage;
import pages.JupiterContactPage;
import pages.JupiterHomePage;
import pages.JupiterMainTabs;
import pages.JupiterShopPage;
import utilities.DriverFactory;
import utilities.ExtentTestManager;
import utilities.ReadProperties;

public class TestNGRunner {
	ReadProperties readProp = new ReadProperties();
	JupiterHomePage homePage;
//	JupiterHomePage homePage = new JupiterHomePage();
	JupiterContactPage contactPage;
	JupiterShopPage shopPage;
	JupiterMainTabs mainTabs;
//	JupiterMainTabs mainTabs = new JupiterMainTabs();
	JupiterCartPage cartPage;
	
	List<String> expectedErrorMessagesList = Arrays.asList("Forename is required","Email is required","Message is required");
	
	@Parameters({"browser"})
	@BeforeMethod
	public void setUp(@Optional("chrome") String browser) throws Exception {
		DriverFactory.init_driver(browser);
		System.out.println(Thread.currentThread().getId() + " : " +DriverFactory.getDriver());
		homePage = new JupiterHomePage();
//		readProp = new ReadProperties();
		homePage.navigateToUrl(readProp.getPropertyValue("URL"));
		homePage.verifyHeaderDisplayed();
		mainTabs = new JupiterMainTabs();
	}
	
	@Parameters({"browser"})
	@Test(description="Verify validation errors on contact tab")
	public void runTest1(@Optional("chrome") String browser, Method method) throws Exception {
			ExtentTestManager.startTest(method.getName()+" on "+browser+" browser", "Run Test 1 - Verify validation errors on contact tab on "+browser+" browser ");
		System.out.println(Thread.currentThread().getId() + " : " +DriverFactory.getDriver() + " : RunTest1");
		contactPage = mainTabs.clickOnContactTab();
		System.out.println("Navigated to contact page");
		contactPage.verifyFeedbackHeaderDisplayed();
		contactPage.clickOnSubmitButton();
		contactPage.validateErrors(expectedErrorMessagesList);
		contactPage.enterValuesForForenameEmailAndMessageFields("Dipak","dipak_test@gmail.com","Exceptional service");
		contactPage.validateNoErrors();
//		mainTabs.clickOnHomeTab();
	}
	
	@Parameters({"browser"})
	@Test(invocationCount=5,description="Feedback recording")
	public void runTest2(@Optional("chrome") String browser, Method method) throws Exception {
		ExtentTestManager.startTest(method.getName()+" on "+browser+" browser", "Run Test 2 - Feedback recording on "+browser+" browser ");
		contactPage = mainTabs.clickOnContactTab();
		System.out.println("Navigated to contact page");
		contactPage.verifyFeedbackHeaderDisplayed();
		contactPage.enterValuesForForenameEmailAndMessageFields("Dipak","dipak_test@gmail.com","Exceptional service");
		contactPage.clickOnSubmitButton();
		contactPage.verifySuccessAlertForFeedbackDisplayed();
//		mainTabs.clickOnHomeTab();
	}
	
	@Parameters({"browser"})
	@Test(description="Testing shop and cart basic functionality check")
	public void runTest3(@Optional("chrome") String browser, Method method) throws Exception {
		ExtentTestManager.startTest(method.getName()+" on "+browser+" browser", "Run Test 3 - Testing shop and cart basic functionality check on "+browser+" browser ");
		shopPage = mainTabs.clickOnShopTab();
		System.out.println("Navigated to shop page");
		Map<String, Integer> itemAndQuantityMap = Map.of("Funny Cow",2, "Fluffy Bunny", 1);
		for(Map.Entry<String, Integer> entry : itemAndQuantityMap.entrySet())
		{
			String itemName = entry.getKey();
			int quantity = entry.getValue();
			for(int counter = 0;counter < quantity;counter++) {
				shopPage.clickOnBuyButtonForSpecificItem(itemName);
			}
		}
		cartPage = mainTabs.clickOnCartIcon();
		for(Map.Entry<String, Integer> entry : itemAndQuantityMap.entrySet())
		{
			cartPage.verifyItemNameAndQuantityInCart(entry.getKey(), entry.getValue());
		}
	}
	
	@Parameters({"browser"})
	@Test(description="Testing shop and cart advance functionality check")
	public void runTest4(@Optional("chrome") String browser, Method method) throws Exception {
		ExtentTestManager.startTest(method.getName()+" on "+browser+" browser", "Run Test 4 - Testing shop and cart advance functionality check on "+browser+" browser ");
		Map<String, String> itemAndPriceMap = new HashMap<>();
//		Map<String, Integer> itemAndQuantityMap = new HashMap<>();
//		Map<String, Integer> itemAndQuantityMap = Stream.of(new Object[][] { 
//		     { "Stuffed Frog", 2 }, 
//		     { "Fluffy Bunny", 5 },
//		     { "Valentine Bear", 3 },
//		 }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
		Map<String, Integer> itemAndQuantityMap = Map.of("Stuffed Frog",2, "Fluffy Bunny", 5, "Valentine Bear", 3);
		System.out.println(Thread.currentThread().getId() + " : " +DriverFactory.getDriver() + " : RunTest4");
		shopPage = mainTabs.clickOnShopTab();
		System.out.println("Navigated to shop page");
		itemAndPriceMap.put("Stuffed Frog", shopPage.getPriceForSpecificItem("Stuffed Frog"));
		itemAndPriceMap.put("Fluffy Bunny", shopPage.getPriceForSpecificItem("Fluffy Bunny"));
		itemAndPriceMap.put("Valentine Bear", shopPage.getPriceForSpecificItem("Valentine Bear"));
		for(Map.Entry<String, Integer> entry : itemAndQuantityMap.entrySet())
		{
			String itemName = entry.getKey();
			int quantity = entry.getValue();
			for(int counter = 0;counter < quantity;counter++) {
				shopPage.clickOnBuyButtonForSpecificItem(itemName);
			}
		}
		cartPage = mainTabs.clickOnCartIcon();
		double totalPrice = 0;
		for(Map.Entry<String, String> entry : itemAndPriceMap.entrySet())
		{
			cartPage.verifyItemNameAndPriceInCart(entry.getKey(), entry.getValue());
			totalPrice += cartPage.verifyItemNameAndTotalPriceOfEachItemInCart(entry.getKey(), entry.getValue(), itemAndQuantityMap.get(entry.getKey()));
		}
		cartPage.verifyTotalPriceOfAllProducts(totalPrice);
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		DriverFactory.closeBrowser();
	}
}
