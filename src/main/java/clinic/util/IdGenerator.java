package clinic.util;

public class IdGenerator {
    private static int patientIdCounter = 0;
    private static int doctorIdCounter = 0;
    private static int appointmentIdCounter = 0;

    public static int nextPatientID(){
        return ++patientIdCounter;
    }

    public static int nextDoctorID(){
        return ++doctorIdCounter;
    }

    public static int nextAppointmentID(){
        return ++appointmentIdCounter;
    }
}
