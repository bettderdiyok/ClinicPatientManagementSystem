package clinic.domain;

import clinic.util.IdGenerator;
import java.time.LocalDateTime;

public class Appointment {
    private final int appointmentId;
    private int doctorId;
    private final int patientId;
    private LocalDateTime time;

    private AppointmentStatus status;
    private AppointmentType type;

    public Appointment(int doctorId, int patientId, LocalDateTime time) {
        this.appointmentId = IdGenerator.nextAppointmentID();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.time = time;
        this.status = AppointmentStatus.BOOKED; //default
        this.type = AppointmentType.FIRST_VISIT; //default
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

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }
}
