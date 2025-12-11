package clinic.service;

import clinic.domain.*;
import clinic.dto.UpdateAppointmentRequest;
import clinic.exception.*;
import clinic.repo.AppointmentRepository;
import clinic.repo.DoctorDayOffRepository;
import clinic.repo.DoctorRepository;
import clinic.repo.PatientRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public void createAppointment(int doctorId, int patientId, LocalDateTime time, Boolean isEmergency){
            LocalDateTime now = LocalDateTime.now();
            AppointmentType type;
            if (!doctorRepository.existsBySystemId(doctorId)) {
                throw new DoctorNotFoundException("Doctor not found.");
            }

            if(!patientRepository.existsBySystemId(patientId)) {
                throw  new PatientNotFoundException("Patient Not Found.");
            }
            if(isEmergency) {
               time = now;
            }

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

            if(appointmentRepository.existsByDoctorPatientAndDate(doctorId, patientId, time.toLocalDate())){
                throw new PatientAlreadyHasAppointmentThatDayException("You already have an appointment with this doctor on this day.");
            }

             if(isEmergency) {
                type = AppointmentType.EMERGENCY;
             } else {
                 Optional<Appointment> lastOpt = appointmentRepository.findLastAppointmentForPatientAndDoctor(patientId, doctorId);
                 if(lastOpt.isEmpty()) {
                    type = AppointmentType.FIRST_VISIT;
                 } else {
                     Appointment lastAppointment = lastOpt.get();
                     if(ChronoUnit.DAYS.between(lastAppointment.getTime().toLocalDate(), now.toLocalDate()) < 10){
                         type = AppointmentType.CONTROL;
                     } else {
                         type = AppointmentType.FIRST_VISIT;
                     }
                 }
             }
             Appointment appointment = new Appointment(doctorId, patientId, time);
             appointment.setType(type);
             appointment.setStatus(AppointmentStatus.BOOKED);
             appointmentRepository.addAppointment(appointment);
    }

    public void cancelAppointment(int appointmentId){
        if (appointmentId <= 0) {
            throw new ValidationException("Appointment ID must be positive.");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId);

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
        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.updateAppointment(appointment);
    }

    public void updateAppointment(int appointmentId, UpdateAppointmentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

        boolean doctorChanging = request.getDoctorId() != 0;
        boolean timeChanging = request.getTime() != null;

        if(!doctorChanging && !timeChanging) {
            throw new ValidationException("At least one field must be provided to update the appointment");
        }

        int effectiveDoctorId = doctorChanging ? request.getDoctorId()
                : appointment.getDoctorId();

        LocalDateTime targetTime = timeChanging ? request.getTime()
                : appointment.getTime();

        if (doctorChanging) {
            Doctor doctor = doctorRepository.findByDoctorId(request.getDoctorId());
            if (doctor == null) {
                throw new DoctorNotFoundException("Doctor not found");
            }
        }

        if (appointmentRepository.existsDoctorAndDateTime(effectiveDoctorId, targetTime)) {
            throw new DoctorAppointmentAlreadyBookedException("Doctor already has an appointment at that time");
        } //The conflict check is performed in a single place.

        if (doctorChanging) {
            appointment.setDoctorId(request.getDoctorId());
        }

        if (timeChanging) {
            appointment.setTime(request.getTime());
        }
        appointmentRepository.updateAppointment(appointment);
    }

    public List<Appointment> listAppointmentsByDoctorId(int doctorId) {
        if(!doctorRepository.existsBySystemId(doctorId)){
            throw new DoctorNotFoundException("Doctor not found");
        }

        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByDoctorId(doctorId);
        if(appointmentList.isEmpty()){
           throw new AppointmentNotFoundException("This doctor has no appointments.");
        }
        return appointmentList.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.BOOKED)
                .toList();
    }

    public List<Appointment> listAppointmentsByPatientId(int patientId){
        if(!patientRepository.existsBySystemId(patientId)){
            throw new PatientNotFoundException("Patient not found");
        }

        List<Appointment>  appointmentList = appointmentRepository.findAppointmentsByPatientId(patientId);
        if(appointmentList.isEmpty()){
            throw new AppointmentNotFoundException("This patient has no appointments.");
        }
        return appointmentList.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.BOOKED)
                .toList();
    }

    public List<Appointment> listAllAppointmentsSortedByTime(){
        List<Appointment> allAppointments = appointmentRepository.allAppointments();
        if(allAppointments.isEmpty()){
            throw new AppointmentNotFoundException("There is no appointment");
        }

        return  allAppointments.stream().sorted(Comparator.comparing(Appointment::getTime)).toList();
    }

    public Patient getPatient(int patientId) {
        Patient patient = patientRepository.findPatient(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found.");
        }
        return patient;
    }

    public Doctor getDoctor(int doctorId) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found.");
        }
        return doctor;
    }

    public List<Appointment> getActiveAppointments(){
        return appointmentRepository.allAppointments().stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.BOOKED)
                .toList();
    }

    public List<Appointment> getCanceledAppointments(){
        return appointmentRepository.allAppointments().stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.CANCELED)
                .toList();
    }


}
