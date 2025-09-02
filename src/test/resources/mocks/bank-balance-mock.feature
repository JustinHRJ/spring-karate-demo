Feature: Bank Service mock (returns balances)

  Background:
    # All responses JSON by default
    * configure responseHeaders = { 'Content-Type': 'application/json' }

  # GET /bank/balances/{customerId}
  Scenario: pathMatches('/bank/balances/{customerId}') && methodIs('get')
    * def id = pathParams.customerId
    # simple routing logic for demo purposes
    * def balance = id == '1' ? 1234.56 : id == '2' ? 9876.54 : 250.00

    * def response =
      """
      {
        "customerId": "#(id)",
        "balance": "#(balance)",
        "currency": "SGD",
        "asOf": "#(new Date().toISOString())"
      }
      """
    * def status = 200

  # Alternate: GET /bank/balance?customerId=123
  Scenario: pathMatches('/bank/balance') && methodIs('get') && paramExists('customerId')
    * def id = requestParams.customerId[0]
    * def response = { "customerId": "#(id)", "balance": 555.00, "currency": "SGD", "asOf": "#(new Date().toISOString())" }
    * def status = 200

  # Example error: customer not found
  Scenario: pathMatches('/bank/balances/{customerId}') && methodIs('get') && pathParams.customerId == '404'
    * def response = { "error": "Customer not found" }
    * def status = 404

  # Example: simulate slowness / timeout testing
  Scenario: pathMatches('/bank/balances/{customerId}') && methodIs('get') && pathParams.customerId == 'slow'
    * karate.delay(1500)
    * def response = { "customerId": "slow", "balance": 10.00, "currency": "SGD", "asOf": "#(new Date().toISOString())" }
    * def status = 200
