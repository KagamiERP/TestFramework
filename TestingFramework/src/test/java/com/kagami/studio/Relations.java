package com.kagami.studio;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Relations {

	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	public Logger log = Logger.getLogger("Relations.class");
	GenericMethods genericMethods = new GenericMethods();
	
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	Global global = new Global();

	By uname = By.id("inputUsername");
	By password = By.id("inputPassword");
	By loginButton = By.xpath("//button[contains(text(),'SIGN IN')]");
	By menuButton = By.xpath("//a[@class='dropdown-toggle' and text()='Menu ']");
	By entityCreationButton = By.linkText("Entity Management");
	By addNewEntityButton = By.xpath("//a[@ng-click='addEntity()']");
	By entityName = By.id("entityName");
	By attributeName = By.id("attributeName");
	By attributeType = By.id("attributeType");
	By pastDate = By.xpath("//input[@id='mindate']");
	By attributeValidationtype = By.id("attributeValidationtypenn");
	By attributeValidationtypePkUq = By.id("attributeValidationtypepkuq"); 
	By addNewAttribute = By.xpath("//input[@value ='Add Attribute']");
	By updateEntity = By.xpath("//button[contains(text(),'Update')]");
	String[] entityArray = {"Day Calculation","Data Import", "Data Validation", "Daily Attendance","Consolidated Attendance"};

	By minValue = By.xpath("//input[@id='attributeValidationtypemin']");
	By maxValue = By.xpath("//input[@id='attributeValidationtypemax']");
	By numberRangeCheckbox = By.xpath("//input[@id='attributeValidationtyperange']");

	public Relations(WebDriver driver)
	{
		this.driver = driver;
	}


	public void relationManager(ExtentTest test)
	{
		try{
			extent = ExtentManager.Instance();
			WebDriverWait wait = new WebDriverWait(driver,10);
			Thread.sleep(2000);
			extent.loadConfig(new File("C:\\extent\\config.xml"));
			
			genericMethods.clickElement(driver, By.xpath("//p[text()='Project']"), test);
			genericMethods.clickElement(driver, By.xpath("//a[contains(text(),'Entity Management')]"), test);
			genericMethods.clickElement(driver, By.xpath("//button[text()='Manage Associations']"), test);
			Thread.sleep(5000);
			genericMethods.clickElement(driver, By.xpath("//div[@class='panel-heading']"), test);
			WebElement dragFrom1 = driver.findElement(By.xpath("//ul/li[@ondragstart='drag(event)'][2]"));
			WebElement dragTo1 = driver.findElement(By.xpath("//div[@id='entity-drawing-area']"));
			dragAndDropElement(driver, dragFrom1, dragTo1, 0, 90);
			Thread.sleep(1000);
			genericMethods.clickElement(driver, By.xpath("//div[@class='panel-heading']"), test);
			
			WebElement dragFrom = driver.findElement(By.xpath("//ul/li[@ondragstart='drag(event)'][1]"));
			WebElement dragTo = driver.findElement(By.xpath("//div[@id='entity-drawing-area']"));
			
			
			dragAndDropElement(driver, dragFrom, dragTo, 0, 90);
			Thread.sleep(5000);
			
			genericMethods.clickElement(driver, By.xpath("//span[@title='Click Here' and text()='t']"), test);
			genericMethods.clickElement(driver, By.xpath("//span[@title='Click Here' and text()='m']"), test);
			genericMethods.clickElement(driver, By.xpath("//button[@ng-click='submit()']"), test);
			//Thread.sleep(5000);
		}
	
	catch(Exception e)
	{
		e.printStackTrace();;
		test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
		test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
	}
	}

	public static void dragAndDropElement(WebDriver driver, WebElement dragFrom, WebElement dragTo, int xOffset, int yOffset) throws Exception {
		System.out.println("dragFrom ="+dragFrom + " dragTo = "+dragTo +" xOffset ="+xOffset +" yOffset ="+yOffset );
		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		//Fullscreen page so selenium coordinates work
		//robot.keyPress(KeyEvent.VK_F11);
		robot.mouseMove(200, 200);
		robot.mousePress(InputEvent.BUTTON1_MASK);


		// Get size of elements
		Dimension fromSize = dragFrom.getSize();
		Dimension toSize = dragTo.getSize();

		//Get centre distance
		int xCentreFrom = fromSize.width / 2;
		int yCentreFrom = fromSize.height / 2;
		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;

		Point toLocation = dragTo.getLocation();
		Point fromLocation = dragFrom.getLocation();
		System.out.println(fromLocation.toString());

		//Make Mouse coordinate centre of element
		toLocation.x += xOffset + xCentreTo;
		toLocation.y += yOffset + yCentreTo;
		fromLocation.x += xOffset + xCentreFrom;
		fromLocation.y += yOffset + yCentreFrom;

		System.out.println(fromLocation.toString());

		//Move mouse to drag from location
		robot.mouseMove(fromLocation.x, fromLocation.y);
		//robot.mouseMove(175,250);

		//       Thread.sleep(1000);
		//Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);

		//robot.mousePress(InputEvent.
		//Drag events require more than one movement to register
		//Just appearing at destination doesn't work so move halfway first
		robot.mouseMove(((toLocation.x - fromLocation.x) / 2) + fromLocation.x, ((toLocation.y
				- fromLocation.y) / 2) + fromLocation.y);

		//Move to final position
		robot.mouseMove(toLocation.x, toLocation.y);

		//robot.mouseMove(175,200);

		//Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
