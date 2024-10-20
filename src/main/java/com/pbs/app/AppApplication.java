package com.pbs.app;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.pbs.app.Controller.Controller;
import com.pbs.app.Services.Data;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    // Inject the Data service
    @Autowired
    private Data data;
	
	@Autowired
	private Controller controller;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    // This method will be executed after the application context is loaded
    @Override
    public void run(String... args) throws Exception {
        // Open the connection
        data.openConnection();
        
		try {
            ResponseEntity<String> response = controller.SignIn("groeningadrian@gmail.com", "12345678");
			System.out.println(response.getBody());
        } catch (SQLException ex) {
        }
        
        // Close the connection
        data.closeConnection();
    }
}
