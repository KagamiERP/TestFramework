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

public class MultipleProjectCreationAndEdit implements GlobalXpath{

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("MultipleProjectAndProcessCreationAndEdit.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	
	
	public MultipleProjectCreationAndEdit(WebDriver driver)
	{
		this.driver = driver;
	}

	public void multipleProjectCreationAndEdit(ExtentTest test, String workBook)
	{
		try{

			int cell = 0;	
			String workbookTestData = workBook;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("EditMultipleProject");
			String[] rowElements = new String[300];

			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
					rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
				}

				String projectSplitedName[] = rowElements[cell].split(":");
				int cellValue = 0;
				genericMethods.clickElement(driver, kagamiLogo, test);
				Thread.sleep(1000);
				genericMethods.clickElement(driver, newProject, test);
				genericMethods.enterText(driver, newProjectText,projectSplitedName[0].trim(), test);
				Thread.sleep(1000);
				if(genericMethods.ElementVisibility(driver, existingProjectErrorMsg, test)){
					studioCommonMethods.deleteExistingProject(driver, rowElements[cellValue]);
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newProject, test);
					genericMethods.enterText(driver, newProjectText,projectSplitedName[0].trim(), test);
					Thread.sleep(1000);
				}

				genericMethods.clickElement(driver, createButton, test);
				test.log(LogStatus.PASS, "Project with name '"+projectSplitedName[0].trim()+"' is Created.");
				Thread.sleep(1500);
				By editItem = By.xpath("//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-edit']");
				genericMethods.clickElement(driver, editItem, test);
				By textBox = By.xpath("//input[@id='prompt-input-field']");
				System.out.println("2nd data is "+projectSplitedName[1].trim());
				studioCommonMethods.removeText(driver,textBox );
				genericMethods.enterText(driver, textBox, projectSplitedName[1].trim(), test);
				genericMethods.clickElement(driver, submitProjectButton, test);
				test.log(LogStatus.PASS, "Project name is renamed with '"+projectSplitedName[1].trim()+"'.");
				genericMethods.clickElementByJsExecutor(driver,By.xpath("//h3[text()='"+(projectSplitedName[1].trim())+"']") , test);
				

						
				//Module Creation And Rename
				String allModules = sheet.getRow(row).getCell(1).toString();
				String[] listOfModules = allModules.split(","); 

				for(String singleModule : listOfModules){
					Thread.sleep(1500);
					genericMethods.clickElement(driver, newModuleButton, test);
					Thread.sleep(1500);
					genericMethods.enterText(driver, newModuleText, singleModule, test);
					genericMethods.clickElement(driver, submitButtonModule, test);
					test.log(LogStatus.PASS, singleModule+ " is created under "+projectSplitedName[1]+".");
					
				}	
				
				String allMod = sheet.getRow(row).getCell(1).toString();
				String moduleSplitedName[] = rowElements[2].split(":");
				String selectMod = sheet.getRow(row).getCell(2).toString();
				
				if(allMod.contains(moduleSplitedName[0].trim()))
				{
					
					By editModule = By.xpath("//h3[text()='"+moduleSplitedName[0].trim()+"']/parent::div/parent::a/parent::div//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-edit']");
					
					genericMethods.clickElement(driver, editModule, test);
					By textBoxMod = By.xpath("//input[@placeholder='(Eg: HRMS, SRM, CRM etc.)']");
					studioCommonMethods.removeText(driver,textBoxMod );
					genericMethods.enterText(driver, textBoxMod, moduleSplitedName[1].trim(), test);
					genericMethods.clickElement(driver, submitButtonModule, test);
					test.log(LogStatus.PASS, "Module name is renamed with "+moduleSplitedName[1].trim());
					Thread.sleep(1500);
					genericMethods.clickElement(driver, By.xpath("//h3[contains(text(),'"+(moduleSplitedName[1]).trim()+"')]"), test);
				
					
					
			
					//Submodule Creation and Rename
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
						test.log(LogStatus.PASS, singleSubModule+ " is created under "+moduleSplitedName[1]+".");
						Thread.sleep(1000);
						
					}
					
					

					String[] subModuleSplitedName = rowElements[4].split(":");
					if(allSubModules.contains(subModuleSplitedName[0].trim()))
					{
						
						
						int subModuleCount = 0;
						By editSubModule = By.xpath("//h3[text()='"+subModuleSplitedName[subModuleCount].trim()+"']/parent::div/parent::a/parent::div//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-edit']");
						genericMethods.clickElement(driver, editSubModule, test);
						By textBoxSubMod = By.xpath("//input[@placeholder='(Eg: Leave Management System)']");
						studioCommonMethods.removeText(driver, textBoxSubMod);
						genericMethods.enterText(driver, textBoxSubMod, subModuleSplitedName[++subModuleCount].trim(), test);
						genericMethods.clickElement(driver, submitSubModButton, test);
						test.log(LogStatus.PASS, "Sub Module name is renamed with "+subModuleSplitedName[subModuleCount].trim());
						genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(subModuleSplitedName[subModuleCount].trim())+"')]"), test);
		
						
						
						
						//genericMethods.click(driver, By.xpath("//h3[contains(text(),'"+(selectSubMod)+"')]"), test);
						String allProcess = sheet.getRow(row).getCell(5).toString();
						String[] listOfProcess = allProcess.split(","); 
						for(String singleProcess : listOfProcess)
						{
							Thread.sleep(1000);
							genericMethods.click(driver, newProcessButton, test);
							Thread.sleep(1500);
							genericMethods.enterText(driver, newProcessText, singleProcess, test);
							genericMethods.click(driver, submitButtonProcess, test);
							test.log(LogStatus.PASS, singleProcess+ " is created under "+subModuleSplitedName[1]+".");
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
