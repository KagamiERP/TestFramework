package com.kagami.concept;

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

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class MultipleProjectAndProcessCreation implements GlobalXpath{

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("MultipleProjectAndProcessCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	ProcessAndConstruct processAndConstruct = new ProcessAndConstruct(driver);

	public MultipleProjectAndProcessCreation(WebDriver driver)
	{
		this.driver = driver;
	}

	public void multipleProjectCreation(ExtentTest test, String workBook)
	{
		try{

			String workbookTestData = workBook;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("ProjectModuleAndSubmodule");
			String[] rowElements = new String[300];

			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
				}
				int cellValue = 0;
				genericMethods.clickElement(driver, kagamiLogo, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, newProject, test);
				genericMethods.enterText(driver, newProjectText, rowElements[cellValue], test);
				Thread.sleep(1000);
				if(genericMethods.ElementVisibility(driver, existingProjectErrorMsg, test))
				{
					studioCommonMethods.deleteExistingProject(driver, rowElements[cellValue]);
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newProject, test);
					genericMethods.enterText(driver, newProjectText, rowElements[cellValue], test);
					Thread.sleep(1000);
				}

				genericMethods.clickElement(driver, createButton, test);
				test.log(LogStatus.PASS, "Project with name "+rowElements[cellValue]+" is Created.");
				genericMethods.clickElement(driver,By.xpath("//h3[text()='"+(rowElements[cellValue])+"']") , test);
				Thread.sleep(1500);

				//Module Creation
				String allModules = sheet.getRow(row).getCell(1).toString();
				String[] listOfModules = allModules.split(","); 

				for(String singleModule : listOfModules)
				{
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newModuleButton, test);
					Thread.sleep(1500);
					genericMethods.enterText(driver, newModuleText, singleModule, test);
					genericMethods.clickElement(driver, submitButtonModule, test);
					test.log(LogStatus.PASS, singleModule+ " is created under "+rowElements[cellValue]+".");
				}	
				String allMod = sheet.getRow(row).getCell(1).toString();
				String selectMod = sheet.getRow(row).getCell(2).toString();

				if(allMod.contains(selectMod))
				{
					genericMethods.clickElement(driver, By.xpath("//h3[contains(text(),'"+(selectMod)+"')]"), test);
					Thread.sleep(1500);
					//Submodule Creation
					String allSubModules = sheet.getRow(row).getCell(3).toString();
					String selectSubMod = sheet.getRow(row).getCell(4).toString();
					String[] listOfSubModules = allSubModules.split(","); 
					for(String singleSubModule : listOfSubModules)
					{
						Thread.sleep(1500);
						genericMethods.clickElement(driver, newSubModuleButton, test);
						Thread.sleep(1500);
						genericMethods.enterText(driver, newSubModuleText, singleSubModule, test);
						genericMethods.clickElement(driver, submitButtonModule, test);
						test.log(LogStatus.PASS, singleSubModule+ " is created under "+selectMod+".");
						Thread.sleep(1000);
					}

					if(allSubModules.contains(selectSubMod))
					{
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(selectSubMod)+"')]"), test);
						String allProcess = sheet.getRow(row).getCell(5).toString();
						String[] listOfProcess = allProcess.split(","); 
						for(String singleProcess : listOfProcess)
						{
							Thread.sleep(1000);
							genericMethods.click(driver, newProcessButton, test);
							Thread.sleep(1500);
							genericMethods.enterText(driver, newProcessText, singleProcess, test);
							genericMethods.click(driver, submitButtonProcess, test);
							test.log(LogStatus.PASS, singleProcess+ " is created under "+selectSubMod+".");
						}

						String selectProcess = sheet.getRow(row).getCell(6).toString();
						if(allProcess.contains(selectProcess)){
							genericMethods.clickElement(driver,By.xpath("//h3[contains(text(),'"+(selectProcess)+"')]"), test);	
							processAndConstruct.createProcess(driver, test, workbookTestData);
							Thread.sleep(2000);
						}

						driver.navigate().back();
						driver.navigate().back();
						driver.navigate().back();
					}
				}
				cellValue = 0;

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
			test.log(LogStatus.FAIL, "Project Creation is failed due to below reasons.");
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.INFO, "Exception is: "+ExceptionUtils.getStackTrace(e));
		}

	}
}
