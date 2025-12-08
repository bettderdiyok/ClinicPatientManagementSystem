package clinic.repo;

import clinic.domain.DoctorDayOff;
import com.google.gson.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorDayOffRepository {
    private ArrayList<DoctorDayOff> dayOffs = new ArrayList<>();
    private static final String FILE_PATH  = "DoctorDayOff.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                    LocalDate.parse(json.getAsString())).create();

    public DoctorDayOffRepository() {
        loadFromJson();
    }

    public void addDayOff(DoctorDayOff dayOff) {
        dayOffs.add(dayOff);
        saveToJson();
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
            if(dayOffArray == null) {
                dayOffs = new ArrayList<>();
            } else {
                dayOffs = new ArrayList<>(Arrays.asList(dayOffArray));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load day offs from JSON file.", e);
        }
    }


}

