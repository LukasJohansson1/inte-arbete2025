package com.example.kassasystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Membership membership;
    private Customer customer;

    //default setUp för att undvika onödig repetition
    @BeforeEach
    public void setUp() {
        customer = new Customer("John", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", membership);
    }

    @Test
    public void testCustomerCreation_allInputsValid() {
        customer = new Customer("John", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", membership);
        assertAll(
                "",
                () -> assertEquals("John", customer.getFirstName()),
                () -> assertEquals("Eriksson", customer.getLastName()),
                () -> assertEquals("Kyrkgränd 14", customer.getAddress()),
                () -> assertEquals("20020305-5523", customer.getSocialSecurityNumber()),
                () -> assertEquals("0739654522", customer.getTelephoneNumber()),
                () -> assertEquals("JohnEriksson@hotmail.com", customer.getEmailAddress()),
                () -> assertSame(membership, customer.getMembership())
        );
    }

    @Test
    public void testCustomerCreation_withoutMembershipObject() {
        customer = new Customer("John", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com");
        assertAll(
                "",
                () -> assertEquals("John", customer.getFirstName()),
                () -> assertEquals("Eriksson", customer.getLastName()),
                () -> assertEquals("Kyrkgränd 14", customer.getAddress()),
                () -> assertEquals("20020305-5523", customer.getSocialSecurityNumber()),
                () -> assertEquals("0739654522", customer.getTelephoneNumber()),
                () -> assertEquals("JohnEriksson@hotmail.com", customer.getEmailAddress())
        );
    }

    @Test
    public void testCustomerCreation_withMembershipTier() {
        customer = new Customer("John", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", "Gold");
        assertAll(
                "",
                () -> assertEquals("John", customer.getFirstName()),
                () -> assertEquals("Eriksson", customer.getLastName()),
                () -> assertEquals("Kyrkgränd 14", customer.getAddress()),
                () -> assertEquals("20020305-5523", customer.getSocialSecurityNumber()),
                () -> assertEquals("0739654522", customer.getTelephoneNumber()),
                () -> assertEquals("JohnEriksson@hotmail.com", customer.getEmailAddress()),
                () -> assertEquals("Gold", customer.getMembershipTier())
        );
    }

    @Test
    public void testCustomerCreation_withMembershipPoints(){
        customer = new Customer("John", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", 10000);
        assertAll(
                "",
                () -> assertEquals("John", customer.getFirstName()),
                () -> assertEquals("Eriksson", customer.getLastName()),
                () -> assertEquals("Kyrkgränd 14", customer.getAddress()),
                () -> assertEquals("20020305-5523", customer.getSocialSecurityNumber()),
                () -> assertEquals("0739654522", customer.getTelephoneNumber()),
                () -> assertEquals("JohnEriksson@hotmail.com", customer.getEmailAddress()),
                () -> assertEquals("Gold", customer.getMembershipTier())
        );
    }

    @Test
    public void testCustomerCreation_invalidFirstName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer = new Customer("john", "Eriksson", "Kyrkgränd 14", "20020305-5523", "0739654522", "JohnEriksson@hotmail.com");
        });
    }

    @Test
    public void testGetFirstName_returnsFirstName() {
        assertEquals("John", customer.getFirstName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Lars", "La", "Larslarslarslarslarslarslarslars",})
    public void testSetFirstName_validFirstName() {
        customer.setFirstName("Lars");
        assertEquals("Lars", customer.getFirstName());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    public void testSetFirstName_invalid_emptyOrNull_throwsException(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName(name);
        });
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_notOnlyLetters_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("La9rs");
        });
        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_lengthBelow2_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("L");
        });
        assertEquals("Name cannot be shorter than 2 or longer than 32 characters", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_lengthAbove32_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("Larslarslarslarslarslarslarslarsl");
        });
        assertEquals("Name cannot be shorter than 2 or longer than 32 characters", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_invalidChars_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("La%rs");
        });
        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_startsWithLowerCase_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("lars");
        });
        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_containsSpace_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("la rs");
        });
        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    public void testSetFirstName_invalid_hasUpperCaseInLaterPartOfName_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.setFirstName("LaRs");
        });
        assertEquals("Invalid name", exception.getMessage());
    }
    //om tid finns lägg till checkar för att nedan skrivs på rätt format

    @Test
    public void testGetLastName_returnsLastName() {
        assertEquals("Eriksson", customer.getLastName());
    }

    @Test
    public void testSetLastName_valid() {
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
        customer.setEmailAddress("JohnEriksson66@gmail.com");
        assertEquals("JohnEriksson66@gmail.com", customer.getEmailAddress());
    }

    @Test
    public void testGetMembership() {
        assertSame(membership, customer.getMembership());
    }


}

