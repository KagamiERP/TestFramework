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
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OrgCreationBase {


	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	static Logger log = Logger.getLogger("EntityGenerationBase.class");
	GenericMethods genericMethods = new GenericMethods();

	By menuButton = By.xpath("//a[contains(text(),'Menu')]");
	By organization = By.linkText("Organization");
	By newOrg = By.xpath("//span[@class='glyphicon glyphicon-plus-sign']");
	By hier1 = By.xpath("//div[@class='node']//h2[contains(text(),'Root')]");
	By hier2 = By.xpath("//div[@class='node' and @node-id='4']");
	By addButton = By.xpath("//div[@class='org-add-button']");

	public OrgCreationBase(WebDriver driver)
	{
		this.driver = driver;
	}

	public void orgCreation()
	{
		try{

			int nodeCnt = 1;
			extent = ExtentManager.Instance();
			extent.loadConfig(new File("C:\\extent\\config.xml"));
			test = extent.startTest("Create Org", "Create Organization..");
			String pathOfFile = "./TestData/testInput.xlsx";
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("Sheet7");
			String[] firstRowElements = new String[50];

			genericMethods.clickElement(driver, menuButton, test);
			genericMethods.clickElement(driver, organization, test);
			genericMethods.clickElement(driver, newOrg, test);



			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				for (int rowItr = 1; rowItr < sheet.getRow(row).getLastCellNum() ; rowItr++)
				{
					firstRowElements[rowItr] =  sheet.getRow(row).getCell(rowItr).toString();
					System.out.print(firstRowElements[rowItr]+" ");
				}

				genericMethods.clickElement(driver, addButton, test);

				int cell = 1;
				Thread.sleep(1500);
				WebElement node = driver.findElement(By.xpath("//div[@class='node' and @node-id="+(++nodeCnt)+"]//h2"));
				Actions actions = new Actions(driver);
				actions.moveToElement(node).click(); 
				actions.sendKeys(firstRowElements[cell++]).perform();
				

			}
			test.log(LogStatus.PASS, "Org Created");
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

}
