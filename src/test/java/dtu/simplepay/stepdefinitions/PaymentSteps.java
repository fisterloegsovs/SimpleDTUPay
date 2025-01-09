package dtu.simplepay.stepdefinitions;

import dtu.simplepay.model.PaymentModel;
import dtu.simplepay.service.PaymentService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertNotNull;

public class PaymentSteps {
    private PaymentService paymentService;
    private PaymentModel payment;

    @Given("a customer with ID {string} and a merchant with ID {string}")
    public void aCustomerWithIDAndAMerchantWithID(String customerId, String merchantId) {

    }

    @When("the customer pays the merchant an amount of {int} with description {string}")
    public void theCustomerPaysTheMerchantAnAmountOfWithDescription(int amount, String description) throws BankServiceException_Exception {

    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertNotNull(payment);
    }
}
