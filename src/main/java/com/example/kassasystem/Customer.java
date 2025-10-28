package com.example.kassasystem;

public class Customer {

    private String firstName, lastName, address, socialSecurityNumber, telephoneNumber, emailAddress;
    private Membership membership;

    public Customer(String firstName, String lastName, String address, String socialSecurityNumber, String telephoneNumber, String emailAddress) {
        setFirstName(firstName);
        this.lastName = lastName;
        this.address = address;
        this.socialSecurityNumber = socialSecurityNumber;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.membership = new Membership();
    }

    public Customer(String firstName, String lastName, String address, String socialSecurityNumber, String telephoneNumber, String emailAddress, Membership membership) {
        setFirstName(firstName);
        this.lastName = lastName;
        this.address = address;
        this.socialSecurityNumber = socialSecurityNumber;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.membership = membership;
    }
    public Customer(String firstName, String lastName, String address, String socialSecurityNumber, String telephoneNumber, String emailAddress, int membershipPoints) {
        setFirstName(firstName);
        this.lastName = lastName;
        this.address = address;
        this.socialSecurityNumber = socialSecurityNumber;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.membership = new Membership(membershipPoints);
    }
    public Customer(String firstName, String lastName, String address, String socialSecurityNumber, String telephoneNumber, String emailAddress, String membershipTier) {
        setFirstName(firstName);
        this.lastName = lastName;
        this.address = address;
        this.socialSecurityNumber = socialSecurityNumber;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.membership = new Membership(membershipTier);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() < 2 || name.length() > 32) {
            throw new IllegalArgumentException("Name cannot be shorter than 2 or longer than 32 characters");
        }
        if(!name.matches("^[A-ZÅÄÖ][a-zåäö]+$")) {
            throw new IllegalArgumentException("Invalid name");

        } else {
            firstName = name;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String ssn) {
        socialSecurityNumber = ssn;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String number) {
        telephoneNumber = number;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMembershipTier() {
        return membership.getTier();
    }

    public Membership getMembership() {
        return membership;
    }
}
