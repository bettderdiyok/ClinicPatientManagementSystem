package clinic.domain;

import java.time.LocalDate;


public class DoctorDayOff {
    private final int doctorId;
    private final LocalDate date;
    private final DayOffType dayOffType;
    private final String note;

    public DoctorDayOff(int doctorId, LocalDate dateTime, DayOffType dayOffType, String note) {
        this.doctorId = doctorId;
        this.date = dateTime;
        this.dayOffType = dayOffType;
        this.note = note;
    }

    public LocalDate getDateTime() {
        return date;
    }

    public DayOffType getDayOffType() {
        return dayOffType;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getNote() {
        return note;
    }
}
