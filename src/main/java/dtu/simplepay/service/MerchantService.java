package dtu.simplepay.service;

import dtu.simplepay.model.MerchantModel;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MerchantService {
    private static final Logger logger = LoggerFactory.getLogger(MerchantService.class);
    private final Map<String, MerchantModel> merchants = new ConcurrentHashMap<>();
    private final BankService bankService;

    public MerchantService(BankService bankService) {
        this.bankService = bankService;
    }

    public MerchantModel registerMerchant(String name, String cprNumber, BigDecimal initialBalance) throws BankServiceException_Exception {
        if (name == null || name.isEmpty() || cprNumber == null || cprNumber.isEmpty() || initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        String id = UUID.randomUUID().toString();

        User user = new User();
        user.setFirstName(name);
        user.setCprNumber(cprNumber);
        user.setLastName("Merchant");

        String accountNumber = bankService.createAccountWithBalance(user, initialBalance);

        MerchantModel merchant = new MerchantModel(id, name, cprNumber, accountNumber);
        merchants.put(id, merchant);
        return merchant;
    }

    public void unregisterMerchant(String id) throws BankServiceException_Exception {
        MerchantModel merchant = merchants.remove(id);
        if (merchant != null) {
            bankService.retireAccount(merchant.getAccountNumber());
        } else {
            logger.warn("Attempted to unregister non-existent merchant with id: {}", id);
        }
    }

    public MerchantModel findMerchant(String id) {
        return merchants.get(id);
    }
}