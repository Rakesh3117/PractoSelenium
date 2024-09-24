package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import Base.Base;

public class HomePage extends Base {
	
	By inputElement = By.xpath("//input[@placeholder='Search location']");
	By crossElement = By.xpath("//i[@class=\"icon-ic_cross_solid\"]");
	By specilization = By.xpath("//input[@data-input-box-id='omni-searchbox-keyword']");
	
	public void locationAndSpecilization() throws Exception{
		
		// find out the city input tag element
		WebElement inputTag = driver.findElement(inputElement);
		inputTag.click();
		
		driver.findElement(crossElement).click();;
		
		// pass the city value from the properties file
		inputTag.sendKeys(properties.getProperty("city"));
		Thread.sleep(2000);

		// Randomly click any one of the related city
		for(int i=0 ; i<3 ;i++) {
			inputTag.sendKeys(Keys.ARROW_DOWN);
		}
		
		inputTag.sendKeys(Keys.ENTER);
		
		// Find the specilization input tag element 
		WebElement specilizationInput = driver.findElement(specilization);
		specilizationInput.sendKeys(properties.getProperty("specilization"));
		Thread.sleep(1000);
		
		// randomly select any of the the related specilization
		for(int i=0 ; i<4 ;i++) {
			specilizationInput.sendKeys(Keys.ARROW_DOWN);
		}
		
		specilizationInput.sendKeys(Keys.ENTER);
		
		
	}
}
