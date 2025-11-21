package clinic.repo;

import clinic.domain.DayOffType;
import clinic.domain.DoctorDayOff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DoctorDayOffRepository {
    private ArrayList<DoctorDayOff> dayOffs = new ArrayList<>();

    public void addDayOff(int doctorId, LocalDate time, DayOffType type, String note) {
        DoctorDayOff dayOff = new DoctorDayOff(doctorId, time, type, note);
        dayOffs.add(dayOff);
    }

    public boolean isDoctorOffOnDate(int doctorId, LocalDate time) {
        return dayOffs.stream().anyMatch(doctorDayOff -> doctorDayOff.getDoctorId() == doctorId &&
                doctorDayOff.getDateTime().equals(time));
    }
}

