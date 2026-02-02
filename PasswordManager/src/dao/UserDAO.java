package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import security.PasswordUtil;
import util.DBConnection;

public class UserDAO {

    // register user
    public boolean register(User user) {
        String sql = """
        INSERT INTO users
        (user_id, name, email, master_password_hash, security_question, security_answer_hash)
        VALUES (users_seq.NEXTVAL, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hash(user.getMasterPassword()));
            ps.setString(4, user.getSecurityQuestion());
            ps.setString(5, PasswordUtil.hash(user.getSecurityAnswer()));

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    // login user
    public User login(String email, String hashedPassword) {
        String sql = "SELECT * FROM users WHERE email=? AND master_password=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(int userId, String name, String email) {
        String sql = "UPDATE users SET name=?, email=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            System.out.println("Email already exists or update failed");
        }
        return false;
    }

    public User getUserByEmail(String email) {

        String sql = """
        SELECT user_id,
               name,
               email,
               master_password_hash,
               security_question,
               security_answer_hash
        FROM users
        WHERE email = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMasterPassword(rs.getString("master_password_hash")); // ✅ FIX
                user.setSecurityQuestion(rs.getString("security_question"));
                user.setSecurityAnswer(rs.getString("security_answer_hash"));  // ✅ FIX
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public boolean verifySecurityAnswer(int userId, String answer) {
        String sql = "SELECT security_answer FROM users WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("security_answer")
                        .equalsIgnoreCase(answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetPassword(int userId, String newPassword) {
        String sql = "UPDATE users SET master_password=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hash(newPassword));
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return false;
    }

    public boolean updateMasterPassword(int userId, String newPassword) {
        String sql = "UPDATE users SET master_password_hash = ? WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hash(newPassword));
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}