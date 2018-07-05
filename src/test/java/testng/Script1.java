package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Script1 {				
    									
     @Test
     public void test() throws InterruptedException {
        String baseUrl = "http://demo.guru99.com/test/tooltip.html";					
        System.setProperty("webdriver.chrome.driver","C:\\Users\\rohsinha2\\Desktop\\code\\IMAP_Automation\\chromedriver.exe");	
        
        WebDriver driver = new ChromeDriver();	
        //driver.manage().window().maximize();
        String expectedTooltip = "What's new in 3.2";					
        driver.get(baseUrl);					
       
        
        WebElement download = driver.findElement(By.xpath(".//*[@id='download_now']"));	
        
        Thread.sleep(5);
        Actions builder = new Actions (driver);							

        builder.clickAndHold().moveToElement(download);					
        builder.moveToElement(download).build().perform(); 	
        
        WebElement toolTipElement = driver.findElement(By.xpath(".//*[@class='box']/div/a"));
        Thread.sleep(5);
        String actualTooltip = toolTipElement.getText();			
        
        System.out.println("Actual Title of Tool Tip  "+actualTooltip);							
        if(actualTooltip.equals(expectedTooltip)) {							
            System.out.println("Test Case Passed");					
        }		
        driver.close();			
     }
}	
