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
import java.util.regex.Pattern;

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


public class StudioCommonMethods implements GlobalXpath {

	private Logger log = Logger.getLogger("StudioCommonMethods.class");
	private WebDriverWait wait;
	private WebElement element;
	private String errorMsg;
	ExtentReports extent;
	GenericMethods genericMethods = new GenericMethods();

	By saveButton = By.xpath("//button[@ng-click='save()']");
	By closeIcon = By.xpath("//button[@class='close']");
	By menuIcon = By.xpath("//span[@class='fa fa-cog']");
	int constructCountLeft = 0;
	int constructCountTop = 0;
	int constructCountTriggers = 1;
	public ExtentTest test;
	public  WebDriver driver;
	String expectedHeader = "Confirm";



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


	public void studioLogin(ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{

		String workbookTestData = workbookName;
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("studiologin");
		String[] rowElements = new String[50];
		int cellValue = 1;
		for(int row = 1; row < sheet.getLastRowNum(); row++){
			for(int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
				rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
			}

			String expectedPageTitle = "KAGAMI STUDIO";
			//fluentWait(driver, uname, 3, 10);
			Thread.sleep(3000);
			genericMethods.enterText(driver, uname, rowElements[cellValue++], test);
			genericMethods.enterText(driver, password,rowElements[cellValue++], test);
			genericMethods.clickElement(driver, loginButton , test);
			//	boolean errorMsgText = driver.findElement(loginErrorMsg).isDisplayed();
			Thread.sleep(2500);
			if(driver.findElement(menuIcon).isDisplayed()){
				test.log(LogStatus.PASS, "Login Successful.");
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
			/*genericMethods.clickElement(driver,closeButtonDeployment,test);
			Thread.sleep(1000);
			System.out.println("Studio Common methods Logout0");	
			genericMethods.clickElement(driver, menuIcon, test);
			System.out.println("StudioLogout1");
			genericMethods.clickElement(driver, signOut, test);
			System.out.println("StudioLogout2");
			test.log(LogStatus.PASS, "Logout Successful.");*/

			//	genericMethods.clickElement(driver,closeButtonDeployment,test);
			//	driver.switchTo().defaultContent();
			System.out.println("Switched to default window : Studio Common");	

			genericMethods.clickElement(driver, menuIcon, test);
			System.out.println("StudioLogout1");
			genericMethods.clickElement(driver, signOut, test);
			System.out.println("StudioLogout2");
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

	public void createFolder(WebDriver driver,String projectName, String folderName, String entityName){
		try{

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	/*
	public void createFolder(WebDriver driver,String projectName,String folderName,String entityName,ExtentTest Test) throws InterruptedException
	{
		try{
			By enterFolderName=By.xpath("//div/input[@name='newFolderName']");
			By createFolderButton=By.xpath("//div/button[contains(text(),'Create')]");
			//By projectRightClick=By.xpath("//tr/td/p/span[contains(text(),'"+(entityName)+"')]");
			Thread.sleep(5000);       
			WebElement rightClickProject=driver.findElement(By.xpath("//tr/td/p/span[contains(text(),'"+(projectName)+"')]"));
			Actions a = new Actions(driver);
			a.moveToElement(rightClickProject);
			a.contextClick(rightClickProject).build().perform(); 
			WebElement selectCreateFolder = driver.findElement(By.xpath("//ul/li/a[contains(text(),'Create Folder')]")); 
			selectCreateFolder.click();
			Thread.sleep(2000);     
			genericMethods.enterText(driver, enterFolderName, folderName, test);     
			genericMethods.clickElement(driver, createFolderButton, test);
			Thread.sleep(2000);
			WebElement rightClickFolder=driver.findElement(By.xpath("//p[@id='parentid']/following-sibling::ul//a[contains(.,'"+(folderName)+"')]"));
			a.moveToElement(rightClickFolder);
			a.contextClick(rightClickFolder).build().perform();
			WebElement selectAddEntity=driver.findElement(By.xpath("//li/a[contains(text(),'Add Entity')]"));
			selectAddEntity.click();
			genericMethods.waitForElementVisibility(driver, By.xpath("//input[@ng-change='createEntityNameChanged(newEntity)' and @placeholder='Enter Entity Name']"), 20);
			genericMethods.enterText(driver, By.xpath("//input[@ng-change='createEntityNameChanged(newEntity)' and @placeholder='Enter Entity Name']"), entityName, test);

		}
		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}

	}

	 */


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
		By deleteItem = By.xpath("//h3[text()='"+itemToDelete+"']/parent::div/parent::a/parent::div//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-trash']");
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


	int viewTriggerCnt = 0;
	public void addTriggers(WebDriver driver, String listOfTriggerValues, ExtentTest test) throws InvalidFormatException, IOException, InterruptedException{
		try{

			genericMethods.clickElement(driver, editTabInView, test);
			String triggers = listOfTriggerValues.toString().replace("[", "").replace("]", "").trim();
			String[] allTriggers = triggers.split(",");
			System.out.println("Outer Trigger Value = "+allTriggers[0]);
			for(int triggerCount = 0; triggerCount < allTriggers.length; triggerCount++){
				System.out.println("Inner Trigger Value = "+allTriggers[triggerCount]);

				genericMethods.waitForElementVisibility(driver, inputTriggerName, 2); 
				genericMethods.enterText(driver, inputTriggerName, allTriggers[triggerCount], test);
				Thread.sleep(1500);
				genericMethods.clickElement(driver, addTriggerButton, test);
				Thread.sleep(1500);
				genericMethods.clickElement(driver, By.xpath("//div[@ng-model='vm.triggers']/a/span[text()='"+allTriggers[triggerCount]+"']"), test);
				genericMethods.waitForElementVisibility(driver, By.xpath("//div[contains(text(),'Select Roles For trigger ("+allTriggers[triggerCount]+"):')]"), 10);
				genericMethods.selectMultiCheckbox(driver, By.xpath("//div[contains(text(),'Select Roles For trigger ("+allTriggers[triggerCount]+"):')]//div[@ng-repeat='role in availableTriggerRoles']//input[@type='checkbox']"), test);
				genericMethods.clickElement(driver, By.xpath("//button[text()='Map']"), test);
				++viewTriggerCnt;
			}
			log.info("Triggers added successfully");
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			errorMsg = e.getMessage();
		}
		log.warn("Unable to add trigger successfully because "+errorMsg);
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


	int triggerCount = 0;
	public List<String> addMultipleTrigger(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		String workbookTestData = workbookName;
		System.out.println("Has multiple trigger");
		genericMethods.clickElement(driver, policiesButton, test);
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		String key = null;
		int cellNum = 0;
		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Triggers")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		List<String> triggerAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>1){
				triggerAllValue.add(val);
			}
		}
		return triggerAllValue;
	}


	int createPolicyCount = 0;
	public boolean hasPolicy(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		String workbookTestData = workbookName;
		System.out.println("Has Policies");
		genericMethods.clickElement(driver, policiesButton, test);
		applyRule(driver, test, workbookTestData);
		File f = new File(workbookTestData);
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

			By policyLHS = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(policyRowInc)+"]//input[@ng-model='$ctrl.variable.value']");
			//	driver.findElement(policyLHS).sendKeys(new CharSequence[]{conditionSeparatedByComma[0]});
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);
			By policyCondition = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(policyRowInc)+"]//div[@class='form-group']/select[@ng-model='$ctrl.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);

			// Rework code		
			removeText(driver, policyLHS);
			Thread.sleep(1000);
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);

			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);

			By policyRHS = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(policyRowInc)+"]//input[@ng-model='$ctrl.literal.$$value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[contains(@ng-repeat,'entryCriteriaRB')]//button[@class='btn btn-primary btn-xs' and contains(@ng-click,'addExpression')]");
			genericMethods.clickElement(driver, plusPolicy, test);
			policyRowInc++;
			Thread.sleep(1000);
		}
		return true;
	}




	int bValidationCount = 0;
	public boolean hasBusinessValidation(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		System.out.println("Has Business Validation");
		genericMethods.clickElement(driver, businessValidation, test);
		String workbookTestData = workbookName;
		//	applyRule(driver, test, workbookName);
		File f = new File(workbookTestData);
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
			System.out.println("Business Validation.....");
			Thread.sleep(1000);

			//div[contains(@ng-repeat,'businessValidationsRB')]//div[@ng-repeat='subCondition in condition.conditions'][2]//div[@class='form-group']//input[@ng-model='$ctrl.variable.value']

			By policyLHS = By.xpath("//div[contains(@ng-repeat,'businessValidationsRB')]//div[@ng-repeat='subCondition in condition.conditions']["+(policyRowInc)+"]//div[@class='form-group']//input[@ng-model='$ctrl.variable.value']");
			boolean isDis = driver.findElement(policyLHS).isEnabled();
			System.out.println("LHS is enabled : "+isDis);
			genericMethods.enterText(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);
			By policyCondition = By.xpath("//div[contains(@ng-repeat,'businessValidationsRB')]//div[@ng-repeat='subCondition in condition.conditions']["+(policyRowInc)+"]//div[@class='form-group']/select[@ng-model='$ctrl.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);

			// Rework code		
			removeText(driver, policyLHS);
			Thread.sleep(1000);
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);

			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1500);
			By policyRHS = By.xpath("//div[contains(@ng-repeat,'businessValidationsRB')]//div[@ng-repeat='subCondition in condition.conditions']["+(policyRowInc)+"]//input[@ng-model='$ctrl.literal.$$value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[contains(@ng-repeat,'businessValidationsRB')]//button[@class='btn btn-primary btn-xs' and contains(@ng-click,'addExpression')]");
			genericMethods.clickElement(driver, plusPolicy, test);
			policyRowInc++;
			Thread.sleep(1000);
		}
		return true;

	}



	int ruleCount = 0;
	public boolean applyRule(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		System.out.println("Apply Rules");
		//genericMethods.clickElement(driver, policiesButton, test);
		String workbookTestData = workbookName;
		File f = new File(workbookTestData);
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
		By selectAllAnyDD = By.xpath("//span[contains(text(),'following true')]/parent::div[@class='form-inline group-heading']/select[@ng-model='condition.type']");
		genericMethods.clickElement(driver, selectAllAnyDD, test);
		genericMethods.selectByVisibleText(driver, selectAllAnyDD, applyRulesSingleValue, test);
		return true;
	}


	int switchPathCount = 0;
	public boolean hasPath(WebDriver driver, ExtentTest test, String workbookName) throws InterruptedException, InvalidFormatException, IOException
	{
		System.out.println("Has Path only for switch");
		By pathButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/paths.png']");
		By createPath = By.xpath("//button[@ng-click='createPath()']");   
		genericMethods.clickElement(driver, pathButton, test);
		Thread.sleep(1000);
		String workbookTestData = workbookName;
		File f = new File(workbookTestData);
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
		System.out.println(sheet.getLastRowNum());
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			System.out.println(val.length());
			if(val.length()>9)
			{
				pathAllValue.add(val);
				pathMap.put(key,pathAllValue);
			}
		}

		List<String> pathCellValue = null;
		Set<String> keySet = pathMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			pathCellValue = pathMap.get(key1);
			System.out.println("Key1 is : "+key1+ "Value is : "+pathCellValue);
		} 
		String[] pathSeparatedByAndValue = pathCellValue.get(switchPathCount++).split("and");
		for(String pathSeparatedBySinglePath : pathSeparatedByAndValue){
			String[] pathSeparatedByComma = pathSeparatedBySinglePath.split(",");
			genericMethods.clickElement(driver, createPath, test);
			int pathRowInc = 1;

			WebElement LHSWebElement = driver.findElement(By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(pathRowInc)+"]//studio-attribute-selector[@variable='subCondition.lhs']"));
			Actions a = new Actions(driver);   
			String[] entityValue=  pathSeparatedByComma[0].split(Pattern.quote("."));   
			a.click(LHSWebElement).build().perform();   
			a.sendKeys(entityValue[0],Keys.DOWN,Keys.ENTER,".",entityValue[1]).build().perform();     
			Thread.sleep(1000);
			By pathCondition = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(pathRowInc)+"]//studio-operator-selector[@operator='subCondition.operator']");    
			genericMethods.clickElement(driver, pathCondition, test);
			Thread.sleep(2000);

			/*	removeTextByWebElement(driver, LHSWebElement);
			Thread.sleep(2000);
			a.click(LHSWebElement).build().perform();   
			a.sendKeys(entityValue[0],Keys.DOWN,Keys.ENTER,".",entityValue[1]).build().perform();     
			 */		

			genericMethods.selectByVisibleText(driver, pathCondition, pathSeparatedByComma[1], test);
			Thread.sleep(2000);   
			WebElement pathRHS = driver.findElement(By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(pathRowInc)+"]//rule-builder-literal[@literal='subCondition.rhs']"));
			a.click(pathRHS).build().perform();
			a.sendKeys(pathSeparatedByComma[2]).build().perform();   

			By modalSavePath = By.xpath("//div[@class='modal-footer']/button[text()='Save']");
			genericMethods.clickElement(driver, modalSavePath, test);

			By plusPath = By.xpath("//div[@class='childsubCondition ng-scope']["+(pathRowInc)+"]//button[@class='btn btn-primary btn-xs' and @ng-disabled='checkConditions()']");
			genericMethods.clickElement(driver, plusPath, test);
			pathRowInc++;
			Thread.sleep(1000);
		}
		return true;
	}


	int viewRestrictionCount = 0;
	public boolean hasRestrictions(WebDriver driver, ExtentTest test, String workbookName) throws InterruptedException, InvalidFormatException, IOException
	{

		System.out.println("Has Restrictions");
		genericMethods.clickElement(driver, restrictionsButton, test);
		String workbookTestData = workbookName;
		applyRule(driver, test, workbookTestData);
		File f = new File(workbookTestData);

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
			By policyLHS = By.xpath("//div[contains(@ng-repeat,'restrictionsRB')]//div[@ng-repeat='subCondition in condition.conditions']["+(restrictionRowInc)+"]//div[@class='form-group']//input[@ng-model='$ctrl.variable.value']");
			driver.findElement(policyLHS).sendKeys(new CharSequence[]{conditionSeparatedByComma[0]});
			Thread.sleep(1000);
			By policyCondition = By.xpath("//div[contains(@ng-repeat,'restrictionsRB')]//div[@ng-repeat='subCondition in condition.conditions']["+(restrictionRowInc)+"]//div[@class='form-group']/select[@ng-model='$ctrl.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);

			// Rework code		
			removeText(driver, policyLHS);
			Thread.sleep(1000);
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);

			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);
			By policyRHS = By.xpath("//div[@class='form-group']/following::input[@ng-model='$ctrl.literal.$$value']["+(restrictionRowInc)+"]");
			//	By policyRHS = By.xpath("//div[contains(@ng-repeat,'restrictionsRB')]//div[@ng-repeat='subCondition in condition.conditions']["+(restrictionRowInc)+"]//div[@class='form-group']//input[@ng-model='$ctrl.literal.$$value']");

			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[contains(@ng-repeat,'restrictionsRB')]//button[@class='btn btn-primary btn-xs' and contains(@ng-click,'addExpression')]");
			genericMethods.clickElement(driver, plusPolicy, test);
			restrictionRowInc++;
			Thread.sleep(1000);
		}
		return true;
	}





	int updateRulesCount = 0;
	public boolean updateRules(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		String workbookTestData = workbookName;
		System.out.println("Has Update Rules");
		//	genericMethods.clickElement(driver, policiesButton, test);
		//	applyRule(driver, test);
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> conditionMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;


		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Update Rule")){
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

		int updateRuleRowInc = 1;
		String[] conditionSeparatedByAndValue = conditionCellValue.get(updateRulesCount++).split("and");
		for(String conditionSeparatedBySingleCondition : conditionSeparatedByAndValue){
			String[] conditionSeparatedByComma = conditionSeparatedBySingleCondition.split(",");

			//div[@ng-repeat='subCondition in condition.conditions']["+(pathRowInc)+"]//studio-attribute-selector[@variable='subCondition.lhs']

			By policyLHS = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(updateRuleRowInc)+"]//input[@ng-model='$ctrl.variable.value']");
			//	driver.findElement(policyLHS).sendKeys(new CharSequence[]{conditionSeparatedByComma[0]});
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);
			By policyCondition = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(updateRuleRowInc)+"]//div[@class='form-group']/select[@ng-model='$ctrl.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);

			// Rework code		
			removeText(driver, policyLHS);
			Thread.sleep(1000);
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);

			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);
			By policyRHS = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(updateRuleRowInc)+"]//input[@ng-model='$ctrl.literal.$$value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[contains(@ng-repeat,'entryCriteriaRB')]//button[@class='btn btn-primary btn-xs' and contains(@ng-click,'addExpression')]");
			genericMethods.clickElement(driver, plusPolicy, test);
			updateRuleRowInc++;
			Thread.sleep(1000);
		}
		return true;
	}



	int updateExpressionCount = 0;
	public boolean updateExpression(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		String workbookTestData = workbookName;
		System.out.println("Has Update Expression");
		genericMethods.clickElement(driver, policiesButton, test);
		applyRule(driver, test, workbookTestData);
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> conditionMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;


		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Update Expression")){
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

		int updateExpressionRowInc = 1;
		String[] conditionSeparatedByAndValue = conditionCellValue.get(updateExpressionCount++).split("and");
		for(String conditionSeparatedBySingleCondition : conditionSeparatedByAndValue){
			String[] conditionSeparatedByComma = conditionSeparatedBySingleCondition.split(",");

			//div[@ng-repeat='subCondition in condition.conditions']["+(pathRowInc)+"]//studio-attribute-selector[@variable='subCondition.lhs']

			By policyLHS = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(updateExpressionRowInc)+"]//input[@ng-model='$ctrl.variable.value']");
			//	driver.findElement(policyLHS).sendKeys(new CharSequence[]{conditionSeparatedByComma[0]});
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);
			By policyCondition = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(updateExpressionRowInc)+"]//div[@class='form-group']/select[@ng-model='$ctrl.operator']");
			genericMethods.clickElement(driver, policyCondition, test);
			Thread.sleep(1000);

			// Rework code		
			removeText(driver, policyLHS);
			Thread.sleep(1000);
			genericMethods.inputTextAndEnter(driver, policyLHS, conditionSeparatedByComma[0], test);
			Thread.sleep(1500);

			genericMethods.selectByVisibleText(driver, policyCondition, conditionSeparatedByComma[1], test);
			Thread.sleep(1000);


			By policyRHS = By.xpath("//div[@ng-repeat='subCondition in condition.conditions']["+(updateExpressionRowInc)+"]//input[@ng-model='$ctrl.literal.$$value']");
			genericMethods.enterText(driver, policyRHS, conditionSeparatedByComma[2], test);
			By plusPolicy = By.xpath("//div[contains(@ng-repeat,'entryCriteriaRB')]//button[@class='btn btn-primary btn-xs' and contains(@ng-click,'addExpression')]");
			genericMethods.clickElement(driver, plusPolicy, test);
			updateExpressionRowInc++;
			Thread.sleep(1000);
		}
		return true;
	}




	int transformCriteriaCount = 0;
	public boolean transformCriteria(WebDriver driver, ExtentTest test, String workbookName) throws InvalidFormatException, IOException, InterruptedException
	{
		String workbookTestData = workbookName;
		System.out.println("Has Transform Crieteria");
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
		Map<String,List<String>> transformMap = new HashMap<String,List<String>>();
		String key = null;
		int cellNum = 0;


		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(2).getCell(cell).toString().trim().equals("Transform Expression")){
				key =  sheet.getRow(2).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		List<String> transformAllValue = new ArrayList<String>();
		for(int rowNum = 3; rowNum < sheet.getLastRowNum(); rowNum++ ){
			String val = sheet.getRow(rowNum).getCell(cellNum).toString();
			if(val.length()>3){
				transformAllValue.add(val);
			}
			transformMap.put(key,transformAllValue);
		}


		List<String> transformCellValue = null;
		Set<String> keySet = transformMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key1 = (String) iterator.next();
			transformCellValue = transformMap.get(key1);
			System.out.println("Key1 is: "+key1+ "Value is "+transformCellValue);
		}

		//		int transformRowInc = 1;
		String[] transformSeparatedByAndValue = transformCellValue.get(transformCriteriaCount++).split("and");
		for(String transformSeparatedBySingleCondition : transformSeparatedByAndValue){
			String[] transformSeparatedByComma = transformSeparatedBySingleCondition.split(",");

			//div[@ng-repeat='subCondition in condition.conditions']["+(pathRowInc)+"]//studio-attribute-selector[@variable='subCondition.lhs']

			By destinationEntityAtt = By.xpath("//tr//td//div[@ng-model='vm.destinationAttribute']//div[@placeholder='Select a entity in the list...']//span[@aria-label='Select box activate']");
			By selectEntityfromList = By.xpath("//div[@ng-model='vm.destinationAttribute']//input[@placeholder='Select a entity in the list...']");
			By sourceEntity = By.xpath("//tr//td//div[@ng-model='vm.sourceEntity']//div[@placeholder='Select a entity in the list...']//span[@aria-label='Select box activate']");
			By selectSourceEntity = By.xpath("//div[@ng-model='vm.sourceEntity']//input[@placeholder='Select a entity in the list...']");
			By sourceEntityAtt = By.xpath("//tr//td//div[@ng-model='vm.sourceEntityAttribute']//div[@placeholder='Select a entity attributes in the list...']//span[@aria-label='Select box activate']");
			By selectSourceAtt = By.xpath("//div[@ng-model='vm.sourceEntityAttribute']//input[@placeholder='Select a entity attributes in the list...']");
			By transformButton = By.xpath("//button[contains(text(),'Transform')]");

			genericMethods.clickElement(driver, destinationEntityAtt, test);
			genericMethods.inputTextAndEnter(driver, selectEntityfromList, transformSeparatedByComma[0].trim(), test);
			Thread.sleep(1500);
			genericMethods.clickElement(driver, sourceEntity, test);
			genericMethods.inputTextAndEnter(driver, selectSourceEntity, transformSeparatedByComma[1].trim(), test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, sourceEntityAtt, test);
			genericMethods.inputTextAndEnter(driver, selectSourceAtt, transformSeparatedByComma[2].trim(), test);
			Thread.sleep(1000);		
			genericMethods.clickElement(driver, transformButton, test);
			Thread.sleep(1500);
		}
		return true;
	}




	public boolean create(WebDriver driver, ExtentTest test, String template, String entityName, String workbookName) throws InterruptedException, InvalidFormatException, IOException {
		try{
			By formTemplate = By.xpath("//div[@class='card']/img[@alt='form']");
			By tabbedTemplate = By.xpath("//div[@class='card']/img[@alt='tabbed']");
			By pcEmbedFormTemplate = By.xpath("//div[@class='card']/img[@alt='pcEmbedForm']");
			By pcCreateTemplate = By.xpath("//div[@class='card']/img[@alt='pcCreate']");
			By pcMultiFormTemplate = By.xpath("//div[@class='card']/img[@alt='pcMultiForm']");
			By pcModalFormTemplate =  By.xpath("//div[@class='card']/img[@alt='pcModalForm']");

			genericMethods.clickElement(driver, createVerb, test);
			constructCounter(driver, test);
			String workbookTestData = workbookName;

			if(template.equalsIgnoreCase("formTemplate"))
			{ 
				Thread.sleep(2000);
				genericMethods.clickElement(driver, formTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);
			}


			else if(template.equalsIgnoreCase("tabbedTemplate")){
				genericMethods.clickElement(driver, tabbedTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);

			}

			else if(template.equalsIgnoreCase("pcCreate")){	
				genericMethods.clickElement(driver, pcCreateTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);

			}
			else if(template.equalsIgnoreCase("pcEmbedForm")){
				genericMethods.clickElement(driver, pcEmbedFormTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);
			}

			else if(template.equalsIgnoreCase("pcMultiForm"))
			{ 
				genericMethods.clickElement(driver, pcMultiFormTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);
			}

			else if(template.equalsIgnoreCase("pcModalForm"))
			{ 
				genericMethods.clickElement(driver, pcModalFormTemplate, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
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




	public boolean edit(WebDriver driver, ExtentTest test, String template, String entityName, String workbookName) throws InterruptedException, InvalidFormatException, IOException {
		try{
			By pcEmbedEdit = By.xpath("//div[@class='card']/img[@alt='pcEmbedEdit']");
			By pcModalEdit = By.xpath("//div[@class='card']/img[@alt='pcModalEdit']");
			By pcMultiEdit = By.xpath("//div[@class='card']/img[@alt='pcMultiEdit']");
			By edit = By.xpath("//div[@class='card']/img[@alt='edit']");
			By editTabbed = By.xpath("//div[@class='card']/img[@alt='editTabbed']");

			genericMethods.clickElement(driver,editVerb , test);
			constructCounter(driver, test);
			String workbookTestData = workbookName;

			if(template.equalsIgnoreCase("pcEmbedEdit"))
			{ 
				genericMethods.clickElement(driver, pcEmbedEdit, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "PC EmbedEdit Template is created.");
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);		
			}


			else if(template.equalsIgnoreCase("pcModalEdit")){
				genericMethods.clickElement(driver, pcModalEdit, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "PC ModalEdit Template is created.");
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookName);
				genericMethods.clickElement(driver, saveButton, test);

			}

			else if(template.equalsIgnoreCase("pcMultiEdit")){	
				genericMethods.clickElement(driver, pcMultiEdit, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "PC MultiEdit Template is created.");
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookTestData);
				genericMethods.clickElement(driver, saveButton, test);

			}
			else if(template.equalsIgnoreCase("edit")){
				genericMethods.clickElement(driver, edit, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				hasPolicy(driver, test, workbookTestData);
				test.log(LogStatus.PASS, "Edit Template is created.");
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookTestData);
				genericMethods.clickElement(driver, saveButton, test);
			}

			else if(template.equalsIgnoreCase("editTabbed"))
			{ 
				genericMethods.clickElement(driver, editTabbed, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "Edit Tabbed Template is created.");
				hasPolicy(driver, test, workbookTestData);
				Thread.sleep(1500);
				hasBusinessValidation(driver, test, workbookTestData);
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


	int triggerCnt = 0;
	public boolean view(WebDriver driver, ExtentTest test, String template, String entityName, String workbookName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By viewVerb = By.xpath("//p[contains(text(),'VIEW')]");
			By pChild = By.xpath("//b[text()='pChild']");
			By list = By.xpath("//b[text()='list']");
			By search = By.xpath("//b[text()='search']");

			String workBook = workbookName;
			genericMethods.clickElement(driver, viewVerb, test);
			Thread.sleep(1000);
			constructCounter(driver, test);
			List<String> triggers = addMultipleTrigger(driver,test,workBook);
			System.out.println("Triggers :"+triggers);

			if(template.equalsIgnoreCase("pChild")){ 
				Thread.sleep(2000);
				genericMethods.clickElement(driver, pChild, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "pChild Template is created.");
				addTriggers(driver, triggers.get(triggerCnt++), test);
				Thread.sleep(1500);
				hasRestrictions(driver, test, workBook);
				genericMethods.clickElement(driver, saveButton, test);
			}
			else if(template.equalsIgnoreCase("list")){
				Thread.sleep(2000);
				genericMethods.clickElement(driver, list, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "List Template is created.");
				addTriggers(driver, triggers.get(triggerCnt++), test);
				Thread.sleep(1500);
				hasRestrictions(driver, test, workBook);
				genericMethods.clickElement(driver, saveButton, test);
			}
			else if(template.equalsIgnoreCase("search")){
				Thread.sleep(1500);
				genericMethods.clickElement(driver, search, test);	
				Thread.sleep(1000);
				genericMethods.clickElement(driver, selectEntityButton, test);
				genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
				genericMethods.clickElement(driver, nextButton, test);
				test.log(LogStatus.PASS, "Search Template is created.");
				addTriggers(driver, triggers.get(triggerCnt++), test);
				Thread.sleep(1500);
				hasRestrictions(driver, test, workBook);
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
		}
		log.warn("The Element cannot be clicked because "+errorMsg);
		return false;
	}



	public boolean fetch(WebDriver driver, ExtentTest test, String template, String entityName, String workbookName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By fetchVerb = By.xpath("//p[contains(text(),'FETCH')]");

			String workBook = workbookName;
			genericMethods.clickElement(driver, fetchVerb, test);
			Thread.sleep(1000);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			hasRestrictions(driver, test, workBook);
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


	public boolean transform(WebDriver driver, ExtentTest test, String template, String entityName, String workbookName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			By transformVerb = By.xpath("//p[contains(text(),'TRANSFORM')]");
			String workBook = workbookName;
			genericMethods.clickElement(driver, transformVerb, test);
			Thread.sleep(1000);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectTransformEntity, test);
			genericMethods.inputTextAndEnter(driver, selectTransformAttribute, entityName, test);
			transformCriteria(driver,test, workBook);
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




	public boolean update(WebDriver driver, ExtentTest test , String entityName, String workbookName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			String workbookTestData = workbookName;
			genericMethods.clickElement(driver, updateVerb, test);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			genericMethods.clickElement(driver, nextButton, test);
			updateRules(driver, test, workbookTestData);
			updateExpression(driver, test, workbookTestData);

			hasPolicy(driver, test, workbookTestData);
			Thread.sleep(1000);
			hasBusinessValidation(driver, test, workbookName);
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



	public boolean Switch(WebDriver driver, ExtentTest test , String entityName , String workbookName) throws InterruptedException, InvalidFormatException, IOException {

		try{
			String workbookTestData = workbookName;
			By switchVerb = By.xpath("//p[contains(text(),'SWITCH')]");
			genericMethods.clickElement(driver, switchVerb, test);
			Thread.sleep(1000);
			constructCounter(driver, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			hasPath(driver, test, workbookTestData);
			Thread.sleep(1000);
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
			By endConstuct = By.xpath("//img[contains(@src,'end.png')]");
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



	//Mine
	public void email(WebDriver driver, ExtentTest test ,String entityName, String sourceType, String selectedSource, String subject, String message) throws Exception{
		try{
		
			genericMethods.clickElement(driver, By.xpath("//p[contains(text(),'EMAIL')]"), test);         
			constructCounter(driver, test);
			Thread.sleep(2500);
			genericMethods.clickElement(driver, selectEntityButton, test);
			genericMethods.inputTextAndEnter(driver, selectEntityTextArea, entityName, test);
			genericMethods.clickElement(driver, nextButton, test);

			By selectEntityx=By.xpath("//span[contains(text(),'Select or search a source in the list...')]");
			By selectEntityy=By.xpath("//input[@placeholder='Select or search a source in the list...']");

			genericMethods.clickElement(driver, selectEntityx, test);
			genericMethods.inputTextAndEnter(driver, selectEntityy, sourceType, test);
			if(sourceType.equalsIgnoreCase("static"))
			{
				genericMethods.enterText(driver, By.xpath("//input[@placeholder='Enter Email']"), selectedSource, test);
				
			}
			else if(sourceType.equalsIgnoreCase("entity"))
			{      
				genericMethods.enterText(driver, By.xpath("//input[@title='Variable']"), selectedSource, test);
				
			}
			else if(sourceType.equalsIgnoreCase("role"))
			{
				genericMethods.enterText(driver, By.xpath("//input[@placeholder='Enter Expression']"), selectedSource, test);
			}
			genericMethods.enterText(driver, By.xpath("//input[@id='email-subject-text']"), subject, test);
			Thread.sleep(2000);
		//	genericMethods.enterText(driver, By.xpath("//trix-editor[@ng-model='vm.emailMessage']"), message, test);
		
			genericMethods.inputTextAndEnter(driver, By.xpath("//trix-editor[@ng-model='vm.emailMessage']"), message, test);
			Thread.sleep(2000);
			genericMethods.enterText(driver, By.xpath("//trix-editor[@ng-model='vm.emailMessage']"), message, test);
			Thread.sleep(2000);
			genericMethods.clickElement(driver, saveButton, test);
			
			test.log(LogStatus.PASS, "Email will be trigger to recepient having id > "+selectedSource+".");
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			log.warn("Email Verb is not added.");
			e.printStackTrace();
		}
	}


	
	public boolean calender(WebDriver driver,ExtentTest test) throws InterruptedException, InvalidFormatException, IOException
	{
		try{
			By calenderVerb=By.xpath("//p[contains(text(),'CALENDER')]");  
			genericMethods.clickElement(driver, calenderVerb, test);
			Thread.sleep(700);
			constructCounter(driver, test);
			Thread.sleep(1000);
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Calender");
			String[] rowElements = new String[50];
			int cell=0;
			for(int row=1;row<=sheet.getLastRowNum();row++)
			{
				for(int value=0;value<sheet.getRow(row).getLastCellNum();value++)
				{
					System.out.println(sheet.getRow(row).getLastCellNum());
					rowElements[value]=sheet.getRow(row).getCell(value).toString();
					System.out.println(rowElements[value]);
				}
				By x=By.xpath("//span[contains(text(),'Select or search a source in the list...')]");
				By y=By.xpath("//input[@placeholder='Select or search a source in the list...']");
				genericMethods.clickElement(driver, x, test);
				genericMethods.inputTextAndEnter(driver, y, rowElements[cell], test);
				//genericMethods.selectByVisibleText(driver, By.xpath("//input[@placeholder='Select or search a source in the list...']"), rowElements[cell], test);
				if(rowElements[cell].trim().equalsIgnoreCase("static")){
					cell++;
					genericMethods.inputTextAndEnter(driver, By.xpath("//div/input[@placeholder='Enter Email']"), rowElements[cell++], test);
				}
				else if(rowElements[cell]=="entity"){
					cell++;
					genericMethods.inputTextAndEnter(driver, By.xpath("//studio-attribute-selector[@attribute-selected='toEmailAttributeSelected']//input"), rowElements[cell++], test);
				}
				else if(rowElements[cell].trim().equalsIgnoreCase("role")){
					cell++;
					genericMethods.inputTextAndEnter(driver, By.xpath("//div/input[@placeholder='Enter Expression']"), rowElements[cell++], test);
				}
				else{
					cell+=2;
				}
				String[] dateSeparation=rowElements[cell++].split("and");
				genericMethods.inputTextAndEnter(driver, By.xpath("//div/label[contains(text(),'From Date')]/following::input[@class='form-control input-sm']"), dateSeparation[0], test);
				genericMethods.inputTextAndEnter(driver, By.xpath("//div/label[contains(text(),'To Date')]/following::input[@class='form-control input-sm']"), dateSeparation[1], test);
				genericMethods.inputTextAndEnter(driver, By.xpath("//div/input[@id='email-subject-text']"), rowElements[cell++], test);
				genericMethods.inputTextAndEnter(driver, By.xpath("//label/following::textarea[@id='email-message-text']"), rowElements[cell++], test);
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


	int ruleCnt=1;
	public void documentManagement(WebDriver driver, String uiTemplate, String entityName, String[] rule, ExtentTest test){
		try{
			fluentWait(driver, By.xpath("//h5[text()='DOCMAN']"), 5, 30);
			genericMethods.clickElement(driver, By.xpath("//b[text()='"+uiTemplate+"']"), test);
			genericMethods.clickElement(driver, By.xpath("//div[@placeholder='Select a entity in the list...']"), test);
			genericMethods.inputTextAndEnter(driver, By.xpath("//input[@placeholder='Select a entity in the list...']"), entityName, test);
			genericMethods.selectSingleCheckbox(driver, By.xpath("//p/input[@type='checkbox' and contains(@ng-change,'CREATE')]"), test);
			genericMethods.selectSingleCheckbox(driver, By.xpath("//p/input[@type='checkbox' and contains(@ng-change,'VIEW')]"), test);
			for(String ruleCondition: rule){
				genericMethods.enterText(driver, By.xpath("//div[@ng-repeat='statement in rule.success.statements']["+ruleCnt+"]//textarea[@id='statementrhsexpression']"), ruleCondition, test);
			}
			genericMethods.clickElement(driver, saveButton, test);
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			log.warn("documentManagement has not been performed successfully");
			e.printStackTrace();
		}
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
			Thread.sleep(3500);
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

			By clickConstruct = By.xpath("//div[@data-position-left='"+(++constructCountLeft)+"' and @data-position-top='"+(constructCountTop)+"']");
			By triggercnt = By.xpath("//div[@data-position-top='1' and @data-position-left='4']");

			if(driver.findElement(clickConstruct).isDisplayed()){
				System.out.println("First If ::");
				genericMethods.clickElement(driver, clickConstruct, test);
			}	
			/* if(driver.findElement(clickConstruct).isDisplayed() && driver.findElement(triggercnt).isDisplayed()){
					System.out.println("2nd If ::");

				}*/
			//	genericMethods.clickElement(driver, triggercnt, test);
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


	/*
	public boolean constructCounter(WebDriver driver, ExtentTest test)
	{
	try{
			By clickConstruct = By.xpath("//div[@data-position-left='"+(++constructCountLeft)+"' and @data-position-top='"+(constructCountTop)+"']");
			By triggercnt = By.xpath("//div[@data-position-top='1' and @data-position-left='4']");

				if(driver.findElement(clickConstruct).isDisplayed()){
					System.out.println("First If ::");
					genericMethods.clickElement(driver, clickConstruct, test);
				}	
				 if(driver.findElement(clickConstruct).isDisplayed() && driver.findElement(triggercnt).isDisplayed()){
					System.out.println("2nd If ::");

				}
			genericMethods.clickElement(driver, triggercnt, test);
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

	 */


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

	/*public void email(WebDriver driver, ExtentTest test) throws InterruptedException, InvalidFormatException, IOException{
		try{

			genericMethods.clickElement(driver, By.xpath("//p[contains(text(),'EMAIL')]"), test);         
			fluentWait(driver, By.xpath("//div[text()='EMAIL']"), 5, 30);
			genericMethods.clickElement(driver, By.xpath("//div[text()='EMAIL']"), test);
			genericMethods.waitForElementVisibility(driver, By.xpath("//h5[text()='EMAIL']"), 20);
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Email");
			String[] rowElements = new String[50];
			int cell=0;
			for(int row=1;row<=sheet.getLastRowNum();row++)
			{
				for(int value=0;value<sheet.getRow(row).getLastCellNum();value++)
				{
					System.out.println(sheet.getRow(row).getLastCellNum());
					rowElements[value]=sheet.getRow(row).getCell(value).toString();
					System.out.println(rowElements[value]);
				}
				By x=By.xpath("//span[contains(text(),'Select or search a source in the list...')]");
				By y=By.xpath("//input[@placeholder='Select or search a source in the list...']");
				genericMethods.clickElement(driver, x, test);
				genericMethods.inputTextAndEnter(driver, y, rowElements[cell], test);
				if(rowElements[cell].trim().equalsIgnoreCase("static"))
				{
					cell++;
					genericMethods.enterText(driver, By.xpath("//input[@placeholder='Enter Email']"), rowElements[cell++], test);
				}
				else if(rowElements[cell].trim().equalsIgnoreCase("entity"))
				{
					cell++;       
					genericMethods.enterText(driver, By.xpath("//input[@title='Variable']"), rowElements[cell++], test);
				}
				else if(rowElements[cell].trim().equalsIgnoreCase("role"))
				{
					cell++;
					genericMethods.enterText(driver, By.xpath("//input[@placeholder='Enter Expression']"), rowElements[cell++], test);
				}
				else{
					cell+=2;
				}
				genericMethods.enterText(driver, By.xpath("//input[@id='email-subject-text']"), rowElements[cell++], test);
				genericMethods.enterText(driver, By.xpath("//trix-editor[@ng-model='vm.emailMessage']"), rowElements[cell++], test);
				genericMethods.clickElement(driver, saveButton, test);

			}
		}
		catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			log.warn("Email Process has not been performed successfully");
			e.printStackTrace();
		}
	}

	 */


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



	public void removeTextByWebElement(WebDriver driver, WebElement elementLocator){
		try{
			elementLocator.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			elementLocator.sendKeys(Keys.BACK_SPACE);
		}

		catch (NoSuchElementException e){
			errorMsg = ExceptionUtils.getStackTrace(e);
			log.warn("Element is not found in the Dom "+errorMsg);
		}
	}
}

