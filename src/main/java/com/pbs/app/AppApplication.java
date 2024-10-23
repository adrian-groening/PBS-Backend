package com.pbs.app;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.pbs.app.Controller.Controller;
import com.pbs.app.Services.Data;

@SpringBootApplication
public class AppApplication {

    @Autowired
    private Controller controller;

    @Autowired
    private Data data;

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(AppApplication.class, args);
    }

    // After application starts, you can call controller methods if needed
    @Autowired
    public void runAfterStartup() throws IOException, InterruptedException, Exception {
        try {
            ResponseEntity<String> response = controller.name("headphones", "price", "groeningadrian@gmail.com");
            System.out.println(response.getBody());

        } catch (SQLException e) {
            System.err.println("Error during execution: " + e.getMessage());
        }
    }
}
