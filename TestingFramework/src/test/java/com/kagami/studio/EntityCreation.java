package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
	By addNewEntityButton = By.xpath("//a[@ng-click='createEntity()']");
	By entityName = By.id("entityName");
	By attributeName = By.id("attributeName");
	By attributeType = By.id("attributeType");
	By pastDate = By.xpath("//input[@id='mindate']");
	By attributeValidationtype = By.id("attributeValidationtypenn");
	By attributeValidationtypePkUq = By.id("attributeValidationtypepkuq"); 
	By addNewAttribute = By.xpath("//input[@value ='Add New Attribute']");
	By saveEntity = By.xpath("//button[contains(text(),'Save')]");
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
			WebDriverWait wait = new WebDriverWait(driver,10);
			Thread.sleep(2000);
			extent.loadConfig(new File("C:\\extent\\config.xml"));
			//test = extent.startTest("Test Suite: Create Entity", "Entity Generation in Studio");
			genericMethods.clickElement(driver, menuButton, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, entityCreationButton, test);
			wait.until(ExpectedConditions.elementToBeClickable(entityName));
			String pathOfFile = Global.testSheet; 
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Entity");
			Map<String, List<String>> addEntityList = new LinkedHashMap<String, List<String>>();
			int attNameCnt = 1;
			int attTypeCnt = 1;
			int attTypeNn = 1;
			int defaultAtt = 1;
			int uniqueKey = 1;
			int autoGen = 1;
			int entityCount = 1;
			//int rangeCount = 1;
			int totalAttributesCount = 2;
			int dateCount = 1;
			String entityNameValue = null;
			List<String> addEntityValues = null;


			int rowCnt = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int colCnt = row.getLastCellNum();

			for(int cell = 0; cell < colCnt ;cell++){
				String entityKeyValue = sheet.getRow(0).getCell(cell).toString();
				addEntityValues = new ArrayList<String>();	
				for(int irow = 1; irow <= rowCnt; irow++){
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
			List<String> minValue = addEntityList.get("Min");
			List<String> maxValue = addEntityList.get("Max");
			List<String> defaultValue = addEntityList.get("DefaultValue");
			List<String> primaryKey = addEntityList.get("PK");
			List<String> uniquKey = addEntityList.get("UQ");
			List<String> ag = addEntityList.get("AG");

			for(int value=0; value < attributeListCnt; value++){
				if(entity.get(value).toString().equalsIgnoreCase("Yes"))
				{
					if(!((getEntityName.get(value) == null || getEntityName.get(value).toString().equalsIgnoreCase(""))))
						genericMethods.enterText(driver, entityName, getEntityName.get(value), test);
					Thread.sleep(1000);
					if(genericMethods.ElementVisibility(driver, By.xpath("//div[contains(text(),'* Entity Name already exists...')]"), test))
					{
						studioCommonMethods.deleteExistingEntity(driver, getEntityName.get(value));
						value--;
						continue;
					}

					genericMethods.clickElementByJsExecutor(driver, addNewAttribute, test);
					genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), attributeName.get(value), test);
					genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), attributeType.get(value), test);

					if(validation.get(value).toString().equalsIgnoreCase("null"))
						genericMethods.deSelectCheckbox(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@name='attributeValidationtypenn']"), test);

					if(attributeType.get(value).toString().equalsIgnoreCase("Number")){
						studioCommonMethods.fluentWait(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtyperange']"), 30);
						System.out.println("waiting for checkbox with elementLocator "+" //tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtyperange']");
						genericMethods.clickElementByJsExecutor(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtyperange']"), test);
						if(!(minValue.get(value).toString().equalsIgnoreCase("Null") || minValue.get(value).toString().equalsIgnoreCase("")))
							genericMethods.enterText(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtypemin']"), minValue.get(value), test);
						if(!(maxValue.get(value).toString().equalsIgnoreCase("Null") || maxValue.get(value).toString().equalsIgnoreCase("")))
							genericMethods.enterText(driver,  By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtypemax']"), maxValue.get(value), test);
					}

					else if(attributeType.get(value).toString().equalsIgnoreCase("date")){
						if(maxValue.get(value).toString().equalsIgnoreCase("Past"))
							genericMethods.clickElement(driver, By.xpath("//input[@id='mindate"+(dateCount++)+"']"), test);
						if(maxValue.get(value).toString().equalsIgnoreCase("Future"))
							genericMethods.clickElement(driver, By.xpath("//input[@id='maxdate"+(dateCount++)+"']"), test);
					}

					genericMethods.enterText(driver, By.xpath("//tr["+(++defaultAtt)+"]//input[@placeholder='Default Value']"), defaultValue.get(value), test);	
					genericMethods.clickElement(driver, By.xpath("//tr["+(++uniqueKey)+"]//input[@id='attributeuniqueKeys']"), test);
					totalAttributesCount++;
					dateCount++;

				}

				else if(entity.get(value).toString().contains("Save"))
				{
					System.out.println("In else block");
					wait.until(ExpectedConditions.elementToBeClickable(saveEntity));
					genericMethods.clickElementByJsExecutor(driver, saveEntity, test);
					String entityName = entity.get(value).toString().substring(4);
					test.log(LogStatus.PASS, "Entity is created for the " +entityName+ "..");
					genericMethods.clickElementByJsExecutor(driver, addNewEntityButton, test);
					attNameCnt = 1;
					attTypeCnt = 1;
					attTypeNn = 1;
					defaultAtt = 1;
					uniqueKey = 1;
					autoGen = 1;
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
	
	public void deleteEntities(){
		try{
			WebDriverWait wait = new WebDriverWait(driver,10);
			genericMethods.clickElement(driver, menuButton, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, entityCreationButton, test);
			wait.until(ExpectedConditions.elementToBeClickable(entityName));
			int entityCnt = genericMethods.getXpathSize(driver, By.xpath("//ul[@class='leftcolumnlist']/li"), test);
			for(int entity=1; entity <= entityCnt; entity++){
			Thread.sleep(1000);	
			genericMethods.clickElement(driver, By.xpath("//ul[@class='leftcolumnlist']/li["+entity+"]//a[@class='pull-right' and contains(text,deleteentity)]"), test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, By.xpath("//button[@class='ajs-button ajs-ok' and text()='OK']"), test);
			entity--;
			entityCnt--;
		}
	}
		catch(Exception e)
		{ 
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}
}
