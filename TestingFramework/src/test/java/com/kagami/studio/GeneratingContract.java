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
public class GeneratingContract 
{
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("GeneratingContract.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	
	By generateButton=By.xpath("//div/button[contains(text(),'Generate')]");
	By contractSucessMsg=By.xpath("//div/div[@class='ng-binding' and contains(text(),'Contract Generated Successfully')]");
	By buildButton=By.xpath("//div/button[contains(text(),'Build')]");
	By buildSucess=By.xpath("//div/div[contains(text(),'Build Completed ')]");
	By deployButton=By.xpath("//div/button[contains(text(),'Deploy')]");
	By deploySucess=By.xpath("//div/div[contains(text(),'Deployment Completed and App is available at')]");
	By testButton=By.xpath("//button[contains(text(),'Test')]");
	By userIdTarget=By.xpath("//div/label/following-sibling::input[@id='username']");
	By pswdTarget=By.xpath("//div/label/following-sibling::input[@id='password']");
	public void BuildDeploy()
	{
		try{
		//Build and Deploy
				genericMethods.clickElement(driver, generateButton, test);
				WebDriverWait wait=new WebDriverWait(driver, 200);
				wait.until(ExpectedConditions.visibilityOfElementLocated(contractSucessMsg));
				if(genericMethods.ElementVisibility(driver, contractSucessMsg, test))
				{
					String contractSucess=genericMethods.getText(driver, contractSucessMsg, test);
					System.out.println(contractSucess);
					By buildDropdown=By.xpath("//select[@class='form-control ng-pristine ng-untouched ng-valid ng-empty']");
					genericMethods.selectByVisibleText(driver, buildDropdown, "192.168.1.55", test);
					genericMethods.clickElement(driver, buildButton, test);
					//WebDriverWait wait=new WebDriverWait(driver, 200);
					wait.until(ExpectedConditions.visibilityOfElementLocated(buildSucess));
					genericMethods.getText(driver, buildSucess, test);
					By deployDropdown=By.xpath("//div/select[@class='form-control ng-pristine ng-untouched ng-valid ng-empty']");
					genericMethods.selectByVisibleText(driver, deployDropdown, "192.168.1.55", test);
					genericMethods.clickElement(driver, deployButton, test);
					wait.until(ExpectedConditions.visibilityOfElementLocated(deploySucess));
					genericMethods.clickElement(driver, testButton, test);			    		    
				 }
		    
		    test.log(LogStatus.PASS, "Target App Generated");
		}
		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}
}
