package com.kagami.studio;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



class Temp
{
	

public static void main(String args[])
{

	
	System.setProperty("webdriver.chrome.driver","./Browser_exe/chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.get("https://www.gmail.com");
}
}