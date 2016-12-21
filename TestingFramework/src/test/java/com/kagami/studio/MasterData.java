package com.kagami.studio;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.kagami.library.ExtentManager;
import com.kagami.library.GenericMethods;
import com.kagami.library.Global;
import com.kagami.library.StudioCommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class MasterData {
	
	ExtentReports extent;
	ExtentTest test;
	public  WebDriver driver;
	public Logger log = Logger.getLogger("MasterData.class");
	GenericMethods genericMethods = new GenericMethods();
	
	StudioCommonMethods studioCommonMethods = new StudioCommonMethods(driver);
	Global global = new Global();

	
	public MasterData(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void masterDataCreation(WebDriver driver, ExtentTest test){
		
		try{
			
			String pathOfFile = Global.testSheet; 
			File f = new File(pathOfFile);
			FileInputStream fis = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet =  wb.getSheet("MasterData");
			
			Map<String, List<String>> addMasterData = new LinkedHashMap<String, List<String>>();
			List<String> addMasterDataList = null;
			
			genericMethods.clickElement(driver, By.xpath("//button[contains(text(),'Master Data')]"), test);
			genericMethods.waitForElementVisibility(driver, By.xpath("//span[text()='Master Data']"), 30);
			
			
			int rowCnt = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int colCnt = row.getLastCellNum();
			
			for(int cell = 0; cell < colCnt ;cell++){
				String masterKeyValue = sheet.getRow(0).getCell(cell).toString();
				addMasterDataList = new ArrayList<String>();	
				for(int irow = 1; irow <= rowCnt; irow++){
					String entityValues = sheet.getRow(irow).getCell(cell).toString();
					addMasterDataList.add(entityValues);
					//System.out.println(addEntityValues);
				}
				addMasterData.put(masterKeyValue, addMasterDataList);
				System.out.println(addMasterData);
			}
			
			int masterDataList = addMasterDataList.size();
			List<String> keyList = new ArrayList<String>();
			
			for(int keyCell = 0; keyCell < colCnt; keyCell++){
				String keyValue = sheet.getRow(0).getCell(keyCell).toString();
				keyList.add(keyValue);
			}
			
			List<String> state = addMasterData.get("State");
			List<String> capital = addMasterData.get("Capital");
			
			int recordCnt = 0;
			for(int value=0; value < masterDataList; value++){
				//Thread.sleep(1000);
				genericMethods.waitForElementVisibility(driver, By.xpath("//div[contains(text(),'Records')]"), 30);
				genericMethods.clickElementByJsExecutor(driver, By.xpath("//a[@ng-click='createNewRecord()']"), test);
				
				genericMethods.clickElementByJsExecutor(driver, By.xpath("//tr["+(++recordCnt)+"][@class='ng-scope']//button[contains(text(),'Edit')]"), test);
				genericMethods.waitForElementVisibility(driver, By.xpath("//h4[contains(text(),State)]"), 30);
				//Thread.sleep(1000);
				genericMethods.enterText(driver, By.xpath("//label[text()='"+keyList.get(0).toString()+"']/following-sibling::div/input[@type='text']"), state.get(value), test);
				genericMethods.enterText(driver, By.xpath("//label[text()='"+keyList.get(1).toString()+"']/following-sibling::div/input[@type='text']"), capital.get(value), test);
				genericMethods.clickElementByJsExecutor(driver, By.xpath("//button[text()='Update']"), test);
			}
			driver.navigate().back();
	}
		catch(Exception e)
		{
			e.printStackTrace();;
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
}
	
	public void deleteData(ExtentTest test){
		try{
			genericMethods.clickElement(driver, By.xpath("//h3[text()='TotalOverdose']"), test);
			genericMethods.clickElement(driver, By.xpath("//a[contains(text(),'Entity Management')]"), test);
			//genericMethods.clickElement(driver, By.xpath("//a[@ng-click='addEntity()']"), test);
			genericMethods.clickElement(driver, By.xpath("//span[@title='State']"), test);
			genericMethods.clickElement(driver, By.xpath("//button[contains(text(),'Master Data')]"), test);
			genericMethods.waitForElementVisibility(driver, By.xpath("//span[text()='Master Data']"), 30);
			int totalData = genericMethods.getXpathSize(driver, By.xpath("//tr//button[@ng-click='removeRecord(record.key)']"), test);
			for(int i=1; i<=totalData; i++){
				Thread.sleep(100);
				genericMethods.clickElementByJsExecutor(driver, By.xpath("//tr//button[@ng-click='removeRecord(record.key)']"), test);
				i--;
				totalData--;
			}
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();;
			test.log(LogStatus.INFO, test.addScreenCapture(ExtentManager.CaptureScreen(driver)));
			test.log(LogStatus.FAIL, ExceptionUtils.getStackTrace(e));
		}
	}
}
