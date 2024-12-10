package pl.wsb.lab.medicalclinic.schedule;

import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository {
    Optional<Schedule> findByDoctorId(UUID doctorId);

    void addSchedule(Schedule schedule);
}
