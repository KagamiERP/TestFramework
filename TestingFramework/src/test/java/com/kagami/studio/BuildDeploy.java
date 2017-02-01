package com.kagami.studio;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BuildDeploy implements GlobalXpath
{
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	public Logger log = Logger.getLogger("BuildDeploy.class");
	By menuIcon = By.xpath("//span[@class='fa fa-cog']");
	By signOut = By.xpath("//a[text()='Sign Out']");
	By pageTitle = By.xpath("//title[text()='::KAGAMI STUDIO::']");
	public BuildDeploy(WebDriver driver)
	{
		this.driver = driver;
	}

	public void generateTargetApp(ExtentTest test)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,140);
			genericMethods.clickElement(driver, projectLink, test);
			genericMethods.clickElement(driver, subMenu, test);
			genericMethods.clickElement(driver, generateButton, test);
			wait.until(ExpectedConditions.visibilityOfElementLocated(contractSuccessMsg));
			genericMethods.selectByVisibleText(driver, By.xpath("//select[@ng-options='buildServer as buildServer.name for buildServer in buildServers']"), "QA", test);
			genericMethods.clickElement(driver, buildButton, test);
			studioCommonMethods.fluentWait(driver, buildSuccessMsg, 8, 300);
			test.log(LogStatus.PASS, "Build is generated successfully.");  

			if(driver.getPageSource().contains("Failed")){
				test.log(LogStatus.FAIL, "Contract generation is Failed");  
				test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
				genericMethods.clickElement(driver,closeButtonDeployment,test);
				Thread.sleep(1000);
				System.out.println("StudioLogout0");	
				genericMethods.clickElement(driver, menuIcon, test);
				System.out.println("StudioLogout1");
				genericMethods.clickElement(driver, signOut, test);
				System.out.println("StudioLogout2");
				test.log(LogStatus.PASS, "Logout Successful.");
			}

			else{
				genericMethods.selectByVisibleText(driver, By.xpath("//select[@ng-options='deployServer as deployServer.name for deployServer in deployServers']"), "QA", test);
				genericMethods.clickElement(driver, deployButton, test);
				test.log(LogStatus.PASS, "Build is deployed on the server.");  
				studioCommonMethods.fluentWait(driver, deploySuccessMsg, 8, 300);
				genericMethods.clickElement(driver, testButton, test);
				Thread.sleep(1000);
				//		driver.switchTo().defaultContent();
				Thread.sleep(1000);
				test.log(LogStatus.PASS, "Target app is generated successfully.");  
				genericMethods.clickElement(driver,closeButtonDeployment,test);
				driver.switchTo().defaultContent();
				System.out.println("Switched to default window");	
				//	genericMethods.clickElement(driver, menuIcon, test);
				if(driver.findElement(pageTitle).isDisplayed()){
					System.out.println("Moved to default window : Kagami title is visible");
				}
				genericMethods.clickElementByJsExecutor(driver, menuIcon, test);
				genericMethods.clickElementByJsExecutor(driver, signOut, test);
				System.out.println("StudioLogout2");
				test.log(LogStatus.PASS, "Logout Successful.");

			}




		}
		catch(Exception e)
		{
			//	test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			//	test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}

}
