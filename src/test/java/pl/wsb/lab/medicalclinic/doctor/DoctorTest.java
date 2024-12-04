package pl.wsb.lab.medicalclinic.doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    private Doctor doctor;
    private MedicalSpecialty cardiology;
    private MedicalSpecialty neurology;

    @BeforeEach
    void setUp() {
        cardiology = MedicalSpecialty.CARDIOLOGY;
        neurology = MedicalSpecialty.NEUROLOGY;
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        doctor = new Doctor("John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, "12345678901", Collections.emptyList());
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
}