package com.kagami.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class StudioCommonMethods {

	
	private Logger log = Logger.getLogger("StudioCommonMethods.class");
	private WebDriverWait wait;
	private WebElement element;
	private String errorMsg;
	ExtentReports extent;
	GenericMethods genericMethods = new GenericMethods();
	By createVerb = By.xpath("//p[contains(text(),'CREATE')]");
	By deleteVerb = By.xpath("//p[contains(text(),'DELETE')]");
	By selectEntityButton = By.xpath("//span[@aria-label='Select box activate']");
	By selectEntityTextArea = By.xpath("//input[contains(@placeholder,'Select a entity in the list')]");
	By nextButton = By.xpath("//button[text()='Next']");
	By saveButton = By.xpath("//button[@ng-click='save()']");
	By closeIcon = By.xpath("//button[@class='close']");
	int constructCount = 1;
	public ExtentTest test;
	public  WebDriver driver;
	String expectedHeader = "Confirm";
	

	//*** Xpath's for Studio LogIn ***//
	By uname = By.id("inputUsername");
	By password = By.id("inputPassword");
	By loginButton = By.xpath("//button[contains(text(),'SIGN IN')]");
	By menuButton = By.xpath("//a[contains(text(),'Menu')]");
	By addNewEntityButton = By.xpath("//a[@ng-click='createEntity()']");
	By deleteEntityPopupHeader = By.xpath("//div[@class='ajs-header']");
	By okButton = By.xpath("//button[text()='OK']");
	By deleteProjectOkButton = By.xpath("//div[text()='Delete']/following::button[text()='OK']");
	By deleteEntityIcon = By.xpath("//a[@ng-click='deleteentity(entity)']/span[@class='glyphicon glyphicon-remove redtext']");
	By signOut = By.xpath("//a[text()='Sign Out']");
	By signOutAlert = By.xpath("//div[@class='alert alert-success alert-signout']");
	By entityName = By.id("entityName");
	By saveOkButton = By.xpath("//div[text()='Save']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By deleteHeader = By.xpath("//div[text()='Delete']");
	By saveProcessButton = By.xpath("//button[@ng-click='saveProcess()']");
	By saveButtonHeader = By.xpath("//div[text()='Save']");
	By homeTab = By.xpath("//a[text()='Home']");
	By noButton = By.xpath("//button[text()='NO']");
	By mapOrgButton = By.xpath("//a[contains(text(),'Map Org')]");
	By mapOrgHeader = By.xpath("//div[contains(text(),'Map Organization')]");
	By searchBoxOrg = By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']");
	By inputSearchBoxOrg = By.xpath("//input[@placeholder='Select or search a Organization in the list...']");
	By saveOrg = By.xpath("//button[@ng-click='saveOrg()' and text()='save']");
	By mapRolesButton = By.xpath("//button[@class='btn btn-default' and @ng-click='associateRoles()']");
	By mapRolesHeader = By.xpath("//h4[text()='Map Roles']");
	By searchBoxRoles = By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']");
	By inputSearchBoxRoles = By.xpath("//input[@placeholder='Select or search a role in the list...']");
	By addRolesButton = By.xpath("//div[@class='input-group-btn']/button[@class='btn btn-default']");
	By saveRolesButton = By.xpath("//button[@ng-click='saveRoles()' and contains(text(),'Save')]");
	By editTabInView = By.xpath("//a[text()='Edit']");
	By inputTriggerName = By.id("trigger-text");
	By addTriggerButton = By.xpath("//button[@class='btn btn-success btn-sm' and @ng-click='addTrigger(vm.trigger)']");
	By closeButton = By.xpath("//button[text()='Close']");
	By deleteOrgOkButton = By.xpath("//div[text()='Delete Organization']//following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By loginErrorMsg = By.xpath("//div[@ng-show='error']/span[@class='sr-only']");
	By invalidUserName = By.xpath("//input[@id='inputUsername' and @class='form-control ng-touched ng-dirty ng-valid-parse ng-empty ng-invalid ng-invalid-required']");


	public StudioCommonMethods(WebDriver driver)
	{
		this.driver = driver;
	}

	/*public void studioLogin(ExtentTest test)
	{
		try{
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("studiologin");
			String[] rowElements = new String[50];
			int cellValue = 0;
			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				for(int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
				}

				String expectedPageTitle = "KAGAMI STUDIO";
				genericMethods.enterText(driver, uname, rowElements[cellValue++], test);
				genericMethods.enterText(driver, password,rowElements[cellValue++], test);
				genericMethods.clickElement(driver, loginButton , test);
				Thread.sleep(1000);
				boolean errorMsgText = driver.findElement(loginErrorMsg).isDisplayed();
				//boolean blankInputfound = driver.findElement(invalidUserName).isDisplayed();
				if(!errorMsgText)
				{	
					Thread.sleep(1500);
					String actualPageTitle = driver.getTitle();
					Assert.assertEquals(actualPageTitle, expectedPageTitle);
					test.log(LogStatus.PASS, "Login Sucessful.");
				}
				else if(errorMsgText){
					test.log(LogStatus.FAIL, "Invalid Username or Password entered.");
					test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
					//	Assert.assertFalse(errorMsgText);

				}		
				else if(errorMsgText && blankInputfound){

					test.log(LogStatus.FAIL, "Input is not given to the username or password field");
					test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
				}
			}
		}

		catch(NoSuchElementException e)
		{
			test.log(LogStatus.PASS, "Login Sucessful.");
		}
		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}*/


	public void studioLogin(ExtentTest test) throws InvalidFormatException, IOException, InterruptedException
	{

		String pathOfFile = Global.testSheet;
		File f = new File(pathOfFile);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("studiologin");
		String[] rowElements = new String[50];
		int cellValue = 0;
		for(int row = 1; row <= sheet.getLastRowNum(); row++){
			for(int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
				rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
			}

			String expectedPageTitle = "KAGAMI STUDIO";
			genericMethods.enterText(driver, uname, rowElements[cellValue++], test);
			genericMethods.enterText(driver, password,rowElements[cellValue++], test);
			genericMethods.clickElement(driver, loginButton , test);
			//	boolean errorMsgText = driver.findElement(loginErrorMsg).isDisplayed();
			Thread.sleep(2500);
			if(driver.findElement(menuButton).isDisplayed()){
				test.log(LogStatus.PASS, "Login Successful");
			}
			//boolean blankInputfound = driver.findElement(invalidUserName).isDisplayed();

			/*	try{

				if(!errorMsgText && rowElements[1].equals("admin"))
				{
					System.out.println("If Block");
					Thread.sleep(1500);
					//	String actualPageTitle = driver.getTitle();
					//	Assert.assertEquals(actualPageTitle, expectedPageTitle);
					test.log(LogStatus.PASS, "Login Sucessful.");
				}
				else if(errorMsgText){
					test.log(LogStatus.FAIL, "Invalid Username or Password entered.");
					test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
					//	Assert.assertFalse(errorMsgText);
				}		

				else{
					try {
						System.out.println("Sikuli Block");
						Screen screen = new Screen();
						Pattern p = new Pattern("TestData//loginPageScreenshot.jpg");
						Thread.sleep(1500);
						if(screen.find(p) != null){
							test.log(LogStatus.FAIL, "Input is not given to the username or password field");
							test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
						}
					} catch (FindFailed e) {
						e.printStackTrace();
					}
				}

			}



			catch(NoSuchElementException e)
			{
				e.printStackTrace();
				System.out.println("Catch Block");
				test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));

				//test.log(LogStatus.PASS, "Login Sucessful.");
			}

			catch(Exception e)
			{
				e.printStackTrace();
				test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
				test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			}*/
		}
	}



	public void studioLogOut(ExtentTest test)
	{
		try{
			genericMethods.clickElement(driver, menuButton, test);
			genericMethods.waitForElementVisibility(driver, signOut, 3);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, signOut, test);
			test.log(LogStatus.PASS, "Logout Successful.");
		}

		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}

	public boolean dismissAlertPopUp(WebDriver driver){
		  try{
		   Alert alert = driver.switchTo().alert();
		   String alertMessage = alert.getText();
		   alert.dismiss();
		   log.info("The alert with the text message "+alertMessage+ "has been dismissed successfully");
		   return true;
		  }
		  catch(NoAlertPresentException e){
		   errorMsg = e.getMessage();
		  }
		  log.warn("Unable to handle the alert pop-up because "+errorMsg);
		  return false;
		 }
	
	public void studioLogoutAndBack(ExtentTest test)
	{
		try{

			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("studiologin");
			String[] rowElements = new String[50];
			int cellValue = 0;
			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				for(int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
				}

				genericMethods.enterText(driver, uname, rowElements[cellValue++], test);
				genericMethods.enterText(driver, password,rowElements[cellValue++], test);
				genericMethods.clickElement(driver, loginButton , test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, menuButton, test);
				genericMethods.waitForElementVisibility(driver, signOut, 3);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, signOut, test);
				Thread.sleep(1000);
				driver.navigate().back();
				Thread.sleep(1000);
				boolean logoutStatus = driver.findElement(loginButton).isDisplayed();
				if(logoutStatus)
				{
					test.log(LogStatus.PASS, "SignIn option is displayed on clicking back button");
				}
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}

	public boolean deleteExistingEntity(WebDriver driver, String entityNameValue) throws InterruptedException{
		  try {
		   driver.findElement(By.xpath("//input[starts-with(@placeholder,'Search Entity')]")).clear();
		   genericMethods.enterText(driver, By.xpath("//input[starts-with(@placeholder,'Search Entity')]"), entityNameValue, test);
		   if(genericMethods.ElementVisibility(driver, By.xpath("//span[@title='"+entityNameValue+"']"), test)){
		    System.out.println("Checking if");
		    rightClickActions(driver, By.xpath("//ul[@class='treemenu_ul']//span[contains(text(),'"+entityNameValue+"')]"), test);
		    genericMethods.clickElement(driver, By.xpath("//a[text()='Delete Entity']"), test);
		    genericMethods.clickElement(driver, By.xpath("//div[text()='Confirm']/following::button[text()='OK']"), test);
		    driver.findElement(By.xpath("//input[starts-with(@placeholder,'Search Entity')]")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		    driver.findElement(By.xpath("//input[starts-with(@placeholder,'Search Entity')]")).sendKeys(Keys.BACK_SPACE);
		    log.info("Existing Entity deleted successfully");
		    return true;
		   } 
		  }
		  catch (NoSuchElementException e){
		   test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		   errorMsg = e.getMessage();
		  }
		  log.warn("The Element cannot be deleted because "+errorMsg);
		  return false;
		 }
	
	
	
	public void rightClickActions(WebDriver driver, By elementLocator, ExtentTest test)
	 {
	  //visibilityStatus = ElementVisibility(wDriver, elementLocator, test); 
	  try{
	   //highlightWebElement(wDriver, elementLocator, test);
	   WebElement element = driver.findElement(elementLocator);
	   Actions rightClick = new Actions(driver).contextClick(element);
	   rightClick.build().perform();
	   log.info("Context Click is performed on " + elementLocator+ " object.");
	  }

	  catch(NoSuchElementException e)
	  { 
	   errorMsg = e.getMessage();
	  }
	  log.warn("Element " + elementLocator + " was not found in DOM "+ errorMsg);
	 }
	

	public boolean deleteItem(WebDriver driver, String itemToDelete){
		By deleteItem = By.xpath("//h3[text()='"+itemToDelete+"']/parent::div/parent::a/parent::div//span[@class='glyphicon glyphicon-trash pull-right card-menu-item']");
		try{
			genericMethods.clickElement(driver, deleteItem, test);
			genericMethods.waitForElementVisibility(driver, deleteHeader, 20);
			genericMethods.clickElement(driver, okButton, test);
			log.info("Element has been deleted successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("The Element cannot be deleted because "+errorMsg);
		return false;
	}

	public boolean deleteExistingProject(WebDriver driver, String itemToDelete){
		By deleteItem = By.xpath("//h3[text()='"+itemToDelete+"']/parent::div/parent::a/parent::div//span[@class='glyphicon glyphicon-trash card-menu-item']");
		try{

			genericMethods.clickElement(driver, closeButton, test);
			genericMethods.clickElement(driver, deleteItem, test);
			genericMethods.waitForElementVisibility(driver, deleteHeader, 20);
			genericMethods.clickElement(driver, deleteProjectOkButton, test);
			log.info("Existing Project has been deleted successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("The Project cannot be deleted because "+errorMsg);
		return false;
	}




	public boolean saveProcess(WebDriver driver){

		try{
			genericMethods.clickElement(driver, saveProcessButton, test);
			genericMethods.waitForElementVisibility(driver, saveButtonHeader, 20);
			genericMethods.clickElement(driver, saveOkButton, test);
			log.info("Process has been saved successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("Process cannot be saved because "+errorMsg);
		return false;
	}

	public boolean discardProcess(WebDriver driver){
		try{
			genericMethods.clickElement(driver, homeTab, test);
			genericMethods.waitForElementVisibility(driver, saveButtonHeader, 20);
			genericMethods.clickElement(driver, noButton, test);
			log.info("Changes in Process are discarded successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("Changes in Process are not discarded "+errorMsg);
		return false;
	}

	public boolean mapOrg(WebDriver driver, String organizationName){
		try{
			genericMethods.waitForElementVisibility(driver, mapOrgButton, 10);
			genericMethods.clickElement(driver, mapOrgButton, test);
			genericMethods.waitForElementVisibility(driver, mapOrgHeader, 10);
			genericMethods.clickElement(driver, searchBoxOrg, test);
			genericMethods.inputTextAndEnter(driver, inputSearchBoxOrg, organizationName, test);
			genericMethods.clickElementByJsExecutor(driver, saveOrg, test);
			log.info("Organization has been selected and Mapped to the Project Succcessfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("Unable to select and Map the Organization because "+errorMsg);
		return false;
	}

	public boolean mapRoles(WebDriver driver, String roleName) throws InterruptedException{
		try{
			String role = roleName;
			genericMethods.clickElement(driver, mapRolesButton, test);
			genericMethods.waitForElementVisibility(driver, mapRolesHeader, 10);
			genericMethods.clickElement(driver, searchBoxRoles, test);
			Thread.sleep(1500);
			genericMethods.inputTextAndEnter(driver, inputSearchBoxRoles, role, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, addRolesButton, test);
			genericMethods.clickElement(driver, saveRolesButton, test);
			log.info("Roles are added and saved successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("Roles are not add and saved successfully "+errorMsg);
		return false;
	}

	
	
	public boolean addTriggers(WebDriver driver, String triggerName){
		try{
			genericMethods.clickElement(driver, editTabInView, test);
			genericMethods.enterText(driver, inputTriggerName, triggerName, test);
			genericMethods.clickElement(driver, addTriggerButton, test);
			log.info("Trigger added successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("Unable to add trigger successfully because "+errorMsg);
		return false;
	}


	public boolean deleteExistingOrganization(WebDriver driver, String organizationToDelete) throws InterruptedException{
		By deleteItem = By.xpath("//h3[text()='"+organizationToDelete+"']/parent::div/parent::a/parent::div//span[@class='glyphicon glyphicon-trash pull-right card-menu-item']");
		try{
			genericMethods.clickElement(driver, deleteItem, test);
			//	genericMethods.waitForElementVisibility(driver, deleteHeader, 5);
			Thread.sleep(2000);
			genericMethods.clickElement(driver, deleteOrgOkButton, test);
			log.info("Element has been deleted successfully");
			return true;
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("The Element cannot be deleted because "+errorMsg);
		return false;
	}

	public boolean create(WebDriver driver, ExtentTest test, String template, String entityName) throws InterruptedException {

		try{
			By formTemplate = By.xpath("//div[@class='card']/img[@alt='form']");
			By tabbedTemplate = By.xpath("//div[@class='card']/img[@alt='tabbed']");
			By pcEmbedForm = By.xpath("//div[@class='card']/img[@alt='pcEmbedForm']");
			By pcCreate = By.xpath("//div[@class='card']/img[@alt='pcCreate']");

			genericMethods.clickElement(driver, createVerb, test);
			//genericMethods.clickElement(driver, clickConstruct, test);
			constructCounter(driver, test);

			if(template.equalsIgnoreCase("formTemplate"))
			{ 
				genericMethods.clickElement(driver, formTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				//genericMethods.clickElement(driver,closeIcon, test);
				genericMethods.clickElement(driver, saveButton, test);
			}


			else if(template.equalsIgnoreCase("tabbedTemplate")){
				genericMethods.clickElement(driver, tabbedTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				genericMethods.clickElement(driver, saveButton, test);

			}

			else if(template.equalsIgnoreCase("pcCreate")){	
				genericMethods.clickElement(driver, pcCreate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				genericMethods.clickElement(driver, saveButton, test);

			}
			else if(template.equalsIgnoreCase("pcEmbedForm")){
				genericMethods.clickElement(driver, pcEmbedForm, test);

			}

			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}


	public boolean delete(WebDriver driver, ExtentTest test , String entityName) throws InterruptedException {

		try{
			genericMethods.clickElement(driver, deleteVerb, test);
			//genericMethods.clickElement(driver, clickConstruct, test);
			constructCounter(driver, test);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			//genericMethods.clickElement(driver,closeIcon, test);
			genericMethods.clickElement(driver, saveButton, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}	




	public boolean view(WebDriver driver, ExtentTest test, String template, String entityName) throws InterruptedException {

		try{
			By viewVerb = By.xpath("//p[contains(text(),'VIEW')]");
			By pChild = By.xpath("//b[text()='pChild']");
			By list = By.xpath("//b[text()='list']");
			By search = By.xpath("//b[text()='search']");
			
			genericMethods.clickElement(driver, viewVerb, test);
			Thread.sleep(700);
			constructCounter(driver, test);
			if(template.equalsIgnoreCase("pChild"))
			{ 
				genericMethods.clickElement(driver, pChild, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				//genericMethods.clickElement(driver,closeIcon, test);
				genericMethods.clickElement(driver, saveButton, test);
			}
			else if(template.equalsIgnoreCase("list"))
			{
				genericMethods.clickElement(driver, list, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				//genericMethods.clickElement(driver,closeIcon, test);
				genericMethods.clickElement(driver, saveButton, test);

			}
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		finally {
			constructCount = 1;
		}
		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}



	public boolean Switch(WebDriver driver, ExtentTest test , String entityName) throws InterruptedException {

		try{
			By switchVerb = By.xpath("//p[contains(text(),'switch')]");
			genericMethods.clickElement(driver, switchVerb, test);
			Thread.sleep(700);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			//genericMethods.clickElement(driver,closeIcon, test);
			genericMethods.clickElement(driver, saveButton, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}





	public boolean email(WebDriver driver, ExtentTest test) {

		try{
			By emailVerb = By.xpath("//p[contains(text(),'EMAIL')]");
			genericMethods.clickElement(driver, emailVerb, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}


	public boolean link(WebDriver driver, ExtentTest test) {

		try{
			By linkVerb = By.xpath("//p[text()='LINK']");
			genericMethods.clickElement(driver, linkVerb, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}



	public boolean compute(WebDriver driver, ExtentTest test) {

		try{
			By computeVerb = By.xpath("//p[text()='COMPUTE']");
			genericMethods.clickElement(driver, computeVerb, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}

	public boolean addIcon(WebDriver driver, ExtentTest test) throws InterruptedException
	{

		try{
			// By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");
			By plusIcon = By.xpath("//div[@class='step circle plus_step']"); 
			Thread.sleep(1500);
			genericMethods.clickElement(driver, plusIcon, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}


	public boolean constructCounter(WebDriver driver, ExtentTest test)
	{
		try{
			By clickConstruct = By.xpath("//div[@data-position-left='"+(constructCount++)+"']");
			genericMethods.clickElement(driver, clickConstruct, test);
			return true;

		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
			log.warn("The Element cannot be clicked because "+errorMsg);
		}

		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}


	public void fluentWait(WebDriver driver, final By elementLocator, int pollingTime, int timeOut){
		try{
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.pollingEvery(pollingTime,  TimeUnit.SECONDS);
			wait.withTimeout(timeOut, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class); 
			Predicate<WebDriver> predicate = new Predicate<WebDriver>()
			{
				public boolean apply(WebDriver arg0) {
					System.out.println("Checking for the element!!");
					WebElement element = arg0.findElement(elementLocator);
					if(element != null)
					{
						System.out.println("Target element found");
						return true;
					}
					return false;
				}
			};

			wait.until(predicate);
		}

		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("The Target Element is not found because "+errorMsg);
		}

	}


	public boolean navigateToProject(WebDriver driver){
		try{

			if(genericMethods.ElementVisibility(driver, By.xpath("//li//span[@ng-bind='selected.parent.name']"), test)){
				genericMethods.clickElement(driver, By.xpath("//li//span[@ng-bind='selected.parent.name']"), test);
				return true;
			}
		}

		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
		return false;
	}

	public boolean navigateToModule(WebDriver driver){
		try{
			if(genericMethods.ElementVisibility(driver, By.xpath("//li//span[@ng-bind='selected.module.name']"), test)){
				genericMethods.clickElement(driver, By.xpath("//li//span[@ng-bind='selected.module.name']"), test);
				return true;
			}
		}

		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
		return false;
	}

	public boolean navigateToSubModule(WebDriver driver) throws InterruptedException{
		try{
			if(genericMethods.ElementVisibility(driver, By.xpath("//li//span[@ng-bind='selected.submodule.name']"), test)){
				genericMethods.clickElement(driver, By.xpath("//li//span[@ng-bind='selected.submodule.name']"), test);
				return true;
			}
		}

		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
		return false;
	}

}

