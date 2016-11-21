package com.kagami.smoketestsuite;

import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.kagami.library.ExtentManager;
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.kagami.studio.EntityGenerationBase;
import com.kagami.studio.OrganisationCreationBase;
import com.kagami.studio.ProjectCreationBase;
import com.kagami.testconfig.BrowserSelection;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class SmokeTestSuite {
	public WebDriver driver;
	public EntityGenerationBase entityGenerationBase;
	ExtentReports extent;
	ExtentTest test;
	public ProjectCreationBase projectCreationBase;
	public OrganisationCreationBase organisationCreationBase; 
	public StudioCommonMethods studioCommonMethods;

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
	public void entityCreation()
	{
	//	extent.loadConfig(new File("C:\\extent\\config.xml"));
		test = extent.startTest("Entity Creation: Test Suite", "Create Entity with multiple attributes....");
		entityGenerationBase = new EntityGenerationBase(driver);
		entityGenerationBase.entityGeneration(test);
		extent.endTest(test);
		extent.flush();
	}
	

	@Test(priority = 2)
	public void projectCreation()
	{
		test = extent.startTest("Project & Process Creation: Test Suite", "Create Project & Process in Studio....");	
		projectCreationBase = new ProjectCreationBase(driver);
		projectCreationBase.newProjectCreation(test);
		extent.endTest(test);
		extent.flush();
	}	
			
	@Test(priority = 3)
	public void newOrgCreation()
	{
		test = extent.startTest("Organisation Creation: Test Suite", "Create Organisation....");	
		projectCreationBase = new ProjectCreationBase(driver);
		organisationCreationBase = new OrganisationCreationBase(driver);
		organisationCreationBase.orgCreation(test);
		extent.endTest(test);
		extent.flush();
	}
	


	
	@Test(priority = 4)
	public void studioSignOut()
	{
		test = extent.startTest("Logout", "Logout from Studio....");	
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogOut(test);
		extent.endTest(test);
		extent.flush();
	}
	

	
	
		 
	/*@AfterClass
	public void browserShutDown()
	{
		driver.close();
	}*/
}
