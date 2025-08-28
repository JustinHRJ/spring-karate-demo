Feature: Customer CRUD

  Background:
    * url baseUrl
    * configure headers = { 'Content-Type': 'application/json' }

  Scenario: create / read / update / delete
    # CREATE
    Given path 'customers'
    And request { "name": "Alice", "email": "alice@example.com" }
    When method post
    Then status 201
    And match response contains { id: '#number', name: 'Alice' }
    * def id = response.id

    # READ
    Given path 'customers', id
    When method get
    Then status 200
    And match response.name == 'Alice'

    # UPDATE
    Given path 'customers', id
    And request { "name": "Alice A.", "email": "alice@example.com" }
    When method put
    Then status 200
    And match response.name == 'Alice A.'

    # DELETE
    Given path 'customers', id
    When method delete
    Then status 204

    # VERIFY NOT FOUND
    Given path 'customers', id
    When method get
    Then status 404
