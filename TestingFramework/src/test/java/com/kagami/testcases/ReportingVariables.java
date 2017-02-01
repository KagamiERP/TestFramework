package com.kagami.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.kagami.library.GenericMethods;
import com.relevantcodes.extentreports.ExtentTest;

public class ReportingVariables{

	GenericMethods genericMethods = new GenericMethods();
	ExtentTest test;
	public static String workbookTestData = "./TestData/TC_01_Create_View.xlsx";
	
	public static String getProjectName() throws InvalidFormatException, IOException{

		
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
	//	Map<String, String> projectMap = new HashMap<String,String>();
		String projectKey = null;
		int cellNum = 0;

		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(0).getCell(cell).toString().trim().equals("Project")){
				projectKey =  sheet.getRow(0).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		String projectVal = sheet.getRow(1).getCell(cellNum).toString();
		return projectVal;
	}



	public static String getModule() throws InvalidFormatException, IOException{

	
		File f = new File(workbookTestData);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet =  wb.getSheet("ProcessAndPolicies");
	//	Map<String, String> projectMap = new HashMap<String,String>();
		String moduleKey = null;
		int cellNum = 0;

		for(int cell =0 ; cell<=sheet.getRow(2).getLastCellNum(); cell++){
			if(sheet.getRow(0).getCell(cell).toString().trim().equals("Modules")){
				moduleKey =  sheet.getRow(0).getCell(cell).toString();
				cellNum = cell;
				break;
			}
		}
		String moduleVal = sheet.getRow(1).getCell(cellNum).toString();
		return moduleVal;
	}




	public static HashMap<String, String> getSubModule(){

		HashMap<String, String> subModuleName = new HashMap<String, String>();
		subModuleName.put("1", "AutomationTestProject");
		subModuleName.put("2", "Project");
		return subModuleName;
	}

}



/*	List<String> conditionCellValue = null;
Set<String> keySet = conditionMap.keySet();
Iterator<String> iterator = keySet.iterator();
while(iterator.hasNext()){
	String key1 = (String) iterator.next();
	conditionCellValue = conditionMap.get(key1);
	System.out.println("Key1 is: "+key1+ "Value is "+conditionCellValue);
}

int policyRowInc = 1;
String[] conditionSeparatedByAndValue = conditionCellValue.get(projectCount++).split("and");
for(String conditionSeparatedBySingleCondition : conditionSeparatedByAndValue){
	String[] conditionSeparatedByComma = conditionSeparatedBySingleCondition.split(",");

			policyRowInc++;

}	*/

/*	projectName.put("1", "AutomationTestProject");
projectName.put("2", "Project");*/
