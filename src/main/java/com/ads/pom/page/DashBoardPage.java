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
public class DashBoardPage extends BasePage	{

	static Logger logger = Logger.getLogger(DashBoardPage.class);

	public DashBoardPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > nav > div.center > div > div.item.settings > a" ) 
	WebElement settings;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > div.settings-container > div > div > div.col-xs-12.col-md-3.side-nav-container > div > a:nth-child(2)")
	WebElement changePassword;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > div.settings-container > div > div > div.col-xs-12.col-md-9 > section > div.row.no-gutters.old-pwd-row > div > div > input")
	WebElement currentPassword;

	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > div.settings-container > div > div > div.col-xs-12.col-md-9 > section > div.pwd-field-row.field-row.row.no-gutters.text-left > div.col-xs-12.col-md-5 > div:nth-child(1) > input")
	WebElement newPassword;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > div.settings-container > div > div > div.col-xs-12.col-md-9 > section > div.pwd-field-row.field-row.row.no-gutters.text-left > div.col-xs-12.col-md-5 > div:nth-child(2) > input")
	WebElement confirmPassword;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > div.settings-container > div > div > div.col-xs-12.col-md-9 > section > div.row.btn-row.text-left > div > button.btn.btn-primary.btn-set-pwd")
	WebElement save;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > nav > div.right > div.nav-menu-right.desktop > div > div.dropdown-label > svg")
	WebElement usrDropDown;
	
	@FindBy( how = How.CSS, using ="#root > div > div > div > div > div > nav > div.right > div.nav-menu-right.desktop > div > div.drop-content > div > p")
	WebElement logOut;
	
	@FindBy( how = How.CSS, using ="body > div:nth-child(4) > div > div > p")
	WebElement PreviousPwdMatchMsg;
	
	@FindBy( how = How.CSS, using ="body > div:nth-child(4) > div > div > button.btn.btn-primary")
	WebElement Dismiss;
	
	public String ResetPassWord(String passWord) {
		settings.click();
		changePassword.click();
		currentPassword.sendKeys(passWord);
		int numbers =Integer.parseInt(passWord.substring(passWord.indexOf("@")+1)) ;
		numbers ++;
		String newPassWd = passWord.substring(0, passWord.indexOf("@")+1) + numbers;
		newPassword.sendKeys(newPassWd);
		System.out.println("New password is  " +newPassWd);
		confirmPassword.sendKeys(newPassWd);
		save.click();
		return newPassWd;
		
	}
	
	public String ResetIncorrectPassWord(String passWord) throws InterruptedException {
		settings.click();
		changePassword.click();
		currentPassword.sendKeys(passWord);
		int numbers =Integer.parseInt(passWord.substring(passWord.indexOf("@")+1)) ;
		numbers ++;
		String newPassWd = passWord.substring(0, passWord.indexOf("@")+1) + numbers;
		newPassword.sendKeys(newPassWd);
		numbers++;
		newPassWd = passWord.substring(0, passWord.indexOf("@")+1) + numbers;
		confirmPassword.sendKeys(newPassWd);
		Thread.sleep(1000);
		save.click();
		return null;
	}
	
	public boolean ResetWithPreviousPassWord(String passWord, int previousPwdNum) throws InterruptedException {
		settings.click();
		changePassword.click();
		currentPassword.sendKeys(passWord);
		int numbers =Integer.parseInt(passWord.substring(passWord.indexOf("@")+1)) ;
		numbers = numbers-previousPwdNum;
		String newPassWd = passWord.substring(0, passWord.indexOf("@")+1) + numbers;
		newPassword.sendKeys(newPassWd);
		confirmPassword.sendKeys(newPassWd);
		Thread.sleep(1000);
		save.click();
		String subWindowHandler = null;
		Set<String> handles = driver.getWindowHandles(); // get all window handles
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()){
		    subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler);
		try {
		if ( PreviousPwdMatchMsg.getText().contains("New password needs to be different from the previous 12 passwords.")) {
			Dismiss.click();
			return true;
		}else {
			return false;
		} }catch (Exception e) {
			return false;
		}
		
	}
	
	public void LogOut() {
		usrDropDown.click();
		logOut.click();
	}

	}

