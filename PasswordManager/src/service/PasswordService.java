package service;

import dao.PasswordDAO;
import model.PasswordEntry;
import security.EncryptionUtil;

import java.util.List;

public class PasswordService {

    private PasswordDAO passwordDAO = new PasswordDAO();

    // -------- Add Password --------
    public boolean addPassword(int userId, String account, String username, String password) {
        String encrypted = EncryptionUtil.encrypt(password);
        return passwordDAO.addPassword(userId, account, username, encrypted);
    }

    // -------- List Passwords --------
    public List<PasswordEntry> listPasswords(int userId) {
        return passwordDAO.getAllPasswords(userId);
    }

    // -------- Update Password (FIXED) --------
    public boolean updatePassword(int entryId, String newPassword) {
        String encrypted = EncryptionUtil.encrypt(newPassword);
        return passwordDAO.updatePassword(entryId, encrypted);
    }

    // -------- Delete Password (USED BELOW) --------
    public boolean deletePassword(int entryId) {
        return passwordDAO.deletePassword(entryId);
    }

    // -------- Search Password --------
    public List<PasswordEntry> searchPasswords(int userId, String keyword) {
        return passwordDAO.searchByAccount(userId, keyword);
    }

}
