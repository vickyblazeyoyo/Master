@SVG
Feature: SVG Feature
@SVGHandling
Scenario: Svg - Functionality Testing
Given User Login to Emicalculator  Site 
When User SwitchTo Bar graph and read the Text
Then User clicks on LogoutBtn

@SVGWebtableHandling
Scenario: Svg - Functionality Testing for Webpage
Given User Login to Emicalculator Site for Webtable Handling
When User read year based data
Then User read month based data