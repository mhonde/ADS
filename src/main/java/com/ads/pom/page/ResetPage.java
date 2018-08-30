package com.ads.pom.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class ResetPage extends BasePage	{

	static Logger logger = Logger.getLogger(ResetPage.class);

	public ResetPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div.container > div > div > div.reset-pwd-component-container > div > input")
	WebElement email;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div.container > div > div > div.reset-pwd-component-container > button")
	WebElement send;
	

	public void sendEmailForPasswordReset( String emailId) {
		email.sendKeys(emailId);
		send.click();
		
	}
	}

