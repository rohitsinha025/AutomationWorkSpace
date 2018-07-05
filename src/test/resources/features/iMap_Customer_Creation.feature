
@High @CH
Feature: Creating Customer via iMap customer portal

 
  Scenario: Create Customer ID in iMap portal
    Given Launch iMap application with "CH"
    And Home page is displayed
    Then Search for customer existance
    And Pass the data to mandatory fields
    And Customer ID created successully
    And Close the application

  Scenario: Verify Email for Customer ID Creation
    Given Email is triggered to user mailbox
    Then Party Number should be read
    And UCID should be read
    And Customer Name should be read

  Scenario: Verify Oracle 11i updated with Customer details
    Given Launch oracle application with "CH"
    And login to oracle with "btgsales01" and "welcome1"
    Then click on EMC SuperUserOCO-USA and pass the partynumber to registryid box
    And verify for presence of record
    And Close the application

  Scenario: search for Customer details in Sales Force
    Given Launch salesforce application with "CH"
    Then Go to search tab and enter party number in the text box
    Then look for the record in the salesforce application
    And Close the application

  Scenario: search for Customer in Informatica
    Given Launch informatica application with "CH"
    Then login to informatica with "koppar" and "Letmein_99"
    And click on data tab and double click on ucid tab to search for ucid
    Then enter ucid value and click on run search
    And match for party name once search result appears
    And Close the application

  Scenario: Read Oracle DB for customer details updates
    Given Oracle DB connections are established
    Then Customer details are updated successfully in DB
