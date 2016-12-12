package com.kagami.library;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class EmailConfigNew {

	public static void sendmailwithtable() throws AddressException, MessagingException {

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
				+"<td bgcolor="+"#FF0000"+"><b>TC Failed<b></td>"
				+"<td bgcolor="+"#00FF00"+"><b>TC Passed<b></td>"
				+"<td bgcolor="+"#0000FF"+"><b>TC Skipped<b></td>"
				+"</tr>"
				+"<tr>"
				+"<td>1</td>"
				+"<td>Employee Management</td>"
				+"<td>40</td>"
				+"<td>30</td>"
				+"<td>5</td>"
				+"<td>5</td>"
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
				+"<b><i>"+"Kagami QA Team"+"<i><b>"
				+"<br></br>"
				+ "Note: For more information you can download and open the attached zip file." 
				+ "<br></br>" ;



		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		// messageBodyPart
		// creates multi-part
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


	private static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		return sdf.format(cal.getTime());
	}
	// Test sending e-mail with attachments  

	public static void main(String[] args) throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException{

		try 
		{

			sendmailwithtable();
			System.out.println("Email has been sent successfully to the registerd id.");
		} 
		catch (Exception ex) 
		{
			System.out.println("Oops!!Could not send email");
			ex.printStackTrace();
		}		



	}
}


