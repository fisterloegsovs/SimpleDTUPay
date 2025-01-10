package dtu.simplepay.controller;

import dtu.simplepay.model.CustomerModel;
import dtu.simplepay.model.MerchantModel;
import dtu.simplepay.service.CustomerService;
import dtu.simplepay.service.MerchantService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final CustomerService customerService;
    private final MerchantService merchantService;

    public UserController(CustomerService customerService, MerchantService merchantService) {
        this.customerService = customerService;
        this.merchantService = merchantService;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> registerCustomer(
            @RequestParam String name,
            @RequestParam String cprNumber,
            @RequestParam BigDecimal initialBalance) {
        if (name == null || name.isEmpty() || cprNumber == null || cprNumber.isEmpty() || initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }

        try {
            CustomerModel customer = customerService.registerCustomer(name, cprNumber, initialBalance);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } catch (BankServiceException_Exception e) {
            logger.error("Bank service exception occurred while registering customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        } catch (Exception e) {
            logger.error("Unexpected error occurred while registering customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @PostMapping("/merchants")
    public ResponseEntity<?> registerMerchant(
            @RequestParam String name,
            @RequestParam String cprNumber,
            @RequestParam BigDecimal initialBalance) {
        if (name == null || name.isEmpty() || cprNumber == null || cprNumber.isEmpty() || initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }

        try {
            MerchantModel merchant = merchantService.registerMerchant(name, cprNumber, initialBalance);
            return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
        } catch (BankServiceException_Exception e) {
            logger.error("Bank service exception occurred while registering merchant", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        } catch (Exception e) {
            logger.error("Unexpected error occurred while registering merchant", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> unregisterCustomer(@PathVariable String id) {
        try {
            customerService.unregisterCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (BankServiceException_Exception e) {
            logger.error("Bank service exception occurred while unregistering customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        } catch (Exception e) {
            logger.error("Unexpected error occurred while unregistering customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @DeleteMapping("/merchants/{id}")
    public ResponseEntity<?> unregisterMerchant(@PathVariable String id) {
        try {
            merchantService.unregisterMerchant(id);
            return ResponseEntity.noContent().build();
        } catch (BankServiceException_Exception e) {
            logger.error("Bank service exception occurred while unregistering merchant", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        } catch (Exception e) {
            logger.error("Unexpected error occurred while unregistering merchant", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }
}