package clinic.exception;

public class DoctorAppointmentAlreadyBookedException extends RuntimeException {
    public DoctorAppointmentAlreadyBookedException(String message) {
        super(message);
    }
}
