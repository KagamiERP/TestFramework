package com.kagami.studio;

import java.io.File;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import TestingFrameWork.AutomationTestFramework.ExtentManager;
import TestingFrameWork.AutomationTestFramework.GenericMethods;


public class StudioCommonMethods {

	private Logger log = Logger.getLogger("StudioCommonMethods.class");
	private WebDriverWait wait;
	private WebElement element;
	private String errorMsg;
	ExtentReports extent;
	GenericMethods genericMethods = new GenericMethods();
	By createVerb = By.xpath("//p[text()='CREATE']");
	By deleteVerb = By.xpath("//p[text()='DELETE']");
	By selectEntityButton = By.xpath("//span[@aria-label='Select box activate']");
	By selectEntityTextArea = By.xpath("//input[contains(@placeholder,'Select a entity in the list')]");
	By nextButton = By.xpath("//button[text()='Next']");
	By saveButton = By.xpath("//button[@ng-click='save()']");
	By closeIcon = By.xpath("//button[@class='close']");
	int constructCount = 1;
	ExtentTest test;
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
	By inputSearchBoxRoles = By.xpath("//span[text()='Select or search a role in the list...']");
	By addRolesButton = By.xpath("//button[contains(text(),'Add')]");
	By saveRolesButton = By.xpath("//button[@ng-click='saveRoles()' and contains(text(),'Save')]");
	By editTabInView = By.xpath("//a[text()='Edit']");
	By inputTriggerName = By.id("trigger-text");
	By addTriggerButton = By.xpath("//button[@class='btn btn-success btn-sm' and @ng-click='addTrigger(vm.trigger)']");

	public StudioCommonMethods(WebDriver driver)
	{
		this.driver = driver;
	}

	public void studioLogin()
	{
		try{
			String expectedPageTitle = "KAGAMI STUDIO";
			extent = ExtentManager.Instance();
			extent.loadConfig(new File("C:\\extent\\config.xml"));
			test = extent.startTest("Login", "Login to Kagami Studio");
			genericMethods.enterText(driver, uname, "admin", test);
			genericMethods.enterText(driver, password, "admin", test);
			genericMethods.clickElement(driver, loginButton , test);
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(menuButton));
			//Thread.sleep(2000);
			String actualPageTitle = driver.getTitle();
			Assert.assertEquals(actualPageTitle, expectedPageTitle);
			test.log(LogStatus.PASS, "Login Successful");
		}


		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

		finally {
			extent.endTest(test);
			extent.flush();

		}
	}


	public boolean deleteExistingEntity(WebDriver driver, String entityNameValue) throws InterruptedException{
		try {
			driver.findElement(By.xpath("//input[starts-with(@placeholder,'Search Entity...')]")).clear();
			genericMethods.enterText(driver, By.xpath("//input[starts-with(@placeholder,'Search Entity...')]"), entityNameValue, test);
			Thread.sleep(1000);
			String actualEntityValue = genericMethods.getText(driver, By.xpath("//a[@class='cursor-pointer ellipis ng-binding']"), test);
			if(entityNameValue.equalsIgnoreCase(actualEntityValue)){
				System.out.println("Checking if");
				genericMethods.clickElement(driver, By.xpath("//a[@ng-click='deleteentity(entity)']/span[@class='glyphicon glyphicon-remove redtext']"), test);
				genericMethods.clickElement(driver, By.xpath("//button[text()='OK']"), test);
				genericMethods.clickElementByJsExecutor(driver, addNewEntityButton, test);
				genericMethods.clickElementByJsExecutor(driver, entityName, test);
				driver.findElement(By.xpath("//input[starts-with(@placeholder,'Search Entity...')]")).clear();
				log.info("Existing Entity deleted successfully");
				return true;
			} 
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}

	public void studioLogOut()
	{
		try{
			extent = ExtentManager.Instance();
			genericMethods.clickElement(driver, menuButton, test);
			genericMethods.waitForElementVisibility(driver, signOut, 20);
			genericMethods.clickElement(driver, signOut, test);
			test.log(LogStatus.PASS, "LogOut Successful");
		}

		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

		finally {
			extent.endTest(test);
			extent.flush();

		}
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

	public boolean mapRoles(WebDriver driver, String roleName){
		try{
			genericMethods.clickElement(driver, mapRolesButton, test);
			genericMethods.waitForElementVisibility(driver, mapRolesHeader, 10);
			genericMethods.clickElement(driver, searchBoxRoles, test);
			genericMethods.inputTextAndEnter(driver, inputSearchBoxRoles, roleName, test);
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

	public boolean create(WebDriver driver, ExtentTest test, String template, String entityName) throws InterruptedException {

		try{
			By formTemplate = By.xpath("//img[@alt='form']");
			By tabbedTemplate = By.xpath("//img[@alt='tabbed']");
			By pcEmbedForm = By.xpath("//img[@alt='pcEmbedForm']");

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


			else if(template.equalsIgnoreCase("tabbedTemplate"))
			{
				genericMethods.clickElement(driver, tabbedTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				genericMethods.clickElement(driver, saveButton, test);

			}

			else if(template.equalsIgnoreCase("pcEmbedForm"))
			{
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
			By viewVerb = By.xpath("//p[text()='VIEW']");
			By pChild = By.xpath("//b[text()='pChild']");
			By list = By.xpath("//b[text()='list']");
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
			By switchVerb = By.xpath("//p[text()='switch']");
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
			By emailVerb = By.xpath("//p[text()='EMAIL']");
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

	public boolean addIcon(WebDriver driver, ExtentTest test)
	{

		try{
			// By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");
			By plusIcon = By.xpath("//div[@class='step circle plus_step']"); 
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


}






