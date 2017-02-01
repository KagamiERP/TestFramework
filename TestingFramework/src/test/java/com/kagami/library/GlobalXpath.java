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
	final By submitSubModButton = By.xpath("//div[@class='modal-footer']/button[contains(text(),'Submit')]");
	final By submitProjectButton = By.xpath("//div[@class='modal-footer']/button[contains(text(),'Submit')]");
	final By organization = By.linkText("Organization");
	final By roles = By.linkText("Roles");
	final By saveButton = By.xpath("//button/img[@src='assets/img/save.png']");
	final By organizationsCli = By.xpath("//div/div/ul/li/a[contains(text(),'Organizations')]");
	final By deleteMsg=By.xpath("//*[contains(text(),'Do you want to delete the Organization?')]");
	final By deleteOk=By.xpath("//*[contains(text(),'OK')]");
	final By deleteCancel=By.className("//*[contains(text(),'Cancel')]");
	
	final By dashboardButton = By.xpath("//div/a/following-sibling::a[contains(text(),'Customize Dashboard')]");
	final By leftPaneloButton = By.xpath("//ul/li/span/following-sibling::button[@ng-click='add(menu)']");
	final By name=By.xpath("//div/label/following-sibling::input[@class='form-control ng-pristine ng-untouched ng-valid ng-empty']");
	final By saveLeftPanel = By.xpath("//div/div/button/following-sibling::button[contains(text(),'Save')]");
	final By createLeftPanel = By.xpath("//div/div/button/following-sibling::button[contains(text(),'Create')]");

	final By saveDashBoard=By.xpath("//div/div/button/img[@src='assets/img/save.png']");
	final By createDashBoard = By.xpath("//div[@class='dashboard-modal ng-scope']//button[contains(text(),'Create')]");
	
	final By createChild = By.xpath("//div[@class='modal-footer']//button[contains(text(),'Create')]");
	final By okSave=By.xpath("//div[text()='Save']/following::button[text()='OK']");
	final By groupRadioButton=By.xpath("//div/label/following-sibling::label/input[@name='menu_type']");
	final By groupNameClick=By.xpath("//div/label/following-sibling::input[@class='form-control ng-pristine ng-untouched ng-valid ng-empty']");
	final By settingsButton=By.xpath("//div/button[contains(text(),'Settings')]");
	final By userDataSettingsButton=By.xpath("//a/h3[@class='panel-title bordertopblue']/i[@class='fa fa-user-secret']");
	final By userDataCheckBox=By.xpath("//th/input[@class='ng-pristine ng-untouched ng-valid ng-empty']");
	final By appDataSettingsButton=By.xpath("//a/h3[@class='panel-title bordertoppurple']/i[@class='fa fa-cogs']");
	final By appDataCheckBox=By.xpath("//th/input[@class='ng-pristine ng-untouched ng-valid ng-empty']");
	final By themeSettingsClick=By.xpath("//a/h3/span[@class='glyphicon glyphicon-picture']");
	final By themeSelect=By.xpath("//div/div/span/input[@class='defaulttheme Almond ng-not-empty']");
	final By saveAllButton=By.xpath("//div/button/following-sibling::button[contains(text(),'Save All')]");
	final By subMenu = By.xpath("//button[contains(text(),'Menu')]");
	final By customizedDashboardButton = By.linkText("Customize Dashboard");
	final By closeButtonDeployment = By.xpath("//button[text()='Close']");
	
    By menuButton = By.xpath("//button[contains(text(),'Menu')]");
	By generateButton=By.xpath("//ul/li/a[contains(text(),'Generate')]");
	By contractSuccessMsg=By.xpath("//div/div[contains(text(),'Contract Generated Successfully')]");
	By buildSelect=By.xpath("//div/select/option[contains(text(),'localhost:8080')]");
	By buildButton=By.xpath("//div/button[contains(text(),'Build')]");
	By buildSuccessMsg=By.xpath("//div/div[contains(text(),'Build Completed')]");
	By deploySelect=By.xpath("//div/select/option[contains(text(),'localhost:8080')]");
	By deployButton=By.xpath("//div/button[contains(text(),'Deploy')]");
	By deploySuccessMsg=By.xpath("//div/div[contains(text(),'Deployment Completed and App')]");
	By testButton=By.xpath("//button[contains(text(),'Test')]");
	By projectLink = By.xpath("//span[@class='ng-binding']");
	By createVerb = By.xpath("//p[contains(text(),'CREATE')]");
	By editVerb =  By.xpath("//p[contains(text(),'EDIT')]");
	By deleteVerb = By.xpath("//p[contains(text(),'DELETE')]");
	By updateVerb = By.xpath("//p[contains(text(),'UPDATE')]");
	By selectEntityButton = By.xpath("//span[@aria-label='Select box activate']");
	By selectEntityAttribute = By.xpath("//div[@placeholder='Select a entity attributes in the list...']//span[@aria-label='Select box activate']");
	By selectEntityTextArea = By.xpath("//input[contains(@placeholder,'Select a entity in the list')]");
	By selectEntityAttributeTextArea = By.xpath("//input[contains(@placeholder,'Select a attribute in the list...')]");
	By selectTransformEntity = By.xpath("//div[@placeholder='Select a attribute in the list...']//span[@aria-label='Select box activate']");
	By selectTransformAttribute = By.xpath("//div[@ng-model='vm.destinationEntity']//input[@placeholder='Select a attribute in the list...']");
	By nextButton = By.xpath("//button[text()='Next']");
	
	

	//*** Xpath's for Studio LogIn ***//
	By uname = By.id("inputUsername");
	By password = By.id("inputPassword");
	By loginButton = By.xpath("//button[contains(text(),'SIGN IN')]");

	By addNewEntityButton = By.xpath("//a[@ng-click='createEntity()']");
	By deleteEntityPopupHeader = By.xpath("//div[@class='ajs-header']");
	By okButton = By.xpath("//button[text()='OK']");
	By deleteProjectOkButton = By.xpath("//div[text()='Delete']/following::button[text()='OK']");
	By deleteEntityIcon = By.xpath("//a[@ng-click='deleteentity(entity)']/span[@class='glyphicon glyphicon-remove redtext']");
	By signOut = By.xpath("//a[text()='Sign Out']");
	By signOutAlert = By.xpath("//div[@class='alert alert-success alert-signout']");
	By entityName = By.id("entityName");
	By saveOkButton = By.xpath("//div[text()='Save']/following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By deleteHeader = By.xpath("//div[text()='Delete']");
	By saveProcessButton = By.xpath("//button[@ng-click='saveProcess()']");
	By saveButtonHeader = By.xpath("//div[text()='Save']");
	By homeTab = By.xpath("//a[text()='Home']");
	By noButton = By.xpath("//button[text()='NO']");
	By mapOrgButton = By.xpath("//a[contains(text(),'Map Org')]");
	By mapOrgHeader = By.xpath("//div[contains(text(),'Map Organization')]");
	By searchBoxOrg = By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']");
	By inputSearchBoxOrg = By.xpath("//input[@placeholder='Select or search a Organization in the list...']");
	By saveOrg = By.xpath("//button[@ng-click='saveOrg()' and text()='save']");
	By mapRolesButton = By.xpath("//button[@class='btn btn-default' and @ng-click='associateRoles()']");
	By mapRolesHeader = By.xpath("//h4[text()='Map Roles']");
	By searchBoxRoles = By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']");
	By inputSearchBoxRoles = By.xpath("//input[@placeholder='Select or search a role in the list...']");
	By addRolesButton = By.xpath("//div[@class='input-group-btn']/button[@class='btn btn-default']");
	By saveRolesButton = By.xpath("//button[@ng-click='saveRoles()' and contains(text(),'Save')]");
	By editTabInView = By.xpath("//a[text()='Edit']");
	By inputTriggerName = By.id("trigger-text");
	By addTriggerButton = By.xpath("//button[@class='btn btn-success btn-sm' and @ng-click='addTrigger(vm.trigger)']");
	By closeButton = By.xpath("//button[text()='Close']");
	By deleteOrgOkButton = By.xpath("//div[text()='Delete Organization']//following::div[@class='ajs-primary ajs-buttons']/button[text()='OK']");
	By loginErrorMsg = By.xpath("//div[@ng-show='error']/span[@class='sr-only']");
	By invalidUserName = By.xpath("//input[@id='inputUsername' and @class='form-control ng-touched ng-dirty ng-valid-parse ng-empty ng-invalid ng-invalid-required']");

	By policiesButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/policies.png']");
	By businessValidation = By.xpath("//a[text()='Business Validation']");
	By restrictionsButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/restrictions.png']");
	By pathButton = By.xpath("//span[@class='list-group-item text-center ng-binding ng-scope']/img[@ng-src='assets/img/stepmodal/paths.png']");
	By createPath = By.xpath("//button[@ng-click='createPath()']");
	
	
	By contractSucessMsg=By.xpath("//div/div[@class='ng-binding' and contains(text(),'Contract Generated Successfully')]");
	By buildSucess=By.xpath("//div/div[contains(text(),'Build Completed ')]");
	By deploySucess=By.xpath("//div/div[contains(text(),'Deployment Completed and App is available at')]");
	By userIdTarget=By.xpath("//div/label/following-sibling::input[@id='username']");
	By pswdTarget=By.xpath("//div/label/following-sibling::input[@id='password']");
	
	//By policyLHS = By.xpath("//div[@class='form-group lhs']//div[@class='ng-isolate-scope']//span[@ng-transclude='']/input[@ng-model='subCondition.lhs.value']");
	//By policyCondition = By.xpath("//div[@class='form-group']/select[@ng-model='subCondition.operator']");
	//By policyRHS = By.xpath("//span[@ng-transclude='']/input[@ng-model='subCondition.rhs.value']");


	
}
