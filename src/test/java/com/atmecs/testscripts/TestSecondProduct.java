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
import com.atmecs.validation.ValidationResult;

public class TestSecondProduct extends TestBase {
	/*
	 *In this class,selecting second product in products list,increasing quantity 
	 *validating data after increasing quantity
	 */
	Properties properties, properties1;
	static String actualhomepage2, actualstock2, productprice2, actualextax2, actualdescription2, actualcartproduct1,
			actualcartproduct2, actualgrandtotal, actualgrandtotal1, actualgrandtotal2;
	ClickOnElementAction click = new ClickOnElementAction();
	SendKeysAction sendkeys = new SendKeysAction();
	ReadExcelFile readexcel = new ReadExcelFile();

	@DataProvider(parallel = true)
	public Object[][] inputValues() {
		/*
		 * Data provider is using to read the data from excel file
		 */
		Object data[][] = readexcel.readExcel("Sheet2", ConstantsFilePaths.TESTDATA_FILE1);
		return data;
	}

	@Test(dataProvider = "inputValues", priority = 1)
	public void testingHomePage(String secondproduct, String quantity) throws Exception {

		// locators are reading through LOCATOR_FILE
		properties = ReadLocatorsFile.loadProperty(ConstantsFilePaths.LOCATOR_FILE);
		// expected data are reading through TESTDATA_FILE
		properties1 = ReadLocatorsFile.loadProperty(ConstantsFilePaths.TESTDATA_FILE);

	
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"), secondproduct);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected MacBook Air Product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-macbookair"));

		actualstock2 = driver.findElement(By.xpath((properties.getProperty("loc-verify-availability2")))).getText();
		ValidationResult.validateData(actualstock2, properties1.getProperty("expectedstock2"),"Verifying availability stock");

		productprice2 = driver.findElement(By.xpath((properties.getProperty("loc-verify-productprice2")))).getText();
		ValidationResult.validateData(productprice2, properties1.getProperty("expectedprice2"),"Verifying product price");

		actualextax2 = driver.findElement(By.xpath((properties.getProperty("loc-verify-extax2")))).getText();
		ValidationResult.validateData(actualextax2, properties1.getProperty("expectedextax2"), "Verifying ex tax");

		actualdescription2 = driver.findElement(By.xpath((properties.getProperty("loc-verify-description2")))).getText();
		ValidationResult.validateData(actualdescription2, properties1.getProperty("expecteddescription2"),"Verifying product description");

		driver.findElement(By.xpath(properties.getProperty("loc-sendkeys-quantity2"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkeys-quantity2"), quantity);

		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-addcart2"));
		log.info("Added product to cart");
		log.info("Successfully selected and validate for second product");

		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-gotocart2"));

		actualcartproduct1 = driver.findElement(By.xpath((properties.getProperty("loc-verify-cartproduct1")))).getText();
		ValidationResult.validateData(actualcartproduct1, properties1.getProperty("expectedcartproduct1"),"Verifying cart product 1");

		actualcartproduct2 = driver.findElement(By.xpath((properties.getProperty("loc-verify-cartproduct2")))).getText();
		ValidationResult.validateData(actualcartproduct2, properties1.getProperty("expectedcartproduct2"),"Verifying cart product 2");

		actualgrandtotal1 = driver.findElement(By.xpath((properties.getProperty("loc-verify-grandtotal")))).getText();
		ValidationResult.validateData(actualgrandtotal1, properties1.getProperty("expectedgrandtotal"),"Verifying cart grand total before removing product");

		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-viewcart"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-removeproduct"));

		actualgrandtotal2 = driver.findElement(By.xpath((properties.getProperty("loc-verify-afterremovegrandtotal")))).getText();
		ValidationResult.validateData(actualgrandtotal2, properties1.getProperty("expectedafterremovegrandtotal"),"Verifying after removing grand total");
		ExtentReport.reportLog("TestSecondProduct", "failed");

	}
}
