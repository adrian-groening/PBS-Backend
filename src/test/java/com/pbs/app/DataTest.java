package com.pbs.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.pbs.app.Entities.APIKeys;
import com.pbs.app.Entities.Creator;
import com.pbs.app.Entities.CreatorAttributes;
import com.pbs.app.Entities.Favorite;
import com.pbs.app.Entities.Product;
import com.pbs.app.Entities.Scan;
import com.pbs.app.Entities.Share;
import com.pbs.app.Services.Data;

class DataTest {

    @InjectMocks
    private Data data;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Mock the Connection's createStatement() and prepareStatement() methods
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Set the mocked connection in Data class
        data.con = mockConnection;
    }

    @Test
    void testInsertCreator() throws SQLException {
        // Arrange
        Creator creator = new Creator("123", "John", "Doe", "john@example.com", "password");

        // Act
        data.insertCreator(creator);

        // Assert
        verify(mockConnection).prepareStatement("INSERT INTO Creator VALUES(?,?,?,?,?)");
        verify(mockPreparedStatement).setString(1, "123");
        verify(mockPreparedStatement).setString(2, "John");
        verify(mockPreparedStatement).setString(3, "Doe");
        verify(mockPreparedStatement).setString(4, "john@example.com");
        verify(mockPreparedStatement).setString(5, "password");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetCreator() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Creator WHERE email = 'john@example.com'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("creatorID")).thenReturn("123");
        when(mockResultSet.getString("firstName")).thenReturn("John");
        when(mockResultSet.getString("surname")).thenReturn("Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("password")).thenReturn("password");

        // Act
        Creator creator = data.getCreator("john@example.com");

        // Assert
        assertEquals("123", creator.getCreatorID());
        assertEquals("John", creator.getFirstName());
        assertEquals("Doe", creator.getSurname());
        assertEquals("john@example.com", creator.getEmail());
        assertEquals("password", creator.getPassword());
    }

    @Test
    void testGetCreatorCount() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT COUNT(*) FROM Creator")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(10);

        // Act
        int count = data.getCreatorCount();

        // Assert
        assertEquals(10, count);
    }

    @Test
    void testInsertProduct() throws SQLException {
        // Arrange
        Product product = new Product();
        product.setProductID("prod123");
        product.setName("Test Product");
        // Set other product fields as needed

        // Act
        data.insertProduct(product);

        // Assert
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "prod123");
        verify(mockPreparedStatement).setString(2, "Test Product");
        // Verify other fields
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetProduct() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Product WHERE productID = 'prod123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("productID")).thenReturn("prod123");
        when(mockResultSet.getString("name")).thenReturn("Test Product");
        // Mock other fields

        // Act
        Product product = data.getProduct("prod123");

        // Assert
        assertEquals("prod123", product.getProductID());
        assertEquals("Test Product", product.getName());
        // Assert other fields
    }

    @Test
    void testProductIDExists() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Product WHERE productID = '2'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Act
        boolean exists = data.productIDExists("2");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testInsertFavorite() throws SQLException {
        // Arrange
        Favorite favorite = new Favorite("fav123", "creator123", "prod123", "2023-10-10");

        // Act
        data.insertFavorite(favorite);

        // Assert
        verify(mockConnection).prepareStatement("INSERT INTO Favorite VALUES(?,?,?,?)");
        verify(mockPreparedStatement).setString(1, "fav123");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).setString(3, "prod123");
        verify(mockPreparedStatement).setString(4, "2023-10-10");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetFavorite() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Favorite WHERE favoriteID = 'fav123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("favoriteID")).thenReturn("fav123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("productID")).thenReturn("prod123");
        when(mockResultSet.getString("dateOfFavorite")).thenReturn("2023-10-10");

        // Act
        Favorite favorite = data.getFavorite("fav123");

        // Assert
        assertEquals("fav123", favorite.getFavoriteID());
        assertEquals("creator123", favorite.getCreatorID());
        assertEquals("prod123", favorite.getProductID());
        assertEquals("2023-10-10", favorite.getDateofFavorite());
    }

    @Test
    void testFavoriteExists() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Favorite WHERE creatorID = 'creator123' AND productID = 'prod123'"))
                .thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Act
        boolean exists = data.favoriteExists("creator123", "prod123");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testInsertScanHistory() throws SQLException {
        // Arrange
        Scan scan = new Scan("scan123", "creator123", "barcode123", "2023-10-10", "Product Name", "PhotoURL");

        // Act
        data.insertScanHistory(scan);

        // Assert
        verify(mockConnection).prepareStatement("INSERT INTO Scan VALUES(?,?,?,?,?,?)");
        verify(mockPreparedStatement).setString(1, "scan123");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).setString(3, "2023-10-10");
        verify(mockPreparedStatement).setString(4, "barcode123");
        verify(mockPreparedStatement).setString(5, "Product Name");
        verify(mockPreparedStatement).setString(6, "PhotoURL");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetScanHistory() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Scan WHERE scanID = 'scan123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("scanID")).thenReturn("scan123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("productBarcode")).thenReturn("barcode123");
        when(mockResultSet.getString("dateOfScan")).thenReturn("2023-10-10");
        when(mockResultSet.getString("name")).thenReturn("Product Name");
        when(mockResultSet.getString("photo")).thenReturn("PhotoURL");

        // Act
        Scan scan = data.getScanHistory("scan123");

        // Assert
        assertEquals("scan123", scan.getScanID());
        assertEquals("creator123", scan.getCreatorID());
        assertEquals("barcode123", scan.getProductBarcode());
        assertEquals("2023-10-10", scan.getDateOfScan());
        assertEquals("Product Name", scan.getName());
        assertEquals("PhotoURL", scan.getPhoto());
    }

    @Test
    void testCreatorEmailExists() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Creator WHERE email = 'john@example.com'"))
                .thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Act
        boolean exists = data.creatorEmailExists("john@example.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testDeleteCreator() throws SQLException {
        // Act
        data.deleteCreator("john@example.com");

        // Assert
        verify(mockStatement).executeUpdate("DELETE FROM Creator WHERE email = 'john@example.com'");
    }

    @Test
    void testInsertShare() throws SQLException {
        // Arrange
        Share share = new Share("share123", "creator123", "prod123", "2023-10-10");

        // Act
        data.insertShare(share);

        // Assert
        verify(mockConnection).prepareStatement("INSERT INTO Share VALUES(?,?,?,?)");
        verify(mockPreparedStatement).setString(1, "share123");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).setString(3, "prod123");
        verify(mockPreparedStatement).setString(4, "2023-10-10");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetShare() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Share WHERE shareID = 'share123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("shareID")).thenReturn("share123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("productID")).thenReturn("prod123");
        when(mockResultSet.getString("dateOfShare")).thenReturn("2023-10-10");

        // Act
        Share share = data.getShare("share123");

        // Assert
        assertEquals("share123", share.getShareID());
        assertEquals("creator123", share.getCreatorID());
        assertEquals("prod123", share.getProductID());
        assertEquals("2023-10-10", share.getDateOfShare());
    }

    @Test
    void testGetTotalShareCount() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT COUNT(*) FROM Share")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(5);

        // Act
        int count = data.getTotalShareCount();

        // Assert
        assertEquals(5, count);
    }

    @Test
    void testGetProductShareCount() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT COUNT(*) FROM Share WHERE productID = 'prod123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(3);

        // Act
        int count = data.getProductShareCount("prod123");

        // Assert
        assertEquals(3, count);
    }

    @Test
    void testGetCreatorShareCount() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT COUNT(*) FROM Share WHERE creatorID = 'creator123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(2);

        // Act
        int count = data.getCreatorShareCount("creator123");

        // Assert
        assertEquals(2, count);
    }

    @Test
    void testGet5MostSharedProducts() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("shareID")).thenReturn("share1", "share2");
        when(mockResultSet.getString("creatorID")).thenReturn("creator1", "creator2");
        when(mockResultSet.getString("productID")).thenReturn("prod1", "prod2");
        when(mockResultSet.getString("dateOfShare")).thenReturn("2023-10-10", "2023-10-11");

        // Act
        List<Share> shares = data.get5MostSharedProducts();

        // Assert
        assertEquals(2, shares.size());
        assertEquals("share1", shares.get(0).getShareID());
        assertEquals("share2", shares.get(1).getShareID());
    }

    @Test
    void testGetMostRecentShare() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Share ORDER BY dateOfShare DESC LIMIT 1")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("shareID")).thenReturn("share123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("productID")).thenReturn("prod123");
        when(mockResultSet.getString("dateOfShare")).thenReturn("2023-10-10");

        // Act
        Share share = data.getMostRecentShare();

        // Assert
        assertEquals("share123", share.getShareID());
    }

    @Test
    void testGetAPIKeys() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM APIKeys WHERE creatorID = 'creator123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("APIKeysID")).thenReturn("api123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("impactUsername")).thenReturn("impactUser");
        when(mockResultSet.getString("impactPassword")).thenReturn("impactPass");
        when(mockResultSet.getString("ebayKey")).thenReturn("ebay123");

        // Act
        APIKeys apiKeys = data.getAPIKeys("creator123");

        // Assert
        assertEquals("api123", apiKeys.getAPIKeysID());
        assertEquals("creator123", apiKeys.getCreatorID());
        assertEquals("impactUser", apiKeys.getImpactUsername());
        assertEquals("impactPass", apiKeys.getImpactPassword());
        assertEquals("ebay123", apiKeys.getEbayKey());
    }

    @Test
    void testInsertAPIKeys() throws SQLException {
        // Arrange
        APIKeys apiKeys = new APIKeys("api123", "creator123", "impactUser", "impactPass", "ebay123");

        // Act
        data.insertAPIKeys(apiKeys);

        // Assert
        verify(mockConnection).prepareStatement("INSERT INTO APIKeys VALUES(?,?,?,?,?,?)");
        verify(mockPreparedStatement).setString(1, "api123");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).setString(3, "impactUser");
        verify(mockPreparedStatement).setString(4, "impactPass");
        verify(mockPreparedStatement).setString(5, "ebay123");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateImpactUsername() throws SQLException {
        // Act
        data.updateImpactUsername("creator123", "newImpactUser");

        // Assert
        verify(mockConnection).prepareStatement("UPDATE APIKeys SET impactUsername = ? WHERE creatorID = ?");
        verify(mockPreparedStatement).setString(1, "newImpactUser");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateImpactPassword() throws SQLException {
        // Act
        data.updateImpactPassword("creator123", "newImpactPass");

        // Assert
        verify(mockConnection).prepareStatement("UPDATE APIKeys SET impactPassword = ? WHERE creatorID = ?");
        verify(mockPreparedStatement).setString(1, "newImpactPass");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateEbayKey() throws SQLException {
        // Act
        data.updateEbayKey("creator123", "newEbayKey");

        // Assert
        verify(mockConnection).prepareStatement("UPDATE APIKeys SET ebayKey = ? WHERE creatorID = ?");
        verify(mockPreparedStatement).setString(1, "newEbayKey");
        verify(mockPreparedStatement).setString(2, "creator123");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateAPIKeys() throws SQLException {
        // Act
        data.updateAPIKeys("creator123", "newImpactUser", "newImpactPass", "newEbayKey");

        // Assert
        verify(mockConnection).prepareStatement("UPDATE APIKeys SET impactUsername = ?, impactPassword = ?, ebayKey = ? WHERE creatorID = ?");
        verify(mockPreparedStatement).setString(1, "newImpactUser");
        verify(mockPreparedStatement).setString(2, "newImpactPass");
        verify(mockPreparedStatement).setString(3, "newEbayKey");
        verify(mockPreparedStatement).setString(4, "creator123");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetCreatorAttributes() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM CreatorAttributes WHERE creatorID = 'creator123'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("attributeID")).thenReturn("attr123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("work")).thenReturn("Developer");
        when(mockResultSet.getString("location")).thenReturn("USA");
        when(mockResultSet.getString("bio")).thenReturn("Bio here");
        when(mockResultSet.getString("influencerLevel")).thenReturn("High");
        when(mockResultSet.getString("tone")).thenReturn("Friendly");
        when(mockResultSet.getString("techLevel")).thenReturn("Expert");
        when(mockResultSet.getString("mindedness")).thenReturn("Open");
        when(mockResultSet.getString("age")).thenReturn("30");

        // Act
        CreatorAttributes attributes = data.getCreatorAttributes("creator123");

        // Assert
        assertEquals("attr123", attributes.getAttributesID());
        assertEquals("creator123", attributes.getCreatorID());
        assertEquals("Developer", attributes.getWork());
        assertEquals("USA", attributes.getLocation());
        assertEquals("Bio here", attributes.getBio());
        assertEquals("High", attributes.getInfluencerLevel());
        assertEquals("Friendly", attributes.getTone());
        assertEquals("Expert", attributes.getTechLevel());
        assertEquals("Open", attributes.getMindedness());
        assertEquals("30", attributes.getAge());
    }

    @Test
    void testInsertCreatorAttributes() throws SQLException {
        // Arrange
        CreatorAttributes attributes = new CreatorAttributes("attr123", "creator123", "Developer", "USA", "Bio here", "High", "Friendly", "Expert", "Open", "30");

        // Act
        data.insertCreatorAttributes(attributes);

        // Assert
        verify(mockConnection).prepareStatement("INSERT INTO CreatorAttributes VALUES(?,?,?,?,?,?,?,?,?,?)");
        verify(mockPreparedStatement).setString(1, "creator123");
        verify(mockPreparedStatement).setString(2, "attr123");
        verify(mockPreparedStatement).setString(3, "Developer");
        verify(mockPreparedStatement).setString(4, "USA");
        verify(mockPreparedStatement).setString(5, "Bio here");
        verify(mockPreparedStatement).setString(6, "High");
        verify(mockPreparedStatement).setString(7, "Friendly");
        verify(mockPreparedStatement).setString(8, "Expert");
        verify(mockPreparedStatement).setString(9, "Open");
        verify(mockPreparedStatement).setString(10, "30");
        verify(mockPreparedStatement).executeUpdate();
    }

    // Add more tests for the remaining methods following the same pattern.

    @Test
    void testProductNameExists() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Product WHERE name = 'Test Product'")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Act
        boolean exists = data.productNameExists("Test Product");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testDeleteProduct() throws SQLException {
        // Act
        data.deleteProduct("prod123");

        // Assert
        verify(mockStatement).executeUpdate("DELETE FROM Product WHERE productID = 'prod123'");
    }

    @Test
    void testGetTotalFavoriteCount() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT COUNT(*) FROM Favorite")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(7);

        // Act
        int count = data.getTotalFavoriteCount();

        // Assert
        assertEquals(7, count);
    }

    @Test
    void testGet5MostFavoritedProducts() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("favoriteID")).thenReturn("fav1", "fav2");
        when(mockResultSet.getString("creatorID")).thenReturn("creator1", "creator2");
        when(mockResultSet.getString("productID")).thenReturn("prod1", "prod2");
        when(mockResultSet.getString("dateOfFavorite")).thenReturn("2023-10-10", "2023-10-11");

        // Act
        List<Favorite> favorites = data.get5MostFavoritedProducts();

        // Assert
        assertEquals(2, favorites.size());
        assertEquals("fav1", favorites.get(0).getFavoriteID());
        assertEquals("fav2", favorites.get(1).getFavoriteID());
    }

    @Test
    void testGet5MostScannedProducts() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("scanID")).thenReturn("scan1", "scan2");
        when(mockResultSet.getString("creatorID")).thenReturn("creator1", "creator2");
        when(mockResultSet.getString("barcode")).thenReturn("barcode1", "barcode2");
        when(mockResultSet.getString("dateOfScan")).thenReturn("2023-10-10", "2023-10-11");
        when(mockResultSet.getString("name")).thenReturn("Product1", "Product2");
        when(mockResultSet.getString("photo")).thenReturn("Photo1", "Photo2");

        // Act
        List<Scan> scans = data.get5MostScannedProducts();

        // Assert
        assertEquals(2, scans.size());
        assertEquals("scan1", scans.get(0).getScanID());
        assertEquals("scan2", scans.get(1).getScanID());
    }

    @Test
    void testGetMostRecentFavorite() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Favorite ORDER BY dateOfFavorite DESC LIMIT 1")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("favoriteID")).thenReturn("fav123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("productID")).thenReturn("prod123");

        // Act
        Favorite favorite = data.getMostRecentFavorite();

        // Assert
        assertEquals("fav123", favorite.getFavoriteID());
    }

    @Test
    void testGetMostRecentScan() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Scan ORDER BY dateOfScan DESC LIMIT 1")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("scanID")).thenReturn("scan123");
        when(mockResultSet.getString("creatorID")).thenReturn("creator123");
        when(mockResultSet.getString("barcode")).thenReturn("barcode123");
        when(mockResultSet.getString("dateOfScan")).thenReturn("2023-10-10");
        when(mockResultSet.getString("name")).thenReturn("Product Name");
        when(mockResultSet.getString("photo")).thenReturn("PhotoURL");

        // Act
        Scan scan = data.getMostRecentScan();

        // Assert
        assertEquals("scan123", scan.getScanID());
    }

    @Test
    void testGetAllScannedProducts() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery("SELECT * FROM Product WHERE barcode IN (SELECT barcode FROM Scan)")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("productID")).thenReturn("prod123");
        when(mockResultSet.getString("name")).thenReturn("Test Product");
        // Mock other fields as needed

        // Act
        List<Product> products = data.getAllScannedProducts();

        // Assert
        assertEquals(1, products.size());
        assertEquals("prod123", products.get(0).getProductID());
    }

    @Test
    void testGet5MostScannedBrands() throws SQLException {
        // Arrange
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("brand")).thenReturn("Brand1", "Brand2");

        // Act
        List<String> brands = data.get5MostScannedBrands();

        // Assert
        assertEquals(2, brands.size());
        assertEquals("Brand1", brands.get(0));
        assertEquals("Brand2", brands.get(1));
    }

    @Test
    void testGetMostScannedBrand() throws SQLException {
        // Arrange
        Product product1 = new Product();
        product1.setBrand("Brand1");
        Product product2 = new Product();
        product2.setBrand("Brand1");
        Product product3 = new Product();
        product3.setBrand("Brand2");

        // Mock getAllScannedProducts
        when(mockStatement.executeQuery("SELECT * FROM Product WHERE barcode IN (SELECT barcode FROM Scan)")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, true, false);
        when(mockResultSet.getString("brand")).thenReturn("Brand1", "Brand1", "Brand2");

        // Act
        String mostScannedBrand = data.getMostScannedBrand();

        // Assert
        assertEquals("Brand1", mostScannedBrand);
    }

    // Continue adding tests for the remaining methods.

}



