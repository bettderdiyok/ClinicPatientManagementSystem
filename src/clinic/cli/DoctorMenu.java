package clinic.cli;

import clinic.domain.Branch;
import clinic.exception.DuplicateDoctorException;
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
        boolean isTrue = true;
        while (isTrue) {
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter doctor name: ");
                        String name = input.nextLine();

                        System.out.print("Enter national ID: ");
                        String nationalId = input.next();

                        listBranch();

                        System.out.print("Enter branch: ");
                        int branch = input.nextInt();
                        doctorService.addDoctor(nationalId, name, branch);
                        System.out.println("Doctor added successfully!");
                    } catch(DuplicateDoctorException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Returning to menu...");
                }
                    break;
                case 2:
                    System.out.print("Enter national ID: ");
                    String nationalId = input.next();
                    doctorService.deleteDoctor(nationalId);
                    break;
                case 3:
                    System.out.print("Enter national ID: ");
                    nationalId = input.next();
                    input.nextLine();

                    System.out.print("Enter doctor name: ");
                    String name = input.nextLine();

                    listBranch();

                    System.out.print("Enter branch: ");
                    int branch = input.nextInt();

                    doctorService.updateDoctor(nationalId, name, branch);

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
        System.out.println("1-Add Doctor\n" +
                "2-Delete Doctor\n" +
                "3-Update Doctor\n" +
                "4-List Doctors\n" +
                "5-Menu Again\n" +
                "6-Back to Main Menu\n"
        );
    }

    public void listBranch(){
        Branch[] branches = Branch.values();
        for(int i=0; i<branches.length; i++) {
            System.out.println((i+1) + " - " + branches[i]);
        }
    }
}



