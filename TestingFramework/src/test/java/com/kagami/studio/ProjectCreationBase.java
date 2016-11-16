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

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import TestingFrameWork.AutomationTestFramework.ExtentManager;
import TestingFrameWork.AutomationTestFramework.GenericMethods;

public class ProjectCreationBase {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProjectCreationBase.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);

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
	By okButton = By.xpath("//button[text()='OK']");
	By okButtonSubModule = By.xpath("//div[text()='Create Sub Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By okButtonProcess = By.xpath("//div[text()='Create Process']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By existingProcess = By.xpath("//p[text()='Process']");

	//	static int plusInc = 10000;
	//	By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");

	public ProjectCreationBase(WebDriver driver)
	{
		this.driver = driver;
	}

	public void newProjectCreation()
	{
		try{

			int cellValue = 0;	
			extent = ExtentManager.Instance();
			extent.loadConfig(new File("C:\\extent\\config.xml"));
			test = extent.startTest("Create Project", "Create Project....");
			String pathOfFile = "./TestData/testInput.xlsx";
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Process");
			String[] firstRowElements = new String[50];
			WebDriverWait wait = new WebDriverWait(driver, 15);

			for(int outerRow = 1; outerRow <= sheet.getLastRowNum(); outerRow++){
				for(int row = outerRow; row <= sheet.getLastRowNum(); row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						System.out.print(firstRowElements[rowItr]+" ");
					}

					wait.until(ExpectedConditions.visibilityOfElementLocated(newProject));
					genericMethods.clickElement(driver, newProject, test);
					genericMethods.enterText(driver, newProjectText, firstRowElements[cellValue], test);
					genericMethods.clickElement(driver, createButton, test);
					genericMethods.clickElement(driver,By.xpath("//h3[text()='"+(firstRowElements[cellValue])+"']") , test);
					wait.until(ExpectedConditions.visibilityOfElementLocated(newModuleButton));
					genericMethods.clickElement(driver, newModuleButton, test);
					wait.until(ExpectedConditions.visibilityOfElementLocated(newModuleText));
					genericMethods.enterText(driver, newModuleText, firstRowElements[++cellValue], test);
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(okButton));
					genericMethods.clickElement(driver, okButton, test);
					genericMethods.clickElement(driver,By.xpath("//h3[text()='"+(firstRowElements[cellValue])+"']") , test);


					String allSubModules = sheet.getRow(1).getCell(2).toString();
					System.out.println("All Modules"+allSubModules);
					String[] listOfSubModules = allSubModules.split(","); 
					for(String singleSubModule : listOfSubModules)
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(newSubModuleButton));
						genericMethods.clickElement(driver, newSubModuleButton, test);
						wait.until(ExpectedConditions.visibilityOfElementLocated(newSubModuleText));
						genericMethods.enterText(driver, newSubModuleText, singleSubModule, test);
						genericMethods.clickElement(driver, okButtonSubModule, test);
						Thread.sleep(1000);
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(singleSubModule)+"')]"), test);
						processCreation();
					}
					test.log(LogStatus.PASS, "Project Created");
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

		finally {
			extent.endTest(test);
			extent.flush();

		}
	}


	int rowCount = 4;
	public void processCreation()
	{
		try{
			test = extent.startTest("Create Process", "Create Process....");
			int cellValue = 1;	
			String pathOfFile = "./TestData/testInput.xlsx";
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Process");
			String[] firstRowElements = new String[50];
			WebDriverWait wait = new WebDriverWait(driver, 15);


			for(int outerRow = rowCount; outerRow <= sheet.getLastRowNum(); outerRow++){
				for(int row = outerRow; row <= sheet.getLastRowNum(); row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						//System.out.print(firstRowElements[rowItr]+" ");
					}



					if(sheet.getRow(row).getCell(1).toString().contains("Process"))
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessButton));
						genericMethods.click(driver, newProcessButton, test);
						wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessText));
						genericMethods.enterText(driver, newProcessText, firstRowElements[cellValue], test);
						genericMethods.click(driver, okButtonProcess, test);
						Thread.sleep(1500);
						genericMethods.click(driver,By.xpath("//h3[text()='"+(firstRowElements[cellValue++])+"']") , test);
						System.out.println("New Process clicked");
						Thread.sleep(1500);
						rowCount++;
					}

					if(sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("create"))
					{
						Thread.sleep(700);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.create(driver, test, firstRowElements[++cellValue],firstRowElements[++cellValue]);
						cellValue = 2;
						rowCount++;
						break;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("delete"))
					{
						System.out.println("Delete Block");
						Thread.sleep(1000);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.delete(driver, test ,firstRowElements[cellValue+2]);
						cellValue = 2;
						rowCount++;
						break;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("view"))
					{
						Thread.sleep(1000);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.view(driver, test ,firstRowElements[++cellValue],firstRowElements[++cellValue]);
						cellValue = 2;
						rowCount++;
						break;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("switch"))
					{
						Thread.sleep(1000);
						studioCommonMethods.addIcon(driver, test);
						studioCommonMethods.Switch(driver, test ,firstRowElements[cellValue+2]);
						cellValue = 2;
						rowCount++;
						break;
					}

					else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("Save"))
					{
						Thread.sleep(1000);
						genericMethods.click(driver, saveProcess, test);
						Thread.sleep(1000);
						genericMethods.click(driver, ok, test);
						driver.navigate().back();
						driver.navigate().back();
						//rowCount++;
						break;
					}


				}
				test.log(LogStatus.PASS, "Process has been Created");
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

		finally {
			extent.endTest(test);
			extent.flush();

		}
	}




}
