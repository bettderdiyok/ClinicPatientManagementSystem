package clinic.exception;

public class InvalidAppointmentIdException extends RuntimeException{
    public InvalidAppointmentIdException(String message) {
        super(message);
    }
}
