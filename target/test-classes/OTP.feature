@Otp
Feature: Otp Feature
Scenario: Otp - Functionality Testing
Given User Login to amzon  Site 
When User Switchover signin dropdown and click on  starthere Link
And Enter yourname,mobileNumber,password,passwordAgain Click on continew
And Enter Otp and Click on ResendOtp verify success Message
Then Logout from the website

