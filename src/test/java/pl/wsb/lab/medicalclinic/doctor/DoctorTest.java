package pl.wsb.lab.medicalclinic.doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoctorTest {

    private Doctor doctor;
    private Doctor doctor1;
    private Doctor doctor2;
    private MedicalSpecialty cardiology;
    private MedicalSpecialty neurology;


    @BeforeEach
    void setUp() {
        cardiology = MedicalSpecialty.CARDIOLOGY;
        neurology = MedicalSpecialty.NEUROLOGY;
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        doctor = new Doctor(UUID.randomUUID(), "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, "12345678901", Collections.emptyList());
        UUID doctorId = UUID.randomUUID();
        doctor1 = new Doctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, "12345678901", Collections.emptyList());
        doctor2 = new Doctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, "12345678901", Collections.emptyList());
    }

    @Test
    void testAddSpecialty() {
        doctor.addSpecialty(cardiology);
        Set<MedicalSpecialty> specialties = doctor.getSpecialties();
        assertEquals(1, specialties.size());
        assertTrue(specialties.contains(cardiology));
    }

    @Test
    void testRemoveSpecialty() {
        doctor.addSpecialty(cardiology);
        doctor.removeSpecialty(cardiology);
        Set<MedicalSpecialty> specialties = doctor.getSpecialties();
        assertTrue(specialties.isEmpty());
    }

    @Test
    void testAddDuplicateSpecialty() {
        doctor.addSpecialty(cardiology);
        doctor.addSpecialty(cardiology);
        Set<MedicalSpecialty> specialties = doctor.getSpecialties();
        assertEquals(1, specialties.size());
        assertTrue(specialties.contains(cardiology));
    }

    @Test
    void testAddMultipleSpecialties() {
        doctor.addSpecialty(cardiology);
        doctor.addSpecialty(neurology);
        Set<MedicalSpecialty> specialties = doctor.getSpecialties();
        assertEquals(2, specialties.size());
        assertTrue(specialties.contains(cardiology));
        assertTrue(specialties.contains(neurology));
    }

    @Test
    void testToString() {
        doctor.addSpecialty(neurology);

        String expected = "Doctor{id=" + doctor.getId() +
                ", firstName='John', lastName='Doe', dateOfBirth=1980-01-01, contactInfo=ContactInfo{phoneNumber='123456789', email='test@example.com'}, pesel='12345678901', specialties=NEUROLOGY}";
        assertEquals(expected, doctor.toString());
    }

    @Test
    void testHashCode() {
        assertEquals(doctor1.hashCode(), doctor2.hashCode());
    }

}