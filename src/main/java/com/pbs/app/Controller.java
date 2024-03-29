package com.pbs.app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/endpoint")
    public String receiveStringFromFrontend(@RequestBody String stringData) {
        // Process the string data received from the frontend
        System.out.println("Received string from frontend: " + stringData);
        return "String received successfully";
    }
}
