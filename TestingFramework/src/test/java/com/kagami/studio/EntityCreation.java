package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EntityCreation {
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	public Logger log = Logger.getLogger("EntityGenerationBase.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	Global global = new Global();
	//WebDriverWait wait = new WebDriverWait(driver, 60);

	By uname = By.id("inputUsername");
	By password = By.id("inputPassword");
	By loginButton = By.xpath("//button[contains(text(),'SIGN IN')]");
	By menuButton = By.xpath("//a[@class='dropdown-toggle' and text()='Menu ']");
	By entityCreationButton = By.linkText("Entity Management");
	By addNewEntityButton = By.xpath("//a[@ng-click='addEntity()']");
	By entityName = By.id("entityName");
	By attributeName = By.id("attributeName");
	By attributeType = By.id("attributeType");
	By pastDate = By.xpath("//input[@id='mindate']");
	By attributeValidationtype = By.id("attributeValidationtypenn");
	By attributeValidationtypePkUq = By.id("attributeValidationtypepkuq"); 
	By addNewAttribute = By.xpath("//input[@value ='Add Attribute']");
	By updateEntity = By.xpath("//button[contains(text(),'Update')]");
	String[] entityArray = {"Day Calculation","Data Import", "Data Validation", "Daily Attendance","Consolidated Attendance"};
	//String entityValue; 

	By minValue = By.xpath("//input[@id='attributeValidationtypemin']");
	By maxValue = By.xpath("//input[@id='attributeValidationtypemax']");
	By numberRangeCheckbox = By.xpath("//input[@id='attributeValidationtyperange']");

	public EntityCreation(WebDriver driver)
	{
		this.driver = driver;
	}

	public void entityGeneration(ExtentTest test)
	{
		try{
			extent = ExtentManager.Instance();
			WebDriverWait wait = new WebDriverWait(driver,8);
			Thread.sleep(2000);
			
			//genericMethods.waitForElementVisibility(driver, By.xpath("//a[@href='#project_project_1']"), 30);
			//genericMethods.clickElementByJsExecutor(driver, By.xpath("//h3[text()='FreedomFighters']"), test);
			genericMethods.clickElement(driver, By.xpath("//a[contains(text(),'Entity Management')]"), test);
			genericMethods.clickElement(driver, By.xpath("//a[@ng-click='addEntity()']"), test);
			Thread.sleep(1000);
			String pathOfFile = Global.testSheet; 
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Entity");
			Map<String, List<String>> addEntityList = new LinkedHashMap<String, List<String>>();
			int attNameCnt = 1;
			int attTypeCnt = 1;
			int defaultAtt = 1;
			int uniqueKey = 1;
			int totalAttributesCount = 2;
			int dateCount = 1;
			List<String> addEntityValues = null;


			int rowCnt = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int colCnt = row.getLastCellNum();

			for(int cell = 0; cell < colCnt ;cell++){
				String entityKeyValue = sheet.getRow(2).getCell(cell).toString();
				addEntityValues = new ArrayList<String>();	
				for(int irow = 3; irow <= rowCnt; irow++){
					String entityValues = sheet.getRow(irow).getCell(cell).toString();
					addEntityValues.add(entityValues);
					//System.out.println(addEntityValues);
				}
				addEntityList.put(entityKeyValue, addEntityValues);
				System.out.println(addEntityList);
			}


			int entityListCnt = addEntityList.size();
			int attributeListCnt = addEntityValues.size();
			System.out.println(entityListCnt+" "+attributeListCnt);

			List<String> entity = addEntityList.get("Entity?");
			List<String> getEntityName = addEntityList.get("EntityName");
			List<String> attributeName = addEntityList.get("AttributeName");
			List<String> attributeType = addEntityList.get("AttributeType");
			List<String> validation = addEntityList.get("Validation");
			/*List<String> minValue = addEntityList.get("Min");
			List<String> maxValue = addEntityList.get("Max");*/
			List<String> attributeValidations = addEntityList.get("AttributeValidations");
			List<String> defaultValue = addEntityList.get("DefaultValue");
			addEntityList.get("UQ");
			addEntityList.get("AG");

			for(int value=0; value < attributeListCnt; value++){
				if(entity.get(value).toString().equalsIgnoreCase("Yes"))
				{
					if(!((getEntityName.get(value) == null || getEntityName.get(value).toString().equalsIgnoreCase(""))))
						genericMethods.enterText(driver, By.xpath("//input[@ng-change='createEntityNameChanged(newEntity)' and @placeholder='Enter Entity Name']"), getEntityName.get(value), test);
					Thread.sleep(1000);
					if((genericMethods.ElementVisibility(driver, By.xpath("//div[@class='haserror pull-left ng-binding ng-scope' or contains(text(),'*Entity with same name already exists')]"), test)))
					{
						//need to add method for closing the pop-up
						//studioCommonMethods.dismissAlertPopUp(driver);
						genericMethods.clickElement(driver, By.xpath("//button[@class='btn btn-default' and contains(text(),'Close')]"), test);
						studioCommonMethods.deleteExistingEntity(driver, getEntityName.get(value));
						driver.navigate().refresh();
						Thread.sleep(1000);
						value--;
						continue;
					}
					genericMethods.clickElement(driver, By.xpath("//button[@class='btn btn-primary' and contains(text(),'Create')]"), test);
					Thread.sleep(1000);
					genericMethods.clickElementByJsExecutor(driver, addNewAttribute, test);
					genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), attributeName.get(value), test);
					genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), attributeType.get(value), test);

					if(validation.get(value).toString().equalsIgnoreCase("null"))
						genericMethods.deSelectCheckbox(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@name='attributeNotNull']"), test);
					
					
				
					if(attributeType.get(value).toString().equalsIgnoreCase("Number")){
						genericMethods.clickElement(driver, By.xpath("//tr["+(totalAttributesCount)+"]//a[@ng-click='editValidations(attribute)']"), test);
						studioCommonMethods.fluentWait(driver, By.xpath("//div[@class='modal-header']//h4[text()='Edit Attribute Validations']"), 5, 30);
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
						studioCommonMethods.fluentWait(driver, By.xpath("//div[@class='modal-header']//h4[text()='Edit Attribute Validations']"), 5, 30);
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
					studioCommonMethods.fluentWait(driver, By.xpath("//div[@class='modal-header']//h4[text()='Edit Attribute Validations']"), 5, 30);
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
					
					genericMethods.enterText(driver, By.xpath("//tr["+(++defaultAtt)+"]//input[@placeholder='Default Value']"), defaultValue.get(value), test);	
					genericMethods.clickElement(driver, By.xpath("//tr["+(++uniqueKey)+"]//input[@type='checkbox' and @ng-click='setAsUniqueKey(attribute)']"), test);
					totalAttributesCount++;
					dateCount++;

				}

				else if(entity.get(value).toString().contains("Save"))
				{
					System.out.println("In else block");
					wait.until(ExpectedConditions.elementToBeClickable(updateEntity));
					genericMethods.clickElementByJsExecutor(driver, updateEntity, test);
					String entityName = entity.get(value).toString().substring(4);
					test.log(LogStatus.PASS, "Entity is created for the " +entityName+ "..");
				//	genericMethods.clickElementByJsExecutor(driver, addNewEntityButton, test);
					attNameCnt = 1;
					attTypeCnt = 1;
					defaultAtt = 1;
					uniqueKey = 1;
					totalAttributesCount = 2;
					dateCount = 1;
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();;
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

	}
}
