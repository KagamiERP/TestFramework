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
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OrganisationCreationBase
{

	int nodeCnt=1 ;
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("OrganisationCreation.class");
	GenericMethods genericMethods = new GenericMethods();
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	private String[] rowElements;

	By menuButton = By.xpath("//button[contains(text(),'Menu')]");
	By organization = By.linkText("Organization");
	By roles = By.linkText("Roles");
	By saveButton = By.xpath("//button/img[@src='assets/img/save.png']");
	By organizationsCli = By.xpath("//div/div/ul/li/a[contains(text(),'Organizations')]");
	By deleteMsg=By.xpath("//*[contains(text(),'Do you want to delete the Organization?')]");
	By deleteOk=By.xpath("//*[contains(text(),'OK')]");
	By deleteCancel=By.className("//*[contains(text(),'Cancel')]");
	//By orgExistingToastMessage = By.xpath("//div[contains(text()='Organization with name "Demo" already exists')]");

	Map<String , Integer> nodeMap = new HashMap<String, Integer>();



	public OrganisationCreationBase(WebDriver driver)
	{
		this.driver = driver;
	}


	public void orgCreation(ExtentTest test)
	{
		try{
			String pathOfFile = Global.testSheet;
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Organisation");
			String[] rowElements = new String[50];
			Thread.sleep(2000);
			genericMethods.clickElement(driver, menuButton, test);	
			//genericMethods.clickElement(driver, organization, test);	   
			genericMethods.clickElement(driver, roles, test);
			Actions actions = new Actions(driver);
			String organisationName = null;

			for(int row = 1; row <= sheet.getLastRowNum(); row++)
			{
				if (sheet.getRow(row).getCell(0) != null
						&& sheet.getRow(row).getCell(0).toString().equalsIgnoreCase("save")) {

					genericMethods.clickElement(driver, saveButton, test);
					Thread.sleep(1500);
					test.log(LogStatus.PASS, organisationName+ " Organisation is created..");
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
							//Removal of nodes
							/*By node1=By.xpath("//div[@class='node' and @node-id=" + nodeMap.get(headDesignation)
							+ "]/h2/following-sibling::div[@class='org-del-button']");
							genericMethods.clickElementByJsExecutor(driver, node1, test);*/
							nodeCnt++;
						}

					} else {
						System.out.println(headDesignation);
						nodeMap.put(headDesignation.replaceAll("\\s",""), nodeCnt);
						By node = By.xpath("//div[@class='node' and @node-id=" + (nodeCnt++) + "]//h2");
						Thread.sleep(2000);
						genericMethods.click(driver, node, test);
						Thread.sleep(2000);
						actions.sendKeys(Keys.chord(Keys.CONTROL, "a"), headDesignation, Keys.ENTER).perform();

						String reportees = sheet.getRow(row).getCell(1).toString();
						System.out.println(reportees);

						String[] listOfReportess = reportees.split(",");

						for (String reportee : listOfReportess) {
							System.out.println(headDesignation + " ==> " + reportee);
							nodeMap.put(reportee.replaceAll("\\s",""), nodeCnt);
							By.xpath("//div[@class='node' and @node-id=" + (nodeCnt++) + "]//h2");
							By addButton = By.xpath("//div[@class='org-add-button']");
							genericMethods.clickElementByJsExecutor(driver, addButton, test);
							actions.sendKeys(reportee, Keys.ENTER).build().perform();
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
