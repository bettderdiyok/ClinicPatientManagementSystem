package clinic.repo;

import clinic.domain.DoctorDayOff;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorDayOffRepository {
    private final ArrayList<DoctorDayOff> dayOffs = new ArrayList<>();
    private static final String FILE_PATH  = "DoctorDayOff.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public DoctorDayOffRepository() {
        loadFromJson();
    }

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

    private void saveToJson(){
        try(FileWriter writer = new FileWriter(FILE_PATH)) {
            String json = GSON.toJson(dayOffs);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFromJson(){
        File file = new File(FILE_PATH);
        if(!file.exists()) {
            return;
        }

        try(FileReader reader = new FileReader(FILE_PATH)) {
            DoctorDayOff[] dayOffArray = GSON.fromJson(reader, DoctorDayOff[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

