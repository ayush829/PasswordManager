package test.security;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import security.PasswordGenerator;

public class PasswordGeneratorTest {

    @Test
    void testGeneratePassword() {
        String password = PasswordGenerator.generate(12);

        assertNotNull(password);
        assertEquals(12, password.length());
    }
}
