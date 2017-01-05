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

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProjectCreation implements GlobalXpath{

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProjectCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);
	public EntityCreation entityCreation;
	
	//	static int plusInc = 10000;
	//	By plusIcon = By.xpath("//div[@data-id= '" +(++plusInc) +"']");

	public ProjectCreation(WebDriver driver)
	{
		this.driver = driver;
	}

	public void newProjectCreation(ExtentTest test, String sheetName)
	{
		try{

			int cellValue = 0;	
			String workbookTestData = sheetName;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Entity");
			String[] rowElements = new String[50];

			int projectRowCount = 1;
			for(int outerRow = 1; outerRow <= projectRowCount; outerRow++){
				for(int row = outerRow; row <= projectRowCount; row++){
					for (int rowItr = 0; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++){
						rowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
						System.out.println("Row Value is ="+rowElements[rowItr]);
					}

					genericMethods.clickElement(driver, kagamiLogo, test);
					Thread.sleep(1000);
					genericMethods.clickElementByJsExecutor(driver, newProject, test);
					genericMethods.enterText(driver, newProjectText, rowElements[cellValue], test);
					Thread.sleep(1000);


					if(genericMethods.ElementVisibility(driver, existingProjectErrorMsg, test))
					{
						studioCommonMethods.deleteExistingProject(driver, rowElements[cellValue]);
						Thread.sleep(1500);
						genericMethods.clickElementByJsExecutor(driver, newProject, test);
						genericMethods.enterText(driver, newProjectText, rowElements[cellValue], test);
						Thread.sleep(1000);
					}
					genericMethods.clickElement(driver, createButton, test);
					test.log(LogStatus.PASS, "Project with name '"+rowElements[0]+"' is Created.");
					genericMethods.clickElementByJsExecutor(driver,By.xpath("//h3[text()='"+(rowElements[cellValue])+"']") , test);



				}
			}
		}

		catch(NoSuchElementException e)	{

		}
		catch(org.openqa.selenium.TimeoutException e){
		}
		catch(Exception e)	{
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Project Creation is failed due to below reasons.");
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, "Exception is : "+ExceptionUtils.getStackTrace(e));
		}
	}


}
