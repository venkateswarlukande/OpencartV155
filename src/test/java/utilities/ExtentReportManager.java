package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter; // UI of the Report
	public ExtentReports extent; // populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods

	String repName;
	
	public void onStart(ITestContext context) {
		
		/*
		SimpleDateFormat df = new SimpleDateFormat("yyy.MM.dd.HH.mm.ss");
		Date dt = new Date();
		String repName = df.format(dt);
		*/
		
		String timeStamp = new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date());
		repName ="Test-Report-"+timeStamp+".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);

		
		sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
		sparkReporter.config().setReportName("OpenCart Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os =context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser =context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Broser", browser);
		
		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());  //create new entry in the report
		test.assignCategory(result.getMethod().getGroups());        //to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed"); //update status p/f/s
	}
	
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try {
			String imgPath = new BaseClass().captureScreen(result.getTestClass().getName())	;
			test.addScreenCaptureFromPath(imgPath);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void onTestSkipped(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP,result.getName()+" got skipped");
		test.log(Status.INFO,result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) {

		
		extent.flush();
		
	
		//open report automatically after test execution finished
		
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		try {
		URL url = new URL("file://"+System.getProperty("user.dir")+"\\reports"+repName);
		
		//create email message
		
		ImageHtmlEmail email = new ImageHtmlEmail();
		email.setDataSourceResolver(new DataSourceUrlResolver(url));
		email.setHostName("smtp.googleemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("venkateswarlukande0995@gmail.com", "Venk155@"));
		email.setSSLOnConnect(true);
		email.setFrom("venkateswarlukande0995@gmail.com"); //sender
		email.setSubject("Test Result");
		email.setMsg("Please find Attached Report.....");
		email.addTo("173r1a0155venkateswarlu@gmail.com");
		email.attach(url, "extent report", "please check report.....");
		email.send();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		*/
		
	}
	
	
	
}
