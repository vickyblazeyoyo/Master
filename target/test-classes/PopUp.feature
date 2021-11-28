@Test
Feature: Popup Feature

Scenario: JavaScript Popup Functionality Testing
Given User Login to redifmail website 
When User Click on signin link
Then User Click on signin Btn without providing Username and password
And Validate Javascript Popup alert Message and click on OK btn