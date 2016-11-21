package com.kagami.studio;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
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

public class EntityGenerationBase {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	public Logger log = Logger.getLogger("EntityGenerationBase.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);

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
	String[] entityArray = {"drivingLicenseDetails","immigrationInfo", "Data Validation", "Daily Attendance","Consolidated Attendance"};
	By minValue = By.xpath("//input[@id='attributeValidationtypemin']");
	By maxValue = By.xpath("//input[@id='attributeValidationtypemax']");
	By numberRangeCheckbox = By.xpath("//input[@id='attributeValidationtyperange']");

	public EntityGenerationBase(WebDriver driver)
	{
		this.driver = driver;
	}

	public void entityGeneration(ExtentTest test)
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,8);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, menuButton, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, entityCreationButton, test);
			wait.until(ExpectedConditions.elementToBeClickable(entityName));
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Entity");
			String[] firstRowElements = new String[50];
			int attNameCnt = 1;
			int attTypeCnt = 1;
			int attTypeNn = 1;
			int defaultAtt = 1;
			int uniqueKey = 1;
			int autoGen = 1;
			int entityCount = 0;
			int totalAttributesCount = 2;
			int dateCount = 1;

			try{

				int dateId = 1;	
				for(int row = 1; row <= sheet.getLastRowNum(); row++){
					for (int rowItr = 1; rowItr < sheet.getRow(row).getLastCellNum(); rowItr++)
					{
						firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						System.out.print(firstRowElements[rowItr]+" ");
					}
					//genericMethods.enterText(driver, entityName, entityNameList.get(skeyEntity).get(entityNameCount).toString(), test);
					int cell = 1;
					String entityValue = sheet.getRow(1).getCell(1).toString();
					if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("Yes"))
					{
						String entityNameValue = firstRowElements[cell];
						System.out.println("In Yes block");
						log.info("Entering Excel data to the application");

						if(!((sheet.getRow(row).getCell(1) == null) || sheet.getRow(row).getCell(1).toString().equalsIgnoreCase("")))	
							genericMethods.enterText(driver, entityName,firstRowElements[cell], test);
						cell++;
						Thread.sleep(1000);
						if(genericMethods.ElementVisibility(driver, By.xpath("//div[contains(text(),'* Entity Name already exists...')]"), test)){

							studioCommonMethods.deleteExistingEntity(driver, entityNameValue);
							--row;
							continue;
						}
						genericMethods.clickElementByJsExecutor(driver, addNewAttribute, test);
						genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), firstRowElements[cell++], test);
						genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), firstRowElements[cell], test);

						Thread.sleep(1000);

						if(firstRowElements[cell].equalsIgnoreCase("Number")){
							cell++;
							Thread.sleep(2000);
							System.out.println("waiting for checkbox with elementLocator "+" //tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtyperange']");
							genericMethods.clickElementByJsExecutor(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtyperange']"), test);
							if(!(firstRowElements[++cell].equalsIgnoreCase("Null") || firstRowElements[cell].equalsIgnoreCase("")))
								genericMethods.enterText(driver, By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtypemin']"), firstRowElements[cell], test);
							if(!(firstRowElements[++cell].equalsIgnoreCase("Null") || firstRowElements[cell].equalsIgnoreCase("")))
								genericMethods.enterText(driver,  By.xpath("//tr["+(totalAttributesCount)+"]//input[@id='attributeValidationtypemax']"), firstRowElements[cell], test);
						}

						else if(firstRowElements[cell].equalsIgnoreCase("date")){
							cell = cell+3;
							if(firstRowElements[cell].equalsIgnoreCase("Past"))
								genericMethods.clickElement(driver, By.xpath("//input[@id='mindate"+(dateCount++)+"']"), test);
							if(firstRowElements[cell].equalsIgnoreCase("Future"))
								genericMethods.clickElement(driver, By.xpath("//input[@id='maxdate"+(dateCount++)+"']"), test);
						}

						else if(firstRowElements[cell].equalsIgnoreCase("Boolean")){
							cell = cell+3;
						}

						else{
							cell = cell+3;
						}
						//if(!((firstRowElements[cell].equalsIgnoreCase("True")||(firstRowElements[cell].equalsIgnoreCase("False"))
						genericMethods.enterText(driver, By.xpath("//tr["+(++defaultAtt)+"]//input[@placeholder='Default Value']"), firstRowElements[cell+1], test);
						//genericMethods.selectByVisibleText(driver, By.xpath("//tr["+(++defaultAtt)+"]//select[@class='form-control ng-pristine ng-untouched ng-valid ng-empty']"), firstRowElements[cell+1], test);	
						genericMethods.clickElement(driver, By.xpath("//tr["+(++uniqueKey)+"]//input[@id='attributeuniqueKeys']"), test);//Thread.sleep(200);
						totalAttributesCount++;
						dateCount++;

					}
					else if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("Save"))
					{
						System.out.println("In else block");
						wait.until(ExpectedConditions.elementToBeClickable(saveEntity));
						genericMethods.clickElementByJsExecutor(driver, saveEntity, test);
						test.log(LogStatus.PASS, "Entity is created for the " +entityArray[entityCount++]+ "..");
						genericMethods.clickElementByJsExecutor(driver, addNewEntityButton, test);
						attNameCnt = 1;
						attTypeCnt = 1;
						attTypeNn = 1;
						defaultAtt = 1;
						uniqueKey = 1;
						autoGen = 1;
						totalAttributesCount = 2;
						dateCount = 1;
						Thread.sleep(1500);
								}
			
				}
			}

			catch(Exception e)
			{
				test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
				test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			}

		}catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}


	}
}
