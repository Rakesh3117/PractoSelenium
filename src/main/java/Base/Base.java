package Base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Base {
	
	public static WebDriver driver;	
	
	protected static ExtentSparkReporter htmlReport;
	protected static ExtentReports report;
	protected static ExtentTest test;
	
	public Properties properties =  new Properties();
	
	final String propertyFilePath= "src\\main\\java\\config\\Configuration.properties";
	
	BufferedReader reader;
	
	public Base(){
		try {
			FileInputStream file = new FileInputStream(propertyFilePath);
			properties.load(file);
		}
		catch(Exception e) {
			System.out.println("Fill not foumd");
		}
	}
	 
	
	public void Browser_Setup(String browser) {
		
		if(browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	

	}
	
	public void openURL() throws Exception{
		driver.get(properties.getProperty("url"));
		Thread.sleep(2000);
	}
	
	public void close_browser() {
		
		driver.close();	
	}
	
	public static void save_report() {
		report.flush();
	}
	
	public static void Report_Generation()
	{
		htmlReport = new ExtentSparkReporter("test-output/practoReport.html");
		
		htmlReport.config().setReportName("Practo");
		htmlReport.config().setDocumentTitle("Practo testing Practice");
		htmlReport.config().setTheme(Theme.DARK);
		

		report=new ExtentReports();
		report.setSystemInfo("Environment", "TestEnv");
		report.setSystemInfo("TesterName", "Rakesh");
		report.attachReporter(htmlReport);
		
	}
}








