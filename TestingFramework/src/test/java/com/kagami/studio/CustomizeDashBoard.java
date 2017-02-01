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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.kagami.testcases.ReportingVariables;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CustomizeDashBoard implements GlobalXpath
{
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("CustomizeDashBoard.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);


	public CustomizeDashBoard(WebDriver driver)
	{
		this.driver = driver;
	}


	public void customizeDashBoard(ExtentTest test, String sheetName)
	{
		try
		{
			//studioCommonMethods.navigateToModule(driver);
			/*driver.navigate().back();
			driver.navigate().back();
			driver.navigate().back();*/
			
			studioCommonMethods.navigateToProject(driver);
			
			String projectName = ReportingVariables.getProjectName();
			Thread.sleep(1000);	
			//Mapping Organization
			genericMethods.clickElement(driver, subMenu, test);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, customizedDashboardButton, test);
			String workbookTestData = sheetName;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("dashboard");
			String[] firstRowElements = new String[50];	
			Actions a=new Actions(driver);
			for(int row = 1; row <= sheet.getLastRowNum(); row++)
			{
				for (int rowItr = 0; rowItr <sheet.getRow(row).getLastCellNum() ; rowItr++)
				{
					firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					System.out.print(firstRowElements[rowItr]+" ");
				}
				int cell = 0;
				int i = 1;
				if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("Process")){
					Thread.sleep(2500);
					genericMethods.clickElement(driver, leftPaneloButton, test);
					genericMethods.enterText(driver, groupNameClick, firstRowElements[++cell], test);
					WebElement wb3 = driver.findElement(By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']"));						
					a.moveToElement(wb3).perform();	
					a.click(wb3).build().perform();										
					By processDropdown=By.xpath("//ul[@class='ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu ng-scope']//div[@role='option']["+i+"]");
					//i++;
					genericMethods.getText(driver, processDropdown, test);
					genericMethods.click(driver, processDropdown, test);			
					genericMethods.clickElement(driver, createLeftPanel, test);					
				}
				else if(sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("Group")){
					Thread.sleep(1500);
					genericMethods.clickElement(driver, leftPaneloButton, test);
					Thread.sleep(1500);
					genericMethods.clickElement(driver, groupRadioButton, test);		
					Thread.sleep(1500);
					String groupName=sheet.getRow(row).getCell(1).toString();
					genericMethods.enterText(driver, groupNameClick,groupName, test);					
					genericMethods.clickElement(driver, createLeftPanel, test);
					Thread.sleep(1500);							
					String groups = sheet.getRow(row).getCell(2).toString();
					//System.out.println(groups);
					String[] multipleGroup = groups.split(",");

					for (String reportee : multipleGroup) {
						By leftGroupAddBtn=By.xpath("//ul/li/span[contains(text()," + "'" + groupName + "'" + ")]/following-sibling::button[@class='btn btn-default btn-sm ng-scope']");
						genericMethods.clickElement(driver, leftGroupAddBtn, test);
						genericMethods.enterText(driver, groupNameClick,reportee, test);					
						WebElement wb3=driver.findElement(By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']"));						
						a.moveToElement(wb3).perform();	
						a.click(wb3).build().perform();										
						By processDropdown=By.xpath("//ul[@class='ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu ng-scope']//div[@role='option']["+i+"]");
						//	i++;
						genericMethods.getText(driver, processDropdown, test);
						genericMethods.click(driver, processDropdown, test);			
						genericMethods.clickElement(driver, createLeftPanel, test);					
					}
				}
			}
			genericMethods.clickElement(driver, saveDashBoard, test);	
			Thread.sleep(2000);
			genericMethods.clickElement(driver, okSave, test);

			//Settings
			genericMethods.clickElement(driver, settingsButton, test);
			genericMethods.clickElement(driver, userDataSettingsButton, test);
			Thread.sleep(2000);	
			genericMethods.clickElement(driver, userDataCheckBox, test);
			genericMethods.clickElement(driver, userDataSettingsButton, test);
			genericMethods.clickElement(driver, appDataSettingsButton, test);
			Thread.sleep(2000);	
			genericMethods.clickElement(driver, appDataCheckBox, test);
			genericMethods.clickElement(driver, appDataSettingsButton, test);
			genericMethods.clickElement(driver, themeSettingsClick, test);
			Thread.sleep(2000);	
			genericMethods.clickElement(driver, themeSelect, test);
			genericMethods.clickElement(driver, themeSettingsClick, test);
			genericMethods.clickElement(driver, saveAllButton, test);
			test.log(LogStatus.PASS, "Created a dashboard under "+projectName);		

		}
		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}

	}	  
}


