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
	public MultipleProjectCreationAndEdit multipleProjectCreationAndEdit;
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

/*
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

*/

	/*@Test(priority = 4)
	public void multipleProjectCreation()
	{
		test = extent.startTest("Multiple Project Creation", "Create Multiple Projects, Modules & Submodules in Kagami Studio....");	
		multipleProjectAndProcessCreation = new MultipleProjectAndProcessCreation(driver);
		multipleProjectAndProcessCreation.multipleProjectCreation(test, workBookName);
		extent.endTest(test);
		extent.flush();
	}
	*/
	@Test(priority = 4)
	public void multipleProjectCreationAndEdit()
	{
		test = extent.startTest("Multiple Project Creation", "Create Multiple Projects, Modules & Submodules in Kagami Studio....");	
		multipleProjectCreationAndEdit = new MultipleProjectCreationAndEdit(driver);
		multipleProjectCreationAndEdit.multipleProjectCreationAndEdit(test, workBookName);
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

	/*
	@AfterClass
	public void browserShutDown() throws AddressException, MessagingException
	{
		driver.close();

		String fileContent = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader("./AutomationReport/TestReport.html"));
			String status;
			int passCount = 0;
			int failCount = 0;
			while ((status = in.readLine()) != null) {
				//fileContent +=str;
				if(status.contains("status pass"))
				{
					++passCount;
					System.out.println(status);
				}
				else if(status.contains("status fail"))
				{
					++failCount;
					System.out.println(status);
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
	//	String ccAddress = "mallinath.mulage@kagamierp.com";
	//	String bccAddress = "mallinath.mulage@kagamierp.com";

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
		String subject = "Automation_Report_"+now();
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
	//	msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(ccAddress));
	//	msg.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bccAddress));
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
				+"<td>Entity Creation</td>"
				+"<td>100</td>"
				+"<td>"+passCount+"</td>"
				+"<td>"+failCount+"</td>"
				+"<td>0</td>"
				+"</tr>"
				+"<tr>"
				+"<td>2</td>"
				+"<td>Module Creation</td>"
				+"<td>110</td>"
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
	}*/

}


	
