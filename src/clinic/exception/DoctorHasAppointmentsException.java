package clinic.exception;

public class DoctorHasAppointmentsException extends RuntimeException {
    public DoctorHasAppointmentsException(String message) {
        super(message);
    }
}
