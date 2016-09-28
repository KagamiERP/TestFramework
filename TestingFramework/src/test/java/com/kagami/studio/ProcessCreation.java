package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProcessCreation {

	ExtentReports extent;
	ExtentTest test;
	public WebDriver driver;
	static Logger log = Logger.getLogger("EntityGenerationBase.class");
	GenericMethods genericMethods = new GenericMethods();
	Map<String, List<String>> readMultipleTestData;
	private String[] firstRowElements;

	By uname = By.id("inputUsername");
	By password = By.id("inputPassword");
	By loginButton = By.xpath("//button[contains(text(),'SIGN IN')]");
	String userName = "admin";
	String passWord = "admin";

	public ProcessCreation(WebDriver driver) {
		this.driver = driver;
	}

	private String chooseConstructHeader = "Choose Construct";
	// By constructList =
	// By.xpath("//div[@class='popover-content']//div[@class='row']//div//p");
	// private int i=1;
	// By constructListText =
	// By.xpath("//div[@class='popover-content']//div[@class='row']//div["+i+"]//p");
	By stepCircle = By.xpath("//div[@class='step circle plus_step']");
	List<String> listConstruct = new ArrayList<>();
	By processModule = By.xpath("//h3[text()='Business Process']");
	By newProjectTab = By.xpath("//li[contains(text(),'Projects')]");

	public void newProjectCreation()
	{
		try{
			String pathOfFile = "./TestData/testInput.xlsx";
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Sheet1");
			firstRowElements = new String[50];
			test = extent.startTest("Project Creation", "Create New Project");
			genericMethods.clickElement(driver, newProjectTab, test);
			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 2; rowItr <=6 ; rowItr++)
				{
					firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					System.out.print(firstRowElements[rowItr]+" ");
				}
				int cell = 2;
				if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("Yes"))
				{
					System.out.println("In Yes block");
					log.info("Entering Excel data to the application");
					newProjectCreation(2);
					newModuleCreation(4);
					newSubModuleCreation(5);
					newProcessesCreation(6);
					genericMethods.clickElement(driver,By.xpath("//a[@class='navbar-brand']"),test);}

			}test.log(LogStatus.PASS,"Project Created Successfully");}

		catch(

				Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}finally
		{
			extent.endTest(test);
			extent.flush();
		}
	}

	By newProject = By.xpath("//span[@ng-click='addProject()']");
	By newProjectHeader = By.xpath("//div[contains(text(),'New Project')]");
	public String projectHeaderName = "NEW PROJECT";
	By projectName = By.xpath("//input[@id='new-project-text']");
	By selectTemplate = By.xpath("//input[@class='form-control ui-select-search ng-pristine ng-valid ng-empty ng-touched']");
	By listTemplateValue = By.xpath("//span[@class='ng-binding ng-scope' and contains(text(),'Test')]");
	By saveButton = By.xpath("//button[text()='Save']");

	public void newProjectCreation(int cell)
	{
		try{
			System.out.println("In newProjectCreation Method");
			test = extent.startTest("Project Creation", "Create New Project");
			log.info("Entering Excel data to the application for creation of New Project");
			genericMethods.clickElement(driver, newProject, test);
			genericMethods.waitForElementVisibility(driver, newProjectHeader, 30);
			String actual = genericMethods.getText(driver, newProjectHeader, test);
			Assert.assertEquals(actual, projectHeaderName);
			genericMethods.enterText(driver, projectName, firstRowElements[cell], test);
			genericMethods.enterText(driver, selectTemplate, firstRowElements[++cell], test);
			genericMethods.clickElement(driver, saveButton, test);
			test.log(LogStatus.PASS, "Project Created Successfully");
		}

		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
		finally {
			extent.endTest(test);
			extent.flush();
		}
	}

	By moduleCreation = By.xpath("//span[@class='pull-right add_btn']");
	By createModuleHeader = By.xpath("//div[@class='ajs-header' and text()='Create Module']");
	By inputModuleName = By.xpath("//p[text()='Module Name']/following-sibling::input[@type='text']");
	By cancelMenuButton = By.className("glyphicon glyphicon-remove pull-right card-menu-item");

	public void newModuleCreation(int cell) {
		try {
			test = extent.startTest("New Module Creation", "Module creation for the Project");
			System.out.println("In Yes block");
			log.info("Entering Excel data to the application");
			genericMethods.clickElement(driver,
					By.xpath("//div[@class='project-card-inner']/h3[text()='" + firstRowElements[2] + "']"), test);
			genericMethods.clickElement(driver, moduleCreation, test);
			genericMethods.waitForElementVisibility(driver, createModuleHeader, 30);
			Thread.sleep(1000);
			genericMethods.enterText(driver, inputModuleName, firstRowElements[cell], test);
			genericMethods.clickElement(driver, okButton, test);
			test.log(LogStatus.PASS, "Project Created Successfully");
		} catch (Exception e) {
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		} finally {
			extent.endTest(test);
			extent.flush();
		}
	}

	By addSubModule = By.xpath("//h3[text()=' Add Submodule']");
	By subModuleHeader = By.xpath("//div[text()='Create Sub Module']");
	public String subModuleHeaderName = "Create Sub Module";
	By subModuleName = By.xpath("//p[text()='Sub Module Name']/following-sibling::input[@class='ajs-input']");
	By okButton = By.xpath("//button[text()='OK']");
	By cancelButton = By.xpath("//button[text()='Cancel']");

	public void newSubModuleCreation(int cell) {
		try {

			test = extent.startTest("Sub Module Creation", "SubModule creation for the Project");
			System.out.println("In Yes block");
			log.info("Entering Excel data to the application");
			Thread.sleep(1000);
			genericMethods.clickElement(driver, addSubModule, test);
			genericMethods.verifyTextVisibility(driver, subModuleHeaderName);
			genericMethods.enterText(driver, subModuleName, firstRowElements[cell], test);
			genericMethods.clickElement(driver, okButton, test);
			test.log(LogStatus.PASS, "Sub Module Created Successfully");
		} catch (Exception e) {
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		} finally {
			extent.endTest(test);
			extent.flush();
		}
	}

	By addProcess = By.xpath("//h3[text()=' Add Process']");
	public String createProcessHeader = "Create Process";
	By processName = By.xpath("//p[text()='Process Name']/following-sibling::input[@class='ajs-input']");
	By subModule = By.xpath("//h3[text()='Functional Test']");

	public void newProcessesCreation(int cell) {
		try {
			test = extent.startTest("Process Creation", "Process creation in the sub module");
			Thread.sleep(1000);
			genericMethods.clickElement(driver, By.xpath("//h3[text()='" + firstRowElements[5] + "']"), test);
			genericMethods.clickElement(driver, addProcess, test);
			genericMethods.verifyTextVisibility(driver, createProcessHeader);
			genericMethods.enterText(driver, processName, firstRowElements[cell], test);
			// genericMethods.enterText(driver, processName, "Business Process",
			// test);
			genericMethods.clickElement(driver, okButton, test);
			test.log(LogStatus.PASS, "New Process Created Successfully");

		} catch (Exception e) {
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		} finally {
			extent.endTest(test);
			extent.flush();
		}
	}


	public void deleteModule() {
		try {
			// extent.loadConfig(new File("C:\\extent\\config.xml"));
			test = extent.startTest("Delete Menu", "Delete Menu in the submodule or process");
			genericMethods.clickElement(driver, cancelMenuButton, test);
			genericMethods.verifyTextVisibility(driver, "Delete Sub Module");

		} catch (Exception e) {
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		} finally {
			extent.endTest(test);
			extent.flush();
		}
	}

	public void getConstruct() {
		try {
			// extent.loadConfig(new File("C:\\extent\\config.xml"));
			test = extent.startTest("Get List of Construct", "This Test Case gets the List of the construct");
			genericMethods.clickElement(driver, processModule, test);
			genericMethods.waitForElementVisibility(driver, stepCircle, 30);
			genericMethods.clickElement(driver, stepCircle, test);
			Thread.sleep(1000);
			genericMethods.verifyTextVisibility(driver, chooseConstructHeader);
			int constructCount = genericMethods.getXpathSize(driver,
					By.xpath("//div[@class='row']/div/div/p[@class='text-center ng-binding']"), test);
			for (int i = 1; i <= constructCount; i++) {
				String constructText = genericMethods.getText(driver,
						By.xpath("//div[@class='row']/div[" + i + "]/div/p[@class='text-center ng-binding']"), test);
				listConstruct.add(constructText);
			}
			System.out.println(listConstruct);
			test.log(LogStatus.PASS, "All the Constructs are added to the list");
		} catch (Exception e) {
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		} finally {
			extent.endTest(test);
			extent.flush();
		}
	}

	public void signOut(){
		try {
			test = extent.startTest("Process Creation", "Process creation in the sub module");
			Thread.sleep(1000);
			genericMethods.clickElement(driver, By.xpath("//li[@class='dropdown open']"), test);
			genericMethods.waitForElementVisibility(driver, By.xpath("//a[text()='Sign Out']"), 30);
			genericMethods.clickElement(driver, By.xpath("//a[text()='Sign Out']"), test);
			test.log(LogStatus.PASS, "Application has been sign outed successfully");
		}
		catch (Exception e) {
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		} 
		finally {
			extent.endTest(test);
			extent.flush();
		}
	}
}
