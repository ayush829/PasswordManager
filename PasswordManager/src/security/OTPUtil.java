package security;

import java.util.Random;

public class OTPUtil {

    private static String currentOTP;

    public static String generateOTP() {
        Random r = new Random();
        currentOTP = String.valueOf(100000 + r.nextInt(900000)); // 6-digit
        return currentOTP;
    }

    public static boolean verifyOTP(String input) {
        return currentOTP != null && currentOTP.equals(input);
    }

    public static void expireOTP() {
        currentOTP = null; 
    }
}
