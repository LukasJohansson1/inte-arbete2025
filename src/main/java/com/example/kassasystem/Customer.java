package com.example.kassasystem;

public class Customer {

    private String firstName, lastName, address, socialSecurityNumber, telephoneNumber, emailAddress;
    private Membership membership;

    public Customer(String firstName, String lastName, String address, String socialSecurityNumber, String telephoneNumber, String emailAddress, Membership membership) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.socialSecurityNumber = socialSecurityNumber;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Någonting");
        }
        if(!name.matches("^[A-ZÅÄÖ][a-zåäö]+$")) {
            throw new IllegalArgumentException("Någonting");
        }
        if (name.length() < 2 || name.length() > 32) {
            throw new IllegalArgumentException("Någonting");
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

    public void setEmailAdress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMembershipTier() {
        return "";
    }
}
