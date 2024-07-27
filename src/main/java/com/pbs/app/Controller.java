package com.pbs.app;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pbs.Entities.Creator;
import com.pbs.Entities.Favorite;
import com.pbs.Entities.Product;
import com.pbs.Entities.ScanHistory;
import com.pbs.app.EbaySearch.EbaySearch;
import com.pbs.app.ImpactSearch.ImpactSearch;
import com.pbs.app.Repositories.AppProductList;
import com.pbs.app.Services.AuthService;
import com.pbs.app.Services.Data;


@RestController
public class Controller {
    Data data = new Data();

    //retrieves barcodes from ebay and impact apis
    @PostMapping("/barcode")
    public ResponseEntity<String> barcode(@RequestBody String stringData, @RequestParam(required = false, defaultValue = "value") String sortAttribute) throws IOException, InterruptedException {

        //remove quotation mark from string
        System.out.println("Received string from frontend: " + stringData);
        String cleanBarcode = stringData.replace("\"", "");

        // Queries ebay and impact results
        ImpactSearch impactResults = new ImpactSearch(cleanBarcode, "barcode");
        EbaySearch ebayResults = new EbaySearch(cleanBarcode, "barcode");
    
        // Create an app product list of products to store results
        AppProductList appProducts = new AppProductList();
    
        //System.out.println("Impact API returned " + impactResults.toList().size() + " products");
        //System.out.println("Ebay API returned " + ebayResults.toList().size() + " products");

        //adds impact products to app product list if not null
        if (impactResults.toList() != null) {
            appProducts.addProducts(impactResults.toList());
        } else {
            System.out.println("Impact API returned null or empty list");
        }
    
        //adds ebay products to app product list if not null
        if (ebayResults.toList() != null) {
            appProducts.addProducts(ebayResults.toList());
        } else {
            System.out.println("Ebay API returned null or empty list");
        }


        //converts the prices of products to USD
        appProducts.convertToUSD();

        //sort the list of products by the specified attribute
        switch (sortAttribute) {
            case "price" -> appProducts.sortByPrice();
            case "commission" -> appProducts.sortByCommission();
            case "value" -> appProducts.sortByValue();
            default -> {
            }
        }
        
        //converts the app product list to json string format
        Gson gson = new Gson();
        String json = gson.toJson(appProducts.getProducts());
        System.out.println(json);

        //dispalys pretty json in terminal
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json);
        String prettyJsonString = prettyGson.toJson(je);
        System.out.println(prettyJsonString);

        //sends products to frontend
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping("/name")
    public ResponseEntity<String> name(@RequestBody String stringData, @RequestParam(required = false, defaultValue = "value") String sortAttribute) throws IOException, InterruptedException {

        //remove quotation mark from string
        System.out.println("Received string from frontend: " + stringData);
        String name = stringData.replace("\"", "");

        // Queries ebay and impact results
        ImpactSearch impactResults = new ImpactSearch(name, "name");
        EbaySearch ebayResults = new EbaySearch(name, "name");
    
        // Create an app product list of products to store results
        AppProductList appProducts = new AppProductList();
    
        System.out.println("Impact API returned " + impactResults.toList().size() + " products");
        System.out.println("Ebay API returned " + ebayResults.toList().size() + " products");

        //adds impact products to app product list if not null
        if (impactResults.toList() != null) {
            appProducts.addProducts(impactResults.toList());
        } else {
            System.out.println("Impact API returned null or empty list");
        }
    
        //adds ebay products to app product list if not null
        if (ebayResults.toList() != null) {
            appProducts.addProducts(ebayResults.toList());
        } else {
            System.out.println("Ebay API returned null or empty list");
        }


        //converts the prices of products to USD
        appProducts.convertToUSD();

        //sort the list of products by the specified attribute
        switch (sortAttribute) {
            case "price" -> appProducts.sortByPrice();
            case "commission" -> appProducts.sortByCommission();
            case "value" -> appProducts.sortByValue();
            default -> {
            }
        }
        
        //converts the app product list to json string format
        Gson gson = new Gson();
        String json = gson.toJson(appProducts.getProducts());
        System.out.println(json);

        //dispalys pretty json in terminal
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json);
        String prettyJsonString = prettyGson.toJson(je);
        System.out.println(prettyJsonString);

        //sends products to frontend
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping("/favorites")
    public ResponseEntity<String> Favs(@RequestParam(required = true) String email, String action, String productJson) throws IOException, InterruptedException, SQLException {
        //open database connection
        data.openConnection();
        
        //fetch creator and their favs from database
        Creator creator = data.getCreator(email);

        List<Favorite> favs;
        List<Product> products = null;
        String json;

        if (action.equals("add")) {
            //inserts product to database
            Product product = new Gson().fromJson(productJson, Product.class);

            //inserts product if it does not exist in database
            if (!data.productBarcodeExists(product.getBarcode())) {
                data.insertProduct(product);
            } else if (!data.productNameExists(product.getName())) {
                data.insertProduct(product);
            } else if (!data.productImageURLExists(product.getImageURL())) {
                data.insertProduct(product);
            } else {
                System.out.println("Product already exists in database");
                product = data.getProductUsingImage(product.getImageURL());
            }

            //randomly generates id
            Random  rand = new Random();
            int random = rand.nextInt(1000);

            //inserts favs to database
            if (data.favoriteExists(creator.getCreatorID(), product.getProductID())) {
                System.out.println("Favorite already exists in database");
            } else {
                data.insertFavorite(new Favorite("F" + random, creator.getCreatorID(), product.getProductID()));
            }
    
        } else if (action.equals("get")) {
            //gets favs from database
            favs = data.getFavorites(creator.getCreatorID());
            //creates a list of products to store favorites
            products = new ArrayList<>();
            //iterates through favs and adds those products to list
            for (Favorite fav : favs) {
                products.add(data.getProduct(fav.getProductID()));
            }
        }

        //converts the list of favorites to json string format
        Gson gson = new Gson();
        json = gson.toJson(products);   

        data.closeConnection();

        //sends the favorites to the front end
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping("/history")
    public ResponseEntity<String> History(@RequestParam(required = true, defaultValue = "none") String email, String action, String barcode, String name, String photo) throws IOException, InterruptedException, SQLException {
        //open database connection
        data.openConnection();
        Creator creator = data.getCreator(email);
        List<ScanHistory> history = null;

        if (action.equals("add")) {
            String todaysDate = java.time.LocalDate.now().toString();
            Random  rand = new Random();
            data.insertScanHistory(new ScanHistory("SH" + rand.nextInt(100000), creator.getCreatorID(), barcode, todaysDate, name, photo));
        } else if (action.equals("get")) {
        //fetch favs from database
            history = data.getScanHistories(creator.getCreatorID());
        }
        
        //converts the list of favorites to json string format
        Gson gson = new Gson();
        String json = gson.toJson(history);   

        data.closeConnection();

        //sends the favorites to the front end
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestParam(required = true, defaultValue = "none") String email, String password, String firstName, String surname) throws IOException, InterruptedException, SQLException {
        String response;
        
        //open database connection
        data.openConnection();

        //if email exists no data is added else data is creator is added
        if (data.creatorEmailExists(email)) {
            response = "Email already exists";
        } else  {
            AuthService authService = new AuthService();
            String pass = authService.encode(password);

            String creatorID = "C" + data.getCreatorCount();

            if (data.creatorIDExists(creatorID)) {
                Random  rand = new Random();
                int random = rand.nextInt(1000);
                creatorID = "C" + (data.getCreatorCount() + random);
            }

            Creator creator = new Creator(creatorID,firstName, surname, email, pass);

            data.insertCreator(creator);  

            response = "Account created";
        }

        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> SignIn(@RequestParam(required = true, defaultValue = "none") String email, String password) throws IOException, InterruptedException, SQLException {
        String response;
        
        //open database connection
        AuthService authService = new AuthService();

        if (authService.login(email, password)) {
            response = "Success";
        } else {
            response = "Failure";
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    



















































    //tests and sandbox: disregard 

    public String barcodeTest() throws IOException, InterruptedException {



        //remove quotation mark from string
        String cleanBarcode = "5702017155630";

        // Queries ebay and impact results
        ImpactSearch impactResults = new ImpactSearch(cleanBarcode, "barcode");
        EbaySearch ebayResults = new EbaySearch(cleanBarcode, "barcode");
    
        // Create a list of products to store the results
        AppProductList appProducts = new AppProductList();


        //Map<String, Object> response = new HashMap<>();
    
        System.out.println("Impact API returned " + impactResults.toList().size() + " products");

        //print impact results to json
        Gson gson = new Gson();
        String json = gson.toJson(impactResults.toList());
        //System.out.println(json);

        //print pretty json
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json);
        String prettyJsonString = prettyGson.toJson(je);
        System.out.println(prettyJsonString);

        System.out.println("Ebay API returned " + ebayResults.toList().size() + " products");


        if (impactResults.toList() != null) {
            appProducts.addProducts(impactResults.toList());
        } else {
            System.out.println("Impact API returned null or empty list");
        }
    
        if (ebayResults.toList() != null) {
            appProducts.addProducts(ebayResults.toList());
        } else {
            System.out.println("Ebay API returned null or empty list");
        }

        System.out.println("Before Conversion");

        appProducts.sortByPrice();
        System.out.println("SORT BY PRICE");
        for (Product product : appProducts.getProducts()) {
            System.out.println(product.getName() + " Price: " + product.getPrice() + " "+product.getCurrency());
        }


        System.out.println("After Conversion");
        appProducts.convertToUSD();
        //sort the list of products by the specified attribute
        
        appProducts.sortByPrice();

        System.out.println("SORT BY PRICE");
        for (Product product : appProducts.getProducts()) {
            System.out.println(product.getName() + " Price: " + product.getPrice() + " "+product.getCurrency());
        }
        

        appProducts.sortByCommission();
        System.out.println("SORT BY COMMISSION");
        for (Product product : appProducts.getProducts()) {
            System.out.println(product.getName() + " " + product.getPrice() + " " + product.getCommissionRate());
        }
       
        appProducts.sortByValue();
        System.out.println("SORT BY VALUE");
        for (Product product : appProducts.getProducts()) {
            System.out.println(product.getName() + " " + product.getPrice() + " " + product.getCommissionRate());
        }
        
        
        //Gson gson = new Gson();
        //String json = gson.toJson(appProducts.getProducts());
        System.out.println(json);

     
    
        return json;
    }

}    