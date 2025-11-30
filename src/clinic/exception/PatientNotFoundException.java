package clinic.exception;

public class PatientNotFoundException extends NotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
