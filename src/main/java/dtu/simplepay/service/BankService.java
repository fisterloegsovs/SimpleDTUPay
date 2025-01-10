package dtu.simplepay.service;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private final dtu.ws.fastmoney.BankService bankService;

    public BankService() {
        BankServiceService service = new BankServiceService();
        this.bankService = service.getBankServicePort();
    }

    public BankService(dtu.ws.fastmoney.BankService bankService) {
        this.bankService = bankService;
    }

    public String createAccountWithBalance(String name, String cprNumber, BigDecimal balance) throws BankServiceException_Exception {
        try {
            User user = new User();
            user.setFirstName(name);
            user.setCprNumber(cprNumber);
            user.setLastName("");
            return bankService.createAccountWithBalance(user, balance);
        } catch (BankServiceException_Exception e) {
            logger.error("Error creating account with balance", e);
            throw e;
        }
    }

    public Account getAccount(String accountId) throws BankServiceException_Exception {
        try {
            return bankService.getAccount(accountId);
        } catch (BankServiceException_Exception e) {
            logger.error("Error getting account", e);
            throw e;
        }
    }

    public void retireAccount(String accountId) throws BankServiceException_Exception {
        try {
            bankService.retireAccount(accountId);
        } catch (BankServiceException_Exception e) {
            logger.error("Error retiring account", e);
            throw e;
        }
    }

    public void transferMoneyFromTo(String debtorId, String creditorId, BigDecimal amount, String description) throws BankServiceException_Exception {
        try {
            bankService.transferMoneyFromTo(debtorId, creditorId, amount, description);
        } catch (BankServiceException_Exception e) {
            logger.error("Error transferring money", e);
            throw e;
        }
    }
}