package test.selenium;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
 /*
  * @author Valentina Martini
  */
public class TestSeleniumSelectVG {
	@Test
	public void seleniumTest() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8081/prova_web_project/Home.jsp");
		
		int pageNumber=0;
		Boolean result=true;
		for(int i=0; true; i++) {
			driver.findElement(By.xpath("//*[@id=\"catalogue\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"videogame\"]")).click();
			for(int j = 0; j < pageNumber; j++) {
				driver.findElement(By.name("nextBtn")).click();
			}
			
			
			try {
				driver.findElement(By.xpath("//*[@id=\"Item"+i+"\"]")).click();
			}catch(NoSuchElementException e ) {
				if(driver.findElement(By.name("nextBtn")).isEnabled()) {
					pageNumber+=1;	
					i = -1;
					continue;
				}
				else {
					break;
				}
			}
			WebElement TxtBoxContent = driver.findElement(By.xpath("//*[@id=\"type\"]"));
			String type= TxtBoxContent.getText();
			if(!type.equals("VIDEOGAME")) {
				result = false;
				break;
			}		
		}

		assertEquals(true, result);
		driver.close();
	}
	
}
