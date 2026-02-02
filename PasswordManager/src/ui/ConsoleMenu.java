package ui;
import java.util.List;
import java.util.Scanner;

import model.User;
import model.PasswordEntry;
import service.UserService;
import service.PasswordService;
import security.EncryptionUtil;
import security.PasswordGenerator;
import security.OTPUtil;




public class ConsoleMenu {

    private UserService userService = new UserService();
    private PasswordService passwordService = new PasswordService();
    private Scanner sc = new Scanner(System.in);

    // ---------------- MAIN MENU ----------------
    public void start() {
        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("4. Forgot Password");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                case 4 -> forgotPassword();
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ---------------- REGISTER ----------------
private void register() {
    System.out.print("Name: ");
    String name = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    System.out.print("Master Password: ");
    String password = sc.nextLine();

    System.out.print("Security Question: ");
    String question = sc.nextLine();

    System.out.print("Security Answer: ");
    String answer = sc.nextLine();

    boolean success = userService.register(
            name,
            email,
            password,
            question,
            answer
    );

    if (success) {
        System.out.println("Registration successful");
    } else {
        System.out.println("Registration failed");
    }
}


    // ---------------- LOGIN ----------------
    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Master Password: ");
        String password = sc.nextLine();

        User user = userService.login(email, password);
        if (user != null) {
            System.out.println("Welcome " + user.getName());
            userMenu(user);
        } else {
            System.out.println("Invalid credentials");
        }
    }

    // ---------------- USER MENU ----------------
    private void userMenu(User user) {
        while (true) {
            System.out.println("\n1. Add Password");
            System.out.println("2. List Accounts");
            System.out.println("3. View Password (Re-verification)");
            System.out.println("4. Update Password");
            System.out.println("5. Delete Password");
            System.out.println("6. Search Account");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addPassword(user);
                case 2 -> listPasswords(user);
                case 3 -> viewPassword(user);
                case 4 -> updatePassword(user);
                case 5 -> deletePassword(user);
                case 6 -> searchPassword(user);
                case 7 -> updateProfile(user);
                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ---------------- ADD PASSWORD (WITH GENERATOR) ----------------
    private void addPassword(User user) {
        System.out.print("Account Name: ");
        String account = sc.nextLine();

        System.out.print("Username: ");
        String uname = sc.nextLine();

        System.out.print("Generate strong password? (yes/no): ");
        String choice = sc.nextLine();

        String pass;

        if (choice.equalsIgnoreCase("yes")) {
            pass = PasswordGenerator.generate(12);
            System.out.println("Generated Password: " + pass);
        } else {
            System.out.print("Enter Password: ");
            pass = sc.nextLine();
        }

        if (passwordService.addPassword(user.getUserId(), account, uname, pass)) {
            System.out.println("Password saved successfully");
        } else {
            System.out.println("Failed to save password");
        }
    }

    // ---------------- LIST PASSWORDS (MASKED) ----------------
    private void listPasswords(User user) {
        var list = passwordService.listPasswords(user.getUserId());

        if (list.isEmpty()) {
            System.out.println("No passwords saved");
            return;
        }

        for (PasswordEntry pe : list) {
            System.out.println(
                "Entry ID: " + pe.getEntryId() +
                " | Account: " + pe.getAccountName() +
                " | Username: " + pe.getUsername() +
                " | Password: ******"
            );
        }
    }

    // ---------------- VIEW PASSWORD WITH RE-VERIFICATION ----------------
private void viewPassword(User user) {
    listPasswords(user);

    System.out.print("Enter Entry ID to view password: ");
    int entryId = sc.nextInt();
    sc.nextLine();

    // 🔐 Step 1: Master password re-verification
    System.out.print("Re-enter master password: ");
    String masterPassword = sc.nextLine();

    User verifiedUser = userService.login(user.getEmail(), masterPassword);
    if (verifiedUser == null) {
        System.out.println("Re-verification failed");
        return;
    }

    // 🔐 Step 2: OTP verification
    String otp = OTPUtil.generateOTP();
    System.out.println("Verification code (OTP): " + otp); // simulated send

    System.out.print("Enter OTP: ");
    String enteredOTP = sc.nextLine();

    if (!OTPUtil.verifyOTP(enteredOTP)) {
        System.out.println("Invalid OTP");
        return;
    }

    OTPUtil.expireOTP(); // expire after use

    // 🔐 Step 3: Show password
    var list = passwordService.listPasswords(user.getUserId());
    for (PasswordEntry pe : list) {
        if (pe.getEntryId() == entryId) {
            System.out.println(
                "Password: " + EncryptionUtil.decrypt(pe.getPassword())
            );
            return;
        }
    }

    System.out.println("Invalid Entry ID");
}


    // ---------------- UPDATE PASSWORD ----------------
    private void updatePassword(User user) {

        listPasswords(user); // show entry IDs first

        System.out.print("Enter Entry ID to update: ");
        int entryId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        if (passwordService.updatePassword(entryId, newPassword)) {
            System.out.println("Password updated successfully");
        } else {
            System.out.println("Failed to update password");
        }
    }


    // ---------------- DELETE PASSWORD ----------------
    private void deletePassword(User user) {

        listPasswords(user);

        System.out.print("Enter Entry ID to delete: ");
        int entryId = sc.nextInt();
        sc.nextLine();

        if (passwordService.deletePassword(entryId)) {
            System.out.println("Password deleted successfully");
        } else {
            System.out.println("Failed to delete password");
        }
    }

    // ---------------- SEARCH PASSWORD ----------------
    private void searchPassword(User user) {

        System.out.print("Enter account name to search: ");
        String keyword = sc.nextLine();

        List<PasswordEntry> list =
                passwordService.searchPasswords(user.getUserId(), keyword);

        if (list.isEmpty()) {
            System.out.println("No matching accounts found");
            return;
        }

        for (PasswordEntry pe : list) {
            System.out.println(
                    "Entry ID: " + pe.getEntryId() +
                            " | Account: " + pe.getAccountName() +
                            " | Username: " + pe.getUsername() +
                            " | Password: ******"
            );
        }
    }

    // ---------------- UPDATE PROFILE ----------------
    private void updateProfile(User user) {
        System.out.print("Enter new name: ");
        String newName = sc.nextLine();

        System.out.print("Enter new email: ");
        String newEmail = sc.nextLine();

        if (userService.updateProfile(user.getUserId(), newName, newEmail)) {
            System.out.println("Profile updated successfully");
            user.setName(newName);
            user.setEmail(newEmail);
        } else {
            System.out.println("Profile update failed");
        }
    }

    private void forgotPassword() {
    System.out.print("Enter registered email: ");
    String email = sc.nextLine();

    User user = userService.getUserByEmail(email);
    if (user == null) {
        System.out.println("User not found");
        return;
    }

    System.out.println("Security Question: " + user.getSecurityQuestion());
    System.out.print("Answer: ");
    String answer = sc.nextLine();

    if (!userService.verifySecurityAnswer(user, answer)) {
        System.out.println("Wrong answer");
        return;
    }

    System.out.print("Enter NEW master password: ");
    String newPass = sc.nextLine();

    if (userService.resetMasterPassword(user.getUserId(), newPass)) {
        System.out.println("Master password reset successfully");
    } else {
        System.out.println("Password reset failed");
    }
}

}
