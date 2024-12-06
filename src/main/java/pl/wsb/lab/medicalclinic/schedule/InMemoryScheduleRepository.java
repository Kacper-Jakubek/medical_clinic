package pl.wsb.lab.medicalclinic.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryScheduleRepository implements ScheduleRepository {
    private final Map<UUID, Schedule> schedules = new HashMap<>();

    public Optional<Schedule> findByDoctorId(UUID doctorId) {
        return Optional.ofNullable(schedules.get(doctorId));
    }

    public void addSchedule(Schedule schedule) {
        schedules.put(schedule.getDoctorId(), schedule);
    }
}
