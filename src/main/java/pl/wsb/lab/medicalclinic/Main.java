package pl.wsb.lab.medicalclinic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wsb.lab.medicalclinic.appointment.AppointmentService;
import pl.wsb.lab.medicalclinic.appointment.InMemoryAppointmentRepository;
import pl.wsb.lab.medicalclinic.doctor.*;
import pl.wsb.lab.medicalclinic.patient.*;
import pl.wsb.lab.medicalclinic.schedule.InMemoryScheduleRepository;
import pl.wsb.lab.medicalclinic.schedule.ScheduleRepository;
import pl.wsb.lab.medicalclinic.schedule.ScheduleService;
import pl.wsb.lab.medicalclinic.shared.exception.AppointmentException;
import pl.wsb.lab.medicalclinic.shared.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws AppointmentException {
        // (1.1) Jako recepcjonista chcę mieć możliwość utworzenia profilu nowego pacjenta zawierającego jego dane osobowe oraz kontaktowe potrzebne do świadczenia usług medycznych.
        PatientRepository patientRepository = new InMemoryPatientRepository();
        PatientService patientService = new PatientService(patientRepository);
        logger.debug("(1.1) Tworzenie profilu nowego pacjenta");
        Patient newPatient = PatientFactory.createPatient("Jan", "Kowalski", "1990-01-01", "515111222", "jan.kowalski@example.com", "90010112345");
        logger.info("Utworzony pacjent: {}", newPatient);
        patientService.addPatient(newPatient);
        // (1.2) Jako recepcjonista chcę mieć możliwość znalezienia pacjenta po numerze PESEL oraz wyświetlenia wszystkich jego danych.
        logger.debug("(1.2) Wyszukiwanie pacjenta po PESELu i wyświetlanie jego danych");
        patientService.searchByPesel("90010112345").ifPresentOrElse(
                patient -> logger.info("Znaleziono pacjenta: {}", patient),
                () -> logger.error("Nie znaleziono pacjenta o numerze PESEL: 90010112345")
        );
        patientService.searchByPesel("88101112345").ifPresentOrElse(
                patient -> logger.info("Znaleziono pacjenta: {}", patient),
                () -> logger.error("Nie znaleziono pacjenta o numerze PESEL: 88101112345")
        );
        // (1.3) Jako recepcjonista chcę móc wyszukać wszystkich pasujących pacjentów o podanym nazwisku oraz wyświetlenia wszystkich danych znalezionych pacjentów.
        logger.debug("(1.3) Wyszukiwanie pacjentów po nazwisku i wyświetlanie ich danych");
        logger.debug("Dodaję jeszcze jednego pacjenta o nazwisku Kowalski");
        Patient anotherPatient = PatientFactory.createPatient("Juliusz", "Kowalski", "1995-01-01", "515222333", "another_kowalski@example.com", "66101012345");
        patientService.addPatient(anotherPatient);
        patientService.searchByLastName("Kowalski").forEach(patient -> logger.info("Znaleziono pacjenta: {}", patient));

        // (2.1) Jako pracownik działu HR chcę mieć możliwość utworzenia profilu zatrudnionego lekarza uwzględniając wszystkie jego specjalizacje.
        DoctorRepository doctorRepository = new InMemoryDoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository);
        logger.debug("(2.1) Tworzenie profilu nowego lekarza");
        Doctor newDoctor = DoctorFactory.createDoctor("Jan", "Nowak", "1970-01-01", "515333444", "imadoctor@example.com", "70010112345", "OTOLARYNGOLOGY,PEDIATRICS");
        logger.info("Utworzony lekarz: {}", newDoctor);
        doctorService.addDoctor(newDoctor);
        // (2.2) Jako pracownik działu HR chcę mieć możliwość dodania nowej specjalizacji lekarzowi, który istnieje już w systemie.
        logger.debug("(2.2) Dodawanie nowej specjalizacji dla lekarza");
        doctorService.addSpecialtyToDoctor(newDoctor.getId(), MedicalSpecialty.valueOf("DERMATOLOGY"));
        logger.info("Specjalizacje lekarza po dodaniu nowej: {}", newDoctor.getSpecialties());
        // (2.3) Jako recepcjonista chcę mieć możliwość znalezienia lekarza po jego numerze ID oraz wyświetlenia jego danych oraz specjalizacji.
        logger.debug("(2.3) Wyszukiwanie lekarza po ID i wyświetlanie jego danych oraz specjalizacji");
        doctorService.findDoctorById(newDoctor.getId()).ifPresentOrElse(
                doctor -> logger.info("Znaleziono lekarza: {}", doctor),
                () -> logger.error("Nie znaleziono lekarza o ID: {}", newDoctor.getId())
        );
        // (2.4) Jako recepcjonista chcę mieć możliwość znalezienia wszystkich lekarzy o określonej specjalizacji oraz wyświetlenia ich danych oraz ID.
        logger.debug("(2.4) Wyszukiwanie lekarzy po specjalizacji i wyświetlanie ich danych");
        logger.debug("Dodaję jeszcze jednego lekarza o specjalizacji DERMATOLOGY");
        Doctor anotherDoctor = DoctorFactory.createDoctor("Marcin", "Kaźmierczak", "1975-01-01", "515444555", "doktor101@example.com", "75010112345", "DERMATOLOGY");
        doctorService.addDoctor(anotherDoctor);
        doctorService.findDoctorsBySpecialty(MedicalSpecialty.valueOf("DERMATOLOGY")).forEach(doctor -> logger.info("Znaleziono lekarza: {}", doctor));

        // (3.1) Jako architekt oprogramowania chcę mieć dostęp do prostej dokumentacji aplikacji zawierającą diagram klas. Diagram powinien zawierać informacje: o wszystkich klasach użytych w projekcie, ich polach oraz zależnościach między klasami.
        // Diagram klas znajduje się w pliku <project root>/docs/diagram klas.png w głównym katalogu projektu.

        // (4.1) Jako kierownik placówki chcę mieć możliwość tworzenia grafików dla wszystkich pracujących lekarzy. Pojedynczy grafik powinien zawierać godziny pracy wybranego lekarza w wybranym dniu.
        logger.debug("(4.1) Tworzenie grafiku dla lekarza");
        ScheduleRepository scheduleRepository = new InMemoryScheduleRepository();
        ScheduleService scheduleService = new ScheduleService(doctorRepository, scheduleRepository);
        logger.debug("Tworzenie grafiku dla lekarza: {}", newDoctor.getFullName());
        scheduleService.createSchedule(newDoctor.getId(), LocalDate.now().plusDays(2), DateTimeParser.parseTime("08:00"), DateTimeParser.parseTime("16:00"));
        scheduleService.createSchedule(newDoctor.getId(), LocalDate.now().plusDays(3), DateTimeParser.parseTime("08:00"), DateTimeParser.parseTime("12:00"));
        scheduleService.createSchedule(newDoctor.getId(), LocalDate.now().plusDays(3), DateTimeParser.parseTime("16:00"), DateTimeParser.parseTime("20:00"));
        // (4.2) Jako pracownik recepcji chcę móc pobrać wszystkie utworzone grafiki wybranego lekarza na najbliższy tydzień, abym mógł sprawdzić w jakich godzinach przyjmuje pacjentów.
        logger.debug("(4.2) Pobieranie grafików dla lekarza na najbliższy tydzień");
        scheduleService.findScheduleByDoctorId(newDoctor.getId()).ifPresentOrElse(
                schedule -> schedule.getNextWeekSchedule().forEach(workingHours -> logger.info("Grafik: {} - {} - {}", workingHours.getDate(), workingHours.getStartTime(), workingHours.getEndTime())),
                () -> logger.error("Nie znaleziono grafiku dla lekarza: {}", newDoctor.getFullName())
        );

        // (5.1) Jako pracownik recepcji chcę mieć możliwość umówienia pacjenta w wybranym dniu o określonej porze na wizytę lekarską z wybranym lekarzem.
        logger.debug("(5.1) Umawianie pacjenta na wizytę lekarską");
        InMemoryAppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        AppointmentService appointmentService = new AppointmentService(appointmentRepository, scheduleService);
        logger.debug("Umawiam pacjenta na wizytę lekarską");
        appointmentService.createAppointment(newPatient, newDoctor, LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(10, 30)));
        logger.info("Utworzono wizytę: {}", appointmentRepository.findByPatient(newPatient).getFirst());
        // (5.2) Wizyta lekarska powinna zostać pomyślnie umówiona tylko i wyłącznie w godzinach pracy wybranego lekarza (na podstawie jego grafiku). W przeciwnym wypadku powinien zostać wyrzucony wyjątek, a wizyta nie powinna zostać utworzona.
        try {
            logger.debug("Próba umówienia pacjenta na wizytę w tym samym czasie");
            appointmentService.createAppointment(anotherPatient, newDoctor, LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(10, 30)));
        } catch (AppointmentException e) {
            logger.error("Nie udało się umówić pacjenta na wizytę: {}", e.getMessage());
        }
        // (5.3) Ponadto wizyta powinna być pomyślnie umówiona tylko wtedy, gdy w tym samym czasie wybrany lekarz nie ma już innej wizyty. W przeciwnym wypadku powinien zostać wyrzucony wyjątek, a wizyta nie powinna zostać utworzona.
        try {
            logger.debug("Próba umówienia pacjenta na wizytę w czasie, kiedy lekarz nie pracuje");
            appointmentService.createAppointment(anotherPatient, newDoctor, LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 30)));
        } catch (AppointmentException e) {
            logger.error("Nie udało się umówić pacjenta na wizytę: {}", e.getMessage());
        }
    }
}
