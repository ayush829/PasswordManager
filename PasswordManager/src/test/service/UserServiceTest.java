package test.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import security.PasswordUtil;

public class UserServiceTest {

    @Test
    void testPasswordHashing() {
        String password = "Test@123";
        String hashed = PasswordUtil.hash(password);

        assertNotNull(hashed);
        assertNotEquals(password, hashed);
    }
}
