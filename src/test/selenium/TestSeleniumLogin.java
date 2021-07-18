package test.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * 
 * @author Marta Fraioli
 * 
 * 
 */

public class TestSeleniumLogin {

	private static final String WRONG_USERNAME = "Piero";
	private static final String WRONG_PASSWORD = "123";
	private static final String RIGHT_USERNAME = "martafra";
	private static final String RIGHT_PASSWORD = "123.AAaa";
	private WebDriver driver;
	@Test
	public void seleniumTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.get("http://localhost:8080/EEProject/Home.jsp");
		
		WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"loginButton\"]"));
		
		Boolean alreadyLogged = false;
		if(loginButton == null)
			alreadyLogged = true;
		
		assertEquals(alreadyLogged, false);
		
		if(loginButton == null) {
			return;
		}
		
		loginButton.click();
		
		assertEquals(false, tryLogin(WRONG_USERNAME, WRONG_PASSWORD));
		Thread.sleep(500);
		assertEquals(false, tryLogin(WRONG_USERNAME, RIGHT_PASSWORD));
		Thread.sleep(500);
		assertEquals(false, tryLogin(RIGHT_USERNAME, WRONG_PASSWORD));
		Thread.sleep(500);
		assertEquals(true, tryLogin(RIGHT_USERNAME, RIGHT_PASSWORD));
	}
	
	private Boolean tryLogin(String username, String password) throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"userID\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click();
		
		Thread.sleep(1000);
		
		try {
			driver.findElement(By.xpath("//*[@id=\"errorLabel\"]"));
		}catch(NoSuchElementException e) {
			return true;
		}
		return false;
		
		
		
	}
	
}