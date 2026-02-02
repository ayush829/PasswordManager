package service;

import dao.UserDAO;
import model.User;
import security.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static final Logger logger =
            LogManager.getLogger(UserService.class);

    private UserDAO userDAO = new UserDAO();

    // ================= REGISTER USER =================
    public boolean register(String name, String email, String password,
                            String securityQuestion, String securityAnswer) {

        logger.info("Registration attempt for email: {}", email);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setMasterPassword(password);     // hashed in DAO
        user.setSecurityQuestion(securityQuestion);
        user.setSecurityAnswer(securityAnswer);

        boolean result = userDAO.register(user);

        if (result) {
            logger.info("User registered successfully: {}", email);
        } else {
            logger.warn("User registration failed for email: {}", email);
        }

        return result;
    }

    // ================= LOGIN USER =================
    public User login(String email, String password) {

        logger.info("Login attempt for email: {}", email);

        User user = userDAO.getUserByEmail(email);

        if (user != null &&
                PasswordUtil.hash(password)
                        .equals(user.getMasterPassword())) {

            logger.info("Login successful for email: {}", email);
            return user;
        }

        logger.warn("Invalid login attempt for email: {}", email);
        return null;
    }

    // ================= UPDATE PROFILE =================
    public boolean updateProfile(int userId, String name, String email) {

        logger.info("Profile update attempt for userId: {}", userId);

        boolean result = userDAO.updateProfile(userId, name, email);

        if (result) {
            logger.info("Profile updated successfully for userId: {}", userId);
        } else {
            logger.warn("Profile update failed for userId: {}", userId);
        }

        return result;
    }

    // ================= FORGOT PASSWORD SUPPORT =================

    // Get user by email
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    // Verify security answer (HASHED comparison)
    public boolean verifySecurityAnswer(User user, String answer) {
        return PasswordUtil.hash(answer)
                .equals(user.getSecurityAnswer());
    }

    // Reset master password
    public boolean resetMasterPassword(int userId, String newPassword) {

        logger.info("Resetting master password for userId: {}", userId);

        return userDAO.updateMasterPassword(userId, newPassword);
    }
}
