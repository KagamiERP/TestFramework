package com.kagami.editcomponent;

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

public class ProcessCreationEdit {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProcessCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);
	public EntityCreation entityCreation;

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
	//Policies Xpath
	
	//	static int plusInc = 10000;
	//	By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");

	public ProcessCreationEdit(WebDriver driver)
	{
		this.driver = driver;
	}

	public void newProcessCreation(ExtentTest test)
	{
		try{
			int cellValue = 0;	
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("ProcessAndPolicies");
			String[] rowElements = new String[50];

			int projectRowCount = 1;
			for(int outerRow = 1; outerRow <= projectRowCount; outerRow++){
				for(int row = outerRow; row <= projectRowCount; row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					}

					driver.navigate().back();
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newModuleButton, test);
					Thread.sleep(1500);
					genericMethods.enterText(driver, newModuleText, rowElements[++cellValue], test);
					Thread.sleep(1000);
					genericMethods.clickElement(driver, okButtonModule, test);
					test.log(LogStatus.PASS, "Module with name "+rowElements[cellValue]+" is Created.");
					genericMethods.clickElement(driver,By.xpath("//h3[text()='"+(rowElements[cellValue])+"']") , test);


					String allSubModules = sheet.getRow(1).getCell(2).toString();
					//System.out.println("All Modules"+allSubModules);
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
						test.log(LogStatus.PASS, "Sub Module with name "+singleSubModule+" is Created.");
						
						Thread.sleep(1000);
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(singleSubModule)+"')]"), test);
						processCreation(test);


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
	public void processCreation(ExtentTest test)
	{
		try{
			int cellValue = 1;	
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("ProcessAndPolicies");
			String[] rowElements = new String[50];
			WebDriverWait wait = new WebDriverWait(driver, 8);


			for(int row = rowCount; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					//System.out.print(rowElements[rowItr]+" ");
				}


				if(sheet.getRow(row).getCell(1).toString().contains("Process"))
				{
					//wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessButton));
					Thread.sleep(1500);
					genericMethods.click(driver, newProcessButton, test);
					//	wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessText));
					Thread.sleep(1500);
					genericMethods.enterText(driver, newProcessText, rowElements[cellValue], test);
					genericMethods.click(driver, okButtonProcess, test);
					Thread.sleep(1500);
					genericMethods.click(driver,By.xpath("//h3[text()='"+(rowElements[cellValue++])+"']") , test);

					Thread.sleep(1500);
					rowCount++;
				}
				if(sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("create"))
				{
					Thread.sleep(1000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.create(driver, test, rowElements[++cellValue],rowElements[++cellValue]);
					cellValue = 2;
					rowCount++;
					continue;
				}

				else if(sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("update"))
				{
					Thread.sleep(1000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.update(driver, test ,rowElements[cellValue+2]);
					cellValue = 2;
					rowCount++;
					continue;
				}
				else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("delete"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.delete(driver, test ,rowElements[cellValue+2]);
					cellValue = 2;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("view"))
				{
					Thread.sleep(2500);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.view(driver, test ,rowElements[++cellValue],rowElements[++cellValue]);
					cellValue = 2;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("switch"))
				{
					Thread.sleep(1000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.Switch(driver, test ,rowElements[cellValue+2]);
					cellValue = 2;
					rowCount++;
					continue;
				}
				

				else if (sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("end"))
				{
					Thread.sleep(1000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.end(driver, test);
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
					//	driver.navigate().back();
					//	driver.navigate().back();
				//	studioCommonMethods.navigateToModule(driver);
					System.out.println("After Navigating to Module");
					break;

				}

			}
			//	driver.navigate().back();
			//	customizeDashBoard.customizeDashBoard(driver,test);
			
			studioCommonMethods.mapRoles(driver, "CTO");

		}

		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));

		}

	}




}