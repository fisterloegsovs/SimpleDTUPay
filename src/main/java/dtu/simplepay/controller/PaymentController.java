package dtu.simplepay.controller;

import dtu.simplepay.model.PaymentModel;
import dtu.simplepay.service.CustomerService;
import dtu.simplepay.service.MerchantService;
import dtu.simplepay.service.PaymentService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;
    private final CustomerService customerService;
    private final MerchantService merchantService;

    public PaymentController(PaymentService paymentService, CustomerService customerService, MerchantService merchantService) {
        this.paymentService = paymentService;
        this.customerService = customerService;
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<?> initiatePayment(
            @RequestParam String customerId,
            @RequestParam String merchantId,
            @RequestParam int amount,
            @RequestParam String description) {
        if (customerId == null || customerId.isEmpty() || merchantId == null || merchantId.isEmpty() || amount <= 0 || description == null || description.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }

        try {
            var customer = customerService.findCustomer(customerId);
            var merchant = merchantService.findMerchant(merchantId);

            if (customer == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found");
            }

            if (merchant == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant not found");
            }

            PaymentModel payment = paymentService.initiatePayment(customer, merchant, amount, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (BankServiceException_Exception e) {
            logger.error("Bank service exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> listPayments() {
        try {
            return ResponseEntity.ok(paymentService.listAllPayments());
        } catch (Exception e) {
            logger.error("Error listing payments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error listing payments");
        }
    }
}