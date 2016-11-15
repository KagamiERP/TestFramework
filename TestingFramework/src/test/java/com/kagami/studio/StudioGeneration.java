package com.kagami.studio;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.kagami.library.Global;
import com.kagami.testconfig.BrowserSelection;

public class StudioGeneration {
	public WebDriver driver;
	public EntityGenerationBase entityGenerationBase;
	public OrgCreationBase orgCreationBase;
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
		studioCommonMethods = new StudioCommonMethods(driver);
		studioCommonMethods.studioLogin();
	}


/*	@Test(priority = 1)
	public void newOrgCreation()
	{

		organisationCreationBase = new OrganisationCreationBase(driver);
		organisationCreationBase.orgCreation();
	}

	@Test(priority = 2)
	public void newEntityCreation()
	{
		entityGenerationBase = new EntityGenerationBase(driver);
		entityGenerationBase.entityGeneration();
	}

*/
		@Test(priority = 2)
	public void projectCreation()
	{
			projectCreationBase = new ProjectCreationBase(driver);
			projectCreationBase.newProjectCreation();

	}	





	/*@AfterClass
	public void browserShutDown()
	{
		driver.close();
	}*/
}
