package com.example.kassasystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Membership membership;
    private Customer customer;

    //default setUp för att undvika onödig repetition
    @BeforeEach
    public void setUp() {
        membership = new Membership();
        customer = new Customer("John", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", membership);
    }

    @Test
    public void testCustomerCreation_allInputsValid() {
        assertAll(
                "fgwafg",
                () -> assertEquals("John", customer.getFirstName()),
                () -> assertEquals("Eriksson", customer.getLastName()),
                () -> assertEquals("Kyrkgränd 14", customer.getLastName()),
                () -> assertEquals("20020305-5523", customer.getSocialSecurityNumber()),
                () -> assertEquals("0739654522", customer.getTelephoneNumber()),
                () -> assertEquals("JohnEriksson@hotmail.com", customer.getEmailAddress())
        );
    }

    @Test
    public void testGetFirstName_returnsFirstName() {
        assertEquals("John", customer.getFirstName());
    }

    @Test
    public void testSetName_validFirstName() {
        customer.setFirstName("Lars");
        assertEquals("Lars", customer.getFirstName());
    }

    @Test
    public void testSetFirstName_invalid_emptyOrNull_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("");
        });
        assertEquals("Name cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_notOnlyLetters_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("La9rs");
        });
        assertEquals("Name can only contain letters", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_lengthBelow2_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("L");
        });
        assertEquals("Name cannot have under 2 or above 32 characters", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_lengthAbove32_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("Larslarslarslarslarslarslarslarsl");
        });
        assertEquals("Name cannot have under 2 or above 32 characters", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_invalidChars_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("La%rs");
        });
        assertEquals("Name cannot contain invalid characters", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_startsWithLowerCase_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("lars");
        });
        assertEquals("Name cannot start with a lowercase letter", exception.getMessage());
    }

    @Test
    public void testGetLastName_returnsLastName() {
        assertEquals("Eriksson", customer.getLastName());
    }

    @Test
    public void testGetLastName_valid() {
        customer.setLastName("Svensson");
        assertEquals("Svensson", customer.getLastName());
    }

    @Test
    public void testGetFullName() {
        assertEquals("John Eriksson", customer.getFullName());
    }

    @Test
    public void testGetAddress_valid() {
        assertEquals("Kyrkgränd 14", customer.getAddress());
    }

    @Test
    public void testSetAddress_returnsAdress() {
        customer.setAddress("Skomakargränd 15");
        assertEquals("Skomakargränd 15", customer.getAddress());
    }

    @Test
    public void testGetSocialSecurityNumber() {
        assertEquals("20020305-5523", customer.getSocialSecurityNumber());
    }

    @Test
    public void testSetSocialSecurityNumber() {
        customer.setSocialSecurityNumber("19960506-4323");
        assertEquals("19960506-4323", customer.getSocialSecurityNumber());
    }

    @Test
    public void testGetTelephoneNumber() {
        assertEquals("0739654522", customer.getTelephoneNumber());
    }

    @Test
    public void testSetTelephoneNumber() {
        customer.setTelephoneNumber("0794537583");
        assertEquals("0794537583", customer.getTelephoneNumber());
    }

    @Test
    public void testGetEmailAddress() {
        assertEquals("JohnEriksson@hotmail.com", customer.getEmailAddress());
    }

    @Test
    public void testSetEmailAdress() {
        customer.setEmailAdress("JohnEriksson66@gmail.com");
        assertEquals("JohnEriksson66@gmail.com", customer.getEmailAddress());
    }


}

