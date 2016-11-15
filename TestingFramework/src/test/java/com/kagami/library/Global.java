package com.kagami.library;

import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.WebDriverWait;

import TestingFrameWork.AutomationTestFramework.GenericMethods;

	public class Global extends CustomizedReport{
		
	
		//obj.postsuite(browserType, browserVersion, os, url);*/
	
		
		GenericMethods genericMethods = new GenericMethods();
		public static final int iMaxRetryCount = 1;

		public static WebDriver wDriver;
		public static WebDriverWait wait;
		public static JavascriptExecutor js;
		
		// Selects the browser to execute
	//	public static String sBrowserType = "firefox";
		//public static String sBrowserType = "IE";
		public static String sBrowserType = "Chrome";
		public static String sTestData="TestData//testdata demoaut.xlsx";
		public static String BuildInfo ="samplebuild";
		
		// Specifies the path of the Drivers
		public static String sIEDriverPath = "Browser_exe//IEDriverServer.exe";	
		public static String sChormeDriverPath = "Browser_exe//chromedriver.exe";
		
		// Specifies the URL to be entered
	//	public static String sUrl = "http://viralpatel.net/blogs/multiple-checkbox-select-deselect-jquery-tutorial-example/";
    //	public static String sUrl = "http://newtours.demoaut.com/";
	//	public static String sUrl = "https://www.makemytrip.com/flights";
	//	public static String sUrl = "http://spreadsheetpage.com/index.php/file/C35/P10//";
	//	public static String sUrl = "http://www.tinyupload.com/";
	//	public static String sUrl = "http://demo.guru99.com/V4/";
	//	public static String sUrl = "http://54.69.203.11:7070/kagami-studio/auth/signin";
		public static String sUrl = "http://localhost:7070/kagami-studio/auth/signin/#";
		
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