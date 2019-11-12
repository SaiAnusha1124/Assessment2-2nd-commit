package com.atmecs.heatclinic.testscripts;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.atmecs.actions.ClickOnElementAction;
import com.atmecs.actions.SendKeysAction;
import com.atmecs.constants.ConstantsFilePaths;
import com.atmecs.helpers.LocatorType;
import com.atmecs.testbase.TestBase;
import com.atmecs.utils.ReadExcelFile;
import com.atmecs.utils.ReadLocatorsFile;
import com.atmecs.validation.VerifyShirtItem;

public class SelectProduct extends TestBase {
	/*
	 * In this Class,Selecting one Product Validating on that product details
	 */
	Properties properties, properties1;
	ClickOnElementAction click = new ClickOnElementAction();
	SendKeysAction sendkeys = new SendKeysAction();
	ReadExcelFile readexcel = new ReadExcelFile();
	WebElement element;

	@DataProvider(parallel = true)
	public Object[][] inputValues() {
		/*
		 * Data provider is using to read the data from excel file
		 */
		Object data[][] = readexcel.readExcel("Sheet4", ConstantsFilePaths.TESTDATA_FILE1);
		return data;
	}

	@Test(dataProvider = "inputValues", priority = 1)
	public void testingHomePage(String name, String quantity) throws Exception {

		// locators are reading through LOCATOR_FILE
		properties = ReadLocatorsFile.loadProperty(ConstantsFilePaths.LOCATOR_FILE);
		// expected data are reading through TESTDATA_FILE
		properties1 = ReadLocatorsFile.loadProperty(ConstantsFilePaths.TESTDATA_FILE);
		VerifyShirtItem.testingHomePageTabs();

		element = driver.findElement(By.xpath(properties.getProperty("loc-click-mens")));
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-mens1"));
		log.info("Clicked Mens Products");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-shirt"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-redcolor"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-size"));
		log.info("Selected Shirt,Color and size");
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkey-name"), name);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-buynow"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-cart"));
		log.info("Product added into cart");
		VerifyShirtItem.validatingProduct();
		driver.findElement(By.xpath(properties.getProperty("loc-sendkey-quantity"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkey-quantity"), quantity);
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-update"));
		log.info("Successfully updated the quantity");
		VerifyShirtItem.validatingAfterUpdate();
	}
}
