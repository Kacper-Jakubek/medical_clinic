package pl.wsb.lab.medicalclinic.schedule;

import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository {
    public Optional<Schedule> findByDoctorId(UUID doctorId);
    public void addSchedule(Schedule schedule);
}
