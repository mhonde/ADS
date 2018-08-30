package com.ads.pom.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * This Test class is set for all the details on contract order payment jsp.
 * @author mhonde
 */
public class LoginPage extends BasePage	{

	static Logger logger = Logger.getLogger(LoginPage.class);

	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div.container > div > div.login-component-container > div.field-container.email > input" ) 
	WebElement emailId;

	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div.container > div > div.login-component-container > div.field-container.password > input" ) 
	WebElement passWord;
    
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div.container > div > div.login-component-container > button" ) 
	WebElement loginButton;
	
	@FindBy( how = How.CSS, using ="body > div:nth-child(4) > div > div > h2")
	WebElement wrongPasswordWindow;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div.container > div > div > div.forgot-pwd > a:nth-child(1)")
	WebElement forgotPasswordLink;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public DashBoardPage enterLoginDetails(String email, String pwd )  {
		emailId.sendKeys(email);
		passWord.sendKeys(pwd);
		loginButton.click();
		
		try {
			  if(wrongPasswordWindow.isDisplayed()){
				  return null;
			  }
			  else{
				  return PageFactory.initElements(driver, DashBoardPage.class);
			  }
			} catch (Exception e) {
				 return PageFactory.initElements(driver, DashBoardPage.class);

			}
	}

	public ResetPage clickForgotPasswordLink() {
		forgotPasswordLink.click();
		 return PageFactory.initElements(driver, ResetPage.class);
		
	}

}

