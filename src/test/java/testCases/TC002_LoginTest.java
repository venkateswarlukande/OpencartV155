package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	

	@Test(groups = {"Sanity","Master"})
	public void verify_login() throws IOException {

		logger.info("**** Started TC002_LoginTest ****");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();

			MyAccountPage ma = new MyAccountPage(driver);

			Assert.assertTrue(ma.isMyAccountPageExist());
		} catch (Exception e) {
			Assert.fail();
		}
		
		logger.info("**** Finished TC002_LoginTest ****");
	}
}
