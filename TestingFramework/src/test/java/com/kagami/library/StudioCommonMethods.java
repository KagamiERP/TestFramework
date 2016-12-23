package com.kagami.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
	By updateVerb = By.xpath("//p[contains(text(),'UPDATE')]");
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

	By policiesButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/policies.png']");
	By businessValidation = By.xpath("//a[text()='Bussiness Validation']");
	By restrictionsButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/restrictions.png']");
	By pathButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/paths.png']");
	By createPath = By.xpath("//button[@ng-click='createPath()']");
	//By policyLHS = By.xpath("//div[@class='form-group lhs']//div[@class='ng-isolate-scope']//span[@ng-transclude='']/input[@ng-model='subCondition.lhs.value']");
	//By policyCondition = By.xpath("//div[@class='form-group']/select[@ng-model='subCondition.operator']");
	//By policyRHS = By.xpath("//span[@ng-transclude='']/input[@ng-model='subCondition.rhs.value']");


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
			if(driver.findElement(signOut).isDisplayed()){
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




	int createPolicyCount = 0;
	public boolean hasPolicy(WebDriver driver, ExtentTest test) throws InvalidFormatException, IOException, InterruptedException
	{
		System.out.println("Has Policies");
		genericMethods.clickElement(driver, policiesButton, test);
		applyRule(driver, test);
		String pathOfFile = Global.testSheet;
		File f = new File(pathOfFile);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> conditionMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;


		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Policies")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		List<String> conditionAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>3){
				conditionAllValue.add(val);
			}
			conditionMap.put(key,conditionAllValue);
		}


		List<String> conditionCellValue = null;
		Set<String> keySet = conditionMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			conditionCellValue = conditionMap.get(key1);
			System.out.println("Key1 is: "+key1+ "Value is "+conditionCellValue);
		}

		int policyRowInc = 1;
		String[] conditionSeparatedByAndValue = conditionCellValue.get(createPolicyCount++).split("and");
		for(String conditionSeparatedBySingleCondition : conditionSeparatedByAndValue){
			String[] conditionSeparatedByComma = conditionSeparatedBySingleCondition.split(",");
			By policyLHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//input[@ng-model='subCondition.lhs.value']");
			driver.findElement(policyLHS).sendKeys(new CharSequence[]{conditionSeparatedByComma[0]});
			Thread.sleep(1000);
			By policyCondition = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//div[@class='form-group']/select[@ng-model='subCondition.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);
			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);
			By policyRHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//span[@ng-transclude='']/input[@ng-model='subCondition.rhs.value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//button[@class='btn btn-primary btn-xs' and @ng-disabled='checkConditions()']");
			genericMethods.clickElement(driver, plusPolicy, test);
			policyRowInc++;
			Thread.sleep(1000);
		}
		return true;

	}


	int bValidationCount = 0;
	public boolean hasBusinessValidation(WebDriver driver, ExtentTest test) throws InvalidFormatException, IOException, InterruptedException
	{
		System.out.println("Has Business Validation");
		genericMethods.clickElement(driver, businessValidation, test);
//		applyRule(driver, test);
		String pathOfFile = Global.testSheet;
		File f = new File(pathOfFile);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> conditionMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;


		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Business Validation")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		List<String> conditionAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>3){
				conditionAllValue.add(val);
			}
			conditionMap.put(key,conditionAllValue);
		}


		List<String> conditionCellValue = null;
		Set<String> keySet = conditionMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			conditionCellValue = conditionMap.get(key1);
			System.out.println("Key1 is: "+key1+ " and Value is "+conditionCellValue);
		}

		int policyRowInc = 1;
		String[] conditionSeparatedByAndValue = conditionCellValue.get(bValidationCount++).split("and");
		for(String conditionSeparatedBySingleCondition : conditionSeparatedByAndValue){
			String[] conditionSeparatedByComma = conditionSeparatedBySingleCondition.split(",");
			System.out.println("Busin.....");
			Thread.sleep(1500);
			By policyLHS = By.xpath("//div[@class='childsubCondition ng-scope'][1]//input[@ng-model='subCondition.lhs.value']");
			boolean isDis = driver.findElement(policyLHS).isDisplayed();
			System.out.println("LHS is displayed : "+isDis);
			genericMethods.enterText(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1000);
			System.out.println("After LHS......");
			By policyCondition = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//div[@class='form-group']/select[@ng-model='subCondition.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);
			//genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);
			By policyRHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//span[@ng-transclude='']/input[@ng-model='subCondition.rhs.value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[@class='childsubCondition ng-scope']["+(policyRowInc)+"]//button[@class='btn btn-primary btn-xs' and @ng-disabled='checkConditions()']");
			genericMethods.clickElement(driver, plusPolicy, test);
			policyRowInc++;
			Thread.sleep(1000);
		}
		return true;

	}

	
	
	
	
	
	int ruleCount = 0;
	public boolean applyRule(WebDriver driver, ExtentTest test) throws InvalidFormatException, IOException, InterruptedException
	{
		System.out.println("Apply Rules");
		genericMethods.clickElement(driver, policiesButton, test);
		String pathOfFile = Global.testSheet;
		File f = new File(pathOfFile);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> applyRuleMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;


		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Select Rules")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}


		List<String> applyRulesAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>1){
				applyRulesAllValue.add(val);
			}
			applyRuleMap.put(key,applyRulesAllValue);
		}



		List<String> applyRuleCellValue = null;
		Set<String> keySet = applyRuleMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			applyRuleCellValue = applyRuleMap.get(key1);
			System.out.println("Key1 is : "+key1+ " and Value is : "+applyRuleCellValue);
		}


		String applyRulesSingleValue = applyRuleCellValue.get(ruleCount++);
		System.out.println("applyRulesSingleValue : "+applyRulesSingleValue);
		Thread.sleep(1000);
		By selectAllAnyDD = By.xpath("//span[contains(text(),'following true')]/parent::div[@class='form-inline']/select[@ng-model='condition.type']");
		genericMethods.clickElement(driver, selectAllAnyDD, test);
		genericMethods.selectByVisibleText(driver, selectAllAnyDD, applyRulesSingleValue, test);
		return true;
	}


	int switchPathCount = 0;
	public boolean hasPath(WebDriver driver, ExtentTest test) throws InterruptedException, InvalidFormatException, IOException
	{

		System.out.println("Has Path");
		genericMethods.clickElement(driver, pathButton, test);
		genericMethods.clickElement(driver, createPath, test);
		Thread.sleep(1000);
		String pathOfFile = Global.testSheet;
		File f = new File(pathOfFile);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> pathMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;
		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Path")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		List<String> pathAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>3){
				pathAllValue.add(val);
			}
			pathMap.put(key,pathAllValue);
		}


		List<String> restrictionsCellValue = null;
		Set<String> keySet = pathMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			restrictionsCellValue = pathMap.get(key1);
			System.out.println("Key1 is : "+key1+ "Value is : "+restrictionsCellValue);
		}

		int pathRowInc = 1;
		String[] pathSeparatedByAndValue = restrictionsCellValue.get(switchPathCount++).split("and");
		for(String pathSeparatedBySinglePath : pathSeparatedByAndValue){
			String[] pathSeparatedByComma = pathSeparatedBySinglePath.split(",");
			By pathLHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(pathRowInc)+"]//input[@ng-model='subCondition.lhs.value']");
			driver.findElement(pathLHS).sendKeys(new CharSequence[]{pathSeparatedByComma[0]});
			Thread.sleep(1000);
			By pathCondition = By.xpath("//div[@class='childsubCondition ng-scope']["+(pathRowInc)+"]//div[@class='form-group']/select[@ng-model='subCondition.operator']");
			genericMethods.clickElement(driver, pathCondition, test);
			Thread.sleep(1000);
			genericMethods.selectByVisibleText(driver, pathCondition, pathSeparatedByComma[1], test);
			Thread.sleep(1000);
			By pathRHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(pathRowInc)+"]//span[@ng-transclude='']/input[@ng-model='subCondition.rhs.value']");
			genericMethods.enterText(driver, pathRHS, pathSeparatedByComma[2], test);
			By plusPath = By.xpath("//div[@class='childsubCondition ng-scope']["+(pathRowInc)+"]//button[@class='btn btn-primary btn-xs' and @ng-disabled='checkConditions()']");
			genericMethods.clickElement(driver, plusPath, test);
			pathRowInc++;
			Thread.sleep(1000);
		}
		By modalSavePath = By.xpath("//div[@class='modal-footer']/button[text()='Save']");
		genericMethods.clickElement(driver, modalSavePath, test);
		return true;
	}



	int viewRestrictionCount = 0;
	public boolean hasRestrictions(WebDriver driver, ExtentTest test) throws InterruptedException, InvalidFormatException, IOException
	{

		System.out.println("Has Restrictions");
		genericMethods.clickElement(driver, restrictionsButton, test);
		applyRule(driver, test);
		String pathOfFile = Global.testSheet;
		File f = new File(pathOfFile);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> conditionMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;
		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Restrictions")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		List<String> conditionAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>3){
				conditionAllValue.add(val);
			}
			conditionMap.put(key,conditionAllValue);
		}


		List<String> restrictionsCellValue = null;
		Set<String> keySet = conditionMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			restrictionsCellValue = conditionMap.get(key1);
			System.out.println("Key1 is : "+key1+ "Value is : "+restrictionsCellValue);
		}

		int restrictionRowInc = 1;
		String[] conditionSeparatedByAndValue = restrictionsCellValue.get(viewRestrictionCount++).split("and");
		for(String conditionSeparatedBySingleCondition : conditionSeparatedByAndValue){
			String[] conditionSeparatedByComma = conditionSeparatedBySingleCondition.split(",");
			By policyLHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(restrictionRowInc)+"]//input[@ng-model='subCondition.lhs.value']");
			driver.findElement(policyLHS).sendKeys(new CharSequence[]{conditionSeparatedByComma[0]});
			Thread.sleep(1000);
			By policyCondition = By.xpath("//div[@class='childsubCondition ng-scope']["+(restrictionRowInc)+"]//div[@class='form-group']/select[@ng-model='subCondition.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);
			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);
			By policyRHS = By.xpath("//div[@class='childsubCondition ng-scope']["+(restrictionRowInc)+"]//span[@ng-transclude='']/input[@ng-model='subCondition.rhs.value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[@class='childsubCondition ng-scope']["+(restrictionRowInc)+"]//button[@class='btn btn-primary btn-xs' and @ng-disabled='checkConditions()']");
			genericMethods.clickElement(driver, plusPolicy, test);
			restrictionRowInc++;
			Thread.sleep(1000);
		}
		return true;
	}

	public boolean create(WebDriver driver, ExtentTest test, String template, String entityName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By formTemplate = By.xpath("//div[@class='card']/img[@alt='form']");
			By tabbedTemplate = By.xpath("//div[@class='card']/img[@alt='tabbed']");
			By pcEmbedFormTemplate = By.xpath("//div[@class='card']/img[@alt='pcEmbedForm']");
			By pcCreateTemplate = By.xpath("//div[@class='card']/img[@alt='pcCreate']");
			By pcMultiFormTemplate = By.xpath("//div[@class='card']/img[@alt='pcMultiForm']");
			By pcModalFormTemplate =  By.xpath("//div[@class='card']/img[@alt='pcModalForm']");

			genericMethods.clickElement(driver, createVerb, test);
			constructCounter(driver, test);

			if(template.equalsIgnoreCase("formTemplate"))
			{ 
				genericMethods.clickElement(driver, formTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test);
				Thread.sleep(2500);
				hasBusinessValidation(driver, test);
				genericMethods.clickElement(driver, saveButton, test);
			}


			else if(template.equalsIgnoreCase("tabbedTemplate")){
				genericMethods.clickElement(driver, tabbedTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test);
				genericMethods.clickElement(driver, saveButton, test);

			}

			else if(template.equalsIgnoreCase("pcCreate")){	
				genericMethods.clickElement(driver, pcCreateTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test);
				genericMethods.clickElement(driver, saveButton, test);

			}
			else if(template.equalsIgnoreCase("pcEmbedForm")){
				genericMethods.clickElement(driver, pcEmbedFormTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test);
				genericMethods.clickElement(driver, saveButton, test);
			}

			else if(template.equalsIgnoreCase("pcMultiForm"))
			{ 
				genericMethods.clickElement(driver, pcMultiFormTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test);	genericMethods.clickElement(driver, saveButton, test);
			}

			else if(template.equalsIgnoreCase("pcModalForm"))
			{ 
				genericMethods.clickElement(driver, pcModalFormTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test);
				genericMethods.clickElement(driver, saveButton, test);
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


	public boolean view(WebDriver driver, ExtentTest test, String template, String entityName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By viewVerb = By.xpath("//p[contains(text(),'VIEW')]");
			By pChild = By.xpath("//b[text()='pChild']");
			By list = By.xpath("//b[text()='list']");
			By search = By.xpath("//b[text()='search']");

			genericMethods.clickElement(driver, viewVerb, test);
			Thread.sleep(700);
			constructCounter(driver, test);
			//	By dataPosition = By.xpath("//div[@data-position-left='4' and @data-position-top='0']");
			//	genericMethods.clickElement(driver, dataPosition, test);

			if(template.equalsIgnoreCase("pChild")){ 
				genericMethods.clickElement(driver, pChild, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasRestrictions(driver, test);
				genericMethods.clickElement(driver, saveButton, test);
			}
			else if(template.equalsIgnoreCase("list")){
				genericMethods.clickElement(driver, list, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasRestrictions(driver, test);
				genericMethods.clickElement(driver, saveButton, test);

			}
			else if(template.equalsIgnoreCase("search")){
				genericMethods.clickElement(driver, search, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasRestrictions(driver, test);
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


	public boolean update(WebDriver driver, ExtentTest test , String entityName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			genericMethods.clickElement(driver, updateVerb, test);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			genericMethods.clickElement(driver, nextButton, test);
			hasPolicy(driver, test);
			Thread.sleep(1500);
			hasBusinessValidation(driver, test);
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



	public boolean delete(WebDriver driver, ExtentTest test , String entityName) throws InterruptedException {

		try{
			genericMethods.clickElement(driver, deleteVerb, test);
			Thread.sleep(1500);			
			constructCounter(driver, test);
			//	By dataPosition = By.xpath("//div[@data-position-left='4' and @data-position-top='1']");
			//	genericMethods.clickElement(driver, dataPosition, test);

			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
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



	public boolean Switch(WebDriver driver, ExtentTest test , String entityName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By switchVerb = By.xpath("//p[contains(text(),'switch')]");
			genericMethods.clickElement(driver, switchVerb, test);
			Thread.sleep(1000);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			hasPath(driver, test);
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


	public boolean end(WebDriver driver, ExtentTest test) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By endConstuct = By.xpath("//p[contains(text(),'END')]");
			genericMethods.clickElement(driver, endConstuct, test);
			Thread.sleep(1000);
			//	constructCounter(driver, test);
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

	int xpathLen = 1;
	public boolean addIcon(WebDriver driver, ExtentTest test) throws InterruptedException
	{

		try{

			// By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");
			By plusIcon = By.xpath("//div[@class='step circle plus_step'][1]"); 
			int xpathCount;
			xpathCount = genericMethods.getXpathSize(driver, plusIcon, test);
			/*if(xpathCount > 1 && xpathCount < 3 ){
				System.out.println("Two plus Icon");
				Thread.sleep(1500);
				genericMethods.clickElement(driver, By.xpath("//div[@class='step circle plus_step']["+(xpathLen++)+"]"), test);
			}*/
			//else{
			System.out.println("Single plus Icon");
			genericMethods.clickElement(driver, plusIcon, test);
			Thread.sleep(4500);
			//}
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

	public void getEntityDataTypes(WebDriver driver, List<String> attributeType, List<String> attributeValidations, int value, int totalAttributesCount){
		try{

			if(attributeType.get(value).toString().equalsIgnoreCase("Number")){
				genericMethods.clickElement(driver, By.xpath("//tr["+(totalAttributesCount)+"]//a[@ng-click='editValidations(attribute)']"), test);
				fluentWait(driver, By.xpath("//div[@class='modal-header']//h4[text()='Edit Attribute Validations']"), 5, 30);
				genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@id='attributeRange' and @type='checkbox']"), test);
				String numberValidationValues = attributeValidations.get(value);
				System.out.println(numberValidationValues);
				String[] listOfNumbers = numberValidationValues.split(",");
				genericMethods.enterText(driver, By.xpath("//input[@id='rangeMin']"), listOfNumbers[0].trim(), test);
				genericMethods.enterText(driver, By.xpath("//input[@id='rangeMax']"), listOfNumbers[1].trim(), test);
				genericMethods.clickElement(driver, By.xpath("//div[@class='modal-footer']//button[@type='button' and contains(text(),'Update')]"), test); 
			}



			else if(attributeType.get(value).toString().equalsIgnoreCase("date")){
				genericMethods.clickElement(driver, By.xpath("//tr["+(totalAttributesCount)+"]//a[@ng-click='editValidations(attribute)']"), test);
				fluentWait(driver, By.xpath("//div[@class='modal-header']//h4[text()='Edit Attribute Validations']"), 5, 30);
				String dateValidationValues = attributeValidations.get(value);
				System.out.println(dateValidationValues);
				String[] listOfDateAttributes = dateValidationValues.split(",");
				genericMethods.selectRadioButton(driver, By.xpath("//label/input[@name='dateValidation' and @value='"+listOfDateAttributes[0].trim()+"']"), test);
				//if(listOfDateAttributes[1].equalsIgnoreCase("DateTime"))
				genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@type='checkbox' and @name='dateValidation']"), test);
				genericMethods.clickElement(driver, By.xpath("//div[@class='modal-footer']//button[@type='button' and contains(text(),'Update')]"), test);
			}

			else if(attributeType.get(value).toString().equalsIgnoreCase("text")){
				genericMethods.clickElement(driver, By.xpath("//tr["+(totalAttributesCount)+"]//a[@ng-click='editValidations(attribute)']"), test);
				fluentWait(driver, By.xpath("//div[@class='modal-header']//h4[text()='Edit Attribute Validations']"), 5, 30);
				String textValidationValues = attributeValidations.get(value);
				System.out.println(textValidationValues);
				String[] listOfTextValues = textValidationValues.split(",");
				if(listOfTextValues[0].trim().equalsIgnoreCase("UpperCase"))
					genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@id='attributeUppercase']"), test);
				else if(listOfTextValues[0].trim().equalsIgnoreCase("UpperCase"))
					genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@id='attributeLowercase']"), test);
				genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@id='attributeTextLength']"), test);
				genericMethods.enterText(driver, By.xpath("//input[@id='textLengthMin']"), listOfTextValues[1].trim(), test);
				genericMethods.enterText(driver, By.xpath("//input[@id='textLengthMax']"), listOfTextValues[2].trim(), test);
				if(listOfTextValues[3].trim().equalsIgnoreCase("Alphabets"))
					genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@id='attributeOnlyAlphabets']"), test);
				else if(listOfTextValues[3].trim().equalsIgnoreCase("Alphanumeric"))
					genericMethods.selectSingleCheckbox(driver, By.xpath("//input[@id='attributeAlphaNumeric']"), test);
				genericMethods.clickElement(driver, By.xpath("//div[@class='modal-footer']//button[@type='button' and contains(text(),'Update')]"), test);
			}
		}
		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
	}

	public void getEntityType(WebDriver driver, List<String> getEntityType, int value){
		try{
			if(getEntityType.get(value).toString().equalsIgnoreCase("Master"))
				genericMethods.selectRadioButton(driver, By.xpath("//div[@ng-init='newEntity.master = false; newEntity.embedded = false']//label[1]/input"), test);
			else if(getEntityType.get(value).toString().equalsIgnoreCase("Transaction"))
				genericMethods.selectRadioButton(driver, By.xpath("//div[@ng-init='newEntity.master = false; newEntity.embedded = false']//label[2]/input"), test);
			else if(getEntityType.get(value).toString().equalsIgnoreCase("Embedded"))
				genericMethods.selectRadioButton(driver, By.xpath("//div[@ng-init='newEntity.master = false; newEntity.embedded = false']//label[3]/input"), test);  
		}
		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
	}

	public void removeText(WebDriver driver, By elementLocator){
		try{
			driver.findElement(elementLocator).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			driver.findElement(elementLocator).sendKeys(Keys.BACK_SPACE);
		}

		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
	}

}

