Feature: Payment Processing
  As a user
  I want to process payments
  So that customers can pay merchants

  Scenario: Successful payment
    Given a customer with ID "123" and a merchant with ID "456"
    When the customer pays the merchant an amount of 100 with description "Purchase"
    Then the payment is successful
    And the payment is added to the system

  Scenario: Payment with insufficient funds
    Given a customer with ID "123" and a merchant with ID "456"
    And the customer has a balance of 50
    When the customer pays the merchant an amount of 100 with description "Purchase"
    Then the payment fails due to insufficient funds

  Scenario: Payment with invalid customer ID
    Given an invalid customer ID "999" and a valid merchant ID "456"
    When the customer pays the merchant an amount of 100 with description "Purchase"
    Then the payment fails due to invalid customer ID

  Scenario: Payment with invalid merchant ID
    Given a valid customer ID "123" and an invalid merchant ID "999"
    When the customer pays the merchant an amount of 100 with description "Purchase"
    Then the payment fails due to invalid merchant ID
