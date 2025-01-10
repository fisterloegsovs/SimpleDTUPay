package dtu.simplepay.service;

import dtu.simplepay.model.CustomerModel;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final Map<String, CustomerModel> customers = new ConcurrentHashMap<>();
    private final BankService bankService;

    public CustomerService(BankService bankService) {
        this.bankService = bankService;
    }

    public CustomerModel registerCustomer(String name, String cprNumber, BigDecimal initialBalance) throws BankServiceException_Exception {
        if (name == null || name.isEmpty() || cprNumber == null || cprNumber.isEmpty() || initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        String id = UUID.randomUUID().toString();

        User user = new User();
        user.setFirstName(name);
        user.setCprNumber(cprNumber);
        user.setLastName("Customer");

        String accountNumber = bankService.createAccountWithBalance(user, initialBalance);

        CustomerModel customer = new CustomerModel(id, name, cprNumber, accountNumber);
        customers.put(id, customer);
        return customer;
    }

    public void unregisterCustomer(String id) throws BankServiceException_Exception {
        CustomerModel customer = customers.remove(id);
        if (customer != null) {
            bankService.retireAccount(customer.getAccountNumber());
        } else {
            logger.warn("Attempted to unregister non-existent customer with id: {}", id);
        }
    }

    public CustomerModel findCustomer(String id) {
        return customers.get(id);
    }
}