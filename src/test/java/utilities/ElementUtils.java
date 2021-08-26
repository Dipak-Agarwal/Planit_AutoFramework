package utilities;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

public class ElementUtils {

	private WebDriver driver = DriverFactory.getDriver();
	
	/**
	 * Navigate to the url passed
	 * 
	 * @param url
	 * @throws Exception
	 */
	public void navigateToUrl(String url) throws Exception {
		driver.navigate().to(url);
	}

	/**
	 * Returns FluentWait<WebDriver> from driver
	 * 
	 * @param timeOut
	 * @param pollingInterval
	 * @return FluentWait<WebDriver>
	 * @throws Exception
	 */
	public FluentWait<WebDriver> getFluentWaitFromDriver(int timeOut, int pollingInterval) throws Exception {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingInterval)).ignoring(NoSuchElementException.class);
		return wait;
	}

	/**
	 * Waits for element to be visible and then highlight it
	 * 
	 * @param element
	 * @param timeOut
	 * @throws Exception
	 */
	public void waitForElementToBeVisible(By element, int timeOut) throws Exception {
		try {
			FluentWait<WebDriver> wait = getFluentWaitFromDriver(timeOut, 1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			highlightElement(element);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * Verifies that the element is displayed or else throws assertion error
	 * 
	 * @param element
	 * @param timeOut
	 * @param failureMessage
	 */
	public void verifyElementVisible(By element, int timeOut, String failureMessage) {
		boolean displayed;
		try {
			waitForElementToBeVisible(element, timeOut);
			if (driver.findElement(element).isDisplayed()) {
				displayed = true;
				Assert.assertTrue(displayed);
			}
		} catch (Exception e) {
			displayed = false;
			Assert.assertTrue(displayed, failureMessage);
		}
	}
	
	public void clickOnElement(By element, int timeOut, String failureMessage) throws Exception {
		try {
			waitForElementToBeVisible(element, timeOut);
			driver.findElement(element).click();
		} catch (Exception e) {
			throw new Exception(failureMessage + ". Reason : "+e.getMessage());
		}
	}
	
	public void enterValueForTextBox(By element, String value, int timeOut, String failureMessage) throws Exception {
		try {
			waitForElementToBeVisible(element, timeOut);
			driver.findElement(element).sendKeys(value);;
		} catch (Exception e) {
			throw new Exception(failureMessage + ". Reason : "+e.getMessage());
		}
	}
	
	public String getTextFromElement(By element, int timeOut, String failureMessage) throws Exception {
		String text = null;
		try {
			waitForElementToBeVisible(element, timeOut);
			text = driver.findElement(element).getText();
		} catch (Exception e) {
			throw new Exception(failureMessage + ". Reason : "+e.getMessage());
		}
		return text;
	}
	
	public List<WebElement> retrieveElements(By element, int timeOut, String failureMessage) throws Exception {
		List<WebElement> elementList;
		try {
			waitForElementToBeVisible(element, timeOut);
			elementList = driver.findElements(element);
		} catch (Exception e) {
			throw new Exception(failureMessage + ". Reason : "+e.getMessage());
		}
		return elementList;
	}
	
	public List<String> getTextList(By element, int timeOut, String failureMessage) throws Exception {
		List<String> textList = new ArrayList<>();
		try {
			List<WebElement> elementList = retrieveElements(element, timeOut, "Could not retrieve elements List");
			for(WebElement elem : elementList) {
				textList.add(elem.getText()); 
			}
		} catch (Exception e) {
			throw new Exception(failureMessage + ". Reason : "+e.getMessage());
		}
		return textList;
	}

	/**
	 * Waits for element to become invisible
	 * 
	 * @param element
	 * @param timeOut
	 * @throws Exception
	 */
	public void waitForElementToBeInvisible(By element, int timeOut) throws Exception {
		try {
			FluentWait<WebDriver> wait = getFluentWaitFromDriver(timeOut, 1);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
//			 highlightElement(element);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * Verifies that the element is not displayed or else throws assertion error
	 * 
	 * @param element
	 * @param timeOut
	 * @param failureMessage
	 */
	public void verifyElementInvisible(By element, int timeOut, String failureMessage) {
		boolean displayed;
		try {
			waitForElementToBeInvisible(element, timeOut);
			if (driver.findElement(element).isDisplayed()) {
				displayed = true;
				Assert.assertFalse(displayed, failureMessage);
			}
		} catch (Exception e) {
			displayed = false;
			Assert.assertFalse(displayed);
		}
	}
	
	public void verifyElementNotDisplayed(By element, int timeOut, String failureMessage) {
		boolean displayed;
		try {
			if (driver.findElement(element).isDisplayed()) {
				displayed = true;
				Assert.assertFalse(displayed, failureMessage);
			}
		} catch (Exception e) {
			displayed = false;
			Assert.assertFalse(displayed);
		}
	}

	/**
	 * Highlights the element on the web page
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void highlightElement(By element) throws Exception {
		sleep(1000);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].setAttribute('style', 'background: yellow; border: 3px solid red;');",
				driver.findElement(element));
	}

	/**
	 * Sleeps for the specified time in millis
	 * 
	 * @param time
	 * @throws Exception
	 */
	public void sleep(long time) throws Exception {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * Captures screenshot of the visible area when called
	 * 
	 * @param fileName
	 * @return path of the screenshot along with the filename
	 */
	public String captureScreenshot(String fileName) {

		String pathOfScreenShot = null;
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			pathOfScreenShot = System.getProperty("user.dir") + "\\Screenshot\\" + fileName + ".png";
			FileUtils.copyFile(scrFile, new File(pathOfScreenShot));
		} catch (Exception e) {
			System.out.println("Screenshot Failed " + e.getMessage());
		}
		return pathOfScreenShot;
	}

	/**
	 * Quits/Closes all the driver instances
	 */
	public void tearDown() {

		driver.quit();
		System.out.println("Browser Closed");
	}

	/**
	 * Scrolls till the bottom of the page
	 */
	public void scrollDownTillEnd() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

}
