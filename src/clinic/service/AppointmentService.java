package clinic.service;

import clinic.domain.Appointment;
import clinic.domain.DoctorDayOff;
import clinic.exception.*;
import clinic.repo.AppointmentRepository;
import clinic.repo.DoctorDayOffRepository;
import clinic.repo.DoctorRepository;
import clinic.repo.PatientRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorDayOffRepository doctorDayOffRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, DoctorDayOffRepository doctorDayOffRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.doctorDayOffRepository = doctorDayOffRepository;
    }

    public void createAppointment(int doctorId, int patientId, LocalDateTime time){
        try {
            if (!doctorRepository.existsBySystemId(doctorId)) {
                throw new DoctorNotFoundException("Doctor not found!!");
            }

            if(!patientRepository.existsBySystemId(patientId)) {
                throw  new PatientNotFoundException("Patient Not Found!");
            }

            if(time.isBefore(LocalDateTime.now())) {
                System.out.println("Appointment time cannot be in the past");
                return;
            }

            DayOfWeek day = time.getDayOfWeek();
            if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                System.out.println("Doctor does not work on weekends!!");
                return;
            }

            int hour = time.getHour();
            if(hour < 9 || hour > 17) {
                System.out.println("Outside working hours! Hour must be between 9 and 17");
                return;
            }

            int minute = time.getMinute();
            if(minute % 15 != 0) {
                System.out.println("Minute must be 00, 15, 30, 45");
                return;
            }

            if(appointmentRepository.existsDoctorAndDataTime(doctorId, time)){
                throw new DoctorAppointmentAlreadyBookedException("The doctor already has an appointment at that time.");
            }

            if(doctorDayOffRepository.isDoctorOffOnDate(doctorId, time.toLocalDate())) {
                throw new DoctorOnLeaveException("Doctor is on leave on this date. Appointment cannot be created.");
            }

            if(appointmentRepository.existsPatientAndDateTime(patientId, time)){
                throw new PatientAppointmentAlreadyBookedException("The patient already has an appointment at that time.");
            }

        } catch (DoctorNotFoundException  | PatientNotFoundException | DoctorAppointmentAlreadyBookedException | DoctorOnLeaveException | PatientAppointmentAlreadyBookedException e) {
            System.out.println(e.getMessage());
        }

        appointmentRepository.addAppointment();
    }

    public void deleteAppointment(){

    }
}
