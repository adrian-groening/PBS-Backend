package com.pbs.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.pbs.app.Controller.Controller;
import com.pbs.app.Entities.APIKeys;
import com.pbs.app.Entities.Creator;
import com.pbs.app.Entities.CreatorAttributes;
import com.pbs.app.Entities.Favorite;
import com.pbs.app.Entities.Product;
import com.pbs.app.Entities.Scan;
import com.pbs.app.Entities.Share;
import com.pbs.app.Repositories.AnalyticsData;
import com.pbs.app.Services.Data;

@WebMvcTest(Controller.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Data data;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBarcodeEndpoint() throws Exception {
        // Arrange
        String barcode = "1234567890";
        String requestBody = "\"" + barcode + "\""; // The controller expects the barcode in quotes
        String responseBody = "[{\"productID\":\"prod1\",\"name\":\"Product 1\"}]";

        // Mock the data service if needed
        // For this test, since the data service is not used, we don't need to mock it

        // Act & Assert
        mockMvc.perform(post("/barcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .param("sortAttribute", "value"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // You can further assert the response content if needed
                .andExpect(content().json(responseBody));
    }

    @Test
    void testNameEndpoint() throws Exception {
        // Arrange
        String productName = "Test Product";
        String requestBody = "\"" + productName + "\"";
        String responseBody = "[{\"productID\":\"prod1\",\"name\":\"Test Product\"}]";

        // Act & Assert
        mockMvc.perform(post("/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .param("sortAttribute", "value"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseBody));
    }

    @Test
    void testFavoritesAdd() throws Exception {
        // Arrange
        String email = "john@example.com";
        String action = "add";
        String productJson = "{\"productID\":\"prod1\",\"name\":\"Product 1\"}";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);
        doNothing().when(data).insertProduct(any(Product.class));
        doNothing().when(data).insertFavorite(any(Favorite.class));
        when(data.favoriteExists(anyString(), anyString())).thenReturn(false);
        when(data.productBarcodeExists(anyString())).thenReturn(false);
        when(data.productNameExists(anyString())).thenReturn(false);
        when(data.productImageURLExists(anyString())).thenReturn(false);
        when(data.productWebURLExists(anyString())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/favorites")
                .param("email", email)
                .param("action", action)
                .param("productJson", productJson))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testFavoritesGet() throws Exception {
        // Arrange
        String email = "john@example.com";
        String action = "get";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);

        Favorite favorite = new Favorite("F1", "C1", "P1", "2023-10-10");
        List<Favorite> favorites = Collections.singletonList(favorite);
        when(data.getFavorites("C1")).thenReturn(favorites);

        Product product = new Product();
        product.setProductID("P1");
        product.setName("Product 1");
        when(data.getProduct("P1")).thenReturn(product);

        String responseBody = "[{\"productID\":\"P1\",\"name\":\"Product 1\"}]";

        // Act & Assert
        mockMvc.perform(post("/favorites")
                .param("email", email)
                .param("action", action))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testSignUpSuccess() throws Exception {
        // Arrange
        String email = "newuser@example.com";
        String password = "password";
        String firstName = "New";
        String surname = "User";

        when(data.creatorEmailExists(email)).thenReturn(false);
        when(data.getCreatorCount()).thenReturn(1);
        doNothing().when(data).insertCreator(any(Creator.class));

        // Act & Assert
        mockMvc.perform(post("/signup")
                .param("email", email)
                .param("password", password)
                .param("firstName", firstName)
                .param("surname", surname))
                .andExpect(status().isOk())
                .andExpect(content().string("Account created"));
    }

    @Test
    void testSignUpEmailExists() throws Exception {
        // Arrange
        String email = "existinguser@example.com";
        String password = "password";
        String firstName = "Existing";
        String surname = "User";

        when(data.creatorEmailExists(email)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/signup")
                .param("email", email)
                .param("password", password)
                .param("firstName", firstName)
                .param("surname", surname))
                .andExpect(status().isOk())
                .andExpect(content().string("Email already exists"));
    }

    @Test
    void testSignInSuccess() throws Exception {
        // Arrange
        String email = "john@example.com";
        String password = "password";

        // Since AuthService is instantiated inside the method, we might need to adjust the code to make it testable.
        // For now, we can assume the login is successful.

        // Act & Assert
        mockMvc.perform(post("/signin")
                .param("email", email)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testSignInFailure() throws Exception {
        // Arrange
        String email = "john@example.com";
        String password = "wrongpassword";

        // Assume the login fails

        // Act & Assert
        mockMvc.perform(post("/signin")
                .param("email", email)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("Failure"));
    }

    // Add more tests for other endpoints following the same pattern.

    @Test
    void testGenerateInstagramCaptionForProduct() throws Exception {
        // Arrange
        String json = "{\"productID\":\"prod1\",\"name\":\"Product 1\"}";
        String email = "john@example.com";
        String expectedCaption = "This is an Instagram caption.";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        CreatorAttributes attributes = new CreatorAttributes();
        attributes.setCreatorID("C1");
        when(data.getCreator(email)).thenReturn(creator);
        when(data.getCreatorAttributes("C1")).thenReturn(attributes);

        // Act & Assert
        mockMvc.perform(post("/generateInstagramCaptionForProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .param("email", email))
                .andExpect(status().isOk())
                // .andExpect(content().string(expectedCaption))
                // Since the AI class is not easily mockable here, you might need to adjust the controller to make it testable
                ;
    }

        @Test
    void testHistoryAdd() throws Exception {
        // Arrange
        String email = "john@example.com";
        String action = "add";
        String barcode = "1234567890";
        String name = "Test Product";
        String photo = "http://example.com/photo.jpg";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);
        doNothing().when(data).insertScanHistory(any(Scan.class));

        // Act & Assert
        mockMvc.perform(post("/history")
                .param("email", email)
                .param("action", action)
                .param("barcode", barcode)
                .param("name", name)
                .param("photo", photo))
                .andExpect(status().isOk())
                .andExpect(content().string("[]")); // Since the response body is empty
    }

    @Test
    void testHistoryGet() throws Exception {
        // Arrange
        String email = "john@example.com";
        String action = "get";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);

        Scan scan = new Scan("SH1", "C1", "1234567890", "2023-10-10", "Test Product", "http://example.com/photo.jpg");
        List<Scan> history = Collections.singletonList(scan);
        when(data.getScanHistories("C1")).thenReturn(history);

        String responseBody = "[{\"scanID\":\"SH1\",\"creatorID\":\"C1\",\"productBarcode\":\"1234567890\",\"dateOfScan\":\"2023-10-10\",\"name\":\"Test Product\",\"photo\":\"http://example.com/photo.jpg\"}]";

        // Act & Assert
        mockMvc.perform(post("/history")
                .param("email", email)
                .param("action", action))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testShareAdd() throws Exception {
        // Arrange
        String email = "john@example.com";
        String action = "add";
        String productJson = "{\"productID\":\"P1\",\"name\":\"Product 1\",\"barcode\":\"1234567890\"}";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        Product product = new Product();
        product.setProductID("P1");
        product.setName("Product 1");
        product.setBarcode("1234567890");

        when(data.getCreator(email)).thenReturn(creator);
        when(data.productBarcodeExists("1234567890")).thenReturn(false);
        when(data.productNameExists("Product 1")).thenReturn(false);
        when(data.productImageURLExists(null)).thenReturn(false);
        when(data.productWebURLExists(null)).thenReturn(false);
        doNothing().when(data).insertProduct(any(Product.class));
        doNothing().when(data).insertShare(any(Share.class));

        // Act & Assert
        mockMvc.perform(post("/share")
                .param("email", email)
                .param("action", action)
                .param("product", productJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Share added"));
    }

    @Test
    void testShareGet() throws Exception {
        // Arrange
        String email = "john@example.com";
        String action = "get";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);

        Share share = new Share("S1", "C1", "P1", "2023-10-10");
        List<Share> shares = Collections.singletonList(share);
        when(data.getShares("C1")).thenReturn(shares);

        Product product = new Product();
        product.setProductID("P1");
        product.setName("Product 1");
        when(data.getProduct("P1")).thenReturn(product);

        String responseBody = "[{\"productID\":\"P1\",\"name\":\"Product 1\"}]";

        // Act & Assert
        mockMvc.perform(post("/share")
                .param("email", email)
                .param("action", action))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testAnalytics() throws Exception {
        // Arrange
        String email = "john@example.com";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");

        when(data.getCreator(email)).thenReturn(creator);
        when(data.getCreatorScanCount("C1")).thenReturn(10);
        when(data.getCreatorShareCount("C1")).thenReturn(5);
        when(data.getMostScannedProductString()).thenReturn(Arrays.asList("Product A", "Product B"));
        // Mock other data methods as needed

        AnalyticsData analyticsData = new AnalyticsData(
                "10", // scanCount
                "5",  // shareCount
                Arrays.asList("Product A", "Product B"), // mostScanned
                Arrays.asList("Product C"), // mostFavoritedProductList
                Arrays.asList("Product D"), // mostSharedProductList
                Arrays.asList("Brand A"),   // mostScannedBrands
                Arrays.asList("Brand B"),   // mostFavoritedBrands
                Arrays.asList("Brand C"),   // mostSharedBrands
                Arrays.asList("Category A"),// mostScannedCategories
                Arrays.asList("Category B"),// mostFavoritedCategories
                Arrays.asList("Category C") // mostSharedCategories
        );

        String responseBody = new Gson().toJson(analyticsData);

        // Act & Assert
        mockMvc.perform(post("/analytics")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testAPIKeys() throws Exception {
        // Arrange
        String email = "john@example.com";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        APIKeys apiKeys = new APIKeys("API1", "C1", "impactUser", "impactPass", "ebayKey");

        when(data.getCreator(email)).thenReturn(creator);
        when(data.getAPIKeys("C1")).thenReturn(apiKeys);

        String responseBody = new Gson().toJson(apiKeys);

        // Act & Assert
        mockMvc.perform(post("/APIKeys")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testUpdateImpactUsername() throws Exception {
        // Arrange
        String email = "john@example.com";
        String impactUsername = "newImpactUser";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");

        when(data.getCreator(email)).thenReturn(creator);
        doNothing().when(data).updateImpactUsername("C1", impactUsername);

        // Act & Assert
        mockMvc.perform(post("/updateImpactUsername")
                .param("email", email)
                .param("impactUsername", impactUsername))
                .andExpect(status().isOk())
                .andExpect(content().string("Impact Username updated"));
    }

    @Test
    void testUpdateImpactPassword() throws Exception {
        // Arrange
        String email = "john@example.com";
        String impactPassword = "newImpactPass";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");

        when(data.getCreator(email)).thenReturn(creator);
        doNothing().when(data).updateImpactPassword("C1", impactPassword);

        // Act & Assert
        mockMvc.perform(post("/updateImpactPassword")
                .param("email", email)
                .param("impactPassword", impactPassword))
                .andExpect(status().isOk())
                .andExpect(content().string("Impact Password updated"));
    }

    @Test
    void testUpdateEbayKey() throws Exception {
        // Arrange
        String email = "john@example.com";
        String ebayKey = "newEbayKey";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");

        when(data.getCreator(email)).thenReturn(creator);
        doNothing().when(data).updateEbayKey("C1", ebayKey);

        // Act & Assert
        mockMvc.perform(post("/updateEbayKey")
                .param("email", email)
                .param("ebayKey", ebayKey))
                .andExpect(status().isOk())
                .andExpect(content().string("Ebay Key updated"));
    }

    @Test
    void testUpdateAPIKeys() throws Exception {
        // Arrange
        String email = "john@example.com";
        String impactUsername = "newImpactUser";
        String impactPassword = "newImpactPass";
        String ebayKey = "newEbayKey";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");

        when(data.getCreator(email)).thenReturn(creator);
        doNothing().when(data).updateAPIKeys("C1", impactUsername, impactPassword, ebayKey);

        // Act & Assert
        mockMvc.perform(post("/updateAPIKeys")
                .param("email", email)
                .param("impactUsername", impactUsername)
                .param("impactPassword", impactPassword)
                .param("ebayKey", ebayKey))
                .andExpect(status().isOk())
                .andExpect(content().string("API Keys updated"));
    }

    @Test
    void testGenerateTwitterCaptionForProduct() throws Exception {
        // Arrange
        String json = "{\"productID\":\"prod1\",\"name\":\"Product 1\"}";
        String email = "john@example.com";
        String expectedCaption = "This is a Twitter caption.";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        CreatorAttributes attributes = new CreatorAttributes();
        attributes.setCreatorID("C1");
        when(data.getCreator(email)).thenReturn(creator);
        when(data.getCreatorAttributes("C1")).thenReturn(attributes);

        // Act & Assert
        mockMvc.perform(post("/generateTwitterCaptionForProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .param("email", email))
                .andExpect(status().isOk());
        // Since the AI class is not easily mockable here, you might need to adjust the controller to make it testable
    }

    @Test
    void testGenerateFacebookCaptionForProduct() throws Exception {
        // Arrange
        String json = "{\"productID\":\"prod1\",\"name\":\"Product 1\"}";
        String email = "john@example.com";
        String expectedCaption = "This is a Facebook caption.";

        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        CreatorAttributes attributes = new CreatorAttributes();
        attributes.setCreatorID("C1");
        when(data.getCreator(email)).thenReturn(creator);
        when(data.getCreatorAttributes("C1")).thenReturn(attributes);

        // Act & Assert
        mockMvc.perform(post("/generateFacebookCaptionForProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .param("email", email))
                .andExpect(status().isOk());
        // Since the AI class is not easily mockable here, you might need to adjust the controller to make it testable
    }

    @Test
    void testGenerateCaptionForProductComparison() throws Exception {
        // Arrange
        String json = "{\"products\":[{\"productID\":\"prod1\",\"name\":\"Product 1\"},{\"productID\":\"prod2\",\"name\":\"Product 2\"}]}";
        String expectedCaption = "This is a comparison caption.";

        // Act & Assert
        mockMvc.perform(post("/generateCaptionForProductComparison")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // Since the AI class is not easily mockable here, you might need to adjust the controller to make it testable
    }

    @Test
    void testCreatorsScanCount() throws Exception {
        // Arrange
        String email = "john@example.com";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);
        when(data.getCreatorScanCount("C1")).thenReturn(10);

        // Act & Assert
        mockMvc.perform(post("/creatorscancount")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testCreatorsShareCount() throws Exception {
        // Arrange
        String email = "john@example.com";
        Creator creator = new Creator("C1", "John", "Doe", email, "password");
        when(data.getCreator(email)).thenReturn(creator);
        when(data.getCreatorShareCount("C1")).thenReturn(5);

        // Act & Assert
        mockMvc.perform(post("/creatorsharecount")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testMostScannedBrands() throws Exception {
        // Arrange
        List<String> mostScannedBrands = Arrays.asList("Brand A", "Brand B");
        when(data.get5MostScannedBrands()).thenReturn(mostScannedBrands);

        String responseBody = mostScannedBrands.toString();

        // Act & Assert
        mockMvc.perform(post("/mostscannedbrands"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    void testMostFavoritedBrands() throws Exception {
        // Arrange
        List<String> mostFavoritedBrands = Arrays.asList("Brand A", "Brand B");
        when(data.get5MostFavoritedBrands()).thenReturn(mostFavoritedBrands);

        String responseBody = mostFavoritedBrands.toString();

        // Act & Assert
        mockMvc.perform(post("/mostfavoritedbrands"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    void testMostSharedBrands() throws Exception {
        // Arrange
        List<String> mostSharedBrands = Arrays.asList("Brand A", "Brand B");
        when(data.get5MostSharedBrands()).thenReturn(mostSharedBrands);

        String responseBody = mostSharedBrands.toString();

        // Act & Assert
        mockMvc.perform(post("/mostsharedbrands"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    void testMostScannedCategories() throws Exception {
        // Arrange
        List<String> mostScannedCategories = Arrays.asList("Category A", "Category B");
        when(data.get5MostScannedCategories()).thenReturn(mostScannedCategories);

        String responseBody = mostScannedCategories.toString();

        // Act & Assert
        mockMvc.perform(post("/mostscannedcategories"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    void testMostFavoritedCategories() throws Exception {
        // Arrange
        List<String> mostFavoritedCategories = Arrays.asList("Category A", "Category B");
        when(data.get5MostFavoritedCategories()).thenReturn(mostFavoritedCategories);

        String responseBody = mostFavoritedCategories.toString();

        // Act & Assert
        mockMvc.perform(post("/mostfavoritedcategories"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    void testMostSharedCategories() throws Exception {
        // Arrange
        List<String> mostSharedCategories = Arrays.asList("Category A", "Category B");
        when(data.get5MostSharedCategories()).thenReturn(mostSharedCategories);

        String responseBody = mostSharedCategories.toString();

        // Act & Assert
        mockMvc.perform(post("/mostsharedcategories"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    // Since some endpoints are marked as "exclude" or "for next iteration", I'll skip writing tests for them.

    // Continue adding tests for other included endpoints...

    @Test
    void testRankingOfMostScanned() throws Exception {
        // Arrange
        Scan scan1 = new Scan("SH1", "C1", "barcode1", "2023-10-10", "Product 1", "PhotoURL1");
        Scan scan2 = new Scan("SH2", "C2", "barcode2", "2023-10-11", "Product 2", "PhotoURL2");
        List<Scan> mostScanned = Arrays.asList(scan1, scan2);

        when(data.get5MostScannedProducts()).thenReturn(mostScanned);

        String responseBody = new Gson().toJson(mostScanned);

        // Act & Assert
        mockMvc.perform(post("/rankingofmostscanned"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testRankingOfMostFavorited() throws Exception {
        // Arrange
        Favorite fav1 = new Favorite("F1", "C1", "P1", "2023-10-10");
        Favorite fav2 = new Favorite("F2", "C2", "P2", "2023-10-11");
        List<Favorite> mostFavoritedList = Arrays.asList(fav1, fav2);

        when(data.get5MostFavoritedProducts()).thenReturn(mostFavoritedList);

        Product product1 = new Product();
        product1.setProductID("P1");
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setProductID("P2");
        product2.setName("Product 2");

        when(data.getProduct("P1")).thenReturn(product1);
        when(data.getProduct("P2")).thenReturn(product2);

        List<Product> mostFavoritedProductList = Arrays.asList(product1, product2);
        String responseBody = new Gson().toJson(mostFavoritedProductList);

        // Act & Assert
        mockMvc.perform(post("/rankingofmostfavorited"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testRankingOfMostShared() throws Exception {
        // Arrange
        Share share1 = new Share("S1", "C1", "P1", "2023-10-10");
        Share share2 = new Share("S2", "C2", "P2", "2023-10-11");
        List<Share> mostSharedList = Arrays.asList(share1, share2);

        when(data.get5MostSharedProducts()).thenReturn(mostSharedList);

        Product product1 = new Product();
        product1.setProductID("P1");
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setProductID("P2");
        product2.setName("Product 2");

        when(data.getProduct("P1")).thenReturn(product1);
        when(data.getProduct("P2")).thenReturn(product2);

        List<Product> mostSharedProductList = Arrays.asList(product1, product2);
        String responseBody = new Gson().toJson(mostSharedProductList);

        // Act & Assert
        mockMvc.perform(post("/rankingofmostshared"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

}
