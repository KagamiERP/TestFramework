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
import org.testng.Assert;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Temp1 {

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
	By entityId = By.id("entityid");
	By attributeName = By.id("attributeName");
	By attributeName1 = By.xpath("//tr[1]//input[@id='attributeName']");
	By attributeType = By.id("attributeType");
	By attributeValidationtype = By.id("attributeValidationtypenn");
	By attributeValidationtypePkUq = By.id("attributeValidationtypepkuq"); 
	By addNewAttribute = By.xpath("//input[@value ='Add New Attribute']");
	By saveEntity = By.id("saveEntitybtn");

	public Temp1(WebDriver driver)
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
			Thread.sleep(2000);
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
			test = extent.startTest("Create Entity", "Entity Generation in Studio");
			genericMethods.clickElement(driver, menuButton, test);
			genericMethods.clickElement(driver, entityCreationButton, test);
			genericMethods.clickElement(driver, addNewEntityButton, test);
			Thread.sleep(1500);
			String pathOfFile = "./TestData/testInput.xlsx";
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Sheet3");
			String[] firstRowElements = new String[50];
			int attNameCnt = 0;
			int attTypeCnt = 0;
			int attTypeNn = 0;
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
						genericMethods.enterText(driver, entityName,firstRowElements[cell++], test);Thread.sleep(1000);
						genericMethods.enterText(driver, entityId, firstRowElements[cell++], test);Thread.sleep(1000);	
						genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), firstRowElements[cell++], test);Thread.sleep(1000);
						genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), "Text", test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++attTypeNn)+"]//input[@id='attributeValidationtypenn']"), test);Thread.sleep(1000);
						genericMethods.clickElement(driver, attributeValidationtypePkUq, test);
						genericMethods.clickElement(driver, addNewAttribute, test);
					}

					else if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("No")){
						int newCell = 1;
						System.out.println("In No block"); 
						genericMethods.enterText(driver, By.xpath("//tr["+(++attNameCnt)+"]//input[@id='attributeName']"), firstRowElements[newCell+2], test);Thread.sleep(1000);
						genericMethods.selectByVisibleText(driver,By.xpath( "//tr["+(++attTypeCnt)+"]//select[@id='attributeType']"), "Text", test);Thread.sleep(1000);
						genericMethods.clickElement(driver, By.xpath("//tr["+(++attTypeNn)+"]//input[@id='attributeValidationtypenn']"), test);Thread.sleep(1000);
						genericMethods.clickElement(driver, addNewAttribute, test);Thread.sleep(1000);
					}
					else
					{
						System.out.println("In else block");
						genericMethods.clickElement(driver, saveEntity, test);Thread.sleep(2000);
						genericMethods.clickElement(driver, addNewEntityButton, test);Thread.sleep(1500);
						genericMethods.clickElement(driver, addNewAttribute, test);Thread.sleep(1500);
						attNameCnt = 0;
						attTypeCnt = 0;
						attTypeNn = 0;
					}

				}
				test.log(LogStatus.PASS, "Create entity test passed");
			}
			catch(Exception e)
			{
				test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
				test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
			}

			finally{
				extent.endTest(test);
				extent.flush();
			}


		}catch(Exception e)
		{

		}
	}

	/*catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	 */






}
