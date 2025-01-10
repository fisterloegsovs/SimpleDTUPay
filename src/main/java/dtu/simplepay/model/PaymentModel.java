package dtu.simplepay.model;

public class PaymentModel {
    private CustomerModel customer;
    private MerchantModel merchant;
    private int amount;

    public PaymentModel() {
        // No-argument constructor for frameworks
    }

    public PaymentModel(CustomerModel customer, MerchantModel merchant, int amount) {
        this.customer = customer;
        this.merchant = merchant;
        this.amount = amount;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
    }

    public MerchantModel getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantModel merchant) {
        if (merchant == null) {
            throw new IllegalArgumentException("Merchant cannot be null");
        }
        this.merchant = merchant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        this.amount = amount;
    }
}