package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven")
	public void verify_TC003_LoginDDT(String email, String pwd, String exp) throws InterruptedException {

		logger.info("***** Starting TC003_LoginDDT *****");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			MyAccountPage ma = new MyAccountPage(driver);
			boolean targetPage = ma.isMyAccountPageExist();

			/*
			 * Data is Valid - Login success - Test pass - Logout Login failed - Test fail
			 * 
			 * Data is Invalid - Login success - Test fail - Logout Login failed - Test pass
			 */

			if (exp.equalsIgnoreCase("Valid")) {

				if (targetPage == true) {
					ma.clickLogout();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}
			if (exp.equalsIgnoreCase("Invalid")) {

				if (targetPage == true) {
					ma.clickLogout();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}

		Thread.sleep(3000);

		logger.info("***** Finished TC003_LoginDDT *****");
	}
}
