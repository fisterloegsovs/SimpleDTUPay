package dtu.simplepay.stepdefinitions;

import dtu.simplepay.model.CustomerModel;
import dtu.simplepay.model.MerchantModel;

public class TestContext {
    private CustomerModel customer;
    private MerchantModel merchant;

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public MerchantModel getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantModel merchant) {
        this.merchant = merchant;
    }
}
