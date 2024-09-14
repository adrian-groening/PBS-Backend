package com.pbs.app;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.pbs.Entities.Product;
import com.pbs.app.ImpactSearch.ImpactSearch;



@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		SpringApplication.run(AppApplication.class, args);

		Controller controller = new Controller();

		ImpactSearch impactSearch = new ImpactSearch("car", "name");
		
		List<Product> products = impactSearch.toList();

		Gson gson = new Gson();

		String productJson = gson.toJson(products.get(1));

		System.out.println(productJson);

		ResponseEntity<String> response = controller.share("groeningadrian@gmail.com", "get", productJson);

		System.out.println("\n GET ___________________________ \n"+response.getBody());

		



		
	}

}