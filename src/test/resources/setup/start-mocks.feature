Feature: Start mocks once

  Scenario:
    * def bankMock = karate.start('classpath:mocks/bank-balance-mock.feature')
    * def BANK_BASE_URL = 'http://localhost:' + bankMock.port
    * configure headers = { 'Content-Type': 'application/json' }
    * print 'Bank mock started at:', BANK_BASE_URL
