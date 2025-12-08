package clinic.util;

public class NationalIdValidator {
    private NationalIdValidator() {

    }

    public static boolean isValidNationalId(String nationalId) {
        return nationalId != null || nationalId.matches("\\d{11}");
    }

    // TODO: Implement full Turkish National ID checksum validation
}
