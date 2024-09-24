package Pages;

import org.openqa.selenium.By;

import Base.Base;

public class Appointment extends Base {
	
	By dateElement = By.xpath("//div[@class='u-cushion c-appointment-info__row']/div/div[1]/span[2]");
	By timeElement = By.xpath("//div[@class='u-cushion c-appointment-info__row']/div/div[2]/span[2]");
	By nameElement = By.xpath("//div[@data-qa-id='doctor_name']");
	
	public boolean[] validate_date_and_time(String Date,String Time,String Name) {
		
		String date = driver.findElement(dateElement).getText();
		String time = driver.findElement(timeElement).getText();
		String name = driver.findElement(nameElement).getText();
		
		//assign  default all the values as false
		Boolean isDate=false;
		Boolean isTime = false;
		Boolean isName=false;
		
		//validate date
		
		if(date.contains(Date)) {
			isDate =  !isDate;
		}
		
		//validate time
		if(Time.contains(time)) {
			isTime=!isTime;
		}
		
		//validate name of the doctor
		if(Name.contains(name)) {
			isName=true;
			
		}
		
		return new boolean[] {isDate,isTime,isName};

		
	}
}	
