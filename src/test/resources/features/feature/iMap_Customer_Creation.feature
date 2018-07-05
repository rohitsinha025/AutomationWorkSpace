@High @CH
Feature: Creating Customer via iMap customer portal

  Scenario: change party in 11i and confirm it is pulled in next incremental
   Given Launch oracle application with "CH"
   And login to application and go to home page
   Then Search for customer name in the search result
   And click on additional information link from overview section
   And edit the name that is displayed under CS site name
   And click on apply
   
  # Scenario: change customer from internal to external and viceversa confirm it is pulled in next incremental
  #  Given Launch oracle application with "CH"
  #  And login to application and go to home page
  #  Then Search for customer name in the search result
  #  And click on profile link from overview section
  #  And click on internal or external viceversa
  #  And click on save
  #  Then check confirmation message if it is updated or not
  
  #Scenario: change preferred install-at flag
  #  Given Launch oracle application with "CH"
  #  And login to application and go to home page
  #  Then click on emc superuser and click on customers link and provide partyid and look for accountname
  #  And click on additional profile link
  #  Then check on installat icon and apply changes
  
  #Scenario: Change party in hub and confirm it is pushed in next publish
  #  Given Login to iMAP portal with "CH"
  #  Then provide any partynumber and click on modify
  #  And modify any party from portal and click on submit
  
  #Scenario: Change party in 11i and confirm it is pulled in next incremental and update CS Name, Add or change account, change internal flag, change preferred install-at flag
  #  Given Launch oracle application with "CH"
  #  And login to application and go to home page
  #  Then click on emc superuser and go to name text box and provide name and click on th result with no account number
  #  Then click on accounts and click on create account
  #  And provide with fields there and click on apply and verify that confirmation message is displayed there
  
  #Scenario: Update address field for party
  #  Given Launch oracle application with "CH"
  #  And login to application and go to home page
  #  Then click on emc superuser and click on customers tab
  #  And provide name in customers field click on search and look for customer with account id
  #  Then click on customer with account id and click on address link
  #  And click on update icon and update the address fields and click on apply
  #  Then see if confirmation message is displayed
  
  #Scenario: Read Oracle DB for customer details updates
  #  Given Launch oracle application with "CH"
  #  And login to application and go to home page
  #  Then click on emc superuser and provide partynumber in ucid text box and click on search
  #  Given Oracle DB connections are established
  #  Then match the fields from oracle to the fields in DB
