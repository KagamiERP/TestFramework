package com.kagami.editcomponent;

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
import com.kagami.studio.BuildDeploy;
import com.kagami.studio.BulkProjectCreation;
import com.kagami.studio.CustomizeDashBoard;
import com.kagami.studio.EntityCreation;
import com.kagami.studio.MasterData;
import com.kagami.studio.OrganisationCreationBase;
import com.kagami.studio.ProcessCreation;
import com.kagami.studio.ProjectCreation;
import com.kagami.studio.Relations;
import com.kagami.testconfig.BrowserSelection;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class EditComponentTestSuite {
	public WebDriver driver;
	//public EntityManager entityManager;
	public EntityCreation entityCreation;
	ExtentReports extent;
	ExtentTest test;

	public ProjectCreationAndEdit projectCreationAndEdit;
	public OrganisationCreationBase organisationCreationBase; 
	public StudioCommonMethods studioCommonMethods;
	public BulkProjectCreation bulkProjectCreation;
	public ProcessCreation processCreation;
	public CustomizeDashBoard customizeDashBoard;
	public BuildDeploy buildDeploy;
	public Relations relation;
	public MasterData masterData;



	@BeforeClass
	public void browserSelection() throws EncryptedDocumentException, AddressException, InvalidFormatException, IOException, InterruptedException, MessagingException
	{
		BrowserSelection browserSelection = new BrowserSelection();
		driver = browserSelection.browserType(driver, Global.sBrowserType);
	}

	@Test(priority = 0)
	public void studioSignIn() throws InvalidFormatException, IOException, InterruptedException
	{
		extent = ExtentManager.Instance();
		extent.loadConfig(new File("./extent/config.xml"));
		test = extent.startTest("Studio Login", "Login to Kagami Studio....");
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogin(test);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 1)
	public void projectCreationAndEdit()
	{
		test = extent.startTest("Project Name Edit:", "Create Project & Edit it....");	
		projectCreationAndEdit = new ProjectCreationAndEdit(driver);
		projectCreationAndEdit.newProjectCreationAndEdit(test);
		extent.endTest(test);
		extent.flush();
	}	

	/*@Test(priority = 2)
	public void newOrgCreation()
	{
		test = extent.startTest("Organisation Creation: Test Suite", "Create Organisation....");	
		organisationCreationBase = new OrganisationCreationBase(driver);
		organisationCreationBase.orgCreation(test);
		extent.endTest(test);
		extent.flush();
	}*/
/*
	@Test(priority = 3)
	public void entityCreation()
	{
		test = extent.startTest("Entity Creation: Test Suite", "Create Entity with multiple attributes....");
		entityCreation = new EntityCreation(driver);
		entityCreation.entityGeneration(test);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 4)
	public void relation()
	{
		test = extent.startTest("Relations: Test Suite", "Create Relations in Studio....");	
		relation = new Relations(driver);
		relation.relationManager(test);
		extent.endTest(test);
		extent.flush();
	}	


	@Test(priority = 5)
	public void processCreation()
	{
		test = extent.startTest("Process Creation: Test Suite", "Create Process in Studio....");	
		processCreation = new ProcessCreation(driver);
		processCreation.newProcessCreation(test);
		extent.endTest(test);
		extent.flush();
	}	

	@Test(priority = 6)
	public void dashboardCreation()
	{
		test = extent.startTest("Dashboard Creation:", "Create Dashboard in Studio....");	
		customizeDashBoard = new CustomizeDashBoard(driver);
		customizeDashBoard.customizeDashBoard(test);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 7)
	public void buildDeployment()
	{
		test = extent.startTest("Build Deployment:", "Build Deployment & Generate EUA....");	
		buildDeploy = new BuildDeploy(driver);
		buildDeploy.generateTargetApp(test);
		extent.endTest(test);
		extent.flush();
	}

	@Test(priority = 8)
	public void studioSignOut()
	{
		test = extent.startTest("Logout", "Logout from Studio....");	
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogOut(test);
		extent.endTest(test);
		extent.flush();
	}
*/

/*	@AfterClass
	public void tearDown()
	{
		driver.close();
	}*/
}

