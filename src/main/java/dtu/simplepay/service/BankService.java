package dtu.simplepay.service;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;

import java.math.BigDecimal;

public class BankService {
    private final dtu.ws.fastmoney.BankService bankService;

    public BankService() {
        BankServiceService service = new BankServiceService();
        this.bankService = service.getBankServicePort();
    }

    public String createAccountWithBalance(String name, String cprNumber, BigDecimal balance) throws BankServiceException_Exception {
        User user = new User();
        user.setFirstName(name);
        user.setCprNumber(cprNumber);
        user.setLastName("");
        return bankService.createAccountWithBalance(user, balance);
    }

    public Account getAccount(String accountId) throws BankServiceException_Exception {
        return bankService.getAccount(accountId);
    }

    public void retireAccount(String accountId) throws BankServiceException_Exception {
        bankService.retireAccount(accountId);
    }

    public void transferMoneyFromTo(String debtorId, String creditorId, BigDecimal amount, String description) throws BankServiceException_Exception {
        bankService.transferMoneyFromTo(debtorId, creditorId, amount, description);
    }
}
