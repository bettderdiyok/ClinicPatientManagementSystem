package clinic.exception;

public class PatientAlreadyHasAppointmentThatDayException extends BusinessRuleException{
    public PatientAlreadyHasAppointmentThatDayException(String message) {
        super(message);
    }
}
