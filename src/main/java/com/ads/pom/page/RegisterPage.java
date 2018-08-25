package com.ads.pom.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.ads.login.tests.ResetPasswordTest;

import junit.framework.Assert;

public class RegisterPage extends BasePage {

	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > div.field-container > input" ) 
	WebElement accountNumber;

	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > button" ) 
	WebElement Next;

	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > section > div.field-container > input" ) 
	WebElement customerName;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > section > div.field-row > div:nth-child(1) > input" ) 
	WebElement emailId;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > section > div.field-row > div:nth-child(2) > input" ) 
	WebElement phoneNumber;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > button" ) 
	WebElement registerButton;
	
	@FindBy( how = How.CSS, using ="body > div:nth-child(4) > div > div > button.styles_closeButton__20ID4 > svg")
	WebElement closePopUpWindow;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > section > div > div > div.login-link > a")
	WebElement alreadyAccount;
	
	@FindBy( how = How.CSS, using ="body > div:nth-child(4) > div > div > button.styles_closeButton__20ID4 > svg")
	WebElement invalidAccountPopUp;
	
	Logger logger = Logger.getLogger(RegisterPage.class);
	public RegisterPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	public RegisterPage enterAccountNumber(String actNumber )  {
		accountNumber.sendKeys(actNumber);
		Next.click();
		
		try {
			if(invalidAccountPopUp.isDisplayed() ) {
				logger.assertLog(true, "Account is not Valid");
				invalidAccountPopUp.click();
				return null;
			}else{
				return PageFactory.initElements(driver, RegisterPage.class);
			  }
			} catch (Exception e) {
				return PageFactory.initElements(driver, RegisterPage.class);

			}
		
	}

	public RegisterPage enterCustomerDetails(String Name, String email, String phoneNo )  {
		customerName.sendKeys(Name);
		emailId.sendKeys(email);
		phoneNumber.sendKeys(phoneNo);
		registerButton.click();
		
		try {
			if(closePopUpWindow.isDisplayed() ) {
				logger.assertLog(true, "Login already exist");
				closePopUpWindow.click();
				return null;
			}else{
				return PageFactory.initElements(driver, RegisterPage.class);
			  }
			} catch (Exception e) {
				return PageFactory.initElements(driver, RegisterPage.class);

			}
		
		
	}
	public LoginPage getLoginPageForExistingAcct() {
		alreadyAccount.click();
		return PageFactory.initElements(driver, LoginPage.class);
	}
}
