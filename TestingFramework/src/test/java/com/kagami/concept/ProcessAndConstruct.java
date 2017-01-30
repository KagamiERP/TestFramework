package com.kagami.concept;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

public class ProcessAndConstruct {

	public WebDriver driver;
	public ExtentTest test;
	public Sheet sheet;
	public Workbook wb;

	public ProcessAndConstruct(WebDriver driver) {
		this.driver = driver;
	}

	public boolean createProcess(WebDriver driver, ExtentTest test, String workbook) throws InvalidFormatException, IOException{

		String workBookTestData = workbook;
		File f = new File(workBookTestData);
		FileInputStream fis = new FileInputStream(f);
		wb = WorkbookFactory.create(fis);
		sheet = wb.getSheet("Process");
		String processNameKey = null;
		int processCellNum = 0;
		Map<String,List<String>> processNameMap = new HashMap<String,List<String>>();
		for(int processNameCell = 0; processNameCell <= sheet.getRow(0).getLastCellNum(); processNameCell++){
			if(sheet.getRow(0).getCell(processNameCell).toString().equals("ProcessName")){
				processNameKey = sheet.getRow(0).getCell(processNameCell).toString();
				processCellNum = processNameCell;
				break;
			}
		}

		List<String> allProcess = new ArrayList<String>();
		for(int row = 1; row <= sheet.getLastRowNum(); row++){
			allProcess.add(sheet.getRow(row).getCell(processCellNum).toString());
			processNameMap.put(processNameKey, allProcess);
		}

		List<String> allProcessName = null;
		Set<String> keyset = processNameMap.keySet();
		Iterator<String> processNameiterator = keyset.iterator();
		while(processNameiterator.hasNext()){
			String pNameKey = processNameiterator.next();
			allProcessName = processNameMap.get(pNameKey);
			System.out.println("Key1 is: "+pNameKey+ " and Value is "+allProcessName);
		}
		return true;
	}


	public boolean selectConstruct(ExtentTest test, String workbook) throws InvalidFormatException, IOException{

		String workBookTestData = workbook;
		File f = new File(workBookTestData);
		FileInputStream fis = new FileInputStream(f);
		wb = WorkbookFactory.create(fis);
		sheet = wb.getSheet("Process");
		String verbNameKey = null;
		int verbCellNum = 0;
		Map<String,List<String>> verbNameMap = new HashMap<String,List<String>>();
		for(int verbNameCell = 0; verbNameCell <= sheet.getRow(0).getLastCellNum(); verbNameCell++){
			if(sheet.getRow(0).getCell(verbNameCell).toString().equals("Verb")){
				verbNameKey = sheet.getRow(0).getCell(verbNameCell).toString();
				verbCellNum = verbNameCell;
				break;
			}
		}

		List<String> allProcess = new ArrayList<String>();
		for(int row = 1; row <= sheet.getLastRowNum(); row++){
			allProcess.add(sheet.getRow(row).getCell(verbCellNum).toString());
			verbNameMap.put(verbNameKey, allProcess);
		}

		List<String> allVerbName = null;
		Set<String> keyset = verbNameMap.keySet();
		Iterator<String> verbNameiterator = keyset.iterator();
		while(verbNameiterator.hasNext()){
			String vNameKey = verbNameiterator.next();
			allVerbName = verbNameMap.get(vNameKey);
			String splitVerb[] = allVerbName.toString().split(",");
			for(int totalVerbCount = 0; totalVerbCount < splitVerb.length; totalVerbCount++){
				System.out.println("Key1 is: "+vNameKey+ " and Value is "+allVerbName);
				
				if(splitVerb[totalVerbCount].equalsIgnoreCase("Create")){
					System.out.println("Under Create");

				}

				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Delete")){

					System.out.println("Under Delete");
				}

				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Update")){

				}


				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Edit")){

				}

				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Compute")){

				}

				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Link")){

				}

				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Transform")){

				}
				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Switch")){

				}
				else if(splitVerb[totalVerbCount].equalsIgnoreCase("View")){

				}
				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Docman")){

				}
				else if(splitVerb[totalVerbCount].equalsIgnoreCase("Cal")){

				}
			}
		}

		return true;
	}

}
