package clinic.cli;

import clinic.repo.AppointmentRepository;
import clinic.repo.DoctorRepository;
import clinic.repo.PatientRepository;
import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientService;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isTrue = true;
        int choice;
        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository);
        DoctorMenu doctorMenu = new DoctorMenu(doctorService);

        PatientRepository patientRepository = new PatientRepository();
        PatientService patientService = new PatientService(patientRepository);
        PatientMenu patientMenu = new PatientMenu(patientService);

        AppointmentRepository appointmentRepository = new AppointmentRepository();
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);
        AppointmentMenu appointmentMenu = new AppointmentMenu(appointmentService);

        System.out.println("Welcome to the Clinic Patient Management System");
        while(isTrue){
            System.out.println("----MENU----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    patientMenu.showThePatientMenu();
                    break;
                case 2:
                    doctorMenu.showTheDoctorMenu();
                    break;
                case 3:
                    appointmentMenu.showTheAppointmentMenu();
                    break;
                case 4:

                    break;
                case 5:
                    showTheMenu();
                    break;
                case 0:
                    isTrue = false;
            }
        }
    }

    public static void showTheMenu(){
        System.out.println( "0-Exit\n" +
                "1-Patient Management\n" +
                "2-Doctor Management\n" +
                "3-Appointment\n" +
                "4-List\n" +
                "5-Menu Again\n"
               );
    }
}
