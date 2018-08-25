/*
 * Copyright (c) 2017 Restoration Hardware, Inc. All rights reserved.
 *
 */

package com.ads.common.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * Initializing and closing Web Driver
 * 
 * @author Restoration Hardware
 */
public final class DriverUtility {

	private final static Logger LOGGER = LoggerFactory.getLogger(DriverUtility.class);

	private static final Properties driverProperties =  new Properties();;
	
	
	/**
	 * Constructor
	 */
	private DriverUtility() {
		
	}
	
	private static void setupProperties() {
		InputStream dataInput;
		try {
			dataInput = new FileInputStream("src/test/resources/driverconfig.properties");
			driverProperties.load(dataInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Method to load properties according to mentioned Browser Name and Initialize
	 * Web Driver
	 * 
	 * @param browserName
	 *            -Name of Browser Mentioned in testng.xml
	 */
	public static WebDriver loadDriver(String browserName) {
		WebDriver webDriver = null;
		InputStream driverInput = null;
		setupProperties();
		try {
			webDriver = initDriver(browserName);
		} catch (Exception ex) {
			LOGGER.error("An Exception occurred!", ex);
		} finally {
			if (driverInput != null) {
				try {
					driverInput.close();
				} catch (IOException ex) {
					LOGGER.error("An Exception occurred!", ex);
				}
			}
		}
		webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		webDriver.manage().timeouts().setScriptTimeout(9000, TimeUnit.MILLISECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.MINUTES);
		return webDriver;
	}
	
	public static void performAction(WebElement element, WebDriver driver, String value)  {
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		WebElement el = myWaitVar.until(ExpectedConditions.elementToBeClickable(element));
		Actions actions = new Actions(driver);
		actions.moveToElement(el);
		actions.click();
		actions.sendKeys(value);
		actions.build().perform();
		
	}

	public static void performAction(WebElement element, WebDriver driver) {
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		WebElement el = myWaitVar.until(ExpectedConditions.elementToBeClickable(element));
		Actions actions = new Actions(driver);
		actions.moveToElement(el);
		actions.click();
		actions.build().perform();
		
	}
	public static void quitWebDriver(WebDriver webDriver) {
		webDriver.quit();
	}
	
	public void closeWebDriver(WebDriver webDriver) {
		webDriver.close();
	}

	/**
	 * To initialize Web Driver according to value of browserName provided in .xml
	 * 
	 * @param browserType
	 *            **Browser Name
	 */
	private static WebDriver initDriver(String browserType) {
		WebDriver webDriver;
		switch (browserType) {
		case "chrome":
			webDriver = initChromeDriver();
			break;
		case "firefox":
			webDriver = initFirefoxDriver();
			break;
		case "IE":
			webDriver = initIEDriver();
			break;
		case "HtmlUnit":
			webDriver = initHtmlUnitDriver();
			break;
		case "PhantomJS":
			webDriver = initPhantomJSDriver();
			break;
		default:
			LOGGER.info("browser: {} is invalid, Launching Chrome as browser of choice..", browserType);
			webDriver = initChromeDriver();
		}
		return webDriver;
	}

	/**
	 * To initialize Chrome Web Driver
	 * 
	 * @return chrome Web Driver
	 */
	private static WebDriver initChromeDriver() {
		String mode = System.getProperty("env.mode");
		if(mode == null) {
			mode = "normal";
		}
		//String driverPath = System.getProperty("env.driverPath");
		String driverPath = driverProperties.getProperty("chromeDriverPath");
		System.setProperty(driverProperties.getProperty("chromewebdriver"), driverPath);
		ChromeOptions options = null;
		WebDriver webDriver = null;
		if (mode != null) {
			switch (mode) {
			case "normal":
				DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome() ;  
				//ChromeOptions chromeOptions = new ChromeOptions();
				//chromeOptions.setExperimentalOption(CapabilityType.ACCEPT_SSL_CERTS, true);
				handlSSLErr.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
				webDriver = new ChromeDriver (handlSSLErr);
				break;
			case "headless":
				options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--start-maximized");
				webDriver = new ChromeDriver(options);
				break;
			case "grid":
				String hubIPAddress = System.getProperty("env.hubIP");
				DesiredCapabilities dc = DesiredCapabilities.chrome();
				try {
					webDriver = new RemoteWebDriver(new URL("http://" + hubIPAddress + "/wd/hub"), dc);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e.getMessage());
				}
				break;
			case "incognito":
				options = new ChromeOptions();
				options.addArguments("--incognito");
				webDriver = new ChromeDriver(options);
				webDriver.manage().window().maximize();
				break;
			default:
				webDriver = new ChromeDriver();
				break;
			}
		}
		return webDriver;
	}

	/**
	 * To initialize Firefox Web Driver
	 * 
	 * @return Firefox Web Driver
	 */
	private static WebDriver initFirefoxDriver() {
		WebDriver webDriver = null;
		String driverPath = driverProperties.getProperty("firfoxDriverPath");
		System.setProperty(driverProperties.getProperty("firefoxwebdriver"), driverPath);
		webDriver = new FirefoxDriver();
		webDriver.manage().window().maximize();
		return webDriver;
	}

	/**
	 * To initialize IE Web Driver
	 * 
	 * @return IE Web Driver
	 */
	private static WebDriver initIEDriver() {
		WebDriver webDriver = null;
		String driverPath = System.getProperty("env.driverPath");
		System.setProperty(driverProperties.getProperty("iewebdriver"), driverPath);
		webDriver = new FirefoxDriver();
		webDriver.manage().window().maximize();
		return webDriver;
	}

	/**
	 * To initialize Headless HtmlUnit Driver
	 * 
	 * @return HtmlUnit Driver
	 */
	private static WebDriver initHtmlUnitDriver() {
		WebDriver webDriver = null;
		webDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
		return webDriver;
	}

	/**
	 * To initialize Headless PhantomJS Driver
	 * 
	 * @return PhantomJS Driver
	 */
	private static WebDriver initPhantomJSDriver() {
		WebDriver webDriver = null;
		String driverPath = System.getProperty("env.driverPath");
		System.setProperty(driverProperties.getProperty("phantomJSDriver"), driverPath);
		DesiredCapabilities cap = DesiredCapabilities.phantomjs();
		cap.setCapability("Platform", Platform.ANY);
		webDriver = new PhantomJSDriver(cap);
		return webDriver;
	}
}
