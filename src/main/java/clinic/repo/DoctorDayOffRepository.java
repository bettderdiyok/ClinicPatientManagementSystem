package clinic.repo;

import clinic.domain.DoctorDayOff;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorDayOffRepository {
    private final ArrayList<DoctorDayOff> dayOffs = new ArrayList<>();

    public void addDayOff(DoctorDayOff dayOff) {
        dayOffs.add(dayOff);
    }

    public boolean isDoctorOffOnDate(int doctorId, LocalDate time) {
        return dayOffs.stream().anyMatch(off ->
                off.getDoctorId() == doctorId &&
                off.getDateTime().isEqual(time));
    }


    public List<DoctorDayOff> findByDoctorId(int doctorId){
        List<DoctorDayOff> result = new ArrayList<>();
        for (DoctorDayOff dayOff : dayOffs) {
            if(dayOff.getDoctorId() == doctorId) {
                result.add(dayOff);
            }

        }
        return result;

    }
}

