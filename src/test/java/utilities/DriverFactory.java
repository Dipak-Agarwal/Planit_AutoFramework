package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	
	/**
	 * Using ThreadLocal<WebDriver> to achieve parallel execution
	 */
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	
	/**
	 * Sets and returns the respective headless driver instance as per the browser
	 * @param browser
	 * @return driver instance
	 */
	public static WebDriver init_driver(String browser) {
		
		System.out.println("Browser value is : "+browser);
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
//			options.setPageLoadStrategy(PageLoadStrategy.NONE);
//			options.addArguments("headless");
			tlDriver.set(new ChromeDriver(options));
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
//			DesiredCapabilities cap = new DesiredCapabilities();
			FirefoxOptions options = new FirefoxOptions();
//			options.setPageLoadStrategy(PageLoadStrategy.NONE);
//			cap.setCapability("marionette", true);
//			options.addArguments("headless");
//			options.setHeadless(true);
//			options.merge(cap);
			tlDriver.set(new FirefoxDriver(options));
//			tlDriver.set(new FirefoxDriver());
		}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
//			options.setPageLoadStrategy(PageLoadStrategy.NONE);
//			options.setCapability("UseChromium", true);
//			options.addArguments("headless");
			tlDriver.set(new EdgeDriver(options));
		}
		else {
			System.out.println("Please pass the correct browser value : "+browser);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return getDriver();
	}
	
	/**
	 * Returns driver instance set earlier
	 * @return driver instance from ThreadLocal
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/**
	 * Closes the driver instance and removes it from the ThreadLocal
	 */
	public static void closeBrowser() {
		tlDriver.get().close();
		tlDriver.remove();
	}
}
