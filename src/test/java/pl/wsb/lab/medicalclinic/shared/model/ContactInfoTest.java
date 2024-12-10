package pl.wsb.lab.medicalclinic.shared.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContactInfoTest {

    @Test
    void testContactInfoCreation() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        assertEquals("123456789", contactInfo.getPhoneNumber());
        assertEquals("test@example.com", contactInfo.getEmail());
    }

    @Test
    void testSetPhoneNumber() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        contactInfo.setPhoneNumber("987654321");
        assertEquals("987654321", contactInfo.getPhoneNumber());
    }

    @Test
    void testSetEmail() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        contactInfo.setEmail("new@example.com");
        assertEquals("new@example.com", contactInfo.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        ContactInfo contactInfo1 = new ContactInfo("123456789", "test@example.com");
        ContactInfo contactInfo2 = new ContactInfo("123456789", "test@example.com");
        ContactInfo contactInfo3 = new ContactInfo("987654321", "new@example.com");

        assertEquals(contactInfo1, contactInfo2);
        assertNotEquals(contactInfo1, contactInfo3);
        assertEquals(contactInfo1.hashCode(), contactInfo2.hashCode());
        assertNotEquals(contactInfo1.hashCode(), contactInfo3.hashCode());
    }

    @Test
    void testToString() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String expectedString = "ContactInfo{phoneNumber='123456789', email='test@example.com'}";
        assertEquals(expectedString, contactInfo.toString());
    }
}