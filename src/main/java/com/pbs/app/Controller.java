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
import com.pbs.Entities.Scan;
import com.pbs.Entities.Share;
import com.pbs.Entities.AnalyticsData;
import com.pbs.app.EbaySearch.EbaySearch;
import com.pbs.app.ImpactSearch.ImpactSearch;
import com.pbs.app.Llama.AI;
import com.pbs.app.Repositories.AppProductList;
import com.pbs.app.Services.AuthService;
import com.pbs.app.Services.Data;


@RestController
public class Controller {
    Data data = new Data();

    //retrieves barcodes from ebay and impact apis
    @PostMapping("/barcode")
    public ResponseEntity<String> barcode(@RequestBody String stringData, @RequestParam(required = false, defaultValue = "value") String sortAttribute) throws IOException, InterruptedException, SQLException {

        data.openConnection();

        System.out.println("Received string from frontend: " + stringData);
        String cleanBarcode = stringData.replace("\"", "");

        ImpactSearch impactResults = new ImpactSearch(cleanBarcode, "barcode");
        EbaySearch ebayResults = new EbaySearch(cleanBarcode, "barcode");
    
        AppProductList appProducts = new AppProductList();
    
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

        data.closeConnection();

        //sends products to frontend
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping("/name")
    public ResponseEntity<String> name(@RequestBody String stringData, @RequestParam(required = false, defaultValue = "value") String sortAttribute) throws IOException, InterruptedException, SQLException {

        data.openConnection();

        //remove quotation mark from string
        System.out.println("Received string from frontend: " + stringData);
        String name = stringData.replace("\"", "");

        // Queries ebay and impact results
        ImpactSearch impactResults = new ImpactSearch(name, "name");
        EbaySearch ebayResults = new EbaySearch(name, "name");
    
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

        data.closeConnection();

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
            } else if (!data.productWebURLExists(product.getWebURL())){
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
                data.insertFavorite(new Favorite("F" + random, creator.getCreatorID(), product.getProductID(), java.time.LocalDate.now().toString()));
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
        List<Scan> history = null;

        if (action.equals("add")) {
            String todaysDate = java.time.LocalDate.now().toString();
            Random  rand = new Random();
            data.insertScanHistory(new Scan("SH" + rand.nextInt(1000000), creator.getCreatorID(), barcode, todaysDate, name, photo));
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

    @PostMapping("/share")
    public ResponseEntity<String> share(@RequestParam(required = true, defaultValue = "none") String email, String action, String product) throws SQLException {
        data.openConnection();
        Creator creator = data.getCreator(email);
        
        if (action.equals("add")) {
            Share share = new Share();
            share.setCreatorID(creator.getCreatorID());
            share.generateDateOfShare();
            share.generateShareID();

            Product productObj = new Gson().fromJson(product, Product.class);
            
            //check if its in database
            if (!data.productBarcodeExists(productObj.getBarcode())) {
                data.insertProduct(productObj);
            } else if (!data.productNameExists(productObj.getName())) {
                data.insertProduct(productObj);
            } else if (!data.productImageURLExists(productObj.getImageURL())) {
                data.insertProduct(productObj);
            } else if (!data.productWebURLExists(productObj.getWebURL())){
                data.insertProduct(productObj);
            } else {
                System.out.println("Product exists");
                productObj = data.getProductUsingImage(productObj.getImageURL());
            }

            share.setProductID(productObj.getProductID());
            data.insertShare(share);

            data.closeConnection();

            return ResponseEntity.status(HttpStatus.OK).body("Share added");

        } else if (action.equals("get")) {
            List<Share> shares = data.getShares(creator.getCreatorID());
            List<Product> products = new ArrayList<>();
            for (Share share : shares) {
                products.add(data.getProduct(share.getProductID()));
            }
            Gson gson = new Gson();
            String json = gson.toJson(products);   
            data.closeConnection();
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            data.closeConnection();
            return ResponseEntity.status(HttpStatus.OK).body("No action");
        }
    }


    //analytics methods

    //exclude
    @PostMapping("/mostrecentscan")
    public ResponseEntity<String> mostRecentScan() throws SQLException {
        data.openConnection();
        Scan recentScan = data.getMostRecentScan();

        Gson gson = new Gson();
        String json = gson.toJson(recentScan);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //inlcude
    @PostMapping("/rankingofmostscanned")
    public ResponseEntity<String> rankingOfMostScanned() throws SQLException {
        data.openConnection();
        List<Scan> mostScanned = data.get5MostScannedProducts();

        Gson gson = new Gson();
        String json = gson.toJson(mostScanned);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //for next iteration
    @PostMapping("/numberofscansforproduct")
    public ResponseEntity<String> numberOfScansForProduct(@RequestParam(required = true, defaultValue = "none") String barcode) throws SQLException {
        data.openConnection();
        int numberOfScans = data.getProductScanCount(barcode);

        Gson gson = new Gson();
        String json = gson.toJson(numberOfScans);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //for next iteration
    @PostMapping("/productfavoritecount")
    public ResponseEntity<String> productFavoriteCount(@RequestParam(required = true, defaultValue = "none") String productID) throws SQLException {
        data.openConnection();
        int favoriteCount = data.getProductFavoriteCount(productID);
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(favoriteCount));
    }

    //inlcude
    @PostMapping("/rankingofmostfavorited")
    public ResponseEntity<String> rankingOfMostFavorited() throws SQLException {
        data.openConnection();
        List<Favorite> mostFavoritedList = data.get5MostFavoritedProducts();
        List<Product> mostFavoritedProductList = new ArrayList();


        for (Favorite favorite : mostFavoritedList) {
            Product fav = data.getProduct(favorite.getProductID());
            mostFavoritedProductList.add(fav);
        }

        Gson gson = new Gson();
        String json = gson.toJson(mostFavoritedProductList);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //exclude
    @PostMapping("/mostrecentfavorite")
    public ResponseEntity<String> mostRecentFavorite() throws SQLException {
        data.openConnection();
        Favorite recentFavorite = data.getMostRecentFavorite();
        Product product = data.getProduct(recentFavorite.getProductID());

        Gson gson = new Gson();
        String json = gson.toJson(product);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //for next iteration
    @PostMapping("/productsharecount")
    public ResponseEntity<String> productShareCount(@RequestParam(required = true, defaultValue = "none") String productID) throws SQLException {
        data.openConnection();
        int shareCount = data.getProductShareCount(productID);
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(shareCount));
    }

    //inlcude
    @PostMapping("/rankingofmostshared")
    public ResponseEntity<String> rankingOfMostShared() throws SQLException {
        data.openConnection();

        List<Share> mostSharedList = data.get5MostSharedProducts();

        List<Product> mostSharedProductList = new ArrayList();

        for (Share share : mostSharedList) {
            Product fav = data.getProduct(share.getProductID());
            mostSharedProductList.add(fav);
        }

        Gson gson = new Gson();
        String json = gson.toJson(mostSharedProductList);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //exclude
    @PostMapping("/mostrecentshare")
    public ResponseEntity<String> mostRecentShare() throws SQLException {
        data.openConnection();
        Share recentShare = data.getMostRecentShare();
        Product product = data.getProduct(recentShare.getProductID());

        Gson gson = new Gson();
        String json = gson.toJson(product);   

        data.closeConnection();

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    //include
    @PostMapping("/creatorscancount")
    public ResponseEntity<String> creatorsScanCount(@RequestParam(required = true, defaultValue = "none") String email) throws SQLException {
        data.openConnection();
        Creator creator = data.getCreator(email);
        int scanCount = data.getCreatorScanCount(creator.getCreatorID());
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(scanCount));
    }

    //for next iteration
    @PostMapping("/creatorfavoritecount")
    public ResponseEntity<String> creatorsFavoriteCount(@RequestParam(required = true, defaultValue = "none") String email) throws SQLException {
        data.openConnection();
        Creator creator = data.getCreator(email);
        int favoriteCount = data.getCreatorFavoriteCount(creator.getCreatorID());
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(favoriteCount));
    }


    //include
    @PostMapping("/creatorsharecount")
    public ResponseEntity<String> creatorsShareCount(@RequestParam(required = true, defaultValue = "none") String email) throws SQLException {
        data.openConnection();
        Creator creator = data.getCreator(email);
        int shareCount = data.getCreatorShareCount(creator.getCreatorID());
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(shareCount));
    }

    //exclude
    @PostMapping("/mostscannedbrand")
    public ResponseEntity<String> mostScannedBrand() throws SQLException {
        data.openConnection();
        String mostScannedBrand = data.getMostScannedBrand();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostScannedBrand);
    }

    //exclude
    @PostMapping("/mostfavoritedbrand")
    public ResponseEntity<String> mostFavoritedBrand() throws SQLException {
        data.openConnection();
        String mostFavoritedBrand = data.getMostFavoritedBrand();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostFavoritedBrand);
    }

    //exclude
    @PostMapping("/mostsharedbrand")
    public ResponseEntity<String> mostSharedBrand() throws SQLException {
        data.openConnection();
        String mostSharedBrand = data.getMostSharedBrand();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostSharedBrand);
    }

    //exclude
    @PostMapping("/mostscannedcategory")
    public ResponseEntity<String> mostScannedCategory() throws SQLException {
        data.openConnection();
        String mostScannedCategory = data.getMostScannedCategory();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostScannedCategory);
    }

    //exclude
    @PostMapping("/mostfavoritedcategory")
    public ResponseEntity<String> mostFavoritedCategory() throws SQLException {
        data.openConnection();
        String mostFavoritedCategory = data.getMostFavoritedCategory();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostFavoritedCategory);
    }

    //exclude
    @PostMapping("/mostsharedcategory")
    public ResponseEntity<String> mostSharedCategory() throws SQLException {
        data.openConnection();
        String mostSharedCategory = data.getMostSharedCategory();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostSharedCategory);
    }

    //exclude
    @PostMapping("/totalscancount")
    public ResponseEntity<String> totalScanCount() throws SQLException {
        data.openConnection();
        int totalScanCount = data.getTotalScanCount();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(totalScanCount));
    }

    //exclude
    @PostMapping("/totalfavoritecount")
    public ResponseEntity<String> totalFavoriteCount() throws SQLException {
        data.openConnection();
        int totalFavoriteCount = data.getTotalFavoriteCount();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(totalFavoriteCount));
    }

    //exclude
    @PostMapping("/totalsharecount")
    public ResponseEntity<String> totalShareCount() throws SQLException {
        data.openConnection();
        int totalShareCount = data.getTotalShareCount();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(totalShareCount));
    }

    //include
    @PostMapping("/mostscannedbrands")
    public ResponseEntity<String> mostScannedBrands() throws SQLException {
        data.openConnection();
        List<String> mostScannedBrands = data.get5MostScannedBrands();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostScannedBrands.toString());
    }

    //include
    @PostMapping("/mostfavoritedbrands")
    public ResponseEntity<String> mostFavoritedBrands() throws SQLException {
        data.openConnection();
        List<String> mostFavoritedBrands = data.get5MostFavoritedBrands();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostFavoritedBrands.toString());
    }

    //include
    @PostMapping("/mostsharedbrands")
    public ResponseEntity<String> mostSharedBrands() throws SQLException {
        data.openConnection();
        List<String> mostSharedBrands = data.get5MostSharedBrands();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostSharedBrands.toString());
    }

    //include
    @PostMapping("/mostscannedcategories")
    public ResponseEntity<String> mostScannedCategories() throws SQLException {
        data.openConnection();
        List<String> mostScannedCategories = data.get5MostScannedCategories();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostScannedCategories.toString());
    }

    //include
    @PostMapping("/mostfavoritedcategories")
    public ResponseEntity<String> mostFavoritedCategories() throws SQLException {
        data.openConnection();
        List<String> mostFavoritedCategories = data.get5MostFavoritedCategories();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostFavoritedCategories.toString());
    }

    //include
    @PostMapping("/mostsharedcategories")
    public ResponseEntity<String> mostSharedCategories() throws SQLException {
        data.openConnection();
        List<String> mostSharedCategories = data.get5MostSharedCategories();
        data.closeConnection();
        return ResponseEntity.status(HttpStatus.OK).body(mostSharedCategories.toString());
    }

    @PostMapping("/generateInstagramCaptionForProduct")
    public ResponseEntity<String> generateInstagramCaptionForProduct(@RequestBody String json) throws Exception {
        AI ai = new AI();
        return ResponseEntity.status(HttpStatus.OK).body(ai.generateInstagramCaptionForProduct(json));
    }
    
    @PostMapping("/analytics")
    public ResponseEntity<String> getAnalytics(@RequestParam(required = true, defaultValue = "none") String email) throws SQLException {
        data.openConnection();
        Creator creator = data.getCreator(email);
        
        //totalScans
        String scanCount = String.valueOf(data.getCreatorScanCount(creator.getCreatorID()));
        
        //totalShares
        String shareCount = String.valueOf(data.getCreatorShareCount(creator.getCreatorID()));
        
        //most scanned products
        List<String> mostScanned = data.getMostScannedProductString();
        
        //most favourited products
        List<Favorite> mostFavoritedList = data.get5MostFavoritedProducts();
        List<String> mostFavoritedProductList = new ArrayList();


        for (Favorite favorite : mostFavoritedList) {
            Product fav = data.getProduct(favorite.getProductID());
            mostFavoritedProductList.add(fav.getName());
        }

         //List<Share> mostSharedList = data.get5MostSharedProducts();
        
        //most scanned brands
        List<String> mostScannedBrands = data.get5MostScannedBrands();
        
        //most favourited brands
        List<String> mostFavoritedBrands = data.get5MostFavoritedBrands();
        
        //most shared brands
        List<String> mostSharedBrands = data.get5MostSharedBrands();
        
        //most scanned categories
        List<String> mostScannedCategories = data.get5MostScannedCategories();
        
        //most favourited categories
        List<String> mostFavoritedCategories = data.get5MostFavoritedCategories();
        
        //most shared categories
        List<String> mostSharedCategories = data.get5MostSharedCategories();
        
        
        AnalyticsData analytics = new AnalyticsData(scanCount, shareCount, mostScanned, mostFavoritedProductList, mostScannedBrands, mostFavoritedBrands, mostSharedBrands, mostScannedCategories,mostFavoritedCategories, mostSharedCategories);
        
        Gson gson = new Gson();
        String json = gson.toJson(analytics);
        
        
        data.closeConnection(); 
         
         
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


}    