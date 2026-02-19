package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;






import test.TestBase;

public class TestUtil extends TestBase{
	
	public static void login(String username,String password) throws InterruptedException{
		Apps_logs.debug("checking the application"+
	            "weather it is logged in or not");
		if(loggedin==true)
			return;
		
		getObject("login_UserName").clear();
		getObject("login_Password").clear();
		getObject("login_UserName").sendKeys(username);
		getObject("login_Password").sendKeys(password);
		getObject("login_LoginButton").click();
		Thread.sleep(5000L);
		try{
			String loginAccount = driver.findElement(By.xpath(OR.getProperty("login_successful_account_Link"))).getText();
			if(loginAccount.equals("My Accounts")){
				loggedin=true;
			}else{
				loggedin=false;
			}
		}catch(Throwable t){
			loggedin=false;
		}	
	}
	
	public static void logout(){
		if(loggedin==false){
			return;
		}
		getObject("login_Logout_Link").click();
		loggedin=false;	
	}
	
	
	public static boolean isSkip(String testCase){
		for(int i=2; i<=excel.getRowCount("Test Cases");i++ ){
	    	  if(excel.getCellData("Test Cases", "TestCase ID", i).equals(testCase)){
	    		  if(excel.getCellData("Test Cases", "Runmode", i).equals("Y"))
	    			  return false;
	    		  else
	    			  return true;
	    	  }
	    	  
	      }
		
		return false;
	}
		
	
	public static Object[][] getDataFromExcel(String sheetName){
		// return test data;
		// read test data from xls
		
		int rows=excel.getRowCount(sheetName);
		if(rows <=0){
			Object[][] testData =new Object[1][0];
			return testData;
			
		}
	    rows = excel.getRowCount(sheetName);  
		int cols = excel.getColumnCount(sheetName);
		System.out.println("total rows -- "+ rows);
		System.out.println("total cols -- "+cols);
		Object data[][] = new Object[rows-1][cols];
		
		for( int rowNum = 2 ; rowNum <= rows ; rowNum++){
			
			for(int colNum=0 ; colNum< cols; colNum++){
				data[rowNum-2][colNum]=excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		
		return data;	
	}
	
	// screenshots
	public static void takeScreenShot(String fileName) throws IOException{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(scrFile, new File(config.getProperty("screenShotsPath")+"\\"+fileName+".jpg"));	   
	    
	}
}
		
	
