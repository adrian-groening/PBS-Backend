package com.pbs.app.Services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pbs.app.Entities.Creator;

@Service
public class AuthService {
    public SCryptPasswordEncoder scryptEncoder;

    @Autowired
    Data data;

    // Constructor
    public AuthService(Data data) {  
        scryptEncoder = new SCryptPasswordEncoder(16384, 8, 4, 32, 64);
        this.data = data;
    }

    // Encode password
    public String encode(String text) {
        String encodedText = scryptEncoder.encode(text);
        System.out.println("Encoded Password: " + encodedText);
        return encodedText;
    }

    // Verify password
    public boolean verify(String text, String encodedText) {
        return scryptEncoder.matches(text, encodedText);
    }

    // Login
    public boolean login(String email, String password) throws SQLException {
        data.openConnection();
        Creator creator = data.getCreator(email);
        if (creator == null) {
            return false;
        } else {
            System.out.println("Creator: " + creator.getEmail());
        }
        data.closeConnection();

        return verify(password, creator.getPassword());
    }
}
