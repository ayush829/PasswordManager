package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.PasswordEntry;
import security.EncryptionUtil;
import util.DBConnection;

public class PasswordDAO {

    public boolean addPassword(int userId, String account, String username, String encryptedPassword) {
        String sql = """
        INSERT INTO password_entries
        (entry_id, user_id, account_name, username, encrypted_password)
        VALUES (password_entries_seq.NEXTVAL, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, account);
            ps.setString(3, username);
            ps.setString(4, encryptedPassword);

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<PasswordEntry> getAllPasswords(int userId) {
        List<PasswordEntry> list = new ArrayList<>();

        String sql = """
        SELECT entry_id, account_name, username, encrypted_password
        FROM password_entries
        WHERE user_id = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PasswordEntry p = new PasswordEntry();
                p.setEntryId(rs.getInt("entry_id"));
                p.setAccountName(rs.getString("account_name"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("encrypted_password")); // ✅ FIX
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public boolean updatePassword(int entryId, String encryptedPassword) {

        String sql = """
        UPDATE password_entries
        SET encrypted_password = ?
        WHERE entry_id = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, encryptedPassword);
            ps.setInt(2, entryId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PasswordEntry> searchByAccount(int userId, String keyword) {
        List<PasswordEntry> list = new ArrayList<>();

        String sql = """
        SELECT entry_id, account_name, username, encrypted_password
        FROM password_entries
        WHERE user_id = ?
          AND LOWER(account_name) LIKE ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, "%" + keyword.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PasswordEntry p = new PasswordEntry();
                p.setEntryId(rs.getInt("entry_id"));
                p.setAccountName(rs.getString("account_name"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("encrypted_password")); // ✅ FIX
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean deletePassword(int entryId) {

        String sql = "DELETE FROM password_entries WHERE entry_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entryId);
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




}
