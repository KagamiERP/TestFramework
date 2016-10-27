package com.kagami.library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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

import org.openqa.selenium.io.Zip;
import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;
/** Summary 
Author Name: Deepika K
Method: Email Operations
Objective: This class will generate customized html report and configures Email with report attachment
Note : Do not change / please talk to author before you make any changes to the below code.*/

public class EmailConfig implements IReporter{

	Date d = new Date();
    String date = d.toString().replace(":", "_");
	private PrintWriter writer;
	private int m_row;
	private Integer m_testIndex;
	private int m_methodIndex;
	private String reportTitle= "Automation Test Report",
			reportFileName = "TestReport.html";

	protected PrintWriter createWriter(String outdir) throws IOException {
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, reportFileName))));
	}
	
	/** Creates summary of the run */
	
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,String outdir) {
		try {
			writer = createWriter(outdir);
		} catch (IOException e) {
			System.err.println("Unable to create output file");
			e.printStackTrace();
			return;
		}

		startHtml();
		
		writeReportTitle(reportTitle);
		String details = testEnvironmentDetails();
		String report = generateSuiteSummaryReport(suites);
		String style = startHtml();
		generateMethodSummaryReport(suites);
		generateMethodDetailReport(suites);
		endHtml();
		writer.flush();
		writer.close();
		try {
			sendMailwithReport(style,report,details);
			System.out.println("Mail sent sucessfully");
		} catch (AddressException e) {
			System.out.println("Mail not sent---Address Exception");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("Mail not sent---Messaging Exception");
			e.printStackTrace();
		}
	}
	
	private void sendMailwithReport(String style, String report, String details) throws AddressException, MessagingException {
		//SMTP configuration
		
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
				String subject = "Test for Sending mail"+getDateAsString();
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(username));
				InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
				msg.setRecipients(Message.RecipientType.TO, toAddresses);
			//	msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(ccAddress));
			//	msg.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bccAddress));*/
				msg.setSubject(subject);
				msg.setSentDate(new Date());
				
				// creates message part
				String message =  "<!DOCTYPE html>"
						+"<head>"
						+"<title>"+reportTitle+"</title>"
						+"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' crossorigin='anonymous'>"
						+"<style>.stacktrace{white-space:pre}</style>"
					+"</head>"
					+"<body><div class=container-fluid>"
						+"<p>Hi All,</p>"
						+"<p>Please find the below automation test execution details.</p>"	
						  +style+report+"<br></br>"+details
							+"<p>Thanks & Regards</p>"
							+"<h4>Kagami QA Team</h4>"
						    + "<dt>Note: For more information you can download and open the attached zip file.</dt>"  
						    +"</div></body></html>";
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				  messageBodyPart.setContent(message, "text/html");
				  // messageBodyPart
				  // creates multi-part
				  Multipart multipart = new MimeMultipart();
				  multipart.addBodyPart(messageBodyPart);
				  
				//to zip a file		  
				 File file = new File("./AutomationReport/"+reportFileName);
				 // File file = new File("./AutomationReport/");
				  String zipFileName = "./TestReport/"+date+"_Reports.zip";
				  
				 zipSingleFile(file, zipFileName); 
				 
				
				 
			        
				 //add attachments
				  
				  //if(zipFileName!=null){
					  MimeBodyPart attachpart = new MimeBodyPart();
					  try{
						  attachpart.attachFile(file);
					  }catch (IOException ex){
						  ex.printStackTrace();
					  }
					  multipart.addBodyPart(attachpart);
				  //}
				  
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

	/** Starts HTML stream */
	protected String startHtml() {
		return null;
		
	}
	
	protected void writeReportTitle(String title) {
		writer.print("<div class='text-center'><img src='data:image/png;base64,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'></div>" 
					+"<h3 class='text-center'>"
						+ title + " - " + getDateAsString() + "</h3>");
	}

	private String environmentDetailsRow(String heading, String value){
		return "<tr>"
			    	+ "<th>" + heading + "</th>"
			    	+ "<td>" + value + "</td>"
			    +"</tr>";
	}
	private String testEnvironmentDetails() {
		String testEnvironmentDetails = 
				"<div class=row>"
					+ "<div class=\"col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2\">"
						+	"<table class=\"table table-bordered table-striped\">"
								+"<tr>"
									+"<th colspan='2'class=text-center bgcolor='#FFE4E1'>Automation Test Environment</th>"
								+"</tr>"
								+environmentDetailsRow("Browser Type", Global.sBrowserType)
								+environmentDetailsRow("URL", Global.sUrl)
								+environmentDetailsRow("OS", System.getProperty("os.name"))
								+environmentDetailsRow("BUILD Info", Global.BuildInfo)
						+"</table>"
					+"</div>"
				+"</div>";
				
		writer.println(testEnvironmentDetails+"<hr>");
		return testEnvironmentDetails;
	}
	
	public String rowGenerateSuiteSummaryReport(String suiteName, int pass, int fail, int skip){
		return "<tr>"
					+ "<th>" + suiteName + "</th>"
					+ "<td class=\"text-center\"><strong>" + (pass + fail + skip) + "</strong></td>"
					+ "<td class=\"text-center\"><strong>" + (pass + fail) + "</strong></td>"
					+ "<td class=\"text-center\"><strong>" + pass + "</strong></td>"
					+ "<td class=\"text-center\"><strong>" + fail + "</strong></td>"
					+ "<td class=\"text-center\"><strong>" + skip + "</strong></td>"
				+ "</tr>";	
	}
	
	public String totalGenerateSuiteSummaryReport(int pass, int fail, int skip){
		return "<tr class=\"active\">"
					+ "<th> Total </th>"
					+ "<td class=\"text-center\"><strong>" + (pass + fail + skip) + "</strong></td>"
					+ "<td class=\"text-center\"><strong>" + (pass + fail) + "</strong></td>"
					+ "<td class=\"text-center\" "+(pass > 0 ? "style='background-color:#2ECC71'": "") +"><strong>" + pass + "</strong></td>"
					+ "<td class=\"text-center\" "+(fail > 0 ? "style='background-color:#E74C3C'": "") +"><strong>" + fail + "</strong></td>"
					+ "<td class=\"text-center\" "+(skip > 0 ? "style='background-color:#FFCE56'": "") +"><strong>" + skip + "</strong></td>"
				+ "</tr>";	
	}
	
	public String getChart(int pass, int fail, int skip){
		return "<canvas id=\"myChart\" width=\"400\" height=\"400\"></canvas>"
				+"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.1/Chart.min.js\"></script>"
				+"<script>var ctx = document.getElementById(\"myChart\");"
				+"var myPieChart = new Chart(ctx,{type: 'doughnut',data: {labels: ['Pass', 'Fail', 'Skipped'], datasets: [{data: ["+pass+","+fail+","+skip+"],  backgroundColor: ['#2ECC71','#E74C3C','#FFCE56']}]}});"
				+"</script>";
	}

	@SuppressWarnings("unused")
	public String generateSuiteSummaryReport(List<ISuite> suites) {
		//tableStart("testOverview", null);
		String generateSuiteSummaryReport = 
				"<div class=row>"
					+ "<div class=\"col-md-7 col-md-offset-1 col-sm-8 col-sm-offset-1\">"
						+	"<table class=\"table table-bordered\">" 
							+ "<tr><th colspan='6'	class=\"text-center\" bgcolor='#FFE4E1'> Test Execution Summary</th></tr>"
							+ "<tr>"
								+ "<th class=\"text-center\">Module Name</th>"
								+ "<th class=\"text-center\">Total TC</th>"
								+ "<th class=\"text-center\">Executed</th>"
								+ "<th class=\"text-center\">Passed</th>"
								+ "<th class=\"text-center\">Failed</th>"
								+ "<th class=\"text-center\">Skipped</th>"
							+ "</tr>";
					

		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_pass_m = 0;
		int qty_pass_s = 0;
		int qty_skip = 0;
		long time_start = Long.MAX_VALUE;
		int qty_fail = 0;
		long time_end = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				
				int pass_m = getMethodSet(overview.getPassedTests(), suite).size();
				qty_pass_m += pass_m;
				
				int skip_m = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += skip_m;
				
				int fail_m = getMethodSet(overview.getFailedTests(), suite).size();
				qty_fail += fail_m;
				
				generateSuiteSummaryReport = generateSuiteSummaryReport + rowGenerateSuiteSummaryReport(
						suite.getName() + "-" +overview.getName(),
						pass_m,
						fail_m,
						skip_m
				);
				
				m_testIndex++;
			}
		}
		
		
		//To get grandtotal of all suites
		if (qty_tests > 0) {
			String totalRow = totalGenerateSuiteSummaryReport(qty_pass_m, qty_fail, qty_skip);
			generateSuiteSummaryReport = generateSuiteSummaryReport + totalRow;
		}
		generateSuiteSummaryReport = generateSuiteSummaryReport + "</table></div>"
				+ "<div class=\"col-md-3 col-sm-3\">"
					+ getChart(qty_pass_m, qty_fail, qty_skip)
				+ "</div></div>";
		
		writer.print(generateSuiteSummaryReport);
		return generateSuiteSummaryReport;
	}


	

	/**
	 * Creates a table showing the highlights of each test method with links to
	 * the method details
	 */
	protected void generateMethodSummaryReport(List<ISuite> suites) {
		m_methodIndex = 0;
		
		String startResultSummaryTable = "<h3 class=text-center id=summary>Test Execution Detailed</h3>"
				+ "<div class=row><div class=\"col-md-12\">"
					+	"<table class=\"table table-bordered\">" 
						+ "<tr class=info>"
						+ "<th class=\"text-center\">Class</th>"
						+ "<th class=\"text-center\">Method</th>"
						+ "<th class=\"text-center\">Exception Info</th>"
						+ "<th class=\"text-center\">Status</th>"
						+ "<th class=\"text-center\">Start Time</th>"
						+ "<th class=\"text-center\">Execution Time<br/>(hh:mm:ss)</th>"
					+ "</tr>";

		writer.println(startResultSummaryTable);
		m_row = 0;
		
		int testIndex = 1;
		for (ISuite suite : suites) {
			int noofsuites = suites.size();
			if (noofsuites >= 1) {
				titleRow(suite.getName(), 5);
			}
	
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				String testName = testContext.getName();
				m_testIndex = testIndex;
				resultSummary(suite, testContext.getFailedConfigurations(), testName, "Failed", " (configuration methods)");
				resultSummary(suite, testContext.getFailedTests(), testName, "Failed", "");
				resultSummary(suite, testContext.getSkippedConfigurations(), testName, "Skipped", " (configuration methods)");
				resultSummary(suite, testContext.getSkippedTests(), testName, "Skipped", "");
				resultSummary(suite, testContext.getPassedTests(), testName, "Passed", "");
				testIndex++;
			}
		}
		writer.println("</table></div></div>");
	}
   
	/** Creates a section showing known results for each method */
	protected void generateMethodDetailReport(List<ISuite> suites) {
		m_methodIndex = 0;
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				if (r.values().size() > 0) {
					writer.println("<h3>" + testContext.getName() + "</h3>");
				}
				resultDetail(testContext.getFailedConfigurations());
				resultDetail(testContext.getFailedTests());
				resultDetail(testContext.getSkippedConfigurations());
				resultDetail(testContext.getSkippedTests());
				resultDetail(testContext.getPassedTests());
			}
		}
	}

	/**
	 * @param tests
	 */
	private void resultSummary(ISuite suite, IResultMap tests, String testname,
			String style, String details) {
		
		if (tests.getAllResults().size() > 0) {
			StringBuffer buff = new StringBuffer();
			String lastClassName = "";
			int mq = 0;
			int cq = 0;
			for (ITestNGMethod method : getMethodSet(tests, suite)) {
				m_row += 1;
				m_methodIndex += 1;
				ITestClass testClass = method.getTestClass();
				String className = testClass.getName();
				if (mq == 0) {
					String id = (m_testIndex == null ? null : "t"
							+ Integer.toString(m_testIndex));
					titleRow(testname + " &#8212; " + style + details, 5, id);
					m_testIndex = null;
				}
				if (!className.equalsIgnoreCase(lastClassName)) {
					if (mq > 0) {
						cq += 1;
						writer.print("<tr class=\"" + style 
								+ (cq % 2 == 0 ? "even" : "odd") + "\">"
								+ "<td");
						if (mq > 1) {
							writer.print(" rowspan=\"" + mq + "\"");
						}
						writer.println(">" + lastClassName + "</td>" + buff);
					}
					mq = 0;
					buff.setLength(0);
					lastClassName = className;
				}
				Set<ITestResult> resultSet = tests.getResults(method);
				long end = Long.MIN_VALUE;
				long start = Long.MAX_VALUE;
				long startMS=0;
				String firstLine="";
				
				for (ITestResult testResult : tests.getResults(method)) {
					if (testResult.getEndMillis() > end) {
						end = testResult.getEndMillis()/1000;
					}
					if (testResult.getStartMillis() < start) {
						startMS = testResult.getStartMillis();
						start =startMS/1000;
					}
					
					Throwable exception=testResult.getThrowable();
					boolean hasThrowable = exception != null;
					if(hasThrowable){
						String str = Utils.stackTrace(exception, true)[0];
						Scanner scanner = new Scanner(str);
						firstLine = scanner.nextLine();
					}
				}
				DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
				Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(startMS);
			     
				mq += 1;
				if (mq > 1) {
					buff.append("<tr class=\"" + style + (cq % 2 == 0 ? "odd" : "even") + "\">");
				}
				String description = method.getDescription();
				String testInstanceName = resultSet
						.toArray(new ITestResult[] {})[0].getTestName();
				buff.append("<td><a href=\"#m"
						+ m_methodIndex
						+ "\">"
						+ qualifiedName(method)
						+ " "
						+ (description != null && description.length() > 0 ? "(\""
								+ description + "\")"
								: "")
								+ "</a>"
								+ (null == testInstanceName ? "" : "<br>("
										+ testInstanceName + ")") + "</td>"
										+ "<td class=\"numi\" style=\"text-align:left;padding-right:2em\">" + firstLine+"<br/></td>"
										+ "<td style=\"text-align:right\">" + formatter.format(calendar.getTime()) + "</td>" + "<td class=\"numi\">"
										+ timeConversion(end - start) + "</td>" + "</tr>");
				
			}
			if (mq > 0) {
				cq += 1;
				writer.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
				if (mq > 1) {
					writer.print(" rowspan=\"" + mq + "\"");
				}
				writer.println(">" + lastClassName + "</td>" + buff);
			}
		}
	}
    
	
	private String timeConversion(long seconds) {

	    final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;

	    int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
	    seconds -= minutes * SECONDS_IN_A_MINUTE;

	    int hours = minutes / MINUTES_IN_AN_HOUR;
	    minutes -= hours * MINUTES_IN_AN_HOUR;

	    return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int)seconds);
	}
	
	private String prefixZeroToDigit(int num){
		int number=num;
		if(number<=9){
			String sNumber="0"+number;
			return sNumber;
		}
		else
			return ""+number;
		
	}
	
	private String qualifiedName(ITestNGMethod method) {
		StringBuilder addon = new StringBuilder();
		String[] groups = method.getGroups();
		int length = groups.length;
		if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
			addon.append("(");
			for (int i = 0; i < length; i++) {
				if (i > 0) {
					addon.append(", ");
				}
				addon.append(groups[i]);
			}
			addon.append(")");
		}

		return "<b>" + method.getMethodName() + "</b> " + addon;
	}

	private void resultDetail(IResultMap tests) {
		Set<ITestResult> testResults=tests.getAllResults();
		List<ITestResult> testResultsList = new ArrayList<ITestResult>(testResults);
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
		Collections.sort(testResultsList, new TestResultsSorter());
		for (ITestResult result : testResultsList) {
			ITestNGMethod method = result.getMethod();
			m_methodIndex++;
			String cname = method.getTestClass().getName();
			writer.println("<h4 id='m"+m_methodIndex+"'>" + cname  + method.getMethodName() + "</h4>");
			Set<ITestResult> resultSet = tests.getResults(method);
			generateResult(result, method, resultSet.size());
			writer.println("<p class='totop'><a href='#summary'>back to summary</a></p>");
			writer.println("<hr/>");
		}
	}

	private void generateResult(ITestResult ans, ITestNGMethod method,
			int resultSetSize) {
		Object[] parameters = ans.getParameters();
		boolean hasParameters = parameters != null && parameters.length > 0;
		if (hasParameters) {
			tableStart("result", null);
			writer.print("<tr class=\"param\">");
			for (int x = 1; x <= parameters.length; x++) {
				writer.print("<th>Param." + x + "</th>");
			}
			writer.println("</tr>");
			writer.print("<tr class=\"param stripe\">");
			for (Object p : parameters) {
				writer.println("<td>" + Utils.escapeHtml(Utils.toString(p))
						+ "</td>");
			}
			writer.println("</tr>");
		}
		List<String> msgs = Reporter.getOutput(ans);
		boolean hasReporterOutput = msgs.size() > 0;
		Throwable exception = ans.getThrowable();
		boolean hasThrowable = exception != null;
		if (hasReporterOutput || hasThrowable) {
			if (hasParameters) {
				writer.print("<tr><td");
				if (parameters.length > 1) {
					writer.print(" colspan=\"" + parameters.length + "\"");
				}
				writer.println(">");
			} else {
				writer.println("<div>");
			}
			if (hasReporterOutput) {
				if (hasThrowable) {
					writer.println("<h3>Test Messages</h3>");
				}
				for (String line : msgs) {
					writer.println(line + "<br/>");
				}
			}
			if (hasThrowable) {
				boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					writer.println("<h3>"
							+ (wantsMinimalOutput ? "Expected Exception"
									: "Failure") + "</h3>");
				}
				generateExceptionReport(exception, method);
			}
			if (hasParameters) {
				writer.println("</td></tr>");
			} else {
				writer.println("</div>");
			}
		}
		if (hasParameters) {
			writer.println("</table>");
		}
	}

	protected void generateExceptionReport(Throwable exception, ITestNGMethod method) {
		writer.print("<div class=\"stacktrace\">");
		writer.print(Utils.stackTrace(exception, true)[0]);
		writer.println("</div>");
	}

	/**
	 * Since the methods will be sorted chronologically, we want to return the
	 * ITestNGMethod from the invoked methods.
	 */
	private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
		
		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}
		
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
		Collections.sort(r,new TestSorter());
		List<ITestNGMethod> result = Lists.newArrayList();
		
		// Add all the invoked methods
		for (IInvokedMethod m : r) {
			for (ITestNGMethod temp : result) {
				if (!temp.equals(m.getTestMethod()))
					result.add(m.getTestMethod());
			}
		}
		
		// Add all the methods that weren't invoked (e.g. skipped) that we
		// haven't added yet
		Collection<ITestNGMethod> allMethodsCollection=tests.getAllMethods();
		List<ITestNGMethod> allMethods=new ArrayList<ITestNGMethod>(allMethodsCollection);
		Collections.sort(allMethods, new TestMethodSorter());
		
		for (ITestNGMethod m : allMethods) {
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}
	
	
    
	
	private void summaryCell(String[] val) {
		StringBuffer b = new StringBuffer();
		for (String v : val) {
			b.append(v + " ");
		}
		summaryCell(b.toString(), true);
	}

	private void summaryCell(String v, boolean isgood) {
		writer.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
				+ "</td>");
	}

	private void startSummaryRow(String label) {
		m_row += 1;
		writer.print("<tr"
				+ (m_row % 2 == 0 ? " class=\"stripe\"" : "")
				+ "><td style=\"text-align:left;padding-right:2em\"><!--a href=\"#t"
				+ m_testIndex + "\"--><b>" + label + "</b><!--/a-->" + "</td>");
		
	}

	private void summaryCell(int v, int maxexpected) {
		summaryCell(String.valueOf(v), v <= maxexpected);
	}

	private void tableStart(String cssclass, String id) {
		writer.println("<table cellspacing=\"0\" cellpadding=\"0\""
				+ (cssclass != null ? " class=\"" + cssclass + "\""
						: " style=\"padding-bottom:2em\"")
						+ (id != null ? " id=\"" + id + "\"" : "") + ">");
		m_row = 0;
	}

	private void titleRow(String label, int cq) {
		titleRow(label, cq, null);
	}

	private void titleRow(String label, int cq, String id) {
		writer.print("<tr");
		if (id != null) {
			writer.print(" id=\"" + id + "\"");
		}
		writer.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
		m_row = 0;
	}

	
	

	/*
	 Method to get Date as String
	 */
	private static String getDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	

	/** Finishes HTML stream */
	protected void endHtml() {		
		writer.println("</div></body></html>");
	}

	// ~ Inner Classes --------------------------------------------------------
	/** Arranges methods by classname and method name */
	private class TestSorter implements Comparator<IInvokedMethod> {
		// ~ Methods
		// -------------------------------------------------------------

		/** Arranges methods by classname and method name */
		
		public int compare(IInvokedMethod obj1, IInvokedMethod obj2) {
			int r = obj1.getTestMethod().getTestClass().getName().compareTo(obj2.getTestMethod().getTestClass().getName());
			return r;
		}
	}
	
	private class TestMethodSorter implements Comparator<ITestNGMethod> {
		
		public int compare(ITestNGMethod obj1, ITestNGMethod obj2) {
			int r = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
			if (r == 0) {
				r = obj1.getMethodName().compareTo(obj2.getMethodName());
			}
			return r;
		}
	}

	private class TestResultsSorter implements Comparator<ITestResult> {
		
		public int compare(ITestResult obj1, ITestResult obj2) {
			int result = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
			if (result == 0) {
				result = obj1.getMethod().getMethodName().compareTo(obj2.getMethod().getMethodName());
			}
			return result;
		}
	}

}