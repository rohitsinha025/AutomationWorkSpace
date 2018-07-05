package IMAP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
//import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;

import java.text.SimpleDateFormat;
import java.util.Date;

//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

@SuppressWarnings("deprecation")
public class BaseClass extends SeleneseTestBase {

	public static WebDriver driver = null;
	@SuppressWarnings("deprecation")
	public static Selenium rc = null;
	public static String baseUrl = null;
	public static ObjectMap map = null;
	public static WebDriverWait wait = null;
	public static String flightNumber = null;
	public static String testsuitePath = ".//POCTestSuite.xls";
	public static String objmapPath = ".//src/main/java/config/objectmap.properties";
	public static String reportFolder = ".//Reports";
	public static String ieDriver = ".//IEDriverServer.exe";
	public static String testDataPath = ".//dataSheets/TestdataSheet.xls";




	/** Start Application and log in 
	 * @return */

	//@BeforeClass
	


	/** Close Application */
//	@AfterClass
	public static void closeApp(){
		try{
			tearDownApp();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/***************************************************************************************************************/
	/**
	 * Load URL
	 * @author Sedhu
	 */
	public static void loadURL() {
		try{
			driver.manage().window().maximize();
		//	rc = new WebDriverBackedSelenium(driver, baseUrl);
			driver.get(baseUrl);
		}catch(Exception e){
			reportFail("Unable to load Url '" + baseUrl + "'");
			Assert.fail("Unable to load Url '" + baseUrl + "'");
		}
	}

	/**************************************************************************************************************/
	/**
	 * Sleep
	 * @param time(In seconds)
	 * @throws Exception
	 * @author Sedhu
	 */
	public static void sleep(int time) throws Exception {
		Thread.sleep(time*1000);
	}



	/**************************************************************************************************************/
	/**
	 * Open Internet Explorer
	 * @author Sedhu
	 */
	public static void openInternetExplorer() {
		try{
			File file = new File(ieDriver);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(
					CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			driver = new InternetExplorerDriver(caps);
			driver.manage().deleteAllCookies();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**************************************************************************************************************/
	/**
	 * Exit
	 * @author Sedhu
	 */
	public static void tearDownApp() {
		driver.close();
		driver.quit();
	}

	/**************************************************************************************************************/
	/**
	 * Generate Four Digit Random Number
	 * @return Four Digit Number
	 * @author Sedhu
	 */
	public static String RandomNumber() {
		double number = Math.random();
		number = number * 10000;
		int flightNumber = (int) (number);

		return "" + flightNumber;
	}


	/**************************************************************************************************************/

	public static String getNextDate(int numOfDays) {
		String dt = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, numOfDays);
			dt = sdf.format(calendar.getTime());
			return dt.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			return dt;
		}
	}



	/**
	 * click method (objname = the object name from objectmap file)
	 */
	public static void Click(String objname) {
		try {
			driver.findElement(map.getLocator(objname)).click();
			reportInfo("clicked on '" + objname + "'");
		} catch (Exception e) {
			reportFail("element '" + objname + "' is not displayed");
			Assert.fail("element '" + objname + "' is not displayed");
		}
	}

	/** report information */
	public static void reportInfo(String message) {
		System.out.println(message);
		Reporter.log(message);
	}

	/** report fail */
	public static void reportFail(String message) {
		System.out.println("Fail: " + message);
		Reporter.log("Fail: " + message);
	}

	/** Switch Between Frames */
	public static void switchToFrame(int frameNumber) {
		try{
			driver.switchTo().defaultContent();
			driver.switchTo().frame(frameNumber);
		}catch(Exception e){
			reportFail("Frame '" + frameNumber + "' is not found");
			Assert.fail("Frame '" + frameNumber + "' is not found");
		}
	}

	public static String switchToFrameByName(String frame) {

		driver.switchTo().defaultContent();
		String actual = "";
		try {

			wait = new WebDriverWait(driver, 90);
			sleep(1);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By
					.name(frame)));

			System.out.println("Navigated to frame with name " + frame);
			actual = "Switched";
		} catch (final NoSuchFrameException e) {

			System.out.println("Unable to locate frame with id " + frame
					+ e.getStackTrace());
			reportFail("Frame with name'" + frame + "' is not found");
			actual = "Not Switched";
		} catch (final Exception e) {

			System.out.println("Unable to navigate t" + "o frame with id "
					+ frame + e.getStackTrace());
			reportFail("Frame with name '" + frame + "' is not found");
			actual = "Not Switched";
		}
		return actual;
	}

	/**
	 * waitForElementPresent method (objname = the object name from objectmap
	 * file) , (time is in seconds)
	 */
	public static void WaitForElementPresent(String objname, int time) {
		try{
			WebDriverWait newWait = new WebDriverWait(driver, time);
			newWait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator(objname)));
		}catch(Exception e){
			Assert.fail("timeout error: element '" + objname + "' not present");
		}
	}

	/** isElementPresent method */
	protected static boolean IsElementPresent(String objname) throws Exception {
		try {
			driver.findElement(map.getLocator(objname));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void testWindowPopup(String parentWindowId)
	{
		Set<String> allWindows = driver.getWindowHandles();
		if(!allWindows.isEmpty()) {
			for (String windowId : allWindows) {
				try {
					if(driver.switchTo().window(windowId).getTitle().equals("Windows Security")) {
						//Close the Visit Us Popup Window
						driver.close();
						break;
					}
				}
				catch(NoSuchWindowException e) {
					e.printStackTrace();
				}
			}
			driver.switchTo().window(parentWindowId);
		}
	}


	/**
	 * sendKeys method (objname = the object name from objectmap file) , (value
	 * is the text to enter)
	 */
	public static void SendKeys(String objname, String value) {
		try {
			driver.findElement(map.getLocator(objname)).clear();
			driver.findElement(map.getLocator(objname)).sendKeys(value);
			reportInfo("'" + value + "' is entered in '" + objname + "'");
		} catch (Exception e) {
			reportFail("element '" + objname + "' is not displayed");
			Assert.fail("element '" + objname + "' is not displayed");
		}
	}

	/** Switch to SubFrame */
	public static void switchToChildFrame(int frameNumber) {
		try{
			driver.switchTo().frame(frameNumber);
		}catch(Exception e){
			reportFail("Childframe '" + frameNumber + "' is not found");
			Assert.fail("Childframe '" + frameNumber + "' is not found");
		}
	}

	/** Get Alert Message */
	public static String getAlertMsg() throws Exception{
		String alertMsg = null;
		try {
			alertMsg = driver.switchTo().alert().getText();
			return alertMsg;
		} catch (Exception e) {
			reportFail("No message found in alert");
			Assert.fail("No message found in alert");
			return alertMsg;
		}
	}

	/** alertAccept method to accept alert if present */
	public static void AlertAccept() {
		try {
			Alert alert = driver.switchTo().alert();
			
			alert.accept();
		} catch (Exception e) {
			reportFail("No alert present");
			Assert.fail("No alert present");
		}
	}

	/** report pass */
	public static void reportPass(String message) {
		System.out.println("Pass: " + message);
		Reporter.log("Pass: " + message);
	}

	/** Verify Check Box is Selected or not */
	public static boolean checkboxSelected(String objName) {
		try {
			if (driver.findElement(map.getLocator(objName)).isSelected()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			reportFail("Checkbox '"+objName+"' is not displayed");
			Assert.fail("Checkbox '"+objName+"' is not displayed");
			return false;
		}
	}

	/**
	 * Is Element Displayed method (objname = the object name from objectmap
	 * file)
	 * 
	 * @return
	 */
	public static boolean IsElementEnabled(String objname) {
		try {
			driver.findElement(map.getLocator(objname)).isEnabled();
			reportInfo("'" + objname + "'is enabled");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * selectTextFromDropdown method (objname = the object name from objectmap
	 * file)
	 */
	public static void SelectTextByVisibleText(String objname, String text) {
		try {
			Select select = new Select(driver.findElement(map
					.getLocator(objname)));
			reportInfo("dropdown '" + objname + "' is selected");
			select.selectByVisibleText(text);
			reportInfo("text '" + text + "' is selected from the dropdown '"
					+ objname + "'");
		} catch (Exception e) {
			reportFail("'" + objname + "' is not present or text '" + text
					+ "' is not selected");
			Assert.fail("'" + objname + "' is not present or text '" + text
					+ "' is not selected");
		}

	}

	/**
	 * selectTextFromDropdown method (objname = the object name from objectmap
	 * file)
	 */
	public static void SelectTextByValue(String objname, String value) {
		try {
			Select select = new Select(driver.findElement(map
					.getLocator(objname)));
			reportInfo("dropdown '" + objname + "' is selected");
			select.selectByValue(value);
			reportInfo("text '" + value + "' is selected from the dropdown '"
					+ objname + "'");
		} catch (Exception e) {
			reportFail("'" + objname + "' is not present or text '" + value
					+ "' is not selected");
			Assert.fail("'" + objname + "' is not present or text '" + value
					+ "' is not selected");
		}

	}

	/** Random String Generator */
	public static String Random_StringGenerator(int length) throws Exception{
		String Random = "";
		String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < length; i++) {
			double a = Math.random();
			int x = (int) (a * 100);

			if (x > 25 && x < 52) {
				x = x - 26;
			} else if (x > 51 && x < 78) {
				x = x - 52;
			} else if (x > 77 && x < 99) {
				x = x - 78;
			} else{
				x = 1;
			}

			char letter = abc.charAt(x);
			Random = Random + String.valueOf(letter);
		}
		System.out.println(Random);
		return Random;

	}



	/** Random Number Generator */
	public static String Random_NumberGenerator(int length) throws Exception{
		double number,value;
		int len;
		number = Math.random();

		StringBuffer s = new StringBuffer(length);
		s.append("1");
		for(int k=0;k<length;k++){
			s.append("0");
		}

		value = Double.parseDouble(s.toString());

		number = number * value;
		int random = (int) (number);
		String randomno = Integer.toString(random);
		len = randomno.length();

		if(len<length){
			int diff = length - len;
			StringBuffer s1 = new StringBuffer(randomno);
			for(int k=0;k<diff;k++){
				s1.append("0");
			}
			randomno = s1.toString();
		}

//		System.out.println(randomno);
		return randomno;

	}

	/**************************************************************************************************************/
	/**
	 * Switch to second window
	 * @param firstWinHandle (Handle of Main Window)
	 */
	public static void switchToSecondWindow(String firstWinHandle){
		waitForNumberOfWindowsToOpen(2);
		Set<String> handles = driver.getWindowHandles();
		handles.remove(firstWinHandle);
		String winHandle = handles.iterator().next();
		if(winHandle != firstWinHandle){
			String secondWinHandle = winHandle;
			driver.switchTo().window(secondWinHandle);
		}
	}

	/**************************************************************************************************************/
	/**
	 * Wait for number of windows to be opened
	 * @param numberOfWindows
	 */
	public static void waitForNumberOfWindowsToOpen(final int numberOfWindows) {
		new WebDriverWait(driver, 60) {
		}.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {                        
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});
	}

	/**************************************************************************************************************/
	/**
	 * Read Excel Data
	 * @param Sheet
	 * @param intRow
	 * @param intCol
	 * @return Cell Data
	 */
	public static String getExcelCell(String Sheet, int intRow, int intCol){
		try{
			File file = new File(testDataPath);
			FileInputStream inputStream = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFSheet worksheet = workbook.getSheet(Sheet);
			HSSFRow row = worksheet.getRow(intRow);
			HSSFCell cellA1 = row.getCell(intCol);
			return cellA1.toString();
		}catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}


	/**************************************************************************************************************/
	/**
	 * Write to Excel's specified cell
	 * @param Sheet
	 * @param intRow
	 * @param intCell
	 * @param strSetCell
	 * @return Entered string
	 */
	public static String setExcelCell(String Sheet,int intRow,int intCell,String strSetCell)
	{
		try {
			File file = new File(testDataPath);
			FileInputStream inputStream = new FileInputStream(file);

			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFSheet worksheet = workbook.getSheet(Sheet);
			HSSFRow row = worksheet.getRow(intRow);
			HSSFCell cellA1 = row.getCell(intCell);
			cellA1.setCellValue(strSetCell);
			inputStream.close();
			FileOutputStream outFile =new FileOutputStream(new File(testDataPath));
			workbook.write(outFile);
			outFile.close();
			return cellA1.toString();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}


	/**************************************************************************************************************/
	/**
	 * Expand Tree Node
	 * @param index (Index number of tree node)
	 * @param treeName (Name of tree node)
	 * @author Sedhu
	 */

	public static void expandTree(String treeName, int index){
		try{
			WebDriverWait myWait = new WebDriverWait(driver, 60);
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(treeName)));
			driver.findElement(By.xpath("(//a[(text()='"+treeName+"')])["+index+"]/preceding-sibling::a[1]")).click();
			reportInfo("Expanded Tree '" + treeName + "'");
		}catch(Exception e){
			reportFail("Tree element '" + treeName + "' is not displayed");
			Assert.fail("Tree element '" + treeName + "' is not displayed");
		}
	}

	/**************************************************************************************************************/
	/**
	 * Click Link By Index Number
	 * @param linkText (Text to Click)
	 * @param index (Index Number of the Link)
	 * @author Sedhu
	 */
	public static void clickLinkByIndex(String linkText, int index){
		try{
			WebDriverWait myWait = new WebDriverWait(driver, 60);
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
			driver.findElement(By.xpath("(//a[contains(text(),'"+linkText+"')])["+index+"]")).click();
			reportInfo("clicked on '" + linkText + "'");
		}catch(Exception e){
			reportFail("element '" + linkText + "' is not displayed");
			Assert.fail("element '" + linkText + "' is not displayed");
		}
	}

	/*********************************************************************************************************************/
	public static synchronized void setValue(String objname, String value) {
		// Object lock1 = new Object();

		try {
			// synchronized(lock1){

			driver.findElement(map.getLocator(objname)).clear();
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].value = arguments[1]",
					driver.findElement(map.getLocator(objname)), value);
			// }
			reportInfo("'" + value + "' is entered in '" + objname + "'");

		} catch (Exception e) {

			reportFail("element '" + objname + "' is not displayed");
			Assert.fail("element '" + objname + "' is not displayed");
		}
	}

	/**********************************************************************************************************/
	public static void openBrowser(String browser) {
		try {
			if (browser.equalsIgnoreCase("FF")) {
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("CH")) {
				System.setProperty("webdriver.chrome.driver",
						".//chromedriver.exe");
				driver = new ChromeDriver();
				
			} else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						".//IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}

		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}

	}

	/**********************************************************************************************************/
	public static void JSClick(String objname){
		try {
			WebElement element = driver.findElement(map.getLocator(objname));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
		}
		catch(Exception E){
			E.printStackTrace();
		}
	}
	
	/**********************************************************************************************************/
	
	public static void mouseHover(String linkText){

		try{
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(map.getLocator(linkText));
			action.moveToElement(we).build().perform();
			sleep(4);			
		}
		catch(Exception E){
			E.printStackTrace();
		}
	} 

	/**********************************************************************************************************/
	
	public Boolean Verify_Presence_of_Object(String obj_name) throws Exception{

        try{
              driver.findElement(map.getLocator(obj_name));
              reportPass(obj_name+" is present");
              return true;
        }
        catch(NoSuchElementException E){
              reportFail(obj_name+" is not present");
              return false;
        }

  }	
	
	
	/**************************************************************************************************************/
	/**
	 * Generate A random string generator having String & current date,time stamp
	 * @return AUTONNNNNNNNNNNNNN
	 * @author Raghavendra
	 */
	public static String RandomCustomerName() {
		String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
		String AutoCustID = "AUTOMATE"+date;
		System.out.println(AutoCustID);
		return AutoCustID;
	}
	

	public static void WaitForElementEnable(String objname, int time) {
		try{
			WebDriverWait newWait = new WebDriverWait(driver, time);
			newWait.until(ExpectedConditions.elementToBeClickable(map.getLocator(objname)));
			
		}catch(Exception e){
			Assert.fail("timeout error: element '" + objname + "' not present");
		}
	}

	
	/** alertAccept method to accept alert if present */
	public static String Alerttext() {
		String val = null;
		try {
			
			Alert alert = driver.switchTo().alert();
			val = alert.getText();
		
		} catch (Exception e) {
			reportFail("No alert present");
			Assert.fail("No alert present");
		}
		return val;
	}

	
	
	/**************************************************************************************************************/
	public static String partyNumber() {
		
		String partynum = null;
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("outlook.corp.emc.com", "sinhar7", "HiThere11");
			Folder inbox = store.getFolder("ImpFolder");
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());
			/*Address[] in = msg.getFrom();
			
			 for (Address address : in) 
			 { 
				 System.out.println("FROM:" +address.toString()); 
			 }*/
			 
			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
			
			 /*System.out.println("SENT DATE:" + msg.getSentDate());
			 System.out.println("SUBJECT:" + msg.getSubject());*/
			 
			// System.out.println("CONTENT:" + bp.getContent());
			String content = bp.getContent().toString();
			if (content.contains("Party Number")) {
				char[] partynumber = new char[content.length()];
				partynumber = content.toCharArray();
				char[] partynumber1 = new char[10];
				for (int i = 0; i < 10; i++) {
					for (int j = 212; j < 222; j++) {
						partynumber1[i] = partynumber[j];
						i++;
					}
					partynum = new String(partynumber1);
				}
			}
		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return partynum;
	}
	/**************************************************************************************************************/
	public static String ucidnumber() {

		String ucidvalue = null;
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("outlook.corp.emc.com", "sinhar7", "HiThere11");
			Folder inbox = store.getFolder("ImpFolder");
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());
			/*
			 * Address[] in = msg.getFrom();
			 * 
			 * for (Address address : in) { System.out.println("FROM:"
			 * +address.toString()); }
			 */

			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);

			/*
			 * System.out.println("SENT DATE:" + msg.getSentDate());
			 * System.out.println("SUBJECT:" + msg.getSubject());
			 */

			
			String content = bp.getContent().toString();
			if (content.contains("Party Number")) {
				char[] ucid = new char[content.length()];
				ucid = content.toCharArray();
				char[] ucid1 = new char[10];
				for (int i = 0; i < 10; i++) {
					for (int j = 230; j < 240; j++) {
						ucid1[i] = ucid[j];
						i++;
					}
					ucidvalue = new String(ucid1);
				}
			}
		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return ucidvalue;
		
	}
	
	/**************************************************************************************************************/
	public static String customername() {

		String ucidvalue = null;
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("outlook.corp.emc.com", "sinhar7", "HiThere11");
			Folder inbox = store.getFolder("ImpFolder");
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());

			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
		
			String content = bp.getContent().toString();
			if (content.contains("Party Number")) {
				char[] ucid = new char[content.length()];
				ucid = content.toCharArray();
				char[] ucid1 = new char[23];
				for (int i = 0; i < 23; i++) {
					for (int j = 262; j < 284; j++) {
						ucid1[i] = ucid[j];
						i++;
					}
					ucidvalue = new String(ucid1);
				}
			}
		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return ucidvalue;
		
	}
	
	public static String gettext(String objname) {
		String textvalue = null;
		try {
			textvalue = driver.findElement(map.getLocator(objname)).getText();
			reportInfo("clicked on '" + objname + "'");
		} catch (Exception e) {
			reportFail("element '" + objname + "' is not displayed");
			Assert.fail("element '" + objname + "' is not displayed");
		}
		return textvalue;
	}
	
	public static WebElement getelementxpath(String xpath){
			WebElement xpathvalue = null;
		try{
			xpathvalue = driver.findElement(By.xpath(xpath));
		}catch(Exception e){
			e.printStackTrace();
		}
		return xpathvalue;
	}
	
	
}