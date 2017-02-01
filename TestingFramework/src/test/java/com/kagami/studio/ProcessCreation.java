package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
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
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.kagami.testcases.ReportingVariables;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProcessCreation implements GlobalXpath{

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProcessCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);
	public EntityCreation entityCreation;


	public ProcessCreation(WebDriver driver)
	{
		this.driver = driver;
	}

	public void newProcessCreation(ExtentTest test, String sheetName)
	{
		try{
			int cellValue = 1;	
			String workbookTestData = sheetName;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("ProcessAndPolicies");
			String[] rowElements = new String[50];
			String moduleName = ReportingVariables.getModule();  

			int projectRowCount = 1;
			for(int outerRow = 1; outerRow <= projectRowCount; outerRow++){
				for(int row = outerRow; row <= projectRowCount; row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					}

					String projectName = ReportingVariables.getProjectName().toString();
					driver.navigate().back();
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newModuleButton, test);
					Thread.sleep(1500);
					genericMethods.enterText(driver, newModuleText, rowElements[++cellValue], test);
					Thread.sleep(1000);
					genericMethods.clickElement(driver, submitButtonModule, test);
					test.log(LogStatus.PASS, "Created a module as '"+rowElements[cellValue]+"' under "+projectName+".");
					genericMethods.clickElement(driver,By.xpath("//h3[text()='"+(rowElements[cellValue])+"']") , test);


					String allSubModules = sheet.getRow(1).getCell(3).toString();
					//System.out.println("All Modules"+allSubModules);
					String[] listOfSubModules = allSubModules.split(","); 
					WebDriverWait wait = new WebDriverWait(driver, 8);
					for(String singleSubModule : listOfSubModules)
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(newSubModuleButton));
						genericMethods.clickElement(driver, newSubModuleButton, test);
						Thread.sleep(1500);
						genericMethods.enterText(driver, newSubModuleText, singleSubModule, test);
						genericMethods.clickElement(driver, submitButtonSubModule, test);
						test.log(LogStatus.PASS, "Created a submodule as '"+singleSubModule+"' under module "+moduleName+".");
						Thread.sleep(1000);
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(singleSubModule)+"')]"), test);
						processCreation(test,workbookTestData);
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
	public void processCreation(ExtentTest test, String sheetName)
	{
		try{
			int cellValue = 2;	
			String workbookTestData = sheetName;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("ProcessAndPolicies");
			String[] rowElements = new String[50];
			WebDriverWait wait = new WebDriverWait(driver, 8);


			for(int row = rowCount; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
				}


				if(sheet.getRow(row).getCell(2).toString().contains("Process"))
				{

					Thread.sleep(1500);
					genericMethods.click(driver, newProcessButton, test);
					//	wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessText));
					Thread.sleep(1500);
					genericMethods.enterText(driver, newProcessText, rowElements[cellValue], test);
					genericMethods.click(driver, submitButtonProcess, test);
					Thread.sleep(1500);
					genericMethods.click(driver,By.xpath("//h3[text()='"+(rowElements[cellValue++])+"']") , test);
					test.log(LogStatus.PASS, sheet.getRow(sheet.getLastRowNum()).getCell(3).toString().substring(4) +" has been Created.");
					Thread.sleep(1500);
					rowCount++;
				}
				if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("create"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.create(driver, test, rowElements[++cellValue],rowElements[++cellValue],workbookTestData);
					cellValue = 3;
					rowCount++;
					//	test.log(LogStatus.PASS, "Create verb is added");
					continue;

				}

				else if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("edit"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					test.log(LogStatus.PASS, "Edit verb is added in the process.");
					studioCommonMethods.edit(driver, test, rowElements[++cellValue],rowElements[++cellValue],workbookTestData);
					cellValue = 3;
					rowCount++;
					continue;
				}

				else if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("update"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.update(driver, test ,rowElements[cellValue+2], workbookTestData);
					cellValue = 3;
					rowCount++;
					continue;
				}
				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("delete"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.delete(driver, test ,rowElements[cellValue+2]);
					cellValue = 3;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("view"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					test.log(LogStatus.PASS, "View verb is added in process.");
					studioCommonMethods.view(driver, test ,rowElements[++cellValue],rowElements[++cellValue], workbookTestData);
					cellValue = 3;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("email"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					test.log(LogStatus.PASS, "Email verb is added in process.");
					//	WebDriver driver, ExtentTest test ,String entityName, String sourceType, String selectedSource, String subject, String message
					studioCommonMethods.email(driver, test, rowElements[cellValue+2],rowElements[cellValue+3],rowElements[cellValue+4],rowElements[cellValue+5],rowElements[cellValue+6]);
					cellValue = 3;
					rowCount++;
					continue;
				}
				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("fetch"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.fetch(driver, test ,rowElements[++cellValue],rowElements[++cellValue], workbookTestData);
					cellValue = 3;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("transform"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.transform(driver, test ,rowElements[++cellValue],rowElements[++cellValue], workbookTestData);
					cellValue = 3;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("switch"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.Switch(driver, test ,rowElements[cellValue+2], workbookTestData);
					cellValue = 3;
					rowCount++;
					continue;
				}


				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("end"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.end(driver, test);
					cellValue = 3;
					rowCount++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().contains("Save"))
				{
					Thread.sleep(2000);
					genericMethods.click(driver, saveProcess, test);
					Thread.sleep(1000);
					genericMethods.click(driver, ok, test);
					//	test.log(LogStatus.PASS, sheet.getRow(row).getCell(3).toString().substring(4) +" has been Created.");
					//	driver.navigate().back();
					//	driver.navigate().back();
					//	studioCommonMethods.navigateToModule(driver);
					System.out.println("After Navigating to Module");
					break;

				}

			}
			//	driver.navigate().back();
			//	customizeDashBoard.customizeDashBoard(driver,test);

		//	studioCommonMethods.mapRoles(driver, "CTO");

		}

		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Process Creation is failed due to below reasons.");
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.INFO, "Exception is: "+ExceptionUtils.getStackTrace(e));

		}

	}


	int rowCountInvoke = 3;
	public void processCreationInvoke(ExtentTest test, String workBook)
	{
		try{
			studioCommonMethods.navigateToSubModule(driver);
			int cellValue = 2;	
			String workbookTestData = workBook;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("InvokeProcess");
			String[] rowElements = new String[50];
			WebDriverWait wait = new WebDriverWait(driver, 8);


			for(int row = rowCountInvoke; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
				}


				if(sheet.getRow(row).getCell(2).toString().contains("Process"))
				{

					Thread.sleep(1500);
					genericMethods.click(driver, newProcessButton, test);
					//	wait.until(ExpectedConditions.visibilityOfElementLocated(newProcessText));
					Thread.sleep(1500);
					genericMethods.enterText(driver, newProcessText, rowElements[cellValue], test);
					genericMethods.click(driver, submitButtonProcess, test);
					Thread.sleep(1500);
					genericMethods.click(driver,By.xpath("//h3[text()='"+(rowElements[cellValue++])+"']") , test);
					test.log(LogStatus.PASS, sheet.getRow(sheet.getLastRowNum()).getCell(3).toString().substring(4) +" has been Created.");
					Thread.sleep(1500);
					rowCountInvoke++;
				}

				if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("invoke"))
				{
					//WebDriver driver, String selectProcess, String async, ExtentTest test
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.invoke(driver, test, rowElements[++cellValue],rowElements[++cellValue]);
					cellValue = 3;
					rowCountInvoke++;
					test.log(LogStatus.PASS, "Invoke verb is added in the process");
					continue;

				}

				
				if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("create"))
				{
					//WebDriver driver, ExtentTest test, String template, String entityName, String workbookName
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.create(driver, test, rowElements[++cellValue],rowElements[++cellValue],workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					//	test.log(LogStatus.PASS, "Create verb is added");
					continue;

				}

				else if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("edit"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					test.log(LogStatus.PASS, "Edit verb is added in the process.");
					studioCommonMethods.edit(driver, test, rowElements[++cellValue],rowElements[++cellValue],workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}

				else if(sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("update"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.update(driver, test ,rowElements[cellValue+2], workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}
				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("delete"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.delete(driver, test ,rowElements[cellValue+2]);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("view"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					test.log(LogStatus.PASS, "View verb is added in process.");
					studioCommonMethods.view(driver, test ,rowElements[++cellValue],rowElements[++cellValue], workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("email"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					test.log(LogStatus.PASS, "Email verb is added in process.");
					//	WebDriver driver, ExtentTest test ,String entityName, String sourceType, String selectedSource, String subject, String message
					studioCommonMethods.email(driver, test, rowElements[cellValue+2],rowElements[cellValue+3],rowElements[cellValue+4],rowElements[cellValue+5],rowElements[cellValue+6]);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}
				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("fetch"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.fetch(driver, test ,rowElements[++cellValue],rowElements[++cellValue], workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("transform"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.transform(driver, test ,rowElements[++cellValue],rowElements[++cellValue], workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("switch"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.Switch(driver, test ,rowElements[cellValue+2], workbookTestData);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}


				else if (sheet.getRow(row).getCell(3).toString().equalsIgnoreCase("end"))
				{
					Thread.sleep(2000);
					studioCommonMethods.addIcon(driver, test);
					studioCommonMethods.end(driver, test);
					cellValue = 3;
					rowCountInvoke++;
					continue;
				}

				else if (sheet.getRow(row).getCell(3).toString().contains("Save"))
				{
					Thread.sleep(2000);
					genericMethods.click(driver, saveProcess, test);
					Thread.sleep(1000);
					genericMethods.click(driver, ok, test);
					//	test.log(LogStatus.PASS, sheet.getRow(row).getCell(3).toString().substring(4) +" has been Created.");
					//	driver.navigate().back();
					//	driver.navigate().back();
					//	studioCommonMethods.navigateToModule(driver);
					System.out.println("After Navigating to Module");
					break;

				}

			}
			//	driver.navigate().back();
			//	customizeDashBoard.customizeDashBoard(driver,test);

		//	studioCommonMethods.mapRoles(driver, "CEO");

		}

		catch(Exception e)
		{
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Process Creation is failed due to below reasons.");
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.INFO, "Exception is: "+ExceptionUtils.getStackTrace(e));

		}

	}

	
}
