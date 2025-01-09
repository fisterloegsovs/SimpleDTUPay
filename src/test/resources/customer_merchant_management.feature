Feature: Customer and Merchant Management
  As a user
  I want to manage customers and merchants
  So that they can participate in the payment system

  Scenario: Successfully register a customer
    Given a customer with name "Alice" and CPR "123456-8191" with balance 1000
    When the customer is registered
    Then the customer is added to the system

  Scenario: Successfully register a merchant
    Given a merchant with name "Bob" and CPR "987654-7911" with balance 500
    When the merchant is registered
    Then the merchant is added to the system

  Scenario: Successfully unregister a customer
    Given a registered customer with ID "123"
    When the customer is unregistered
    Then the customer is removed from the system

  Scenario: Successfully unregister a merchant
    Given a registered merchant with ID "456"
    When the merchant is unregistered
    Then the merchant is removed from the system
