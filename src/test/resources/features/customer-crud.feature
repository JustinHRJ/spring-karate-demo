Feature: Customer CRUD

  Background:
    * callonce read('classpath:setup/start-mocks.feature')
    * def bankBase = BANK_BASE_URL
    * url baseUrl
    * configure headers = { 'Content-Type': 'application/json' }

  Scenario: create / read / update / delete + balance lookup
    # CREATE
    Given path 'api', 'customers'
    And request { name: 'Alice', email: 'alice@example.com' }
    When method post
    Then status 201
    And match response contains { id: '#number', name: 'Alice' }
    And match response !contains { bankBalanceDto: '#present' }
    * def id = response.id

    # READ
    Given path 'api', 'customers', id
    When method get
    Then status 200
    And match response ==
      """
      { id: '#number', name: 'Alice' }
      """

    # UPDATE
    Given path 'api', 'customers', id
    And request { name: 'Alice A.', email: 'alice@example.com' }
    When method put
    Then status 200
    And match response ==
      """
      { id: '#number', name: 'Alice A.' }
      """

    # BALANCE (calls downstream bank service mock)
    Given path 'api', 'customers', id, 'balance'
    When method get
    Then status 200
    And match response ==
      """
      {
        id: '#number',
        name: '#string',
        bankBalanceDto: {
          customerId: '#string',
          balance: '#number',
          currency: '#string',
          asOf: '#string'
        }
      }
      """
    # Optional stricter checks:
    And match response.bankBalanceDto.customerId == id + ''
    And match response.bankBalanceDto.currency == 'SGD'

    # DELETE
    Given path 'api', 'customers', id
    When method delete
    Then status 204

    # VERIFY NOT FOUND
    Given path 'api', 'customers', id
    When method get
    Then status 404
