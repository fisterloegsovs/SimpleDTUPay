package dtu.simplepay.controller;

import dtu.simplepay.model.CustomerModel;
import dtu.simplepay.model.MerchantModel;
import dtu.simplepay.service.CustomerService;
import dtu.simplepay.service.MerchantService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CustomerService customerService;
    private final MerchantService merchantService;

    public UserController(CustomerService customerService, MerchantService merchantService) {
        this.customerService = customerService;
        this.merchantService = merchantService;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerModel> registerCustomer(
            @RequestParam String name,
            @RequestParam String cprNumber,
            @RequestParam BigDecimal initialBalance) {
        try {
            CustomerModel customer = customerService.registerCustomer(name, cprNumber, initialBalance);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } catch (BankServiceException_Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/merchants")
    public ResponseEntity<MerchantModel> registerMerchant(
            @RequestParam String name,
            @RequestParam String cprNumber,
            @RequestParam BigDecimal initialBalance) {
        try {
            MerchantModel merchant = merchantService.registerMerchant(name, cprNumber, initialBalance);
            return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
        } catch (BankServiceException_Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> unregisterCustomer(@PathVariable String id) {
        try {
            customerService.unregisterCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (BankServiceException_Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/merchants/{id}")
    public ResponseEntity<Void> unregisterMerchant(@PathVariable String id) {
        try {
            merchantService.unregisterMerchant(id);
            return ResponseEntity.noContent().build();
        } catch (BankServiceException_Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
