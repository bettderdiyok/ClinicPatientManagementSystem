package clinic.repo;

import clinic.domain.DoctorDayOff;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorDayOffRepository extends JsonBaseRepository<DoctorDayOff> {
    private List<DoctorDayOff> dayOffs;
    private static final Path FILE_PATH  = Path.of("DoctorDayOff.json");
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                    LocalDate.parse(json.getAsString())).create();

    public DoctorDayOffRepository() {
        super(FILE_PATH, GSON, new TypeToken<List<DoctorDayOff>> () {}.getType());
        dayOffs = new ArrayList<>(readAllInternal());
    }

    public void addDayOff(DoctorDayOff dayOff) {
        dayOffs.add(dayOff);
        saveAll();
    }

    private void saveAll() {
        writeAllInternal(dayOffs);
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

