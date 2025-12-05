package clinic.repo;

import clinic.domain.Appointment;
import clinic.util.IdGenerator;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppointmentRepository {
    private static final String FILE_PATH = "appointments.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                            LocalDateTime.parse(json.getAsString()))
            .create();

    private ArrayList<Appointment> appointments = new ArrayList<>();
    public boolean existsAppointmentByDoctorId(int doctorId){
       return  appointments.stream().anyMatch(appointment -> appointment.getDoctorId() == doctorId);
    }

    public boolean existsPatientAndDateTime(int patientId, LocalDateTime time){
        return appointments.stream().anyMatch(appointment -> appointment.getPatientId() == patientId && appointment.getTime().equals(time));
     /* for (Appointment appointment : appointments){
            if (appointment.getDoctorId() == doctorId && appointment.getTime().equals(time)) {
                return true;
            }
        }
        return false;
        */
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    public void saveToJson() {
        try (FileWriter writer = new FileWriter(FILE_PATH)){
            String json = GSON.toJson(appointments);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save appointments to JSON file.", e);
        }
    }

    public void loadFromJson() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // ilk açılışta dosya yoksa hata vermesin
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Appointment[] array = GSON.fromJson(reader, Appointment[].class);
            appointments = new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load appointments from JSON file.", e);
        }

        int maxId = appointments.stream()
                .mapToInt(Appointment::getAppointmentId)
                .max()
                .orElse(0);

        IdGenerator.initAppointmentId(maxId + 1);
    }


    public boolean existsDoctorAndDateTime(int doctorId, LocalDateTime time) {
        return appointments.stream().anyMatch(appointment -> appointment.getDoctorId() == doctorId && appointment.getTime().equals(time));
    }

    public List<Appointment> findAppointmentsByDoctorId(int doctorId){
        return appointments.stream().filter(appointment -> appointment.getDoctorId() == doctorId).toList();
    }

    public List<Appointment> findAppointmentsByPatientId(int patientId) {
        return appointments.stream().filter(appointment -> appointment.getPatientId() == patientId).toList();
    }

    public List<Appointment> allAppointments(){
        return new ArrayList<>(appointments); //defensive copy
    }

    public Appointment findById(int appointmentId) {
        return appointments.stream()
                .filter(appointment -> appointment.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);
    }

    public boolean existsByDoctorPatientAndDate(int doctorId, int patientId, LocalDate date){
        return appointments.stream()
                .anyMatch(appointment ->
                        appointment.getDoctorId() == doctorId &&
                        appointment.getPatientId() == patientId &&
                                appointment.getTime().toLocalDate().equals(date)
                );
    }

}
