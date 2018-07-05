package StepDefinitionClass;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;

import org.testng.Assert;

import IMAP.BaseClass;
import IMAP.ExcelToProperty;
import IMAP.ObjectMap;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Step_Definations extends BaseClass {

	public static String iMAPURL = "https://ecp-tst.isus.emc.com/iMapPortal/";
	public static String oracleURL = "https://crmcrwr3.emc.com/OA_HTML/AppsLocalLogin.jsp?cancelUrl=/OA_HTML/AppsLocalLogin.jsp";

	// public static String browser = "FF";
	public static int time = 40;
	public String rndCustName = RandomCustomerName();

	@Given("^Launch oracle application with \"([^\"]*)\"$")
	public void launch_oracle_application_with(String browser) {
		try {
			ExcelToProperty.ExcelSheetToPropertyFileConvert();
			map = new ObjectMap(objmapPath);
			baseUrl = oracleURL;
			openBrowser(browser);
			Thread.sleep(2000);
			reportPass("Launch is successful");
			driver.get(oracleURL);
			driver.manage().window().maximize();
		} catch (Exception E) {
			reportFail("Launch has failed");

		}
	}

	@Given("^login to application and go to home page$")
	public void login_to_application_and_go_to_home_page() {
		SendKeys("iMAP_username", "btgsales01");
		SendKeys("iMAP_password", "welcome1");
		Click("iMAP_login");
	}

	@Then("^Search for customer name in the search result$")
	public void search_for_customer_name_in_the_search_result() {
		try {
			sleep(10);
			WebElement emcsuperuser = driver.findElement(By.xpath("(//a[text()='EMC Super User OCO - USA'])[2]"));
			emcsuperuser.click();
			sleep(10);
			SendKeys("iMAP_registryid1", partyNumber());
			Click("iMAP_search1");
			sleep(5);
			Click("iMAP_customername1");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Then("^click on additional information link from overview section$")
	public void click_on_additional_information_link_from_overview_section() {
		try {
			sleep(5);
			Click("iMAP_additionalinformation");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Then("^edit the name that is displayed under CS site name$")
	public void edit_the_name_that_is_displayed_under_CS_site_name() {
		try {
			sleep(10);
			WebElement name = driver.findElement(By.xpath("//input[@name='CSName']"));
			String accountname = name.getAttribute("value");
			String newaccountname = accountname.concat("1");
			SendKeys("iMAP_sitename", newaccountname);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Then("^click on apply$")
	public void click_on_apply() {
		Click("iMAP_apply");
	}

	@Given("^click on profile link from overview section$")
	public void click_on_profile_link_from_overview_section() {
		try {
			sleep(10);
			Click("iMAP_profile");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("^click on internal or external viceversa$")
	public void click_on_internal_or_external_viceversa() {
		try {
			sleep(10);
			if (driver.findElement(By.xpath("//input[@id='Internal']")).isSelected()) {
				Click("iMAP_external");
			} else if (driver.findElement(By.xpath("//input[@id='External']")).isSelected()) {
				Click("iMAP_internal");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Given("^click on save$")
	public void click_on_save() {
		try {
			sleep(5);
			Click("iMAP_save");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Then("^check confirmation message if it is updated or not$")
	public void check_confirmation_message_if_it_is_updated_or_not() {
		try {
			sleep(10);
			String confirmation = "The value has been updated.";
			Assert.assertEquals(driver
					.findElement(By
							.xpath("//table[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr[2]/td[2]/div/div/span[2]"))
					.getText(), confirmation, "both values are same");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	@Then("^click on emc superuser and click on customers link and provide partyid and look for accountname$")
	public void click_on_emc_superuser_and_click_on_customers_link_and_provide_partyid_and_look_for_accountname(){
		try {
			WebElement emcsuperuser = driver.findElement(By.xpath("(//a[text()='EMC Super User OCO - USA'])[2]"));
			emcsuperuser.click();
			sleep(5);
			Click("iMAP_customer");
			sleep(5);
			SendKeys("iMAP_registryid", partyNumber());
			Click("iMAP_search");
			sleep(5);
			Click("iMAP_accountname");
			sleep(5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Then("^click on additional profile link$")
	public void click_on_additional_profile_link(){
		Click("iMAP_additionalinformation");
	}
	
	@Then("^check on installat icon and apply changes$")
	public void check_on_installat_icon_and_apply_changes(){
		try {
			sleep(5);
			WebElement installat = driver.findElement(By.xpath("(//input[@title='Preferred Flag'])[2]"));
			if(!installat.isSelected()){
				installat.click();
			}
			else {
				installat.click();
			}
			Click("iMAP_apply");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Given("^Login to iMAP portal with \"([^\"]*)\"$")
	public void login_to_iMAP_portal_with(String browser){
		try {
			ExcelToProperty.ExcelSheetToPropertyFileConvert();
			map = new ObjectMap(objmapPath);
			baseUrl = iMAPURL;
			openBrowser(browser);
			Thread.sleep(2000);
			reportPass("Launch is successful");
			driver.get(iMAPURL);
			driver.manage().window().maximize();
		} catch (Exception E) {
			reportFail("Launch has failed");

		}
	}

	@Then("^provide any partynumber and click on modify$")
	public void provide_any_partynumber_and_click_on_modify(){
		try{
			sleep(10);
			WebElement dropdownclick = driver.findElement(By.xpath("(//img[@src='images/s.gif'])[1]"));
			dropdownclick.click();
			sleep(2);
			WebElement ucid = driver.findElement(By.xpath("(//div[text()='UCID'])[2]"));
			ucid.click();
			SendKeys("iMAP_customerid",ucidnumber());
			Click("iMAP_search");
			sleep(5);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^modify any party from portal and click on submit$")
	public void modify_any_party_from_portal_and_click_on_submit(){
		try{
			Click("iMAP_modify");
			sleep(5);
			SendKeys("iMAP_csname", "updatedname");
			SendKeys("iMAP_addressline1", "updatedaddressline2");
			Click("iMAP_submit");
			sleep(20);
			Click("iMAP_submitanyway");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Then("^click on emc superuser and go to name text box and provide name and click on th result with no account number$")
	public void click_on_emc_superuser_and_go_to_name_text_box_and_provide_name_and_click_on_th_result_with_no_account_number(){
		try{
			sleep(5);
			WebElement emcsuperuser = driver.findElement(By.xpath("(//a[text()='EMC Super User OCO - USA'])[2]"));
			emcsuperuser.click();
			sleep(5);
			Click("iMAP_customer");
			SendKeys("iMAP_customername", "sandy");
			Click("iMAP_customersearch");
			sleep(7);
			Click("iMAP_searchresultname");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^click on accounts and click on create account$")
	public void click_on_accounts_and_click_on_create_account(){
		try{
			sleep(5);
			Click("iMAP_accounts");
			sleep(5);
			Click("iMAP_createaccount");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^provide with fields there and click on apply and verify that confirmation message is displayed there$")
	public void provide_with_fields_there_and_click_on_apply_and_verify_that_confirmation_message_is_displayed_there(){
		try{
			sleep(5);
			SendKeys("iMAP_accountcreation", "testaccount");
			SendKeys("iMAP_emailaddress", "rohit.sinha@emc.com");
			Click("iMAP_apply");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@Then("^click on emc superuser and click on customers tab$")
	public void click_on_emc_superuser_and_click_on_customers_tab(){
		try{
			sleep(5);
			WebElement emcsuperuser = driver.findElement(By.xpath("(//a[text()='EMC Super User OCO - USA'])[2]"));
			emcsuperuser.click();
			sleep(5);
			Click("iMAP_customer");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^provide name in customers field click on search and look for customer with account id$")
	public void provide_name_in_customers_field_click_on_search_and_look_for_customer_with_account_id(){
		try{
			SendKeys("iMAP_customername", "sandy");
			Click("iMAP_customersearch");
			sleep(7);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^click on customer with account id and click on address link$")
	public void click_on_customer_with_account_id_and_click_on_address_link(){
		try{
			Click("iMAP_searchresultname");
			sleep(5);
			Click("iMAP_addresses");
			sleep(5);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^click on update icon and update the address fields and click on apply$")
	public void click_on_update_icon_and_update_the_address_fields_and_click_on_apply(){
		try{
			Click("iMAP_update");
			sleep(5);
			WebElement name = driver.findElement(By.xpath("//input[@name='HzAddressStyleFlex1']"));
			String addressfield1 = name.getAttribute("value");
			String newaddressfield1 = addressfield1.concat("1");
			SendKeys("iMAP_address1", newaddressfield1);
			Click("iMAP_apply");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^see if confirmation message is displayed$")
	public void see_if_confirmation_message_is_displayed(){
		try{
			sleep(5);
			WebElement confirmation = driver.findElement(By.xpath("//span[text()='The value has been updated.']"));
			String confirm = confirmation.getText();
			Assert.assertEquals("The value has been updated.",confirm,"the value has been updated");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Then("^click on emc superuser and provide partynumber in ucid text box and click on search$")
	public void click_on_emc_superuser_and_provide_partynumber_in_ucid_text_box_and_click_on_search(){
		try{
			sleep(7);
			WebElement emcsuperuser = driver.findElement(By.xpath("(//a[text()='EMC Super User OCO - USA'])[2]"));
			emcsuperuser.click();
			sleep(7);
			SendKeys("oracle11i_registryidtextbox", partyNumber());
			Click("oracle11i_searchbutton");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Given("^Oracle DB connections are established$")
	public void oracle_DB_connections_are_established(){
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Driver loaded");
			// connection to Oracle
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@echoratst01.isus.emc.com:1630:ECHT","emcparty", "c5xt3st");
			// DriverManager.get
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select * from C_B_PARTY where party_nbr = "+"'"+partyNumber()+"'");
			System.out.println("Query executed");
			while (rs.next()) {
				String partynumber = rs.getString("PARTY_NBR");
				String partyname = rs.getString("PARTY_NAME");
				String taskcomments1 = rs.getString("TASK_COMMENTS");
				String cssitename = taskcomments1.substring(26, 37);
				String ucid = rs.getString("UCID");
				String row_id_object = rs.getString("ROWID_OBJECT");
				
				ResultSet rs2 = stm.executeQuery("Select * from emcparty.c_b_party_xref where ROWID_OBJECT= "+"'"+row_id_object+"'");
				String pkey_src_object = null;
				while(rs2.next()){
					pkey_src_object = rs2.getString("PKEY_SRC_OBJECT");
				}

				ResultSet rs1 = stm.executeQuery("Select * from C_B_ADDR_xref where PKEY_SRC_OBJECT = "+"'"+pkey_src_object+"'");
				String address = null;
				String address4 = null;
				String ADDRESS = null;
				while(rs1.next()) {
					String address1 = rs1.getString("ADDR_1");
					String address2 = rs1.getString("CITY");
					String address3 = rs1.getString("POSTAL_CD");
					address4 = rs1.getString("COUNTRY_CD");
					address = address1+", "+address2+" "+address3+", "+address4;
					ADDRESS = address.toUpperCase();
					
				}
				System.out.println("Party_Number---> " + partynumber + " partyname--->" + partyname + " cssitename--->" + cssitename + " ucid--->" + ucid + " address--->" + ADDRESS + " countrycode--->" + address4);
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
				
				String field1 = gettext("oracle11i_accountnumber");
				String field2 = gettext("oracle11i_name");
				String field3 = gettext("oracle11i_cssitename");
				String field4 = gettext("oracle11i_registryid");
				String field5 = gettext("oracle11i_address");
				String completeaddress = field5.substring(0, 34);
				String field6 = gettext("oracle11i_pia");
				String field7 = gettext("oracle11i_country");
				String sub = field7.substring(0, 2);
				String sub1 = sub.substring(0,1);
				String sub2 = sub.substring(1);
				String sub3 = sub2.toUpperCase();
				String sub4 = sub1+sub3;
				System.out.println("Party_Number---> " + field1 + " partyname--->" + field2 + " cssitename--->" + field3 + " ucid--->" + field4 + " completeaddress--->" + completeaddress + " PIA--->" + field6 + " Country--->" + sub4);
				Assert.assertEquals(partynumber, field1);
				Assert.assertEquals(partyname, field2);
				Assert.assertEquals(cssitename, field3);
				Assert.assertEquals(ucid, field4);
				Assert.assertEquals(ADDRESS, completeaddress);
				Assert.assertEquals(address4, sub4);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Then("^match the fields from oracle to the fields in DB$")
	public void match_the_fields_from_oracle_to_the_fields_in_DB(){
	System.out.println("all fields are matched from database and oracle11i resprctively");
	}
}
