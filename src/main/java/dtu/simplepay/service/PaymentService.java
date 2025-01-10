package dtu.simplepay.service;

import dtu.simplepay.model.CustomerModel;
import dtu.simplepay.model.MerchantModel;
import dtu.simplepay.model.PaymentModel;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final List<PaymentModel> payments = new CopyOnWriteArrayList<>();
    private final BankService bankService;

    public PaymentService(BankService bankService) {
        this.bankService = bankService;
    }

    public PaymentModel initiatePayment(CustomerModel customer, MerchantModel merchant, int amount, String description) throws BankServiceException_Exception {
        if (customer == null || merchant == null || amount <= 0 || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        try {
            bankService.transferMoneyFromTo(customer.getAccountNumber(), merchant.getAccountNumber(), BigDecimal.valueOf(amount), description);
            PaymentModel payment = new PaymentModel(customer, merchant, amount);
            payments.add(payment);
            return payment;
        } catch (BankServiceException_Exception e) {
            logger.error("Error initiating payment", e);
            throw e;
        }
    }

    public List<PaymentModel> listAllPayments() {
        return new CopyOnWriteArrayList<>(payments);
    }
}