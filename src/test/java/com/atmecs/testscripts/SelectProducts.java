package com.atmecs.testscripts;

import java.util.Properties;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.atmecs.actions.ClickOnElementAction;
import com.atmecs.actions.SendKeysAction;
import com.atmecs.constants.ConstantsFilePaths;
import com.atmecs.extentreports.ExtentReport;
import com.atmecs.helpers.LocatorType;
import com.atmecs.testbase.TestBase;
import com.atmecs.utils.ReadExcelFile;
import com.atmecs.utils.ReadLocatorsFile;
import com.atmecs.validation.VerifyProducts;

public class SelectProducts extends TestBase {
	/*
	 * In this class,validating home page,selecting one product in products list
	 */
	Properties properties, properties1;
	ClickOnElementAction click = new ClickOnElementAction();
	SendKeysAction sendkeys = new SendKeysAction();
	ReadExcelFile readexcel = new ReadExcelFile();

	@DataProvider(parallel = true)
	public Object[][] inputValues() {
		Object data[][] = readexcel.readExcel("Sheet1", ConstantsFilePaths.TESTDATA_FILE1);
		return data;
	}

	@Test(dataProvider = "inputValues", priority = 1)
	/*
	 * Data provider is using to read the data from excel file
	 */
	public void selectingFirstProduct(String firstproduct, String quantity) throws Exception {
		// locators are reading through LOCATOR_FILE
		properties = ReadLocatorsFile.loadProperty(ConstantsFilePaths.LOCATOR_FILE);
		// expected data are reading through TESTDATA_FILE
		properties1 = ReadLocatorsFile.loadProperty(ConstantsFilePaths.TESTDATA_FILE);

		VerifyProducts.verifyingHomePage();
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"), firstproduct);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected Iphone Product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-iphone"));
		VerifyProducts.verifyingFirstProduct();
		driver.findElement(By.xpath(properties.getProperty("loc-sendkeys-quantity1"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkeys-quantity1"), quantity);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-addcart1"));
		log.info("Added product to cart");
		log.info("Successfully selected and validate for first product");
	}

	@DataProvider(parallel = true)
	public Object[][] inputValues1() {
		/*
		 * Data provider is using to read the data from excel file
		 */
		Object data[][] = readexcel.readExcel("Sheet2", ConstantsFilePaths.TESTDATA_FILE1);
		return data;
	}

	@Test(dataProvider = "inputValues1", priority = 2)
	public void selectingSecondProduct(String secondproduct, String quantity) throws Exception {
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		driver.findElement(By.xpath(properties.getProperty("loc-click-bttn-search"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"), secondproduct);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected MacBook Air Product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-macbookair"));
		VerifyProducts.verifyingSecondProduct();
		driver.findElement(By.xpath(properties.getProperty("loc-sendkeys-quantity2"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkeys-quantity2"), quantity);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-addcart2"));
		log.info("Added product to cart");
		log.info("Successfully selected and validate for second product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-gotocart2"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-viewcart"));
		VerifyProducts.verifyingCartList();
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-removeproduct"));
		VerifyProducts.afterUpdateGrandTotal();
		log.info("Sucessfulyy selected and validated both iphone and macbook air products");
		ExtentReport.reportLog("TestSecondProduct", "failed");
	}
	@DataProvider(parallel = true)
	public Object[][] inputValues2() {
		/*
		 * In this class,validating negative cases product in products list
		 */
		Object data[][] = readexcel.readExcel("Sheet3", ConstantsFilePaths.TESTDATA_FILE1);
		return data;
	}

	@Test(dataProvider = "inputValues2", priority = 3)
	public void testingNegativeCase(String NegativeProduct) throws Exception {
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"), NegativeProduct);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected Chairs as Negative Product");
		VerifyProducts.verifyingNegativeCase();
	}
}