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
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EntityCreation implements GlobalXpath{
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	public Logger log = Logger.getLogger("EntityCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	Global global = new Global();
	MasterData master = new MasterData(driver);
	String[] entityArray = {"Day Calculation","Data Import", "Data Validation", "Daily Attendance","Consolidated Attendance"};

	public EntityCreation(WebDriver driver)
	{
		this.driver = driver;
	}

	public void entityGeneration(ExtentTest test, String sheetName)
	{
		try{
			extent = ExtentManager.Instance();
			WebDriverWait wait = new WebDriverWait(driver,8);
			Thread.sleep(2000);
			genericMethods.clickElement(driver, By.xpath("//a[contains(text(),'Entity Management')]"), test);
			Thread.sleep(1000);
			String workbookTestData = sheetName; 
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Entity");
			Map<String, List<String>> addEntityList = new LinkedHashMap<String, List<String>>();
			int attNameCnt = 1;
			int attTypeCnt = 1;
			int defaultAtt = 1;
			int uniqueKey = 1;
			int totalAttributesCount = 2;
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
				}
				addEntityList.put(entityKeyValue, addEntityValues);
				System.out.println(addEntityList);
			}


			int entityListCnt = addEntityList.size();
			int attributeListCnt = addEntityValues.size();
			System.out.println(entityListCnt+" "+attributeListCnt);

			List<String> entity = addEntityList.get("Entity?");
			List<String> getEntityName = addEntityList.get("EntityName");
			List<String> getEntityType = addEntityList.get("EntityType");
			List<String> attributeName = addEntityList.get("AttributeName");
			List<String> attributeType = addEntityList.get("AttributeType");
			List<String> validation = addEntityList.get("Validation");
			List<String> attributeValidations = addEntityList.get("AttributeValidations");
			List<String> defaultValue = addEntityList.get("DefaultValue");
			addEntityList.get("UQ");
			addEntityList.get("AG");

			int attCountforReport = 0;
			for(int value=0; value < attributeListCnt; value++){
				
				if(entity.get(value).toString().equalsIgnoreCase("Yes"))
				{
					
					if(!((getEntityName.get(value) == null || getEntityName.get(value).toString().equalsIgnoreCase("")))){

						genericMethods.clickElement(driver, By.xpath("//a[@ng-click='addEntity()']"), test);
						genericMethods.waitForElementVisibility(driver, By.xpath("//input[@ng-change='createEntityNameChanged(newEntity)' and @placeholder='Enter Entity Name']"), 20);
						genericMethods.enterText(driver, By.xpath("//input[@ng-change='createEntityNameChanged(newEntity)' and @placeholder='Enter Entity Name']"), getEntityName.get(value), test);
						studioCommonMethods.getEntityType(driver, getEntityType, value);
						
					}
					Thread.sleep(1000);
					if((genericMethods.ElementVisibility(driver, By.xpath("//div[@class='haserror pull-left ng-binding ng-scope' or contains(text(),'*Entity with same name already exists')]"), test)))
					{
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
					studioCommonMethods.getEntityDataTypes(driver, attributeType, attributeValidations, value, totalAttributesCount);
					genericMethods.enterText(driver, By.xpath("//tr["+(++defaultAtt)+"]//input[@placeholder='Default Value']"), defaultValue.get(value), test);	
					genericMethods.clickElement(driver, By.xpath("//tr["+(++uniqueKey)+"]//input[@type='checkbox' and @ng-click='setAsUniqueKey(attribute)']"), test);
					
					totalAttributesCount++;
					attCountforReport++;

				}

				else if(entity.get(value).toString().contains("Save"))
				{
				
					System.out.println("In else block");
					wait.until(ExpectedConditions.elementToBeClickable(updateEntity));
					genericMethods.clickElementByJsExecutor(driver, updateEntity, test);
					String entityName = entity.get(value).toString().substring(4);
					test.log(LogStatus.PASS, "Entity is created as '"+entityName.trim()+"' with "+attCountforReport+" attributes..");
					if(driver.findElement(By.xpath("//label[1]/input[@type='radio']")).isSelected()){
						master.masterDataCreation(driver, test);
					}
					attNameCnt = 1;
					attTypeCnt = 1;
					defaultAtt = 1;
					uniqueKey = 1;
					totalAttributesCount = 2;
					attCountforReport = 0;
					
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();;
			test.log(LogStatus.FAIL, "Entity Creation is failed due to below reasons.");
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, "Exception is: "+ExceptionUtils.getStackTrace(e));
		}

	}
}
