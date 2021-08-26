package pages;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import utilities.ElementUtils;
import utilities.Log;

public class JupiterContactPage extends ElementUtils {
	
	private int timeOut = 10;
	private int long_TimeOut = 20;
	
	private By header_Feedback=By.xpath("//strong[text()='We welcome your feedback']");
	private By btn_Submit=By.xpath("//a[text()='Submit']");
	private By error_msgs = By.xpath("//span[contains(@id,'err')]");
	private By textBox_Forename = By.cssSelector("input[id='forename']");
	private By textBox_Email = By.cssSelector("input[id='email']");
	private By textArea_Email = By.cssSelector("textArea[id='message']");
	private By successAlert = By.xpath("//div[contains(@class,'alert-success')]");
//	private By btn_Back = By.xpath("//a[contains(text(),'Back')]");
	
	public void verifyFeedbackHeaderDisplayed() throws Exception {
		verifyElementVisible(header_Feedback, timeOut, "Feedback header is not displayed on Contact Page");
		Log.info("Verified header is available on Contact Page");
	}
	
	public void clickOnSubmitButton() throws Exception {
		clickOnElement(btn_Submit, timeOut, "Could not click on Submit button");
		Log.info("Clicked on Submit button");
	}
	
	public void validateErrors(List<String> expectedErrorMessagesList) throws Exception {
		List<String> errorMessageList = getTextList(error_msgs, timeOut, "Could not retrieve text for the elements");
		Collections.sort(expectedErrorMessagesList);
		Collections.sort(errorMessageList);
		Assert.assertTrue(expectedErrorMessagesList.equals(errorMessageList), "Expected and actual error messages do not match. Expected = "+expectedErrorMessagesList+" but found = "+errorMessageList);
		Log.info("Expected and actual error messages match");
	}
	
	private void enterValueForForeNameTextBox(String foreNameValue) throws Exception {
		enterValueForTextBox(textBox_Forename, foreNameValue, timeOut, "Could not enter text for text box Forename");
		Log.info("Entered text in Forename text box as "+foreNameValue);
	}
	
	private void enterValueForEmailTextBox(String emailValue) throws Exception {
		enterValueForTextBox(textBox_Email, emailValue, timeOut, "Could not enter text for text box Email");
		Log.info("Entered text in Email text box as "+emailValue);
	}
	
	private void enterValueForMessageTextBox(String messageValue) throws Exception {
		enterValueForTextBox(textArea_Email, messageValue, timeOut, "Could not enter text for text box Email");
		Log.info("Entered text in Message text area as "+messageValue);
	}
	
	public void enterValuesForForenameEmailAndMessageFields(String foreNameValue, String emailValue, String messageValue) throws Exception {
		enterValueForForeNameTextBox(foreNameValue);
		enterValueForEmailTextBox(emailValue);
		enterValueForMessageTextBox(messageValue);
	}
	
	public void validateNoErrors() throws Exception {
		verifyElementNotDisplayed(error_msgs, timeOut, "There are still some field validation errors");
		Log.info("No more errors are displayed on the page");
	}
	
	public void verifySuccessAlertForFeedbackDisplayed() throws Exception {
		verifyElementVisible(successAlert, long_TimeOut, "Success msg for feedback is not displayed.");
		Log.info("Success msg for feedback is displayed");
	}
	
}

