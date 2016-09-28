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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EntityGenerationBase {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("EntityGenerationBase.class");
	GenericMethods genericMethods = new GenericMethods();

	By uname = By.id("inputUsername");
	By password = By.id("inputPassword");
	By loginButton = By.xpath("//button[contains(text(),'SIGN IN')]");
	By menuButton = By.xpath("//a[contains(text(),'Menu')]");
	By entityCreationButton = By.linkText("Entity Creation");
	By addNewEntityButton = By.xpath("//span[contains(text(),'Add New')]");
	By entityName = By.id("entityName");
	By attributeName = By.id("attributeName");
	By attributeType = By.id("attributeType");

	By attributeValidationtype = By.id("attributeValidationtypenn");
	By attributeValidationtypePkUq = By.id("attributeValidationtypepkuq"); 
	By addNewAttribute = By.xpath("//input[@value ='Add New Attribute']");
	By saveEntity = By.xpath("//button[contains(text(),'Save')]");
	String[] entityArray = {"Day Calculation","Data Import", "Data Validation", "Daily Attendance","Consolidated Attendance"};
	
	public EntityGenerationBase(WebDriver driver)
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

		}
	}

	public void entityGeneration()
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,10);
			Thread.sleep(5000);
			test = extent.startTest("Create Entity", "Entity Generation in Studio");
			genericMethods.clickElement(driver, menuButton, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, entityCreationButton, test);
			wait.until(ExpectedConditions.elementToBeClickable(entityName));
			String pathOfFile = "./TestData/testInput.xlsx";
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Sheet4");
			String[] firstRowElements = new String[50];
			int attNameCnt = 1;
			int attTypeCnt = 1;
			int attTypeNn = 1;
			int defaultAtt = 1;
			int uniqueKey = 1;
			int autoGen = 1;
			int entityCount = 0;
			try{
				for(int row = 1; row <= sheet.getLastRowNum(); row++){
					for (int rowItr = 1; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++)
					{
						firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						System.out.print(firstRowElements[rowItr]+" ");
					}
					int cell = 1;
					if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("Yes"))
					{
						System.out.println("In Yes block");
						log.info("Entering Excel data to the application");
						genericMethods.enterText(driver, entityName,firstRowElements[cell++], test);wait.until(ExpectedConditions.elementToBeClickable(addNewAttribute));
						genericMethods.clickElement(driver, addNewAttribute, test);wait.until(ExpectedConditions.elementToBeClickable(attributeName));
						genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), firstRowElements[cell++], test);
						genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), firstRowElements[cell++], test);
						genericMethods.enterText(driver, By.xpath("//tr["+(++defaultAtt)+"]//input[@placeholder='Default Attribute']"), firstRowElements[cell++], test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++attTypeNn)+"]//input[@id='attributeValidationtypenn']"), test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++uniqueKey)+"]//input[@id='attributeuniqueKeys']"), test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++autoGen)+"]//input[@id='attributeautogen']"), test);Thread.sleep(1000);


					}

					else if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("No")){
						int newCell = 2;
						System.out.println("In No block"); 
						genericMethods.clickElement(driver, addNewAttribute, test);
						genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), firstRowElements[newCell++], test);Thread.sleep(1000);
						genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), firstRowElements[newCell++], test);Thread.sleep(1000);
						genericMethods.enterText(driver, By.xpath("//tr["+(++defaultAtt)+"]//input[@placeholder='Default Attribute']"), firstRowElements[newCell++], test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++attTypeNn)+"]//input[@id='attributeValidationtypenn']"), test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++uniqueKey)+"]//input[@id='attributeuniqueKeys']"), test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++autoGen)+"]//input[@id='attributeautogen']"), test);Thread.sleep(1000);
						
					}
					else
					{
						System.out.println("In else block");
						genericMethods.clickElement(driver, saveEntity, test);Thread.sleep(2000);
						test.log(LogStatus.PASS, "Entity is created for the " +entityArray[entityCount++]+ "..");
						//extent.endTest(test);
						//extent.flush();
						genericMethods.clickElement(driver, addNewEntityButton, test);Thread.sleep(1500);
						attNameCnt = 1;
						attTypeCnt = 1;
						attTypeNn = 1;
						defaultAtt = 1;
						uniqueKey = 1;
						autoGen = 1;
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
		finally{
			extent.endTest(test);
			extent.flush();
		}

	}

	/*catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	 */






}
