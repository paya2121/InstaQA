package instatest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class scenario1{

public WebDriver driver;

 @Parameters("browser")

 @BeforeClass

 // Passing Browser parameter from TestNG xml

 public void beforeTest(String browser) {
 System.out.println("tests to validate instawork website appears first in google search. ");
 // If the browser is Firefox, then do this

 if(browser.equalsIgnoreCase("firefox")) {
 System.out.println("launching Firefox browser");
 System.setProperty("webdriver.firefox.marionette", "src\\resources\\geckodriver.exe");
 	driver = new FirefoxDriver();
   driver.manage().window().maximize();
   driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

 // If browser is IE, then do this	 

 }else if (browser.equalsIgnoreCase("ie")) { 
 System.out.println("launching IE browser");

 System.setProperty("webdriver.ie.driver", "src\\resources\\IEDriverServer.exe");
       driver = new InternetExplorerDriver();
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

 } 
 else if (browser.equalsIgnoreCase("chrome")) { 
 System.out.println("launching chrome browser");

 System.setProperty("webdriver.chrome.driver", "src\\resources\\chromedriver.exe");
       driver = new ChromeDriver();
       driver.manage().window().maximize();
 }
	 
	  		driver.get("https://www.google.co.in"); 
	 
	  }
	 
	  // Once Before method is completed, Test method will start
	 
	  @Test(priority=1 ,description="=enter text in google search bar")
	    
	    public void searchGoogle() throws InterruptedException{
		 
	    	String searchTerm="instawork";
	    	//String matchInUrl="https://www.instagram.com/explore/tags/instawork/";
	    	List<WebElement> results = new ArrayList<WebElement>();
	    	ListIterator<WebElement> itr = null;
	    
	        // search page

	        WebDriverWait wait = new WebDriverWait(driver, 10);
	        String cssOfInputField = "input[name='q']";
	        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssOfInputField)));
	        WebElement element;
	        inputField.sendKeys(searchTerm);
	        inputField .sendKeys(Keys.ENTER);
	       
	        int pageNumber=1;
	        WebDriverWait wait1 = new WebDriverWait(driver, 10);
	        boolean foundMatch=false;
	        String foundMatchingUrl="";

	        while(!foundMatch){
	        	
	        	results= wait1.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("h3.r > a")));
	        	itr = results.listIterator(); // re-initializing iterator

	        	while (itr.hasNext()) {
	        			element = itr.next();
	        	     //   String href = element.getAttribute("href");
	                   // String title = element.getText();

	                  //  System.out.println(href);
	                  //  System.out.println(title);
	        	        if (element.getAttribute("href").contains(searchTerm)) {
	        	        	 
	        	        	 foundMatchingUrl = element.getAttribute("href");
	        	        	 element.click();
	        	            foundMatch = true;
	        	            break;
	        	        }
	        	}
	          

	            if (!foundMatch) {
	            	pageNumber++;

	                String cssOfNextPageButton = "a#pnnext";
	                WebElement nextPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssOfNextPageButton)));
	                nextPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssOfNextPageButton)));
	                nextPage.click();
	                results.clear(); // clean list
	                wait.until(ExpectedConditions.textToBePresentInElementLocated(
	                    By.xpath("//td[@class='cur']"), pageNumber + ""));
	            }
	           
	        }
	        System.out.println(String.format("Found match on Page Number %d - %s", pageNumber, foundMatchingUrl));
	           
	        }

	@Test(priority=2 ,description="Click on login button of instawork ")
	    
	    public void Login() throws InterruptedException{
				System.out.println("Click on login button");
				driver.findElement(By.linkText("Login")).click();
				System.out.println("Click on Browse Jobs button");
				driver.findElement(By.linkText("Browse Jobs")).click(); 
		
	}

	 
	  @AfterClass public void afterTest() {
	 
			driver.quit();
	 
		}
	 
	}