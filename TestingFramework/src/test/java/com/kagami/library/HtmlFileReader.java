package com.kagami.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

public class HtmlFileReader implements IReporter {
	
	public static void main(String[] args) {
		
	
	String fileContent = "";
    try {
        BufferedReader in = new BufferedReader(new FileReader("./AutomationReport/TestReport.html"));
        String str;
        int passCount = 0;
        while ((str = in.readLine()) != null) {
            //fileContent +=str;
        	if(str.contains("status pass"))
        	{
        		++passCount;
        		System.out.println(str);
        	}        	   
        	}
        System.out.println("Pass Count is = "+passCount);
        in.close();
    } catch (IOException e) {
    }
	
	}

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub
		
	}
		
}
