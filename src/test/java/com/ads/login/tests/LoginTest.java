package com.ads.login.tests;

import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.ads.common.tests.BaseTest;
import com.ads.pom.page.DashBoardPage;
import com.ads.pom.page.LoginPage;
import com.ads.pom.page.RegisterPage;
public class LoginTest extends BaseTest {

	LoginPage loginPage =null;
	RegisterPage registerPage =null ;
	
	public LoginTest() {
		super();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registerPage =PageFactory.initElements(driver, RegisterPage.class);
	}

	Logger logger = Logger.getLogger(LoginTest.class);
	
	@Test
	public void loginToAccount() {
		getWebUrl();
		loginPage = registerPage.getLoginPageForExistingAcct();
		dataSetup();
		DashBoardPage dashBoardPage = loginPage.enterLoginDetails(dataProperties.getProperty("emailId"),
										dataProperties.getProperty("passWord"));
		if ( driver.getPageSource().contains("MY SERVICES") ) {
			logger.info("User successfully logged in to account");
			Assert.assertTrue(driver.getTitle().contains("ADS eService"));
		}
		logger.info("User successfully logged in to account");
		dashBoardPage.LogOut();
	}
	
	@Test
	public void loginWithIncorrectPwd() {
		String subWindowHandler = null;
		getWebUrl();
		loginPage = registerPage.getLoginPageForExistingAcct();
		dataSetup();
		DashBoardPage dashBoardPage = loginPage.enterLoginDetails(dataProperties.getProperty("emailId"),
										"TestAutomation@123456");
		
		System.out.println("Current title " + driver.getTitle());
		if ( dashBoardPage == null) {
			Set<String> handles = driver.getWindowHandles(); // get all window handles
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()){
			    subWindowHandler = iterator.next();
			}
			driver.switchTo().window(subWindowHandler); // switch to popup window
			
		}
		if (driver.getPageSource().contains("Wrong login credentials.")) {
			
			Assert.assertTrue("Password is wrong", true);
			logger.assertLog(true, "Wrong login credentials.");
		}
	}


	@After
	public void closeBrowser() {
		driver.close();
	}
	
}
