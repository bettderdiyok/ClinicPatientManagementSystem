package clinic.cli;


import clinic.dto.UpdatePatientRequest;
import clinic.service.PatientService;

import java.util.Scanner;

public class PatientMenu {
    Scanner input = new Scanner(System.in);
    private final PatientService patientService;

    public PatientMenu(PatientService patientService) {
        this.patientService = patientService;
    }

    public void showThePatientMenu() {
        int choice;
        boolean isTrue = true;
        boolean hasGuardian = false;
        while (isTrue) {
            System.out.println("------MENU-----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter patient name: ");
                        String name = input.nextLine();

                        System.out.print("Enter national ID: ");
                        String nationalId = input.nextLine();

                        System.out.print("Enter age : ");
                        int age = input.nextInt();
                        input.nextLine();

                        if(age < 18){
                            System.out.print("Is a guardian present? (yes / no) ");
                            String answer = input.nextLine().trim().toLowerCase();
                            hasGuardian = answer.equalsIgnoreCase("yes");
                            if (!hasGuardian) {
                                System.out.println("Patients under 18 must have a guardian. Returning to menu...");
                                break;
                            }
                        }


                        System.out.print("Enter patient complaint: ");
                        String complain = input.nextLine();

                        patientService.addPatient(name, nationalId, age, hasGuardian, complain);
                    } catch (Exception e) {
                        System.out.println("Error : " + e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("------- PATIENT LIST ------");
                    patientService.listPatients();
                    System.out.print("Enter patient ID : ");
                    int patientId = input.nextInt();
                    patientService.deletePatient(patientId);
                    break;
                case 3:
                    System.out.println("------- PATIENT LIST ------");
                    patientService.listPatients();
                    System.out.print("Enter patient ID : ");
                    patientId = input.nextInt();

                    System.out.print("New name (Leave it blank if you don’t want to change it.): ");
                    String fullName = input.nextLine();

                    System.out.print("New age (Leave it blank if you don’t want to change it.): ");
                    String ageInput  = input.nextLine();

                    System.out.print("New complaint (Leave it blank if you don't want to change it.): ");
                    String complaintInput = input.nextLine();

                    UpdatePatientRequest request = new UpdatePatientRequest();

                    if(!fullName.isBlank()) {
                        request.setFullname(fullName);
                    }

                    if (!ageInput.isBlank()) {
                        int age = Integer.parseInt(ageInput);
                        request.setAge(age);
                    }

                    if (!complaintInput.isBlank()) {
                        request.setComplaint(complaintInput);
                    }

                    patientService.updatePatient(patientId, request);
                    break;
                case 4:
                    patientService.listPatients();
                    break;
                case 5:
                    patientService.searchPatient();
                    break;
                case 6:
                    showTheMenu();
                    break;
                case 7:
                    isTrue = false;
                    break;
            }
        }
    }
    public static void showTheMenu(){
        System.out.println("1-Add New Patient\n" +
                "2-Delete Patients\n" +
                "3-Update Patients\n" +
                "4-List All Patients\n" +
                "5-Find Patient by ID\n" +
                "6-Menu Again\n" +
                "7-Back to Main Menu\n"
        );
    }
}
