package com.ads.common.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;



/**
 * This is base Test class for all the tests for RH site
 * 
 * @author mhonde
 */

public class BaseTest {
	public static WebDriver driver;
	protected String webUrl;
	protected Properties properties;
	protected Properties dataProperties;
	String dataFile;
	//protected List<WebElement> orderEmails =null;
	
	public BaseTest() {
		String file = System.getProperty("user.dir") + "/src/test/resources/envconfig.properties";
		File fis = new File(file);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		properties = new Properties();
		try {
			properties.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String browser = properties.getProperty("browser");
		driver = DriverUtility.loadDriver(browser);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void dataSetup() {
		dataFile = System.getProperty("user.dir") + "/src/test/resources/testdata.properties";
		File fis = new File(dataFile);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dataProperties = new Properties();
		try {
			dataProperties.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePropertiesFile(String newPassWord) throws IOException {
		FileOutputStream out;
		dataFile = System.getProperty("user.dir") + "/src/test/resources/testdata.properties";
		try {
			out = new FileOutputStream(dataFile);
			dataProperties.setProperty("passWord", newPassWord);
			dataProperties.store(out, null);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method will setup base url for the application.
	 * 
	 * 
	 */
	public void getWebUrl() {
		driver.get(properties.getProperty("baseurl"));
	}
	

}