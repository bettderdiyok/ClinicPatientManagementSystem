package clinic.service;

import clinic.domain.Appointment;
import clinic.domain.Doctor;
import clinic.domain.Patient;
import clinic.exception.*;
import clinic.repo.AppointmentRepository;
import clinic.repo.DoctorDayOffRepository;
import clinic.repo.DoctorRepository;
import clinic.repo.PatientRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

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
            if (!doctorRepository.existsBySystemId(doctorId)) {
                throw new DoctorNotFoundException("Doctor not found.");
            }

            if(!patientRepository.existsBySystemId(patientId)) {
                throw  new PatientNotFoundException("Patient Not Found.");
            }

            LocalDateTime now = LocalDateTime.now();
            if(time.isBefore(now)) {
                throw  new InvalidAppointmentTimeException("Appointment time cannot be in the past.");
            }

            DayOfWeek day = time.getDayOfWeek();
            if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                throw new InvalidAppointmentTimeException("Doctor does not work on weekends.");
            }

            int hour = time.getHour();
            if(hour < 9 || hour > 17) {
                throw new InvalidAppointmentTimeException("Outside working hours! Hour must be between 9 and 17");
            }

            int minute = time.getMinute();
            if(minute % 15 != 0) {
                throw new InvalidAppointmentTimeException("Minute must be 00, 15, 30, 45");
            }

            if(appointmentRepository.existsDoctorAndDateTime(doctorId, time)){
                throw new DoctorAppointmentAlreadyBookedException("The doctor already has an appointment at that time.");
            }

            if(doctorDayOffRepository.isDoctorOffOnDate(doctorId, time.toLocalDate())) {
                throw new DoctorOnLeaveException("Doctor is on leave on this date. Appointment cannot be created.");
            }

            if(appointmentRepository.existsPatientAndDateTime(patientId, time)){
                throw new PatientAppointmentAlreadyBookedException("The patient already has an appointment at that time.");
            }

             Appointment appointment = new Appointment(doctorId, patientId, time);
             appointmentRepository.addAppointment(appointment);
    }

    public void deleteAppointment(int appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointmentId <= 0) {
            throw new InvalidAppointmentIdException("Appointment ID must be positive.");
        }

        if(appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

        if(appointment.getTime().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentTimeException("Cannot cancel past appointments.");
        }
        LocalDateTime now = LocalDateTime.now();
        if(appointment.getTime().minusHours(24).isBefore(now)){
            throw new InvalidAppointmentTimeException("You cannot cancel less than 24 hours before the appointment");
        }
        appointmentRepository.deleteAppointment(appointmentId);
    }

    public List<Appointment> listAppointmentsByDoctorId(int doctorId) {
        if(!doctorRepository.existsBySystemId(doctorId)){
            throw new DoctorNotFoundException("Doctor not found");
        }
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByDoctorId(doctorId);

        if(appointmentList.isEmpty()){
           throw new AppointmentNotFoundException("This doctor has no appointments.");
        }
        return appointmentList;

    }

    public List<Appointment> listAppointmentsByPatientId(int patientId){
        if(!patientRepository.existsBySystemId(patientId)){
            throw new PatientNotFoundException("Patient not found");
        }

        List<Appointment>  appointmentList = appointmentRepository.findAppointmentsByPatientId(patientId);
        if(appointmentList.isEmpty()){
            throw new AppointmentNotFoundException("This patient has no appointments.");
        }

        return appointmentList;


    }

    public List<Appointment> listAllAppointmentsSortedByTime(){
        List<Appointment> allAppointments = appointmentRepository.allAppointments();

        if(allAppointments.isEmpty()){
            throw new AppointmentNotFoundException("There is no appointment");
        }

        return  allAppointments.stream().sorted(Comparator.comparing(Appointment::getTime)).toList();

    }

    public Patient getPatient(int patientId) {
        if (!patientRepository.existsBySystemId(patientId)) {
            throw new PatientNotFoundException("Patient not found.");
        }
        return patientRepository.findPatient(patientId);
    }

    public Doctor getDoctor(int doctorId) {
        if (!doctorRepository.existsBySystemId(doctorId)) {
            throw new DoctorNotFoundException("Doctor not found.");
        }
        return doctorRepository.findByDoctorId(doctorId);
    }





}
