package com.kagami.testconfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.kagami.library.Global;
public class BrowserSelection {
	// static	int urlCount = 0;

	public WebDriver browserType(WebDriver driver, String browserName) throws EncryptedDocumentException, InvalidFormatException, IOException, AddressException, InterruptedException, MessagingException
	{
		if(browserName.equalsIgnoreCase("firefox") )
		{
			System.setProperty("webdriver.firefox.bin","C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		//	ProfilesIni allProfiles = new ProfilesIni();
		//	FirefoxProfile myProfile = allProfiles.getProfile("default");
		//	myProfile.setAcceptUntrustedCertificates(true);
		//	myProfile.setAssumeUntrustedCertificateIssuer(true);
		//	myProfile.setPreference("webdriver.log.file", "c:\\temp\\firefox.log");
			driver = new FirefoxDriver();
		
		}

		else if(browserName.equalsIgnoreCase("chrome"))
		{
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("start-maximized");
			System.setProperty("webdriver.chrome.driver",Global.sChormeDriverPath);
			driver = new ChromeDriver(options);
		}
		else
		{
			System.setProperty("webdriver.ie.driver",Global.sIEDriverPath);
			driver = new InternetExplorerDriver();
		}

		//String url = Global.sUrlArray[urlCount++];
		String url = Global.sUrl;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.get(url);
		Thread.sleep(1000);

		//	Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		//	String browserType = cap.getBrowserName();
		//	String browserVersion = cap.getVersion();
		//	String os = System.getProperty("os.name").toLowerCase();
		//	postsuite(browserType, browserVersion, os, url);
		return driver;

	}


}
