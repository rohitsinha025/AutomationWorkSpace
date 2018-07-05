package IMAP;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ReportScreenShot<X> implements ITestListener{
	
	 
	    public void onStart(ITestContext result) {
	 
	         System.out.println("Start Of Execution-> "+result.getName());
	 
	    }
	 
	    public void onTestStart(ITestResult result) {
	 
	        System.out.println("Test Started-> "+result.getName());
	 
	    }
	 
	    public void onTestSuccess(ITestResult result) {
	 
	        System.out.println("Test Pass-> "+result.getName());
	 
	    }
	 
	    public void onTestFailure(ITestResult result) {
	    	String workingDirectory = System.getProperty("user.dir");
	    	String fileName = workingDirectory + File.separator + "screenshots" + File.separator +  result.getMethod().getMethodName() + "().png";//filename
	    	String fileNameReport = "file:///"+ workingDirectory + "/" + "screenshots" + "/" +  result.getMethod().getMethodName() + "().png";
	    	BaseClass.driver.switchTo().defaultContent();
	    	File scrFile = ((TakesScreenshot)BaseClass.driver).getScreenshotAs(OutputType.FILE);
	    	try {
	    		FileUtils.copyFile(scrFile, new File(fileName));
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    	Reporter.log("<a href=\"" + fileNameReport  + "\">  ScreenShot  </a>"  );
	    	Reporter.setCurrentTestResult(null);
	    	System.out.println("Test Failed-> "+result.getName());
	    }
	        
	 
	    public void onTestSkipped(ITestResult result) {
	 
	        System.out.println("Test Skipped->"+result.getName());
	 
	    }
	 
	    public void onFinish(ITestContext result) {
	 
	System.out.println("END Of Execution(TEST)->"+result.getName());
	 
	    }
	 
	    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	 
	 
	    }
	    
}
