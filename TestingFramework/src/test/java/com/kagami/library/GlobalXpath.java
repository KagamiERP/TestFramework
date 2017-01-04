package com.kagami.library;

import org.openqa.selenium.By;

public interface GlobalXpath {

	final By newProject = By.xpath("//button[@ng-click='add()']");
	final By newProjectText = By.xpath("//input[@id='new-project-text']");
	final By projectName = By.xpath("//input[@id='new-project-text']");
	final By createButton = By.xpath("//button[contains(text(),'Create')]");
	final By okButtonModule = By.xpath("//div[text()='Create Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	final By newModuleButton = By.xpath("//button[@ng-click='addModule()']");
	final By newModuleText = By.xpath("//input[@id='prompt-input-field']");
	final By newSubModuleButton = By.xpath("//button[@ng-click='addSubModule()']");
	final By newSubModuleText = By.xpath("//input[@placeholder = '(Eg: Leave Management System)']");
	final By newProcessButton = By.xpath("//button[@ng-click='addProcess()']");
	final By newProcessText = By.xpath("//input[@placeholder='(Eg: Leave Request Process)']");
	final By saveProcess = By.xpath("//button[@ng-click='saveProcess()']");
	final By ok = By.xpath("//div[text()='Save']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	final By project  = By.xpath("//h3[text()='test']");
	final By module = By.xpath("//div[@class='studio-card-inner']//p[(text()='Module')]");
	final By subModule = By.xpath("//p[(text()='Sub Module')]");
	final By newProcess = By.xpath("//button[@class='btn btn-primary process-add pull-right']");
	final By processName = By.xpath("//input[@class='ajs-input']");
	final By submitButtonModule = By.xpath("//div[@class='modal-footer']/button[text()='Submit']");
	final By okButtonSubModule = By.xpath("//div[text()='Create Sub Module']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	final By okButtonProcess = By.xpath("//div[text()='Create Process']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	final By existingProcess = By.xpath("//p[text()='Process']");
	final By existingProjectErrorMsg = By.xpath("//div[text()='*Should not create same Project Name']");
	final By kagamiLogo = By.xpath("//img[@src='assets/img/logo.png']");
	final By updateProjectButton = By.xpath("//div[@class='modal-footer']/button[contains(text(),'Update')]");
	final By deleteSubmodule = By.xpath("//span[@class='ng-scope studio-card-menu-item glyphicon glyphicon-trash']");
	final By deleteSubModuleOk = By.xpath("//div[text()='Delete Sub Module']/following::button[text()='OK']");
	final By deleteModuleOk = By.xpath("//div[text()='Delete Module']/following::button[text()='OK']");
	final By submitButtonSubModule = By.xpath("//label[text()='Submodule Name']/following::div[@class='modal-footer']//button[text()='Submit']");
	final By submitButtonProcess = By.xpath("//label[text()='Process Name']/following::button[text()='Submit']");
	final By addNewAttribute = By.xpath("//input[@value ='Add Attribute']");
	final By updateEntity = By.xpath("//button[contains(text(),'Update')]");
	final By minValue = By.xpath("//input[@id='attributeValidationtypemin']");
	final By maxValue = By.xpath("//input[@id='attributeValidationtypemax']");
	final By numberRangeCheckbox = By.xpath("//input[@id='attributeValidationtyperange']");


}
