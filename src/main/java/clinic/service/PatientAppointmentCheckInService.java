package clinic.service;

import clinic.domain.Appointment;
import clinic.domain.AppointmentStatus;
import clinic.domain.Patient;
import clinic.exception.*;
import clinic.repo.AppointmentRepository;
import clinic.repo.PatientRepository;
import clinic.util.NationalIdValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientAppointmentCheckInService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    public PatientAppointmentCheckInService(PatientRepository patientRepository1, AppointmentRepository appointmentRepository1) {
        this.patientRepository = patientRepository1;
        this.appointmentRepository = appointmentRepository1;
    }

    public void checkIn(String patientNationalId){
        if(!NationalIdValidator.isValidNationalId(patientNationalId)){
            throw new InvalidNationalIdException("Invalid ID.");
        }

        if(!patientRepository.existsByNationalId(patientNationalId)){
            throw new PatientNotFoundException("Patient not found. Register the patient first.");
        }

        Patient patient = patientRepository.findByNationalId(patientNationalId);
        int patientSystemId = patient.getPatientId();

        Appointment todayAppointment = findTodayAppointmentForPatient(patientSystemId);

        if(todayAppointment == null){
            throw new AppointmentNotFoundException("Appointment not found for today.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentTime = todayAppointment.getTime();

        if (appointmentTime.isBefore(now)) {
            todayAppointment.setStatus(AppointmentStatus.MISSED);
            appointmentRepository.updateAppointment(todayAppointment);
            throw  new ValidationException("The appointment time has passed and it is marked as MISSED.");
        }

        todayAppointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.updateAppointment(todayAppointment);
    }

    public Appointment findTodayAppointmentForPatient(int patientId) {
        LocalDate today = LocalDate.now();

        return appointmentRepository.findAppointmentsByPatientId(patientId).stream().
                filter(appointment -> appointment.getTime().toLocalDate().equals(today))
                .findFirst()
                .orElse(null);
    }
}
