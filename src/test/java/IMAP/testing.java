package IMAP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

public class testing extends BaseClass {

	public static void main(String[] args) {
		/*String str = testing.partyNumber();
		System.out.println(str);*/
		try {
			//testing.customername();
			//testing.readline();
			testing.testVerifyDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void testVerifyDB() {

        try {
        	String customername = "AUTOMATE04102016114451";
               Class.forName("oracle.jdbc.OracleDriver");
               System.out.println("Driver loaded");
               // connection to Oracle
               Connection con = DriverManager.getConnection("jdbc:oracle:thin:@echoratst01.isus.emc.com:1630:ECHT","emcparty", "c5xt3st");
               // DriverManager.get
               System.out.println("connection established");
               Statement stm = con.createStatement();
               ResultSet rs = stm
                            .executeQuery("Select * from emcparty.c_b_party_xref where PARTY_NAME = "+"'"+customername+"'");
               System.out.println("Query executed");
               while (rs.next()) {
                     String partynumber = rs.getString("PARTY_NBR");
                     System.out.println("Party_Number--->" + partynumber);
               }

        } catch (Exception e) {
               e.printStackTrace();
        }
 }

	public static String customername() {

		String ucidvalue = null;
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("outlook.corp.emc.com", "koppar", "Letmein_99");
			Folder inbox = store.getFolder("Impbox");
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());

			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
			
			String content = bp.getContent().toString();
			System.out.println(content.charAt(262));
			System.out.println(content.charAt(282));
			System.out.println(content.charAt(283));
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

	
	public static String readline() throws IOException{
		FileReader file = new FileReader("C:\\Users\\koppar\\Desktop\\rohitmailcontent.txt");
		BufferedReader br = new BufferedReader(file);
		StringBuilder sb = new StringBuilder();
		try{
			
			String line = br.readLine();
			System.out.println(line);
			System.out.println(line.charAt(200));
			System.out.println(line.charAt(201));
			System.out.println(line.charAt(202));
			System.out.println(line.charAt(262));
			System.out.println(line.charAt(263));
			System.out.println(line.charAt(283));
			/*while(line!=null){
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
				System.out.println(line);
			}*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			br.close();
		}
		return sb.toString();
		
	}
		public static String partyNumber(){
			
			String partynum = null;
			Properties props = new Properties();
			props.setProperty("mail.store.protocol", "imaps");
			try {
				Session session = Session.getInstance(props, null);
				Store store = session.getStore();
				store.connect("outlook.corp.emc.com", "koppar", "Letmein_99");
				Folder inbox = store.getFolder("Impbox");
				inbox.open(Folder.READ_ONLY);
				Message msg = inbox.getMessage(inbox.getMessageCount());

				Multipart mp = (Multipart) msg.getContent();
				BodyPart bp = mp.getBodyPart(0);
				
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
						System.out.println("Party Number generated in the email is >> "+partynum);
					}
				}
			} catch (Exception mex) {
				mex.printStackTrace();
			}
			return partynum;
		}
		
		
		
		/*	String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
		System.out.println(date);
		String AutoCustID = "AUTO"+date;
		System.out.println(AutoCustID);
		 		
		testing.test();


		public static String URL = "https://ecp-tst.isus.emc.com/iMapPortal/";
		public static int time = 40;
		public String rndCustName = RandomCustomerName();


		public static void test(){

			try {
				ExcelToProperty.ExcelSheetToPropertyFileConvert();
				map = new ObjectMap(objmapPath);
				baseUrl = URL;
				openBrowser(browser);
				Thread.sleep(2000);
				reportPass("Launch is successful");
				driver.get(URL);
				driver.manage().window().maximize();

			} catch (Exception E) {
				reportFail("Launch has failed");


			}


			try {

				sleep(4);
				WaitForElementPresent("ele_header", 30);
				Assert.assertEquals(Verify_Presence_of_Object("ele_header").equals(true),true,"iMap Home page is dispayed");
			} catch (Exception e) {
				e.printStackTrace();
			}	
			try{

				WaitForElementPresent("txt_CustName", time);

				SendKeys("txt_CustName", rndCustName);
				Click("btn_Search");
				sleep(10);
				WaitForElementPresent("ele_NoRecords", time);

				try{
					WebElement ele_NoRecords = driver.findElement(By.xpath("//div[contains(text(),'No Search Results to Display')]/../div"));
					sleep(5);
					WaitForElementEnable("ele_NoRecords", time);
					if(ele_NoRecords.isDisplayed()){
						Assert.assertEquals(Verify_Presence_of_Object("ele_NoRecords").equals(true),true,"No Records found with the customer Name> "+rndCustName);
					}	
				}
				catch(Exception e){
					System.out.println("Some records are displayed with the Customer name");	
				}

			} catch (Exception e){
				e.printStackTrace();
			}



			try {
				sleep(10);
				//WaitForElementPresent("customerName", time);
				//SendKeys("customerName", rndCustName);
				WaitForElementPresent("txt_CustAddress", time);
				SendKeys("txt_CustAddress", "Address1");
				WaitForElementPresent("txt_City", time);
				SendKeys("txt_City", "Bangalore");
				WaitForElementPresent("drp_Country", time);
				sleep(4);
				SendKeys("drp_Country", "India");
				Actions builder=new Actions(driver);
				builder.sendKeys(Keys.ENTER).perform(); 
				Sleep(2);
				Assert.assertEquals(Verify_Presence_of_Object("drp_Country").equals(true),true,"All mandatory fields are entered");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}






		try {
			sleep(5);
			WaitForElementPresent("btn_CantFindAMatch", time);
			JSClick("btn_CantFindAMatch");
			WaitForElementPresent("lnk_Create", time);
			sleep(15);
			Click("lnk_Create");

			WaitForElementPresent("drp_requestPurpose", time);
			Assert.assertEquals(Verify_Presence_of_Object("drp_requestPurpose").equals(true),true,"Customer Create Request Popup has opened");
			SelectTextByVisibleText("drp_requestPurpose", "Billing");
			WaitForElementPresent("btn_SubmitRequest", time);
			Click("btn_SubmitRequest");



			Assert.assertEquals(Verify_Presence_of_Object("lnk_Search").equals(true),true,"Top Header - Search Link is present");
			JSClick("lnk_Search");
			WaitForElementPresent("txt_Search_CustomerName", time);
			Assert.assertEquals(Verify_Presence_of_Object("txt_Search_CustomerName").equals(true),true,"The Contract Search Form page is displayed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}

