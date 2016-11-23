package com.kagami.smoketestsuite;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.kagami.library.ExtentManager;
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.kagami.studio.BulkProjectCreation;
import com.kagami.studio.CustomizeDashBoard;
import com.kagami.studio.EntityCreation;
import com.kagami.studio.OrganisationCreationBase;
import com.kagami.studio.ProjectCreationBase;
import com.kagami.testconfig.BrowserSelection;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class SmokeTestSuite {
	public WebDriver driver;
	public EntityCreation entityGenerationBase;
	ExtentReports extent;
	ExtentTest test;
	public ProjectCreationBase projectCreationBase;
	public ProjectAndDashboardCreation projectAndDashboardCreation;
	public OrganisationCreationBase organisationCreationBase; 
	public StudioCommonMethods studioCommonMethods;
	public BulkProjectCreation bulkProjectCreation;
	public CustomizeDashBoard customizeDashBoard;

	@BeforeClass
	public void browserSelection() throws EncryptedDocumentException, AddressException, InvalidFormatException, IOException, InterruptedException, MessagingException
	{
		BrowserSelection browserSelection = new BrowserSelection();
		driver = browserSelection.browserType(driver, Global.sBrowserType);
	}

	@Test(priority = 0)
	public void studioSignIn()
	{
		extent = ExtentManager.Instance();
		extent.loadConfig(new File("C:\\extent\\config.xml"));
		test = extent.startTest("Studio Login", "Login....");
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogin(test);
		extent.endTest(test);
		extent.flush();
	}


	@Test(priority = 1)
	public void newOrgCreation()
	{
		test = extent.startTest("Organisation Creation: Test Suite", "Create Organisation....");	
		projectCreationBase = new ProjectCreationBase(driver);
		organisationCreationBase = new OrganisationCreationBase(driver);
		organisationCreationBase.orgCreation(test);
		extent.endTest(test);
		extent.flush();
	}


	@Test(priority = 2)
	public void entityCreation()
	{
		test = extent.startTest("Entity Creation: Test Suite", "Create Entity with multiple attributes....");
		entityGenerationBase = new EntityCreation(driver);
		entityGenerationBase.entityGeneration(test);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 3)
	public void projectCreation()
	{
		test = extent.startTest("Project & Process Creation: Test Suite", "Create Project & Process in Studio....");	
		projectAndDashboardCreation = new ProjectAndDashboardCreation(driver);
		projectAndDashboardCreation.newProjectCreation(test);
		extent.endTest(test);
		extent.flush();
	}	
	@Test(priority = 4)
	public void dashBoardCreation()
	{
		test = extent.startTest("DashBoard Creation: Test Suite", "Create Dashboard in Studio....");	
		customizeDashBoard = new CustomizeDashBoard(driver);
		customizeDashBoard.customizeDashBoard(test);
		extent.endTest(test);
		extent.flush();
	}


	@Test(priority = 5)
	public void bulkProjectCreation()
	{
		test = extent.startTest("Bulk Project Creation", "Create Multiple Projects in Kagami Studio....");	
		bulkProjectCreation = new BulkProjectCreation(driver);
		bulkProjectCreation.bulkProjectCreationInStudio(test);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 6)
	public void studioSignOut()
	{
		test = extent.startTest("Logout", "Logout from Studio....");	
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogOut(test);
		extent.endTest(test);
		extent.flush();
	}

	@AfterClass
	public void browserShutDown()
	{
		driver.close();
	}
}
