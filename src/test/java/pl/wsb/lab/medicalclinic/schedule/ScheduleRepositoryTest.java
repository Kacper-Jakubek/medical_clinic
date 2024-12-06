package pl.wsb.lab.medicalclinic.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryScheduleRepositoryTest {
    private InMemoryScheduleRepository repository;
    private UUID doctorId;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        repository = new InMemoryScheduleRepository();
        doctorId = UUID.randomUUID();
        schedule = new Schedule(doctorId, new HashMap<>());
    }

    @Test
    void testAddSchedule() {
        repository.addSchedule(schedule);
        Optional<Schedule> retrievedSchedule = repository.findByDoctorId(doctorId);

        assertTrue(retrievedSchedule.isPresent());
        assertEquals(schedule, retrievedSchedule.get());
    }

    @Test
    void testFindByDoctorIdNotFound() {
        Optional<Schedule> retrievedSchedule = repository.findByDoctorId(UUID.randomUUID());

        assertFalse(retrievedSchedule.isPresent());
    }

    @Test
    void testAddScheduleNull() {
        assertThrows(NullPointerException.class, () -> repository.addSchedule(null));
    }

    @Test
    void testFindByDoctorIdNull() {
        Optional<Schedule> retrievedSchedule = repository.findByDoctorId(null);
        assertFalse(retrievedSchedule.isPresent());
    }
}