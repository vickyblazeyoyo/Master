
@TestingApi
Feature: Title of your feature
  I want to use this template for my feature file

@ApiTesting
Scenario: IBS Cards API - Generate a Cardbase System account number
#Given connecting to API server and generate authBearer code (Direct API Test)
#OR
#Given Login to Code Connect site (UI Test)
When I navigate to  API Group page
When I navigate to API page
And I  using the "post" method
Then "post" method should be successful and generates "200" response code
