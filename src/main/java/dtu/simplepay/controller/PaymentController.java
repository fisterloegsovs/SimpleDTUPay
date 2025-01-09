package dtu.simplepay.controller;

import dtu.simplepay.model.PaymentModel;
import dtu.simplepay.service.CustomerService;
import dtu.simplepay.service.MerchantService;
import dtu.simplepay.service.PaymentService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final CustomerService customerService;
    private final MerchantService merchantService;

    public PaymentController(PaymentService paymentService, CustomerService customerService, MerchantService merchantService) {
        this.paymentService = paymentService;
        this.customerService = customerService;
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<PaymentModel> initiatePayment(
            @RequestParam String customerId,
            @RequestParam String merchantId,
            @RequestParam int amount,
            @RequestParam String description) {
        try {
            var customer = customerService.findCustomer(customerId);
            var merchant = merchantService.findMerchant(merchantId);

            if (customer == null || merchant == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            PaymentModel payment = paymentService.initiatePayment(customer, merchant, amount, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (BankServiceException_Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<?> listPayments() {
        return ResponseEntity.ok(paymentService.listAllPayments());
    }
}
