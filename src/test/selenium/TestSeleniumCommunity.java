package test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import logic.bean.ArticleBean;
import logic.controller.application.CommunityController;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TestSeleniumCommunity {

	@Test
	public void seleniumTest() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/EEProject/Community.jsp");
		
		CommunityController controller = new CommunityController();
		List<ArticleBean> articles = controller.getAllAcceptedArticles();
		
		ArticleBean choosenArticle = null;
		for(ArticleBean article : articles) {
			if(!article.getTags().isEmpty()) {
				choosenArticle = article;
				break;
			}
		}
		
		if(choosenArticle == null)
			return;
		
		String tag = choosenArticle.getTags().get(0);
		String name = choosenArticle.getID().toString();
		
		driver.findElement(By.xpath("//*[@id=\"searchBar\"]")).sendKeys(tag);
		driver.findElement(By.xpath("//*[@id=\"searchBtn\"]")).click();
		List<WebElement> elements = driver.findElements(By.className("articleCase"));
		
		Boolean found = false;
		for(WebElement element : elements) {
			WebElement id = element.findElements(By.name("articleID")).get(0);
			String articleID = id.getAttribute("value");
			if(articleID.equals(name)){
				found = true;
			}
		}
		
		assertEquals(found, true);
		
	}
	
}
