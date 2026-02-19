package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeSuite;

import dataTable.Xls_Reader;

public class TestBase {
	
	public static Properties config=null;
	public static Properties OR=null;
	public static WebDriver wdv= null;
	public static EventFiringWebDriver driver=null;
	public static Logger Apps_logs = null;
	public static boolean loggedin = false;
	public static Xls_Reader excel = null;
	
	@BeforeSuite
	public void intialize() throws IOException{
		Apps_logs = Logger.getLogger("devpinoyLogger"); 
		//loading config file
		Apps_logs.debug("loading configuration file");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\config.properties");
		config = new Properties();
		config.load(fis);

		//loading OR File
		Apps_logs.debug("loading OR or xpath file");
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\OR.properties");
		OR = new Properties();
		OR.load(fis);
		
		Apps_logs.debug("Creating Object for XLS_Reader for excel operations");
		excel=new Xls_Reader(System.getProperty("user.dir")+"\\src\\config\\Controller.xlsx");
		//intializing Driver
		if(config.getProperty("browserType").equals("Firefox")){
			Apps_logs.debug("loading Firefox Driver");
			wdv = new FirefoxDriver();
		}else if(config.getProperty("browserType").equals("i.e")){
			Apps_logs.debug("loading Internet Explorer Driver");
			wdv = new InternetExplorerDriver();
		}else if(config.getProperty("browserType").equals("chrome")){
			Apps_logs.debug("loading Chrome  Driver");
			wdv = new ChromeDriver();
		}
		driver = new EventFiringWebDriver(wdv);
		//launching application
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	
	}
	
	public static WebElement getObject(String xpathType){
		try{
			return driver.findElement(By.xpath(OR.getProperty(xpathType)));
		}catch(Throwable t){
			Apps_logs.debug("Error-->"+t.getMessage());
			return null;
		}
		
		
	}
	
	
 }
