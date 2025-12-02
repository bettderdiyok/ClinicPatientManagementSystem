package clinic.domain;

public enum AppointmentStatus {
    BOOKED,
    CANCELED,
    //TODO : Ensure all conflict checks consider only appointments with status = BOOKED.
}
