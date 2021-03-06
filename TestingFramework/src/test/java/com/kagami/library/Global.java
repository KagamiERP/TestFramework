package com.kagami.library;

import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.WebDriverWait;

	public class Global extends CustomizedReport{
		
		GenericMethods genericMethods = new GenericMethods();
		public static final int iMaxRetryCount = 1;

		public static WebDriver wDriver;
		public static WebDriverWait wait;
		public static JavascriptExecutor js;
		
		//	Browser Type
		//	public static String browserType = "firefox";
		//	public static String browserType = "IE";
			public static String browserType = "Chrome";
		
	//		Build Information
			public static String BuildInfo ="Argentium Version 1.0";
		
			
    //Test Data file path
	//  public static String testSheet = "./TestData/testInput.xlsx";
	    public static String testSheet = "./TestData/smokeTestInput.xlsx";
	//	public static String testSheet = "./TestData/TC_01_Create_View.xlsx";
	//	public static String testSheet = "./TestData/TC_02_Create.xlsx";
	//	public static String testSheet = "./TestData/TC_03_View.xlsx";
	//	public static String testSheet = "./TestData/TC_04_Create_Fetch_View.xlsx";
	//	public static String testSheet = "./TestData/TC_05_Create_Transform_View.xlsx";
	//	public static String testSheet = "./TestData/TC_06_Create_Switch_View.xlsx";
	//	public static String testSheet = "./TestData/TC_07_Create_Fetch_Edit_View.xlsx";
	//	public static String testSheet = "./TestData/TC_08_Edit_View.xlsx";
	//	public static String testSheet = "./TestData/TC_09_Create_Fetch_View_Edit_Triggers.xlsx";
	//	public static String testSheet = "./TestData/TC_11_ViewTemplate.xlsx";
	//	public static String testSheet = "./TestData/TC_12_Create_Email.xlsx";
		
				
		//	public static String loginImage = "./TestData/loginPageScreenshot.jpg";
		
	// Driver locations
		public static String sIEDriverPath = "Browser_exe//IEDriverServer.exe";	
		public static String sChormeDriverPath = "Browser_exe//chromedriver.exe";
		
	// URLs
	//	public static String sUrl = "localhost:7070/kagami-studio/";
	    public static String sUrl = "http://35.162.58.24:7070/kagami-studio/#/";

	// Zip files    
		public String zipFileName = "./TestReport/TestReport_"+genericMethods.getcurrentDateAndTime()+".zip/";
		public static String htmlFileForEmail = "./test-output/custom-report.html";
		//public String date = genericMethods.getcurrentDateAndTime();
		public static int testCasesCount = 0;
		public static int testcasesFailCount = 0;
		public static int testcasesPassCount = 0;
		public static String Testcasename = null;
		public static String Functionality = null;
		
		//DB Details
		public static String gDBUserName = null;
		public static String gDBPassword = null;
		public static String gDBServer = null;
		public static String gDBName = null;
		public static String gDBDNSName = null;

		public static ProfilesIni allProfiles;
		public static FirefoxProfile myProfile; 
		
		public String date(){
		Date d = new Date();
	    String date = d.toString().replace(":", "_");
	    return date;
	}
}