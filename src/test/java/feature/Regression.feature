@Regression
Feature: Rest Assured API Test
  Scenario Outline: Verify 200 Response for positive scenarios
    Given Test <testID> for "Regression : <Scenario>"
    Then The Client calls "CreateRecord" as well as "CreateRecord" and sends request with body
    And The Client validates response code as "responseCode"
    And The Client validates "expectedNameField" with value "expectedNameValue" in response body
   # And The Client validates "expectedSalaryField" with value "expectedSalaryValue" in response body
    #And The Client validates "expectedAgeField" with value "expectedAgeValue" in response body

  Examples:
    | testID | Reference
    | 1      | Verify 200 response for the service
   # | 2      | Verify 200
  #| 3      | Verify

