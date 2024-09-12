package com.pbs.app;



import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pbs.app.Services.Data;



@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		SpringApplication.run(AppApplication.class, args);

		Data data = new Data();
		data.openConnection();

		//for (Favorite favorite : data.get5MostFavoritedProducts()) {
		//	System.out.println(favorite.getProductID());
		//}

		//System.out.println(data.getMostRecentFavorite().getProductID());
        
		Controller controller = new Controller();

		//ResponseEntity<String> response = controller.mostSharedCategories();
		//System.out.println(response.getBody());

		System.out.println(data.getProduct("P2085239638").getLogoURI());
		


		


		//System.out.println();

	}

}