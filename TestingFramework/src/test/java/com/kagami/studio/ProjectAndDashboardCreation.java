package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.NoSuchElementException;

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

public class ProjectAndDashboardCreation {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProjectCreationBase.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);

	By newProject = By.xpath("//button[@ng-click='add()']");
	By newProjectText = By.xpath("//input[@id='new-project-text']");
	By projectName = By.xpath("//input[@id='new-project-text']");
	By createButton = By.xpath("//button[contains(text(),'Create')]");
	By newModuleButton = By.xpath("//button[@ng-click='addModule()']");
	By newModuleText = By.xpath("//input[@class='ajs-input']");
	By newSubModuleButton = By.xpath("//button[@ng-click='addSubModule()']");
	By newSubModuleText = By.xpath("//input[@class='ajs-input']");
	By newProcessButton = By.xpath("//button[@ng-click='addProcess()']");
	By newProcessText = By.xpath("//input[@class='ajs-input']");
	By saveProcess = By.xpath("//button[@ng-click='saveProcess()']");
	By ok = By.xpath("//div[text()='Save']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By project  = By.xpath("//h3[text()='test']");
	By module = By.xpath("//div[@class='studio-card-inner']//p[(text()='Module')]");
	By subModule = By.xpath("//p[(text()='Sub Module')]");
	By newProcess = By.xpath("//button[@class='btn btn-primary process-add pull-right']");
	By processName = By.xpath("//input[@class='ajs-input']");
	By okButtonModule = By.xpath("//div[text()='Create Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By okButtonSubModule = By.xpath("//div[text()='Create Sub Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By okButtonProcess = By.xpath("//div[text()='Create Process']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By existingProcess = By.xpath("//p[text()='Process']");
	By existingProjectErrorMsg = By.xpath("//div[text()='*Should not create same Project Name']");
	By kagamiLogo = By.xpath("//img[@src='assets/img/logo.png']");

	//	static int plusInc = 10000;
	//	By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");

	public ProjectAndDashboardCreation(WebDriver driver)
	{
		this.driver = driver;
	}

	public void newProjectCreation(ExtentTest test, String sheetName)
	{
		try{

			int cellValue = 0;	
			String testDataSheet = sheetName;
			File f = new File(testDataSheet);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Process");
			String[] firstRowElements = new String[50];

			int projectRowCount = 1;
			for(int outerRow = 1; outerRow <= projectRowCount; outerRow++){
				for(int row = outerRow; row <= projectRowCount; row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						//System.out.print(firstRowElements[rowItr]+" ");
					}

					genericMethods.clickElement(driver, kagamiLogo, test);
					Thread.sleep(1000);
					genericMethods.clickElementByJsExecutor(driver, newProject, test);
					genericMethods.enterText(driver, newProjectText, firstRowElements[cellValue], test);
					Thread.sleep(1000);


					if(genericMethods.ElementVisibility(driver, existingProjectErrorMsg, test))
					{
						studioCommonMethods.deleteExistingProject(driver, firstRowElements[cellValue]);
						Thread.sleep(1500);
						genericMethods.clickElementByJsExecutor(driver, newProject, test);
						genericMethods.enterText(driver, newProjectText, firstRowElements[cellValue], test);
						Thread.sleep(1000);
					}
					genericMethods.clickElement(driver, createButton, test);
					test.log(LogStatus.PASS, "Project with name "+firstRowElements[cellValue]+" is Created.");
					genericMethods.clickElementByJsExecutor(driver,By.xpath("//h3[text()='"+(firstRowElements[cellValue])+"']") , test);
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newModuleButton, test);
					Thread.sleep(1500);
					genericMethods.enterText(driver, newModuleText, firstRowElements[++cellValue], test);
					Thread.sleep(1000);
					genericMethods.clickElement(driver, okButtonModule, test);
					test.log(LogStatus.PASS, "Module with name "+firstRowElements[cellValue]+" is Created.");
					genericMethods.clickElement(driver,By.xpath("//h3[text()='"+(firstRowElements[cellValue])+"']") , test);


					String allSubModules = sheet.getRow(1).getCell(2).toString();
					String[] listOfSubModules = allSubModules.split(","); 
					WebDriverWait wait = new WebDriverWait(driver, 8);
					for(String singleSubModule : listOfSubModules)
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(newSubModuleButton));
						//Thread.sleep(1500);
						genericMethods.clickElement(driver, newSubModuleButton, test);
						Thread.sleep(1500);
						//wait.until(ExpectedConditions.visibilityOfElementLocated(newSubModuleText));
						genericMethods.enterText(driver, newSubModuleText, singleSubModule, test);
						genericMethods.clickElement(driver, okButtonSubModule, test);
						test.log(LogStatus.PASS, "SubModule with name "+singleSubModule+" is Created.");
						Thread.sleep(1000);
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(singleSubModule)+"')]"), test);
						processCreation(test, testDataSheet);
	
					
					}
					//test.log(LogStatus.PASS, "Project Created");
				}
			}
		}

		catch(NoSuchElementException e)
		{

		}
		catch(org.openqa.selenium.TimeoutException e)
		{

		}
		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

	}

	int rowCount = 3;
	public void processCreation(ExtentTest test, String sheetName)
	{
		try{
			int cellValue = 1;	
			String testDataSheet = sheetName;
			File f = new File(testDataSheet);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Process");
			String[] firstRowElements = new String[50];
			WebDriverWait wait = new WebDriverWait(driver, 8);

			
				for(int row = rowCount; row <= sheet.getLastRowNum(); row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						//System.out.print(firstRowElements[rowItr]+" ");
					}


					if(sheet.getRow(row).getCell(1).toString().contains("Process"))
					{
						//wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessButton));
						Thread.sleep(2000);
						genericMethods.click(driver, newProcessButton, test);
					//	wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessText));
						Thread.sleep(1500);
						genericMethods.enterText(driver, newProcessText, firstRowElements[cellValue], test);
						genericMethods.click(driver, okButtonProcess, test);
						Thread.sleep(1500);
						genericMethods.click(driver,By.xpath("//h3[text()='"+(firstRowElements[cellValue++])+"']") , test);
						
						Thread.sleep(1500);
						rowCount++;
					}

					if(sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("create"))
					{
						Thread.sleep(700);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.create(driver, test, firstRowElements[++cellValue],firstRowElements[++cellValue], testDataSheet);
						cellValue = 2;
						rowCount++;
						continue;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("delete"))
					{
						Thread.sleep(1000);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.delete(driver, test ,firstRowElements[cellValue+2]);
						cellValue = 2;
						rowCount++;
						continue;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("view"))
					{
						Thread.sleep(1000);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.view(driver, test ,firstRowElements[++cellValue],firstRowElements[++cellValue]);
						cellValue = 2;
						rowCount++;
						continue;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("switch"))
					{
						Thread.sleep(1000);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.Switch(driver, test ,firstRowElements[cellValue+2]);
						cellValue = 2;
						rowCount++;
						continue;
					}

					else if (sheet.getRow(row).getCell(2).toString().contains("Save"))
					{
						Thread.sleep(1000);
						genericMethods.click(driver, saveProcess, test);
						Thread.sleep(1000);
						genericMethods.click(driver, ok, test);
						test.log(LogStatus.PASS, sheet.getRow(row).getCell(2).toString().substring(4) +" has been Created.");
						driver.navigate().back();
						driver.navigate().back();
					//	studioCommonMethods.navigateToSubModule(driver);
						break;
						//rowCount++;

					}
		
			}
			driver.navigate().back();
		
	//		customizeDashBoard.customizeDashBoard(driver,test);

		}

		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));

		}

		}




}
