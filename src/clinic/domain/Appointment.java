package clinic.domain;

import clinic.util.IdGenerator;

import java.time.LocalDateTime;

public class Appointment {
    private final int appointmentId;
    private final int doctorId;
    private final int patientId;
    private final LocalDateTime time;

    private enum appointmentStatus {
        BOOKED,
        CANCELED
    }

    public Appointment(int doctorId, int patientId, LocalDateTime time) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.time = time;
        this.appointmentId = IdGenerator.nextAppointmentID();
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
