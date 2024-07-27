package com.pbs.app;



import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;



@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		SpringApplication.run(AppApplication.class, args);
        
		Controller controller = new Controller();



		System.out.println("Histories:");
		ResponseEntity<String> response = controller.History("groeningadrian@gmail.com", "get", "", "Thing", "cool");
		System.out.println(response.getBody());

		System.out.println("Favorites:");
		ResponseEntity<String> response2 = controller.Favs("groeningadrian@gmail.com", "get", "none");
		System.out.println(response2.getBody());

		



		
		

		//controller.Favs(email, action, productJson);


	}

}