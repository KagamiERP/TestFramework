package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.GlobalXpath;
import com.kagami.library.StudioCommonMethods;
import com.kagami.testcases.ReportingVariables;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OrganisationCreationBase implements GlobalXpath
{

	ReportingVariables reportingVariables = new ReportingVariables();
	int nodeCnt=1 ;
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("OrganisationCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	Map<String , Integer> nodeMap = new HashMap<String, Integer>();

	public OrganisationCreationBase(WebDriver driver)
	{
		this.driver = driver;
	}


	public void orgCreation(ExtentTest test, String sheetName)
	{
		try{
			String workbookTestData = sheetName;
			File f = new File(workbookTestData);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Organisation");
			String[] rowElements = new String[50];
			Thread.sleep(1500);

			String projectName = ReportingVariables.getProjectName().toString();


			System.out.println("Project Name is :"+projectName);
			
			genericMethods.clickElement(driver, menuButton, test);	
			genericMethods.clickElement(driver, roles, test);
			Actions actions = new Actions(driver);
			
			for(int row = 1; row <= sheet.getLastRowNum(); row++)
			{
				if (sheet.getRow(row).getCell(0) != null
						&& sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("save")) {

					genericMethods.clickElement(driver, saveButton, test);
					Thread.sleep(1500);
					driver.navigate().back();

				} 

				else {

					String headDesignation = sheet.getRow(row).getCell(0).toString().replaceAll("\\s","");
					//Thread.sleep(2000);
					if (nodeMap.get(headDesignation) != null) {
						By node = By.xpath("//div[@class='node' and @node-id=" + nodeMap.get(headDesignation)
						+ "]/h2/following-sibling::div[@class='org-add-button']");

						Thread.sleep(1500);
						String reportees = sheet.getRow(row).getCell(1).toString();
						System.out.println(reportees);
						String[] listOfReportess = reportees.split(",");
						for (String reportee : listOfReportess) {

							System.out.println(headDesignation + " ==> " + reportee);
							nodeMap.put(reportee.replaceAll("\\s",""), nodeCnt);							
							genericMethods.clickElementByJsExecutor(driver, node, test);
							actions.sendKeys(reportee, Keys.ENTER).build().perform();
							Thread.sleep(1500);
							nodeCnt++;
					//		test.log(LogStatus.PASS,reportee+" is created under "+projectName);
							test.log(LogStatus.PASS, "Created a role as '"+reportee+"' under "+projectName);

						}

					} else {
						System.out.println("HeadDesignation : "+headDesignation);
						nodeMap.put(headDesignation.replaceAll("\\s",""), nodeCnt);
						By node = By.xpath("//div[@class='node' and @node-id=" + (nodeCnt++) + "]//h2");
						Thread.sleep(2000);
						genericMethods.click(driver, node, test);
						Thread.sleep(2000);
						actions.sendKeys(Keys.chord(Keys.CONTROL, "a"), headDesignation, Keys.ENTER).perform();
					//	test.log(LogStatus.PASS,headDesignation+" is created under "+projectName);
						test.log(LogStatus.PASS, "Created a role as '"+headDesignation+"' under "+projectName);

						String reportees = sheet.getRow(row).getCell(1).toString();
						System.out.println("Roles are : "+reportees);

						String[] listOfReportess = reportees.split(",");

						for (String reportee : listOfReportess) {
							System.out.println(headDesignation + " ==> " + reportee);
							nodeMap.put(reportee.replaceAll("\\s",""), nodeCnt);
							By.xpath("//div[@class='node' and @node-id=" + (nodeCnt++) + "]//h2");
							By addButton = By.xpath("//div[@class='org-add-button']");
							genericMethods.clickElementByJsExecutor(driver, addButton, test);
							actions.sendKeys(reportee, Keys.ENTER).build().perform();
				//			test.log(LogStatus.PASS,reportee+" is created in Studio.");
							test.log(LogStatus.PASS, "Created a role as '"+reportee+"' under "+projectName);

							Thread.sleep(1500);
						}
					}
				}
			}

		}
		catch(NoSuchElementException e)
		{

		}

		catch(Exception e)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}  


	}

}
