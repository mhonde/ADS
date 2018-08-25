package com.ads.login.tests;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.ads.common.tests.BaseTest;
import com.ads.pom.page.DashBoardPage;
import com.ads.pom.page.LoginPage;
import com.ads.pom.page.RegisterPage;

import junit.framework.Assert;
public class ResetPasswordTest extends BaseTest {

	LoginPage loginPage =null;
	RegisterPage registerPage =null ;
	
	public ResetPasswordTest() {
		super();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registerPage =PageFactory.initElements(driver, RegisterPage.class);
	}

	Logger logger = Logger.getLogger(ResetPasswordTest.class);
	
	@Test
	public void loginAndResetPassWord() {
		getWebUrl();
		loginPage = registerPage.getLoginPageForExistingAcct();
		dataSetup();
		DashBoardPage dashBoardPage = loginPage.enterLoginDetails(dataProperties.getProperty("emailId"),
										dataProperties.getProperty("passWord"));
		if(dashBoardPage != null) {
			
		//	Assert.assertTrue("Successfully login", driver.getPageSource().contains("MY SERVICES"));
			logger.info("User able to login to account to reset password" );
		}
		System.out.println("Current password is  " + dataProperties.getProperty("passWord"));
		String newPassword =dashBoardPage.ResetPassWord(dataProperties.getProperty("passWord"));
		logger.info("new Password is set for next use");
		try {
			updatePropertiesFile(newPassword);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dashBoardPage.LogOut();
	}

	@Test
	public void resetToIncorrectPassWord() throws InterruptedException {
		String subWindowHandler = null;
		getWebUrl();
		loginPage = registerPage.getLoginPageForExistingAcct();
		dataSetup();
		DashBoardPage dashBoardPage = loginPage.enterLoginDetails(dataProperties.getProperty("emailId"),
										dataProperties.getProperty("passWord"));
		if(dashBoardPage != null) {
			
		//	Assert.assertTrue("Successfully login", driver.getPageSource().contains("MY SERVICES"));
			logger.info("User able to login to account to reset password" );
		}
		System.out.println("Current password is  " + dataProperties.getProperty("passWord"));
		String wrongPassword = dashBoardPage.ResetIncorrectPassWord(dataProperties.getProperty("passWord"));
		
		if ( wrongPassword == null ) {
			Set<String> handles = driver.getWindowHandles(); // get all window handles
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()){
			    subWindowHandler = iterator.next();
			}
			driver.switchTo().window(subWindowHandler); // switch to popup window
			
		}
		if (driver.getPageSource().contains("You need to match both fields.")) {
			
			Assert.assertTrue("Confirm Password is wrong", true);
			logger.assertLog(true, "You need to match both fields.");
		}
		logger.info("confirm Password is not matched with new password");
		
		dashBoardPage.LogOut();
	}
	@After
	public void closeBrowser() {
		driver.close();
	}
	
}
