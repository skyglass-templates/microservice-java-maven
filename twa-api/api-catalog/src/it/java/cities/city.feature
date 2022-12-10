Feature: Catalog

  Background:
    * url AppUrl

  Scenario: City OK
    * def catalog_response = read('response/catalog_response_success.json')
    Given path '/city/BUE'
    When method GET

    Then status 200
    Then match response == catalog_response