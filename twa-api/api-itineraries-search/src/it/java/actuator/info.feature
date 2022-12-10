Feature: Info

  Background:
    * url AppUrl
    
   Scenario: Info OK
     * def info_response = read('response/info_response_success.json')
     Given path '/info'
     When method GET

     Then status 200
     Then match response == info_response