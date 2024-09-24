package practoTestCases;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import Base.Base;
import Pages.Appointment;
import Pages.DoctorsList;
import Pages.HomePage;

public class PractoTestCases extends Base {
	Base basePage = new Base();
	HomePage homePage = new HomePage();
	DoctorsList doctorsList = new DoctorsList();
	Appointment appointmentPage = new Appointment();
	
	
	@Test(priority=1)
	@Parameters("browser")
	public void verify_the_category_list_of_all_doctors(@Optional("chrome") String browser) throws Exception {
		
		basePage.Browser_Setup( browser);
		basePage.openURL();
		
		homePage.locationAndSpecilization();
		
		doctorsList.verify_cityName();
		
		int[] PassFail  = doctorsList.verify_specilization();
		System.out.println();
		
        String result ="All the Doctors belongs the same specilization";
		
        if(PassFail[1] == 0) {
        	test=report.createTest("verify the category list of all doctors ");
        	test.log(Status.PASS, result);
        }
        else {
        	test=report.createTest("verify the category list of all doctors");
        	test.log(Status.FAIL, result);
        }
    	
    	Thread.sleep(2000);
    	basePage.close_browser();
	}
	
	
	@Test(priority = 2)
	@Parameters("browser")
	public void verify_doctor_details_date_time(@Optional("edge") String browser) throws Exception {
		
		basePage.Browser_Setup(browser);
		basePage.openURL();
		
		homePage.locationAndSpecilization();
		
		String[] dateTime = doctorsList.select_doctor();
		
		boolean[] result = appointmentPage.validate_date_and_time(dateTime[0], dateTime[1],dateTime[2]);
		
		if(result[0] && result[1] && result[2]) {
			test = report.createTest("Verify Date and Time");
			test.log(Status.PASS, " Doctor Name Available Date And Time of booking slot is Matched");
		}
		else {
			if(result[0] || result[1]) {
				if(!result[0]) {
					test = report.createTest("Verify Date and Time");
					test.log(Status.FAIL, "Date is not matched");
				}
				else if(!result[1]) {
					test = report.createTest("Verify Date and Time");
					test.log(Status.FAIL, "Time is not matched");
				}
				else {
					test = report.createTest("Verify Date and Time");
					test.log(Status.FAIL, "Name is not matched");
				}
				
			}
			else {
				test = report.createTest("Verify Date and Time");
				test.log(Status.FAIL, "Doctor Name Available Date And Time of booking slot is  Not Matched");
			}
		}
		Thread.sleep(2000);
    	basePage.close_browser();
		
	
	}
	
	
	
	@Test(priority = 3)
	@Parameters("browser")
	public void verify_filtered_values(@Optional("chrome")String browser) throws Exception {
		basePage.Browser_Setup(browser);
		basePage.openURL();
		homePage.locationAndSpecilization();
		int[] result = doctorsList.filters();
		String failed = result[1]+" Doctrs are under below price Range";
		
		if(result[1]==0) {
			test=report.createTest(" To verify the filtered values  ");
	    	test.log(Status.PASS, "All the Doctors are in the Price range");
		}
		else {
			test=report.createTest(" To verify the filtered values  ");
	    	test.log(Status.FAIL, failed);
		}
    	
    	Thread.sleep(2000);
    	
    	basePage.close_browser();
		
	}
	
	@BeforeSuite
	public void reportGeneration() {
		Report_Generation();
	}
	
	@AfterSuite
	public void save_reports() {
		save_report();
	}
	
	
	
	
}
