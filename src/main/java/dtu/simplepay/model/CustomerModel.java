package dtu.simplepay.model;

public class CustomerModel {
    private String id;
    private String name;
    private String cprNumber;
    private String accountNumber;

    public CustomerModel() {
        // No-argument constructor for frameworks
    }

    public CustomerModel(String id, String name, String cprNumber, String accountNumber) {
        this.id = id;
        this.name = name;
        this.cprNumber = cprNumber;
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        if (cprNumber == null || cprNumber.isEmpty()) {
            throw new IllegalArgumentException("CPR number cannot be null or empty");
        }
        this.cprNumber = cprNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        this.accountNumber = accountNumber;
    }
}