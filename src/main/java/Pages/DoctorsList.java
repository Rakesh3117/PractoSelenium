package Pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Base.Base;

public class DoctorsList extends Base{
	
	String city = properties.getProperty("city");
	String date;
	
	By headingCityName = By.xpath("//h1[@class='u-xx-large-font u-bold']");
	By specilizationOfEachDoctor = By.xpath("//div[@class='info-section']/div[1]/div/span");
	By listOfDoctorsNames = By.xpath("//div[@class='listing-doctor-card']/div/div[2]/a");
	By bookClinikButtons = By.xpath("//div[@class='listing-doctor-card']/div[2]/div/div/div[2]/div[1]/button");
	By availabileDates = By.xpath("//div[@class='u-pos-rel c-slots-header__daybar ']/div");
	By availableTimesElement = By.xpath("//div[@class='c-day-slot']/div/div[2]/div/span");
	By filterElement = By.xpath("//i[@data-qa-id='all_filters_icon']");
	By feeElements = By.xpath("//div[@class=\"o-page-container u-cushion--vertical pure-g\"]/div[1]/div[1]/label/span");
	By doctorsFeeElement = By.xpath("//span[@data-qa-id=\"consultation_fee\"]");
	
	// Validate City name 
	public void verify_cityName() throws Exception {
		//Explicit Wait 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(headingCityName));
        
        String actualCityName = driver.findElement(headingCityName).getText();
        System.out.println(actualCityName);
        
        if (actualCityName.contains(city)) {
            System.out.println("City Name is Matched");
        } else {
            System.out.println("City Name is Mismatched");
        }
    }
	
	public int[] verify_specilization() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(specilizationOfEachDoctor));
        
        int success = 0;
        int fail = 0;
        
        // List of all doctors Specilization
        List<WebElement> specilizationList = driver.findElements(specilizationOfEachDoctor);
        
        for (WebElement eachSpecilization : specilizationList) {
        	
        	//if the specilization is not match with acutal value then we increment  fail as 1 otherwise success as 1
            if (eachSpecilization.getText().contains(properties.getProperty("specilization"))) {
                success += 1;
            } else {
                fail += 1;
            }
        }

        return new int[] {success,fail};
    }

	//select the doctor
	public String[] select_doctor() throws InterruptedException {
		
        String formattedDate = "";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        //Number of "Book Clinic Visit " buttons
        List<WebElement> clinicButtons = driver.findElements(bookClinikButtons);

        // if the page is empty 
        if (clinicButtons.isEmpty()) {
            System.out.println("No clinic buttons available.");
            return new String[] {};
        }
        
        // Randomly click any on of the button
        Random random = new Random();
        int number = random.nextInt(clinicButtons.size());
        System.out.println("Random Number = " + number + " Total Buttons = " + clinicButtons.size());
        
        // Get the Doctor name for the Randomly clicked Button
        WebElement selectedDoctorElement = clinicButtons.get(number);
        String selectedDoctor = selectedDoctorElement.findElement(By.xpath("../../../../../../div[1]/div[2]/a")).getText();
       
        System.out.println("Selected Doctor = " + selectedDoctor);
        String doctorNameBefore = selectedDoctor;

        clinicButtons.get(number).click();

        // Available days to book the Appointment
        List<WebElement> availableDays = driver.findElements(availabileDates);
        
        int daysCount =0;
        for (WebElement day : availableDays) {
            wait.until(ExpectedConditions.visibilityOf(day));
            
            // Check Wheather the respective day has available slots if not navigate to next day
            if (!(day.getText().contains("No"))) {
                day.click();

                System.out.println("date = " + day.getText());

                LocalDate today = LocalDate.now();
            	
                // Get the Date  in the required format
            	LocalDate futureDate = today.plusDays(daysCount); 
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            	formattedDate = futureDate.format(formatter); 
            	System.out.println("Formatted date: " + formattedDate);
            	
            	// when we find the available slot break the loop
                break;
            }
            daysCount+=1;
        }

        // List the Available Slot in the Morning , Afternoon And Evening
        
        List<WebElement> availableTimes = driver.findElements(availableTimesElement);

        // if nthe number of available slots is zero return empty 
        if (availableTimes.isEmpty()) {
            System.out.println("No available times.");
            return new String[] {};
        }

        // Randomly click any of the Available slot
        int randomTime = random.nextInt(availableTimes.size());
        
        // Save the Randomly clicked available slot time for the Validation 
        String time = availableTimes.get(randomTime).getText();
        availableTimes.get(randomTime).click();

        System.out.println("Selected time: " + time);
        return new String[]{formattedDate, time,doctorNameBefore};
    }
	
	
	public int[] filters() throws Exception {
        String result = "";
        
        driver.findElement(filterElement).click();

        List<WebElement> feeTypes = driver.findElements(feeElements);
        Thread.sleep(1000); // You might want to replace this as well

        Random random = new Random();
        int number = random.nextInt(feeTypes.size());
        
        feeTypes.get(number).click();
        String feeRange = feeTypes.get(number).getText();
        System.out.println("fee Range  = " + feeRange);
        
        List<WebElement> doctorsFee = driver.findElements(doctorsFeeElement);
        int filterPass = 0, filterFail = 0;

        if (number != 0) {
            String priceString = feeRange.replace("Above ₹", "");
            int priceLimit = Integer.parseInt(priceString);
            

            for (WebElement fee : doctorsFee) {
                String feeString = (fee.getText());
                String numericString = feeString.replace("₹", "").trim();
                int price = Integer.parseInt(numericString);
                
                if (priceLimit <= price) {
                    filterPass += 1;
                } else {
                    filterFail += 1;
                }
            }

            result = "Filter Pass = " + filterPass + " Filter Fail = " + filterFail + " Filter Total = " + doctorsFee.size();        
        } else {
            for (WebElement fee : doctorsFee) {
                String feeString = (fee.getText());
                String numericString = feeString.replace("₹", "").trim();
                int price = Integer.parseInt(numericString);
                
                if (price <= 500 && price > 0) {
                    filterPass += 1;
                } else {
                    filterFail += 1;
                }
            }

            result = "Filter Pass = " + filterPass + " Filter Fail = " + filterFail + " Filter Total = " + doctorsFee.size();
        }
        
        System.out.println(result);

        return new int[] {filterPass,filterFail};
    }
	
}
