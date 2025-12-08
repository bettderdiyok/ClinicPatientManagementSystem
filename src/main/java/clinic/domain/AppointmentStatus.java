package clinic.domain;

public enum AppointmentStatus {
    BOOKED,
    CANCELED,
    COMPLETED,
    MISSID
    //TODO : Ensure all conflict checks consider only appointments with status = BOOKED.
}
