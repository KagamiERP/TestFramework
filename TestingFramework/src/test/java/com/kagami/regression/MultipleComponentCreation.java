package com.kagami.regression;

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
import com.kagami.studio.BuildDeploy;
import com.kagami.studio.CustomizeDashBoard;
import com.kagami.studio.EntityCreation;
import com.kagami.studio.MasterData;
import com.kagami.studio.OrganisationCreationBase;
import com.kagami.studio.ProcessCreation;
import com.kagami.studio.ProjectCreation;
import com.kagami.studio.Relations;
import com.kagami.testconfig.BrowserSelection;
import com.kagami.testconfig.Testconfiguration;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class MultipleComponentCreation extends Testconfiguration{
	public WebDriver driver;
	public EntityCreation entityCreation;
	ExtentReports extent;
	ExtentTest test;

	public ProjectCreation projectCreation;
	public OrganisationCreationBase organisationCreationBase; 
	public StudioCommonMethods studioCommonMethods;
	public MultipleProjectAndProcessCreation multipleProjectAndProcessCreation;
	public ProcessCreation processCreation;
	public CustomizeDashBoard customizeDashBoard;
	public BuildDeploy buildDeploy;
	public Relations relation;
	public MasterData masterData;
	String workBookName =  "./TestData/Regression.xlsx";

	@BeforeClass
	public void browserSelection() throws EncryptedDocumentException, AddressException, InvalidFormatException, IOException, InterruptedException, MessagingException
	{
		BrowserSelection browserSelection = new BrowserSelection();
		driver = browserSelection.browserType(driver, Global.browserType);
	}


	@Test(priority = 0)
	public void studioSignIn() throws InvalidFormatException, IOException, InterruptedException
	{
		extent = ExtentManager.Instance();
		extent.loadConfig(new File("./extent/config.xml"));
		test = extent.startTest("Studio Login", "Login to Kagami Studio....");
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogin(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}


	@Test(priority = 2)
	public void projectCreation()
	{
		test = extent.startTest("Project Creation: Test Suite", "Create Project in Studio....");	
		projectCreation = new ProjectCreation(driver);
		projectCreation.newProjectCreation(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 3)
	public void entityCreation()
	{
		test = extent.startTest("Entity Creation: Test Suite", "Create Entity with multiple attributes....");
		entityCreation = new EntityCreation(driver);
		entityCreation.entityGeneration(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}



	@Test(priority = 4)
	public void multipleProjectCreation()
	{
		test = extent.startTest("Multiple Project Creation", "Create Multiple Projects, Modules & Submodules in Kagami Studio....");	
		multipleProjectAndProcessCreation = new MultipleProjectAndProcessCreation(driver);
		multipleProjectAndProcessCreation.multipleProjectCreation(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}
	

	/*	@Test(priority = 1)
	public void projectCreation()
	{
		test = extent.startTest("Project Creation: Test Suite", "Create Project in Studio....");	
		projectCreation = new ProjectCreation(driver);
		projectCreation.newProjectCreation(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}	

	@Test(priority = 3)
	public void entityCreation()
	{
		test = extent.startTest("Entity Creation: Test Suite", "Create Entity with multiple attributes....");
		entityCreation = new EntityCreation(driver);
		entityCreation.entityGeneration(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}*/

	@Test(priority = 8)
	public void studioSignOut()
	{
		test = extent.startTest("Logout", "Logout from Studio....");	
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogOut(test);
		extent.endTest(test);
		extent.flush();
	}


	/*	@AfterClass
	public void tearDown()
	{
		driver.close();
	}*/
}

