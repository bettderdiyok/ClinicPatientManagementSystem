package clinic.cli;

import clinic.exception.DoctorAppointmentAlreadyBookedException;
import clinic.exception.DoctorNotFoundException;
import clinic.exception.PatientNotFoundException;
import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AppointmentMenu {
    Scanner input = new Scanner(System.in);
    private final AppointmentService appointmentService;
    private  final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentMenu(AppointmentService appointmentService, DoctorService doctorService,  PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void showTheAppointmentMenu() {
        int choice;
        boolean isTrue = true;
        while (isTrue) {
            System.out.println("------MENU-----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    try {
                        System.out.println("----- DOCTOR LIST -----");
                        doctorService.listDoctors();

                        System.out.print("Enter Doctor ID : ");
                        int doctorID = input.nextInt();

                        System.out.println("----- PATIENT LIST ------");
                        patientService.listPatients();

                        System.out.print("Enter Patient ID : ");
                        int patientID = input.nextInt();

                        System.out.println("Enter Appointment date and time : ");
                        System.out.print("Year (e.g. 2025) : ");
                        int year = input.nextInt();

                        System.out.print("Month (1-12) : ");
                        int month = input.nextInt();

                        System.out.print("Day : ");
                        int day = input.nextInt();

                        System.out.print("Hour (0-23): ");
                        int hour = input.nextInt();

                        int minute = input.nextInt();
                        input.nextLine();
                        LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute);
                        appointmentService.createAppointment(doctorID, patientID, time);
                        System.out.println("Appointment created successfully!");
                    } catch (DoctorNotFoundException | PatientNotFoundException |
                             DoctorAppointmentAlreadyBookedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                        case 2:


                            break;
                        case 3:


                            break;
                        case 4:

                            break;
                        case 5:
                            showTheMenu();
                            break;
                        case 6:
                            isTrue = false;
                    }
            }
        }
    public static void showTheMenu(){
        System.out.println("1-Create Appointment\n" +
                "2-Cancel Appointment \n" +
                "3-Update Appointment\n" +
                "4-List\n" +
                "5-Menu Again\n" +
                "6-Back to Main Menu\n"
        );
    }
}

