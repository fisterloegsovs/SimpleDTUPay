package dtu.simplepay.service;

import dtu.simplepay.model.CustomerModel;
import dtu.simplepay.model.MerchantModel;
import dtu.simplepay.model.PaymentModel;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private List<PaymentModel> payments = new ArrayList<>();
    private final BankService bankService;

    public PaymentService(BankService bankService) {
        this.bankService = bankService;
    }

    public PaymentModel initiatePayment(CustomerModel customer, MerchantModel merchant, int amount, String description) throws BankServiceException_Exception {
        bankService.transferMoneyFromTo(customer.getAccountNumber(), merchant.getAccountNumber(), BigDecimal.valueOf(amount), description);
        PaymentModel payment = new PaymentModel(customer, merchant, amount);
        payments.add(payment);
        return payment;
    }

    public List<PaymentModel> listAllPayments() {
        return new ArrayList<>(payments);
    }
}
