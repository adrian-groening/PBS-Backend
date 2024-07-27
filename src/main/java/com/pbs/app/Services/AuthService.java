package com.pbs.app.Services;

import java.sql.SQLException;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.pbs.Entities.Creator;

public class AuthService {
    SCryptPasswordEncoder scryptEncoder;
    Data data;

    public AuthService() {  
        scryptEncoder = new SCryptPasswordEncoder(16384, 8, 4, 32, 64); 
        data = new Data();
    }

    public String encode(String text) {
        String encodedText = scryptEncoder.encode(text);
        System.out.println("Encoded Password: " + encodedText);
        return encodedText;
    }

    public boolean verify(String text, String encodedText) {
        return scryptEncoder.matches(text, encodedText);
    }

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
