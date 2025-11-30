package clinic.exception;

public class AppointmentNotFoundException extends NotFoundException{
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
