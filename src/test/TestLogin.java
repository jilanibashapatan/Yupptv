package test;

import java.io.IOException;

import javax.servlet.jsp.SkipPageException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import util.TestUtil;

public class TestLogin extends TestBase{
	
	@BeforeTest
	public void isSkipped() throws SkipPageException {
		String testCase="TestLogin";
		if(TestUtil.isSkip(testCase)){
			throw new SkipException("RunMode is set to No");
		}
		
	}
	
	@Test(dataProvider="getData")
	public void testLogin(String userName,String passWord,String testType) throws InterruptedException, IOException{
		Apps_logs.debug("UserName:"+userName+" " +"password:"+passWord);
		boolean value = Boolean.valueOf(testType);
		
		driver.get(config.getProperty("testSite"));
		TestUtil.takeScreenShot("LoginTest_positive");
		TestUtil.login(userName, passWord);
		//checking the application with correct credentials
		if((!loggedin) & (value)){
			//report error
			Apps_logs.debug("Not able to login with correct credentials");
			return;
		}else if((loggedin) & (!value)){
			//report error
			Apps_logs.debug("Not able to login with correct credentials");
			return;
		}
		
		
		if(value){
			String successfulMessage = getObject("login_successful_message").getText();
			try{
				TestUtil.takeScreenShot("LoginTest_positive");
				Assert.assertEquals("Geico",successfulMessage);
			}catch(Throwable t){
				Apps_logs.debug("Error-->"+t.getMessage());
			}
		}else{
			String invalidEmailId = getObject("login_invalid_emailId").getText();
			try{
				TestUtil.takeScreenShot("LoginTest_negitive");
				Assert.assertEquals("Invalid UserName and Password.",invalidEmailId);
				Apps_logs.debug("Invalid Email Address");
			}catch(Throwable t){
				Apps_logs.debug("Error-->"+t.getMessage());
			}	
		}
		
		TestUtil.logout();
	}
	
	@DataProvider
	public static Object[][] getData()
	{
		return TestUtil.getDataFromExcel("TestLogin");	
	}

}

//return TestUtil.getDataFromExcel("TestLogin");	
