@Action
Feature: Action Feature
@ActionHandling
Scenario: Action - Mouseover Functionality Testing
Given User Login to Spicejet Site 
When User mouse over to Add-ons tab
Then Pring all the subtab menu of Add-ons


@DragAndDrop
Scenario: Action - DragandDrop Functionality Testing
Given User Login to jqueryui Site 
When User perform DragandDrop 
Then user logout from the webpage 