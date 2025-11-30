package clinic.dto;

import java.time.LocalDateTime;

public class UpdateAppointmentRequest {
    private int doctorId;
    private LocalDateTime time;

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
