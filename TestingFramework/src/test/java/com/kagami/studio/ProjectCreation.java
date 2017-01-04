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
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProjectCreation {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("ProjectCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	CustomizeDashBoard customizeDashBoard = new CustomizeDashBoard(driver);
	public EntityCreation entityCreation;

	By newProject = By.xpath("//button[@ng-click='add()']");
	By newProjectText = By.xpath("//input[@id='new-project-text']");
	By projectName = By.xpath("//input[@id='new-project-text']");
	By createButton = By.xpath("//button[contains(text(),'Create')]");
	By okButtonModule = By.xpath("//div[text()='Create Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	
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
	By updateProjectButton = By.xpath("//div[@class='modal-footer']/button[contains(text(),'Update')]");
	By deleteSubmodule = By.xpath("//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-trash']");
	By deleteSubModuleOk = By.xpath("//div[text()='Delete Sub Module']/following::button[text()='OK']");
	By deleteModuleOk = By.xpath("//div[text()='Delete Module']/following::button[text()='OK']");
	
	
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
			String testDataSheet = sheetName;
			File f = new File(testDataSheet);
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
					test.log(LogStatus.PASS, "Project with name "+rowElements[0]+" is Created.");
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
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}


}
