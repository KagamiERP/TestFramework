package com.kagami.studio;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BuildDeploy 
{
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	public Logger log = Logger.getLogger("BuildDeploy.class");
	//StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);

	By menuButton = By.xpath("//button[contains(text(),'Menu')]");
	By generateButton=By.xpath("//ul/li/a[contains(text(),'Generate')]");
	By contractSuccessMsg=By.xpath("//div/div[contains(text(),'Contract Generated Successfully')]");
	By buildSelect=By.xpath("//div/select/option[contains(text(),'192.168.1.55')]");
	By buildButton=By.xpath("//div/button[contains(text(),'Build')]");
	By buildSuccessMsg=By.xpath("//div/div[contains(text(),'Build Completed')]");
	By deploySelect=By.xpath("//div/select/option[contains(text(),'192.168.1.55')]");
	By deployButton=By.xpath("//div/button[contains(text(),'Deploy')]");
	By deploySuccessMsg=By.xpath("//div/div[contains(text(),'Deployment Completed and App')]");
	By testButton=By.xpath("//button[contains(text(),'Test')]");
	By subMenu = By.xpath("//button[contains(text(),'Menu')]");
	By projectLink = By.xpath("//span[@class='ng-binding']");
	
	
	public BuildDeploy(WebDriver driver)
	{
		this.driver = driver;
	}

	public void generateTargetApp(ExtentTest test)
	 {
	  try
	  {
	    WebDriverWait wait = new WebDriverWait(driver,240);
	//   driver.navigate().back();
	   genericMethods.clickElement(driver, projectLink, test);
	   genericMethods.clickElement(driver, subMenu, test);
	   genericMethods.clickElement(driver, generateButton, test);
	   wait.until(ExpectedConditions.visibilityOfElementLocated(contractSuccessMsg));
	   genericMethods.selectByVisibleText(driver, By.xpath("//select[@ng-options='buildServer as buildServer.name for buildServer in buildServers']"), "192.168.1.55", test);
	   genericMethods.clickElement(driver, buildButton, test);
	   studioCommonMethods.fluentWait(driver, buildSuccessMsg, 30, 500);
	   genericMethods.selectByVisibleText(driver, By.xpath("//select[@ng-options='deployServer as deployServer.name for deployServer in deployServers']"), "192.168.1.55", test);
	   genericMethods.clickElement(driver, deployButton, test);
	   studioCommonMethods.fluentWait(driver, deploySuccessMsg, 30, 500);
	   genericMethods.clickElement(driver, testButton, test);
	   test.log(LogStatus.PASS, "Contract is generated successfully ");  
	  }
		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}

}
