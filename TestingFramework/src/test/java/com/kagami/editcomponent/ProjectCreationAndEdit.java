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

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.Global;
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.kagami.studio.CustomizeDashBoard;
import com.kagami.studio.EntityCreation;
import com.kagami.studio.ProjectCreation;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProjectCreationAndEdit implements GlobalXpath{

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProjectCreationAndEdit.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);
	public EntityCreation entityCreation;
	public ProjectCreation projectCreation;

	
	public ProjectCreationAndEdit(WebDriver driver)
	{

		this.driver = driver;
	}

	public void newProjectCreateAndEdit(ExtentTest test)
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

					String projectSplitedName[] = rowElements[cellValue].split(":");
					genericMethods.clickElement(driver, kagamiLogo, test);
					Thread.sleep(1000);
					genericMethods.clickElementByJsExecutor(driver, newProject, test);
					genericMethods.enterText(driver, newProjectText,projectSplitedName[0].trim(), test);
					Thread.sleep(1000);


					if(genericMethods.ElementVisibility(driver, existingProjectErrorMsg, test)){
						studioCommonMethods.deleteExistingProject(driver, projectSplitedName[0].trim());
						Thread.sleep(1000);
					}
							
					genericMethods.clickElement(driver, createButton, test);
					test.log(LogStatus.PASS, "Project with name "+projectSplitedName[0].trim()+" is Created.");
					By editItem = By.xpath("//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-edit']");
					genericMethods.clickElement(driver, editItem, test);
					By textBox = By.xpath("//input[@id='prompt-input-field']");
					System.out.println("2nd data is "+projectSplitedName[1].trim());
					studioCommonMethods.removeText(driver,textBox );
					genericMethods.enterText(driver, textBox, projectSplitedName[1].trim(), test);
					genericMethods.clickElement(driver, submitProjectButton, test);
					test.log(LogStatus.PASS, "Project name is renamed with "+projectSplitedName[1].trim());
					genericMethods.clickElementByJsExecutor(driver,By.xpath("//h3[text()='"+(projectSplitedName[1].trim())+"']") , test);
					
					
				}
			}
		}

		catch(NoSuchElementException e)	{

		}
		catch(org.openqa.selenium.TimeoutException e){
		}
		catch(Exception e)	{
			e.printStackTrace();
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}


}
