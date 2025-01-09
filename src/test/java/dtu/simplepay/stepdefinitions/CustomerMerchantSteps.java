package dtu.simplepay.stepdefinitions;

import dtu.simplepay.model.CustomerModel;
import dtu.simplepay.model.MerchantModel;
import dtu.simplepay.service.CustomerService;
import dtu.simplepay.service.MerchantService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.en.*;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

public class CustomerMerchantSteps {
    private final CustomerService customerService;
    private final MerchantService merchantService;
    private final TestContext context;


    public CustomerMerchantSteps() {
        dtu.ws.fastmoney.BankService bankService = new dtu.ws.fastmoney.BankServiceService().getBankServicePort();
        this.customerService = new CustomerService(bankService);
        this.merchantService = new MerchantService(bankService);
        this.context = new TestContext();
    }

    @Given("a customer with name {string} and CPR {string} with balance {int}")
    public void aCustomerWithNameAndCPRWithBalance(String name, String cpr, int balance) throws BankServiceException_Exception {
        CustomerModel customer = customerService.registerCustomer(name, cpr, BigDecimal.valueOf(balance));
        context.setCustomer(customer);
    }

    @Given("a merchant with name {string} and CPR {string} with balance {int}")
    public void aMerchantWithNameAndCPRWithBalance(String name, String cpr, int balance) throws BankServiceException_Exception {
        MerchantModel merchant = merchantService.registerMerchant(name, cpr, BigDecimal.valueOf(balance));
        context.setMerchant(merchant);
    }

    @When("the customer is registered")
    public void theCustomerIsRegistered() {
        assertNotNull(context.getCustomer());
    }

    @When("the merchant is registered")
    public void theMerchantIsRegistered() {
        assertNotNull(context.getMerchant());
    }

    @Given("a registered customer with ID {string}")
    public void aRegisteredCustomerWithID(String id) {
        CustomerModel customer = customerService.findCustomer(id);
        context.setCustomer(customer);
        assertNotNull(customer);
    }

    @Given("a registered merchant with ID {string}")
    public void aRegisteredMerchantWithID(String id) {
        MerchantModel merchant = merchantService.findMerchant(id);
        context.setMerchant(merchant);
        assertNotNull(merchant);
    }

    @When("the customer is unregistered")
    public void theCustomerIsUnregistered() throws BankServiceException_Exception {
        customerService.unregisterCustomer(context.getCustomer().getId());
    }

    @When("the merchant is unregistered")
    public void theMerchantIsUnregistered() throws BankServiceException_Exception {
        merchantService.unregisterMerchant(context.getMerchant().getId());
    }

    @Then("the customer is added to the system")
    public void theCustomerIsAddedToTheSystem() {
        assertNotNull(context.getCustomer());
    }

    @Then("the merchant is added to the system")
    public void theMerchantIsAddedToTheSystem() {
        assertNotNull(context.getMerchant());
    }
}
