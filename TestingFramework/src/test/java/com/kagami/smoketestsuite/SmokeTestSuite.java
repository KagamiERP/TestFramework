package com.kagami.smoketestsuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.kagami.library.ExtentManager;
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.kagami.studio.BuildDeploy;
import com.kagami.studio.BulkProjectCreation;
import com.kagami.studio.CustomizeDashBoard;
import com.kagami.studio.EntityCreation;
import com.kagami.studio.OrganisationCreationBase;
import com.kagami.studio.ProcessCreation;
import com.kagami.studio.ProjectCreation;
import com.kagami.studio.Relations;
import com.kagami.testconfig.BrowserSelection;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class SmokeTestSuite {
	public WebDriver driver;
	//public EntityManager entityManager;
	public EntityCreation entityCreation;
	ExtentReports extent;
	ExtentTest test;
	public ProjectCreation projectCreation;
	public OrganisationCreationBase organisationCreationBase; 
	public StudioCommonMethods studioCommonMethods;
	public BulkProjectCreation bulkProjectCreation;
	public ProcessCreation processCreation;
	public CustomizeDashBoard customizeDashBoard;
	public BuildDeploy buildDeploy;
	public Relations relation;



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
	
	/*@Test(priority = 1)
	public void relation()
	{
		test = extent.startTest("Relations: Test Suite", "Create Relations in Studio....");	
		relation = new Relations(driver);
		relation.relationManager(test);
		extent.endTest(test);
		extent.flush();
	}*/	
	@Test(priority = 1)
	public void projectCreation()
	{
		test = extent.startTest("Project Creation: Test Suite", "Create Project in Studio....");	
		projectCreation = new ProjectCreation(driver);
		projectCreation.newProjectCreation(test);
		extent.endTest(test);
		extent.flush();
	}	
	
	@Test(priority = 2)
	public void newOrgCreation()
	{
		test = extent.startTest("Organisation Creation: Test Suite", "Create Organisation....");	
		organisationCreationBase = new OrganisationCreationBase(driver);
		organisationCreationBase.orgCreation(test);
		extent.endTest(test);
		extent.flush();
	}
	
	
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
	public void processCreation()
	{
		test = extent.startTest("Process Creation: Test Suite", "Create Process in Studio....");	
		processCreation = new ProcessCreation(driver);
		processCreation.newProcessCreation(test);
		extent.endTest(test);
		extent.flush();
	}	


	@Test(priority = 5)
	public void dashboardCreation()
	{
		test = extent.startTest("Dashboard Creation:", "Create Dashboard in Studio....");	
		customizeDashBoard = new CustomizeDashBoard(driver);
		customizeDashBoard.customizeDashBoard(test);
		extent.endTest(test);
		extent.flush();
	}
	
	@Test(priority = 6)
	public void buildDeployment()
	{
		test = extent.startTest("Build Deployment:", "Build Deployment....");	
		buildDeploy = new BuildDeploy(driver);
		buildDeploy.generateTargetApp(test);
		extent.endTest(test);
		extent.flush();
	}
		

	@AfterClass
	public void browserShutDown() throws AddressException, MessagingException
	{
		driver.close();

		String fileContent = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader("./AutomationReport/TestReport.html"));
			String str;
			int passCount = 0;
			int failCount = 0;
			while ((str = in.readLine()) != null) {
				//fileContent +=str;
				if(str.contains("status pass"))
				{
					++passCount;
					System.out.println(str);
				}
				else if(str.contains("status fail"))
				{
					++failCount;
					System.out.println(str);
				}

			}
			System.out.println("Pass Count is = "+passCount);
			System.out.println("Fail Count is = "+failCount);
			emailConfig(passCount, failCount);
			in.close();
		} catch (IOException e) {
		}
	}






	@AfterSuite
	public void emailConfig(int passCount, int failCount) throws AddressException, MessagingException
	{
		//SMTP configuration
		String host = "smtp.office365.com";
		String port = "587";
		final String username = "manish.anand@kagamierp.com";
		final String password = "Anand@093";

		//Recipients
		String toAddress = "manish.anand@kagamierp.com";
		/*String ccAddress = "mallinath.mulage@kagamierp.com";
		String bccAddress = "mallinath.mulage@kagamierp.com";*/

		//SMTP server properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.office365.com");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		Session session = Session.getInstance(props, auth);

		//creates email
		String subject = "Test for Sending mail"+now();
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		/*msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(ccAddress));
		msg.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bccAddress));*/
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		String message = "Hi All," + "<br></br>"
				+ "Please find the below automation test execution details." 
				+ "<br></br>"
				+ "<table border=1><style=width:100%>"
				+"<tr>"
				+"<td bgcolor="+"#CCEEFF"+"><b>Serial No<b></td>"
				+"<td bgcolor="+"#CCEEFF"+"><b>Module Name<b></td>"
				+"<td bgcolor="+"#CCEEFF"+"><b>Total TC<b></td>"
				+"<td bgcolor="+"#00FF00"+"><b>TC Passed<b></td>"
				+"<td bgcolor="+"#FF0000"+"><b>TC Failed<b></td>"
				+"<td bgcolor="+"#0000FF"+"><b>TC Skipped<b></td>"
				+"</tr>"
				+"<tr>"
				+"<td>1</td>"
				+"<td>Studio Login</td>"
				+"<td>40</td>"
				+"<td>"+passCount+"</td>"
				+"<td>"+failCount+"</td>"
				+"<td>0</td>"
				+"</tr>"
				+"<tr>"
				+"<td>2</td>"
				+"<td>Payroll mgmt</td>"
				+"<td>50</td>"
				+"<td>30</td>"
				+"<td>10</td>"
				+"<td>10</td>"
				+"</tr>"
				+"<tr>"
				+"<td>3</td>"
				+"<td></td>"
				+"<td></td>"
				+"<td></td>"
				+"<td></td>"
				+"<td></td>"
				+"</tr>"
				+"<tr>"
				+"<td>4</td>"
				+"<td></td>"
				+"<td></td>"
				+"<td></td>"
				+"<td></td>"
				+"<td></td>"
				+"</tr>"
				+"</tr>"
				+ "</td></tr></table>"
				+"<br></br>"
				+"Thanks & Regards"
				+"<br>"
				+"<b><i>"+"Kagami QA Team"+"<i><b>"
				+"<br></br>"
				+ "Note: For more information you can download and open the attached zip file." 
				+ "<br></br>" ;



		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		//to zip a file		  
		File file = new File("./AutomationReport/TestReport.html");
		String zipFileName = "./extent/TestReports.zip";
		zipSingleFile(file, zipFileName); 
		//add attachments
		if(zipFileName!=null){
			MimeBodyPart attachpart = new MimeBodyPart();
			try{
				attachpart.attachFile(zipFileName);
			}catch (IOException ex){
				ex.printStackTrace();
			}
			multipart.addBodyPart(attachpart);
		}

		msg.setContent(multipart);
		Transport.send(msg);

	}



	private static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	private static void zipSingleFile(File file, String zipFileName) {

		try{
			//create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);

			//add a new Zip Entry to the ZipOutputStream
			ZipEntry ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);

			//read the file and write to ZipOutputStream
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while((len=fis.read(buffer))>0){
				zos.write(buffer, 0, len);
			}

			//Close the zip entry to write to zip file
			zos.closeEntry();
			//close resources
			zos.close();
			fis.close();
			fos.close();
			System.out.println(file.getCanonicalPath()+ " is zipped to "+zipFileName); 

		} catch	 (IOException e) {
			e.printStackTrace();
		}
	}

}

