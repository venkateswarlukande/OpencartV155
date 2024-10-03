package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegisterPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = {"Regression","Master"})
	public void verify_account_registration() {

		logger.info("***** Starting TC001_AccountRegistrationTest *****");

		try {
			HomePage homePage = new HomePage(driver);
			homePage.clickMyAccount();
			logger.info("Clicked on MyAccount Link");

			homePage.clickRegister();
			logger.info("Clicked on Register Link");

			AccountRegisterPage registerPage = new AccountRegisterPage(driver);

			logger.info("Providing Customer Details");
			registerPage.setFirstName(randomeString().toUpperCase());
			registerPage.setLastName(randomeString().toUpperCase());
			registerPage.setEmail(randomeString() + "@gmail.com");
			registerPage.setTelephone(randomeNumeric());

			String pwd = randomeAlphaNumeric();

			registerPage.setPassword(pwd);
			registerPage.setConfirmPwd(pwd);

			registerPage.setPrivacyPolicy();
			registerPage.clickContinue();

			logger.info("Validating expected message");
			if(registerPage.getConfirmationMsg().equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}else {
				logger.error("Test Failed ");
				logger.debug("Debug Logs");
				Assert.assertTrue(false);
			}
			
//			Assert.assertEquals(registerPage.getConfirmationMsg(), "Your Account Has Been Created!");

		} catch (Exception e) {
			Assert.fail();
		}
		
		logger.info("***** Finished TC001_AccountRegistrationTest *****");

	}

}
