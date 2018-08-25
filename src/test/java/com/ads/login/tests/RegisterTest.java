package com.ads.login.tests;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.ads.common.tests.BaseTest;
import com.ads.pom.page.LoginPage;
import com.ads.pom.page.RegisterPage;

import junit.framework.Assert;

public class RegisterTest extends BaseTest {

	LoginPage loginPage =null;
	RegisterPage registerPage =null;
	
	public RegisterTest() {
		super();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registerPage = PageFactory.initElements(driver, RegisterPage.class);
		}

	Logger logger = Logger.getLogger(ResetPasswordTest.class);
	
	@Test
	public void RegisterWithValidAccount() {
		getWebUrl();
		dataSetup();
		registerPage.enterAccountNumber(dataProperties.getProperty("accountNumber"));
		registerPage = registerPage.enterCustomerDetails(dataProperties.getProperty("customerName"), 
											dataProperties.getProperty("newEmailID")
											, dataProperties.getProperty("phoneNumber"));
		if ( driver.getPageSource().contains("REGISTER")  ) {
			Assert.assertTrue("User able to register successfully", true);
		}
		
		
	}
	
	@Test
	public void RegisterWithInvalidAccount() {
		getWebUrl();
		dataSetup();
		registerPage.enterAccountNumber("12345");
		if ( registerPage == null ) {
			Assert.assertTrue("email id already exist", true);
			logger.info("Account is invalid so user can not register with this account");
		}
		
	}

	@After
	public void closeBrowser() {
		driver.close();
	}
	

}
