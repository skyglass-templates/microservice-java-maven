Feature: Search

  Background:
    * url AppUrl

  Scenario: Search OK
    * def search_request = read('request/search_request.json')
    * def search_response = read('response/search_response_success.json')
    Given path '/itineraries'
    And params search_request
    When method GET

    Then status 200
    Then match response == search_response