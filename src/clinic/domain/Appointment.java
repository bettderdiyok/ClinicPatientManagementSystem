package clinic.domain;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private LocalDateTime time;

    private enum appointmentStatus {
        BOOKED,
        CANCELED
    }

    public Appointment(int doctorId, int patientId, LocalDateTime time) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.time = time;
    }
}
