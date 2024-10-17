package com.pbs.app;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.pbs.app.Entities.Creator;
import com.pbs.app.Services.AuthService;
import com.pbs.app.Services.Data;

class AuthServiceTests {

    private AuthService authService;
    private SCryptPasswordEncoder scryptPasswordEncoder;
    private Data data;

    @BeforeEach
    void setUp() {
        // Mock the Data class
        data = mock(Data.class);
        
        // Inject mock dependencies into AuthService
        authService = new AuthService(data); // Mocked data
        authService.scryptEncoder = new SCryptPasswordEncoder(16384, 8, 4, 32, 64); // Keep real encoder for tests
    }

    @Test
    void testEncode() {
        String rawPassword = "password123";
        String encodedPassword = authService.encode(rawPassword);

        // Ensure the encoded password is not null and different from the raw password
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    @Test
    void testVerify() {
        String rawPassword = "password123";
        String encodedPassword = authService.encode(rawPassword);

        // Ensure that verification passes for the correct password
        assertTrue(authService.verify(rawPassword, encodedPassword));

        // Ensure that verification fails for the wrong password
        assertFalse(authService.verify("wrongPassword", encodedPassword));
    }

    @Test
    void testLoginSuccess() throws SQLException {
        // Arrange
        String email = "john@example.com";
        String password = "password123";
        String encodedPassword = authService.encode(password);
        Creator creator = new Creator("C1", "John", "Doe", email, encodedPassword);

        // Mock the Data class's getCreator method to return a Creator with the same email
        when(data.getCreator(email)).thenReturn(creator);

        // Act & Assert
        assertTrue(authService.login(email, password));

        // Verify that openConnection and closeConnection are called
        verify(data, times(1)).openConnection();
        verify(data, times(1)).closeConnection();
    }

    @Test
    void testLoginFailureDueToIncorrectPassword() throws SQLException {
        // Arrange
        String email = "john@example.com";
        String password = "password123";
        String wrongPassword = "wrongPassword";
        String encodedPassword = authService.encode(password);
        Creator creator = new Creator("C1", "John", "Doe", email, encodedPassword);

        // Mock the Data class's getCreator method to return a Creator with the same email
        when(data.getCreator(email)).thenReturn(creator);

        // Act & Assert
        assertFalse(authService.login(email, wrongPassword));

        // Verify that openConnection and closeConnection are called
        verify(data, times(1)).openConnection();
        verify(data, times(1)).closeConnection();
    }

    @Test
    void testLoginFailureDueToNonExistentCreator() throws SQLException {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        // Mock the Data class's getCreator method to return null, simulating a non-existent user
        when(data.getCreator(email)).thenReturn(null);

        // Act & Assert
        assertFalse(authService.login(email, password));

        // Verify that openConnection and closeConnection are called
        verify(data, times(1)).openConnection();
        verify(data, times(1)).closeConnection();
    }
}
