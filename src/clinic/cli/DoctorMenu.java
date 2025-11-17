package clinic.cli;

import clinic.service.DoctorService;

import java.util.Scanner;

public class DoctorMenu {
    Scanner input = new Scanner(System.in);
    private final  DoctorService doctorService;

    public DoctorMenu(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void showTheDoctorMenu() {
        int choice;
        System.out.println("------MENU-----");
        showTheMenu();
        boolean isTrue = true;
        while (isTrue) {
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter doctor name: ");
                    String name = input.next();

                    System.out.print("Enter national ID: ");
                    String nationalId = input.next();

                    System.out.print("Enter branch: ");
                    String branch = input.next();
                    doctorService.addDoctor(nationalId, name, branch);
                    break;
                case 2:
                    doctorService.deleteDoctor();
                    break;
                case 3:
                    doctorService.updateDoctor();
                    break;
                case 4:
                    doctorService.listDoctors();
                    break;
                case 5:
                    showTheMenu();
                    break;
                case 6:
                    isTrue = false;
                    break;
            }
        }
    }

    public static void showTheMenu(){
        System.out.println( "0-Exit\n" +
                "1-Add Doctor\n" +
                "2-Delete Doctor\n" +
                "3-Update Doctor\n" +
                "4-List Doctors\n" +
                "5-Menu Again\n" +
                "6-Back to Main Menu\n"
        );
    }
}



