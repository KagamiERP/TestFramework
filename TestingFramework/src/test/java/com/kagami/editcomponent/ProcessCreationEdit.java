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
import com.kagami.studio.CustomizeDashBoard;
import com.kagami.studio.EntityCreation;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProcessCreationEdit {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProcessCreationEdit.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);
	public EntityCreation entityCreation;

	By newProject = By.xpath("//button[@ng-click='add()']");
	By newProjectText = By.xpath("//input[@id='new-project-text']");
	By projectName = By.xpath("//input[@id='new-project-text']");
	By createButton = By.xpath("//button[contains(text(),'Create')]");
	By newModuleButton = By.xpath("//button[@ng-click='addModule()']");
	By newModuleText = By.xpath("//input[@id='prompt-input-field']");
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
	By submitButtonModule = By.xpath("//div[@class='modal-footer']/button[text()='Submit']");
	By okButtonSubModule = By.xpath("//div[text()='Create Sub Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By okButtonProcess = By.xpath("//div[text()='Create Process']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By existingProcess = By.xpath("//p[text()='Process']");
	By existingProjectErrorMsg = By.xpath("//div[text()='*Should not create same Project Name']");
	By kagamiLogo = By.xpath("//img[@src='assets/img/logo.png']");
	By submitSubModButton = By.xpath("//div[@class='modal-footer']/button[contains(text(),'Submit')]");
	By deleteSubmodule = By.xpath("//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-trash']");
	By deleteSubModuleOk = By.xpath("//div[text()='Delete Sub Module']/following::button[text()='OK']");
	By deleteModuleOk = By.xpath("//div[text()='Delete Module']/following::button[text()='OK']");

	public ProcessCreationEdit(WebDriver driver)
	{
		this.driver = driver;
	}

	public void newProcessCreationAndEdit(ExtentTest test)
	{
		try{
			int cellValue = 0;	
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Edit");
			String[] rowElements = new String[50];

			int projectRowCount = 1;
			for(int outerRow = 1; outerRow <= projectRowCount; outerRow++){
				for(int row = outerRow; row <= projectRowCount; row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					}

					//Module
					driver.navigate().back();
					String moduleSplitedName[] = rowElements[++cellValue].split(":");
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newModuleButton, test);
					Thread.sleep(1500);
					genericMethods.enterText(driver, newModuleText, moduleSplitedName[0].trim(), test);
					Thread.sleep(1000);
					genericMethods.clickElement(driver, submitButtonModule, test);
					test.log(LogStatus.PASS, "Module with name "+moduleSplitedName[0].trim()+" is Created.");
					Thread.sleep(1500);
					By editModule = By.xpath("//h3[text()='"+moduleSplitedName[0].trim()+"']/parent::div/parent::a/parent::div//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-edit']");
					genericMethods.clickElement(driver, editModule, test);
					By textBoxMod = By.xpath("//input[@placeholder='(Eg: HRMS, SRM, CRM etc.)']");
					studioCommonMethods.removeText(driver,textBoxMod );
					genericMethods.enterText(driver, textBoxMod, moduleSplitedName[1].trim(), test);
					genericMethods.clickElement(driver, submitButtonModule, test);
					test.log(LogStatus.PASS, "Module name is renamed with "+moduleSplitedName[1].trim());
					genericMethods.clickElementByJsExecutor(driver,By.xpath("//h3[text()='"+(moduleSplitedName[1].trim())+"']") , test);


					//SubModules
					String allSubModules = sheet.getRow(1).getCell(2).toString();
					String[] listOfSubModules = allSubModules.split(","); 
					WebDriverWait wait = new WebDriverWait(driver, 8);

					for(String singleSubModule : listOfSubModules)
					{
						int subModuleCount = 0;
						String[] subModuleSplitedName = singleSubModule.split(":");
						wait.until(ExpectedConditions.visibilityOfElementLocated(newSubModuleButton));
						genericMethods.clickElement(driver, newSubModuleButton, test);
						Thread.sleep(1500);
						genericMethods.enterText(driver, newSubModuleText, subModuleSplitedName[subModuleCount].trim(), test);
						genericMethods.clickElement(driver, okButtonSubModule, test);
						test.log(LogStatus.PASS, "Sub Module with name "+subModuleSplitedName[subModuleCount].trim()+" is Created.");
						Thread.sleep(1000);
						By editSubModule = By.xpath("//h3[text()='"+subModuleSplitedName[subModuleCount].trim()+"']/parent::div/parent::a/parent::div//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-edit']");
						genericMethods.clickElement(driver, editSubModule, test);
						By textBoxSubMod = By.xpath("//input[@placeholder='(Eg: Leave Management System)']");
						studioCommonMethods.removeText(driver, textBoxSubMod);
						genericMethods.enterText(driver, textBoxSubMod, subModuleSplitedName[++subModuleCount].trim(), test);
						genericMethods.clickElement(driver, submitSubModButton, test);
						test.log(LogStatus.PASS, "Sub Module name is renamed with "+subModuleSplitedName[subModuleCount].trim());
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(subModuleSplitedName[subModuleCount].trim())+"')]"), test);
						processCreationAndEdit(test);

					}
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
	public void processCreationAndEdit(ExtentTest test)
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
					test.log(LogStatus.PASS, "Process is created in the name of "+rowElements[cellValue]);
					Thread.sleep(1500);
					genericMethods.clickElement(driver, By.xpath("//span[@ng-click='removeProcess(process)']"), test);
					genericMethods.clickElement(driver, By.xpath("//div[@class='ajs-primary ajs-buttons']/following::button[text()='OK']"), test);
					test.log(LogStatus.PASS,rowElements[cellValue]+" process is deleted.");
					driver.navigate().back();
					genericMethods.clickElement(driver, deleteSubmodule, test);
					Thread.sleep(1000);
					genericMethods.clickElement(driver, deleteSubModuleOk, test);
					test.log(LogStatus.PASS,"Submodule is deleted.");
					Thread.sleep(1000);
					driver.navigate().back();
					genericMethods.clickElement(driver, deleteSubmodule, test);
					Thread.sleep(1000);
					genericMethods.clickElement(driver, deleteModuleOk, test);
					test.log(LogStatus.PASS,"Module is deleted.");
					//genericMethods.click(driver,By.xpath("//h3[text()='"+(rowElements[cellValue++])+"']") , test);
					Thread.sleep(1000);
					rowCount++;
				}



				/*if(sheet.getRow(row).getCell(2).toString().equalsIgnoreCase("create"))
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

				 */			}
			//	driver.navigate().back();
			//	customizeDashBoard.customizeDashBoard(driver,test);

			//		studioCommonMethods.mapRoles(driver, "CTO");

		}

		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));

		}

	}




}
