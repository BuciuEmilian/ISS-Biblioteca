package iss.library.libraryiss1.model;

public class Subscriber extends Registered {
    private long CNP;
    private String phoneNumber;
    private Address address;

    public Subscriber() {
        super("", "");
    }

    public Subscriber(String username, String password) {
        super(username, password);
    }

    public Subscriber(String name, long CNP, String phoneNumber) {
        super(name, String.join(".", name.split(" ")), "1");
        this.CNP = CNP;
        this.phoneNumber = phoneNumber;
    }

    public Subscriber(String name, String username, String password) {
        super(name, username, password);
    }

    public Subscriber(String name, String username, String password, long CNP, String phoneNumber) {
        super(name, username, password);
        this.CNP = CNP;
        this.phoneNumber = phoneNumber;
    }

    public Subscriber(String name, String username, String password, long CNP, String phoneNumber, Address address) {
        super(name, username, password);
        this.CNP = CNP;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public long getCNP() {
        return CNP;
    }

    public void setCNP(long CNP) {
        this.CNP = CNP;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
