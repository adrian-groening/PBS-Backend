package com.pbs.app.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pbs.app.Entities.APIKeys;
import com.pbs.app.Entities.Creator;
import com.pbs.app.Entities.CreatorAttributes;
import com.pbs.app.Entities.Favorite;
import com.pbs.app.Entities.Product;
import com.pbs.app.Entities.Scan;
import com.pbs.app.Entities.Share;


@Service
public class Data {
    private static final Logger LOGGER = Logger.getLogger(Data.class.getName());

    public Connection con;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.port}")
    private String port;

    public Data() {
    }

    public void openConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void insertCreator(Creator creator) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO Creator VALUES(?,?,?,?,?)");
        p.setString(1, creator.getCreatorID());
        p.setString(2, creator.getFirstName());
        p.setString(3, creator.getSurname());
        p.setString(4, creator.getEmail());
        p.setString(5, creator.getPassword());
        p.executeUpdate();
    }

    public Creator getCreator(String email) throws SQLException {
        Creator creator = new Creator();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Creator WHERE email = '" + email + "'");
        while (rs.next()) {
            creator.setCreatorID(rs.getString("creatorID"));
            creator.setFirstName(rs.getString("firstName"));
            creator.setSurname(rs.getString("surname"));
            creator.setEmail(rs.getString("email"));
            creator.setPassword(rs.getString("password"));
        }
        return creator;
    }

    public int getCreatorCount() throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Creator");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public void insertProduct(Product product) throws SQLException {
        String insertQuery = "INSERT INTO Product (productID, name, price, barcode, currency, imageURL, category, brand, commissionRate, affiliate, webURL, affiliateWebURL, description, earningsPerClick, totalSalesVolume, couponsAvailable, topRated, isNew, isPriorityListing, onSale, onPromotion, logoURI, mostRecentDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement p = con.prepareStatement(insertQuery)) {
            p.setString(1, product.getProductID());
            p.setString(2, product.getName());
            p.setString(3, product.getPrice());
            p.setString(4, product.getBarcode());
            p.setString(5, product.getCurrency());
            p.setString(6, product.getImageURL());
            p.setString(7, product.getCategory());
            p.setString(8, product.getBrand());
            p.setString(9, product.getCommissionRate());
            p.setString(10, product.getAffiliate());
            p.setString(11, product.getWebURL());
            p.setString(12, product.getAffiliateWebURL());
            p.setString(13, product.getDescription());
            p.setString(14, product.getEarningsPerClick());
            p.setString(15, product.getTotalSalesVolume());
            p.setBoolean(16, product.hasCouponsAvailable());
            p.setBoolean(17, product.isTopRated());
            p.setBoolean(18, product.isNew());
            p.setBoolean(19, product.isPriorityListing());
            p.setBoolean(20, product.isOnSale());
            p.setBoolean(21, product.isOnPromotion());
            p.setString(22, product.getLogoURI());
            p.setString(23, product.getMostRecentDate());
            p.executeUpdate();
        }
    }
    

    public Product getProduct(String productID) throws SQLException {
        Product product = new Product();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE productID = '" + productID + "'");
        
        while (rs.next()) {
            product.setProductID(rs.getString("productID"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getString("price"));
            product.setBarcode(rs.getString("barcode"));
            product.setCurrency(rs.getString("currency"));
            product.setImageURL(rs.getString("imageURL"));
            product.setCategory(rs.getString("category"));
            product.setBrand(rs.getString("brand"));
            product.setCommissionRate(rs.getString("commissionRate"));
            product.setAffiliate(rs.getString("affiliate"));
            product.setWebURL(rs.getString("webURL"));
            product.setAffiliateWebURL(rs.getString("affiliateWebURL"));
            product.setDescription(rs.getString("description"));
            product.setEarningsPerClick(rs.getString("earningsPerClick"));
            product.setTotalSalesVolume(rs.getString("totalSalesVolume"));
            product.setCouponsAvailable(rs.getBoolean("couponsAvailable"));
            product.setTopRated(rs.getBoolean("topRated"));
            product.setNew(rs.getBoolean("isNew"));
            product.setPriorityListing(rs.getBoolean("isPriorityListing"));
            product.setOnSale(rs.getBoolean("onSale"));
            product.setOnPromotion(rs.getBoolean("onPromotion"));
            product.setLogoURI(rs.getString("logoURI"));
            product.setMostRecentDate(rs.getString("mostRecentDate"));
        }
        
        return product;
    }
    

    public Product getProductUsingImage(String imageURL) throws SQLException {
        Product product = new Product();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE imageURL = '" + imageURL + "'");
        while (rs.next()) {
            product.setProductID(rs.getString("productID"));
            product.setPrice(rs.getString("price"));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setCurrency(rs.getString("currency"));
            product.setImageURL(rs.getString("imageURL"));
            product.setCategory(rs.getString("category"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setAffiliate(rs.getString("affiliate"));
            product.setCommissionRate(rs.getString("commissionRate"));
        }
        return product;
    }


    


    public String getProductCount() throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Product");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return Integer.toString(count);
    }

    public boolean productIDExists(String productID) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE productID = '" + productID + "'");
        return rs.next();
    }

    public boolean productNameExists(String name) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE name = '" + name + "'");
        return rs.next();
    }

    public boolean productBarcodeExists(String barcode) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE barcode = '" + barcode + "'");
        return rs.next();
    }

    public boolean productImageURLExists(String imageURL) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE imageURL = '" + imageURL + "'");
        return rs.next();
    }

    public boolean productWebURLExists(String webURL) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE webURL = '" + webURL + "'");
        return rs.next();
    }

    public void insertFavorite(Favorite favorite) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO Favorite VALUES(?,?,?,?)");
        p.setString(1, favorite.getFavoriteID());
        p.setString(2, favorite.getCreatorID());
        p.setString(3, favorite.getProductID());
        p.setString(4, favorite.getDateofFavorite());
        p.executeUpdate();
    }

    public List<Favorite> getFavorites(String creatorID) throws SQLException {
        List<Favorite> favorites = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Favorite WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            Favorite favorite = new Favorite();
            favorite.setFavoriteID(rs.getString("favoriteID"));
            favorite.setCreatorID(rs.getString("creatorID"));
            favorite.setProductID(rs.getString("productID"));
            favorites.add(favorite);
        }
        return favorites;
    }

    public Favorite getFavorite(String favoriteID) throws SQLException {
        Favorite favorite = new Favorite();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Favorite WHERE favoriteID = '" + favoriteID + "'");
        while (rs.next()) {
            favorite.setFavoriteID(rs.getString("favoriteID"));
            favorite.setCreatorID(rs.getString("creatorID"));
            favorite.setProductID(rs.getString("productID"));
            favorite.setDateOfFavorite(rs.getString("dateOfFavorite"));
        }
        return favorite;
    }

    public boolean favoriteExists(String creatorID, String productID) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Favorite WHERE creatorID = '" + creatorID + "' AND productID = '" + productID + "'");
        return rs.next();
    }

    public void insertScanHistory(Scan scanHistory) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO Scan VALUES(?,?,?,?,?,?)");
        p.setString(1, scanHistory.getScanID());
        p.setString(2, scanHistory.getCreatorID());
        p.setString(4, scanHistory.getProductBarcode());
        p.setString(3, scanHistory.getDateOfScan());
        p.setString(5, scanHistory.getName());
        p.setString(6, scanHistory.getPhoto());
        p.executeUpdate();
    }

    public Scan getScanHistory(String scanID) throws SQLException {
        Scan scanHistory = new Scan();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Scan WHERE scanID = '" + scanID + "'");
        while (rs.next()) {
            scanHistory.setScanID(rs.getString("scanID"));
            scanHistory.setCreatorID(rs.getString("creatorID"));
            scanHistory.setProductBarcode(rs.getString("productBarcode"));
            scanHistory.setDateOfScan(rs.getString("dateOfScan"));
            scanHistory.setName(rs.getString("name"));
            scanHistory.setPhoto(rs.getString("photo"));
        }
        return scanHistory;
    }

    public List<Scan> getScanHistories(String creatorID) throws SQLException {
        List<Scan> scanHistories = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Scan WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            Scan scanHistory = new Scan();
            scanHistory.setScanID(rs.getString("scanID"));
            scanHistory.setCreatorID(rs.getString("creatorID"));
            scanHistory.setProductBarcode(rs.getString("barcode"));
            scanHistory.setDateOfScan(rs.getString("dateOfScan"));
            scanHistory.setName(rs.getString("name"));
            scanHistory.setPhoto(rs.getString("photo"));
            scanHistories.add(scanHistory);
        }
        return scanHistories;
    }

    

    public boolean creatorEmailExists(String email) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Creator WHERE email = '" + email + "'");
        return rs.next();
    }

    public boolean creatorIDExists(String creatorID) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Creator WHERE creatorID = '" + creatorID + "'");
        return rs.next();
    }

    public void deleteCreator(String email) throws SQLException {
        Statement s = con.createStatement();
        s.executeUpdate("DELETE FROM Creator WHERE email = '" + email + "'");
    }

    public void deleteProduct(String productID) throws SQLException {
        Statement s = con.createStatement();
        s.executeUpdate("DELETE FROM Product WHERE productID = '" + productID + "'");
    }

    public void deleteFavorite(String favoriteID) throws SQLException {
        Statement s = con.createStatement();
        s.executeUpdate("DELETE FROM Favorite WHERE favoriteID = '" + favoriteID + "'");
    }

    //

    public void deleteScanHistory(String scanID) throws SQLException {
        Statement s = con.createStatement();
        s.executeUpdate("DELETE FROM Scan WHERE scanID = '" + scanID + "'");
    }

    public void insertShare(Share share) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO Share VALUES(?,?,?,?)");
        p.setString(1, share.getShareID());
        p.setString(2, share.getCreatorID());
        p.setString(3, share.getProductID());
        p.setString(4, share.getDateOfShare());
        p.executeUpdate();
    }

    public Share getShare(String shareID) throws SQLException {
        Share share = new Share();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Share WHERE shareID = '" + shareID + "'");
        while (rs.next()) {
            share.setShareID(rs.getString("shareID"));
            share.setCreatorID(rs.getString("creatorID"));
            share.setProductID(rs.getString("productID"));
            share.setDateOfShare(rs.getString("dateOfShare"));
        }
        return share;
    }

    public List<Share> getShares(String creatorID) throws SQLException {
        List<Share> shares = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Share WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            Share share = new Share();
            share.setShareID(rs.getString("shareID"));
            share.setCreatorID(rs.getString("creatorID"));
            share.setProductID(rs.getString("productID"));
            share.setDateOfShare(rs.getString("dateOfShare"));
            shares.add(share);
        }
        return shares;
    }


    //share counts
    public int getTotalShareCount() throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Share");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getProductShareCount(String productID) throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Share WHERE productID = '" + productID + "'");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getCreatorShareCount(String creatorID) throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Share WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    // favoriute counts

    public int getTotalFavoriteCount() throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Favorite");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getProductFavoriteCount(String productID) throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Favorite WHERE productID = '" + productID + "'");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getCreatorFavoriteCount(String creatorID) throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Favorite WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }


    //scan counts
    public int getTotalScanCount() throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Scan");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getProductScanCount(String productBarcode) throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Scan WHERE barcode = '" + productBarcode + "'");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getCreatorScanCount(String creatorID) throws SQLException {
        int count = 0;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Scan WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    // rankings
    public List<Share> get5MostSharedProducts() throws SQLException {
        List<Share> shares = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT s.shareID, s.creatorID, s.productID, s.dateOfShare " +
            "FROM Share s " +
            "JOIN (SELECT productID, MAX(shareID) as latestShareID, COUNT(*) AS share_count " +
            "      FROM Share " +
            "      GROUP BY productID " +
            "      ORDER BY share_count DESC " +
            "      LIMIT 5) subquery " +
            "ON s.shareID = subquery.latestShareID"
        );
        while (rs.next()) {
            Share share = new Share();
            share.setShareID(rs.getString("shareID"));
            share.setCreatorID(rs.getString("creatorID"));
            share.setProductID(rs.getString("productID"));
            share.setDateOfShare(rs.getString("dateOfShare"));
            shares.add(share);
        }
        return shares;
    }

    public List<Favorite> get5MostFavoritedProducts() throws SQLException {
        List<Favorite> favorites = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT f.favoriteID, f.creatorID, f.productID, f.dateOfFavorite " +
            "FROM Favorite f " +
            "JOIN (SELECT productID, MAX(favoriteID) as latestFavoriteID, COUNT(*) AS favorite_count " +
            "      FROM Favorite " +
            "      GROUP BY productID " +
            "      ORDER BY favorite_count DESC " +
            "      LIMIT 5) subquery " +
            "ON f.favoriteID = subquery.latestFavoriteID"
        );
        while (rs.next()) {
            Favorite favorite = new Favorite();
            favorite.setFavoriteID(rs.getString("favoriteID"));
            favorite.setCreatorID(rs.getString("creatorID"));
            favorite.setProductID(rs.getString("productID"));
            favorite.setDateOfFavorite(rs.getString("dateOfFavorite")); 
            favorites.add(favorite);
        }
        return favorites;
    }
    
    
        

    public List<Scan> get5MostScannedProducts() throws SQLException {
        List<Scan> scanHistories = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT sh.* FROM Scan sh " +
            "JOIN (SELECT barcode, MAX(scanID) as latestScanID, COUNT(*) AS scan_count " +
            "      FROM Scan " +
            "      GROUP BY barcode " +
            "      ORDER BY scan_count DESC " +
            "      LIMIT 5) subquery " +
            "ON sh.scanID = subquery.latestScanID"
        );
        while (rs.next()) {
            Scan scanHistory = new Scan();
            scanHistory.setScanID(rs.getString("scanID"));
            scanHistory.setCreatorID(rs.getString("creatorID"));
            scanHistory.setProductBarcode(rs.getString("barcode"));
            scanHistory.setDateOfScan(rs.getString("dateOfScan"));
            scanHistory.setName(rs.getString("name"));
            scanHistory.setPhoto(rs.getString("photo"));
            scanHistories.add(scanHistory);
        }
        return scanHistories;
    }
    
    public List<String> getMostScannedProductString() throws SQLException {
        List<String> scanHistories = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT sh.* FROM Scan sh " +
            "JOIN (SELECT barcode, MAX(scanID) as latestScanID, COUNT(*) AS scan_count " +
            "      FROM Scan " +
            "      GROUP BY barcode " +
            "      ORDER BY scan_count DESC " +
            "      LIMIT 5) subquery " +
            "ON sh.scanID = subquery.latestScanID"
        );
        while (rs.next()) {
            scanHistories.add(rs.getString("name"));
        }
        return scanHistories;
    }
    


    //most recent
    public Share getMostRecentShare() throws SQLException {
        Share share = new Share();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Share ORDER BY dateOfShare DESC LIMIT 1");
        while (rs.next()) {
            share.setShareID(rs.getString("shareID"));
            share.setCreatorID(rs.getString("creatorID"));
            share.setProductID(rs.getString("productID"));
            share.setDateOfShare(rs.getString("dateOfShare"));
        }
        return share;
    }

    public Favorite getMostRecentFavorite() throws SQLException {
        Favorite favorite = new Favorite();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Favorite ORDER BY dateOfFavorite DESC LIMIT 1");
        while (rs.next()) {
            favorite.setFavoriteID(rs.getString("favoriteID"));
            favorite.setCreatorID(rs.getString("creatorID"));
            favorite.setProductID(rs.getString("productID"));
        }
        return favorite;
    }

    public Scan getMostRecentScan() throws SQLException {
        Scan scanHistory = new Scan();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Scan ORDER BY dateOfScan DESC LIMIT 1");
        while (rs.next()) {
            scanHistory.setScanID(rs.getString("scanID"));
            scanHistory.setCreatorID(rs.getString("creatorID"));
            scanHistory.setProductBarcode(rs.getString("barcode"));
            scanHistory.setDateOfScan(rs.getString("dateOfScan"));
            scanHistory.setName(rs.getString("name"));
            scanHistory.setPhoto(rs.getString("photo"));
        }
        return scanHistory;
    }

    //gets all scanned products
    public List<Product> getAllScannedProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE barcode IN (SELECT barcode FROM Scan)");
        while (rs.next()) {
            Product product = new Product();
            product.setProductID(rs.getString("productID"));
            product.setPrice(rs.getString("price"));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setCurrency(rs.getString("currency"));
            product.setImageURL(rs.getString("imageURL"));
            product.setCategory(rs.getString("category"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setAffiliate(rs.getString("affiliate"));
            product.setCommissionRate(rs.getString("commissionRate"));
            products.add(product);
        }
        return products;
    }

    //gets all shared products
    public List<Product> getAllSharedProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE productID IN (SELECT productID FROM Share)");
        while (rs.next()) {
            Product product = new Product();
            product.setProductID(rs.getString("productID"));
            product.setPrice(rs.getString("price"));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setCurrency(rs.getString("currency"));
            product.setImageURL(rs.getString("imageURL"));
            product.setCategory(rs.getString("category"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setAffiliate(rs.getString("affiliate"));
            product.setCommissionRate(rs.getString("commissionRate"));
            products.add(product);
        }
        return products;
    }

    //gets all favorited products
    public List<Product> getAllFavoritedProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Product WHERE productID IN (SELECT productID FROM Favorite)");
        while (rs.next()) {
            Product product = new Product();
            product.setProductID(rs.getString("productID"));
            product.setPrice(rs.getString("price"));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setCurrency(rs.getString("currency"));
            product.setImageURL(rs.getString("imageURL"));
            product.setCategory(rs.getString("category"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setAffiliate(rs.getString("affiliate"));
            product.setCommissionRate(rs.getString("commissionRate"));
            products.add(product);
        }
        return products;
    }

    //5 most scanned brands
    public List<String> get5MostScannedBrands() throws SQLException {
        List<String> brands = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT p.brand, COUNT(*) AS scan_count " +
            "FROM Product p " +
            "JOIN Scan sh ON p.barcode = sh.barcode " +
            "GROUP BY p.brand " +
            "ORDER BY scan_count DESC " +
            "LIMIT 5"
        );
        while (rs.next()) {
            brands.add(rs.getString("brand"));
        }
        return brands;
    }

    //5 most favorited brands
    public List<String> get5MostFavoritedBrands() throws SQLException {
        List<String> brands = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT p.brand, COUNT(*) AS favorite_count " +
            "FROM Product p " +
            "JOIN Favorite f ON p.productID = f.productID " +
            "GROUP BY p.brand " +
            "ORDER BY favorite_count DESC " +
            "LIMIT 5"
        );
        while (rs.next()) {
            brands.add(rs.getString("brand"));
        }
        return brands;
    }

    

    //brand and category
    public String getMostScannedBrand() throws SQLException {
        //get list of scanned products using scan hisotry
        List<Product> products = new ArrayList<>();
        products = getAllScannedProducts();

        //get the most scanned brand
        String mostScannedBrand = "";
        int maxCount = 0;
        for (Product product : products) {
            int count = 0;
            for (Product product2 : products) {
                if (product.getBrand().equals(product2.getBrand())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostScannedBrand = product.getBrand();
            }
        }
        return mostScannedBrand;
    }

    //5 most shared brands
    public List<String> get5MostSharedBrands() throws SQLException {
        List<String> brands = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT p.brand, COUNT(*) AS share_count " +
            "FROM Product p " +
            "JOIN Share s ON p.productID = s.productID " +
            "GROUP BY p.brand " +
            "ORDER BY share_count DESC " +
            "LIMIT 5"
        );
        while (rs.next()) {
            brands.add(rs.getString("brand"));
        }
        return brands;
    }

    public String getMostSharedBrand() throws SQLException {
        //get list of shared products using share
        List<Product> products = new ArrayList<>();
        products = getAllSharedProducts();

        //get the most shared brand
        String mostSharedBrand = "";
        int maxCount = 0;
        for (Product product : products) {
            int count = 0;
            for (Product product2 : products) {
                if (product.getBrand().equals(product2.getBrand())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostSharedBrand = product.getBrand();
            }
        }
        return mostSharedBrand;
    }

    public String getMostFavoritedBrand() throws SQLException {
        //get list of favorited products using favorite
        List<Product> products = new ArrayList<>();
        products = getAllFavoritedProducts();

        //get the most favorited brand
        String mostFavoritedBrand = "";
        int maxCount = 0;
        for (Product product : products) {
            int count = 0;
            for (Product product2 : products) {
                if (product.getBrand().equals(product2.getBrand())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostFavoritedBrand = product.getBrand();
            }
        }
        return mostFavoritedBrand;
    }

    public String getMostScannedCategory() throws SQLException {
        //get list of scanned products using scan hisotry
        List<Product> products = new ArrayList<>();
        products = getAllScannedProducts();

        //get the most scanned category
        String mostScannedCategory = "";
        int maxCount = 0;
        for (Product product : products) {
            int count = 0;
            for (Product product2 : products) {
                if (product.getCategory().equals(product2.getCategory())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostScannedCategory = product.getCategory();
            }
        }
        return mostScannedCategory;
    }

    public String getMostSharedCategory() throws SQLException {
        //get list of shared products using share
        List<Product> products = new ArrayList<>();
        products = getAllSharedProducts();

        //get the most shared category
        String mostSharedCategory = "";
        int maxCount = 0;
        for (Product product : products) {
            int count = 0;
            for (Product product2 : products) {
                if (product.getCategory().equals(product2.getCategory())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostSharedCategory = product.getCategory();
            }
        }
        return mostSharedCategory;
    }

    public String getMostFavoritedCategory() throws SQLException {
        //get list of favorited products using favorite
        List<Product> products = new ArrayList<>();
        products = getAllFavoritedProducts();

        //get the most favorited category
        String mostFavoritedCategory = "";
        int maxCount = 0;
        for (Product product : products) {
            int count = 0;
            for (Product product2 : products) {
                if (product.getCategory().equals(product2.getCategory())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostFavoritedCategory = product.getCategory();
            }
        }
        return mostFavoritedCategory;
    }

    //5 most scanned categories
    public List<String> get5MostScannedCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT p.category, COUNT(*) AS scan_count " +
            "FROM Product p " +
            "JOIN Scan sh ON p.barcode = sh.barcode " +
            "GROUP BY p.category " +
            "ORDER BY scan_count DESC " +
            "LIMIT 5"
        );
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
        return categories;
    }

    //5 most shared categories
    public List<String> get5MostSharedCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT p.category, COUNT(*) AS share_count " +
            "FROM Product p " +
            "JOIN Share s ON p.productID = s.productID " +
            "GROUP BY p.category " +
            "ORDER BY share_count DESC " +
            "LIMIT 5"
        );
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
        return categories;
    }

    //5 most favorited categories
    public List<String> get5MostFavoritedCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(
            "SELECT p.category, COUNT(*) AS favorite_count " +
            "FROM Product p " +
            "JOIN Favorite f ON p.productID = f.productID " +
            "GROUP BY p.category " +
            "ORDER BY favorite_count DESC " +
            "LIMIT 5"
        );
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
        return categories;
    }

    public APIKeys getAPIKeys(String creatorID) throws SQLException {
        APIKeys apiKeys = new APIKeys();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM APIKeys WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            apiKeys.setAPIKeysID(rs.getString("APIKeysID"));
            apiKeys.setCreatorID(rs.getString("creatorID"));
            apiKeys.setImpactUsername(rs.getString("impactUsername"));
            apiKeys.setImpactPassword(rs.getString("impactPassword"));
            apiKeys.setEbayKey(rs.getString("ebayKey"));
        }
        return apiKeys;
    }

    public void insertAPIKeys(APIKeys apiKeys) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO APIKeys VALUES(?,?,?,?,?,?)");
        p.setString(1, apiKeys.getAPIKeysID());
        p.setString(2, apiKeys.getCreatorID());
        p.setString(3, apiKeys.getImpactUsername());
        p.setString(4, apiKeys.getImpactPassword());
        p.setString(5, apiKeys.getEbayKey());
        p.executeUpdate();
    }

    public void updateImpactUsername(String creatorID, String impactUsername) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE APIKeys SET impactUsername = ? WHERE creatorID = ?");
        p.setString(1, impactUsername);
        p.setString(2, creatorID);
        p.executeUpdate();
    }

    public void updateImpactPassword(String creatorID, String impactPassword) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE APIKeys SET impactPassword = ? WHERE creatorID = ?");
        p.setString(1, impactPassword);
        p.setString(2, creatorID);
        p.executeUpdate();
    }

    public void updateEbayKey(String creatorID, String ebayKey) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE APIKeys SET ebayKey = ? WHERE creatorID = ?");
        p.setString(1, ebayKey);
        p.setString(2, creatorID);
        p.executeUpdate();
    }

    public void updateAPIKeys(String creatorID, String impactUsername, String impactPassword, String ebayKey) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE APIKeys SET impactUsername = ?, impactPassword = ?, ebayKey = ? WHERE creatorID = ?");
        p.setString(1, impactUsername);
        p.setString(2, impactPassword);
        p.setString(3, ebayKey);
        p.setString(4, creatorID);
        p.executeUpdate();
    }

    public CreatorAttributes getCreatorAttributes(String creatorID) throws SQLException {
        CreatorAttributes creatorAttributes = new CreatorAttributes();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM CreatorAttributes WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            creatorAttributes.setAttributesID(rs.getString("attributeID"));
            creatorAttributes.setCreatorID(rs.getString("creatorID"));
            creatorAttributes.setWork(rs.getString("work"));
            creatorAttributes.setLocation(rs.getString("location"));
            creatorAttributes.setBio(rs.getString("bio"));
            creatorAttributes.setBio(rs.getString("bio"));
            creatorAttributes.setInfluencerLevel(rs.getString("influencerLevel"));
            creatorAttributes.setTone(rs.getString("tone"));
            creatorAttributes.setTechLevel(rs.getString("techLevel"));
            creatorAttributes.setMindedness(rs.getString("mindedness"));
            creatorAttributes.setAge(rs.getString("age"));
        }
        return creatorAttributes;
    }

    public void insertCreatorAttributes(CreatorAttributes creatorAttributes) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO CreatorAttributes VALUES(?,?,?,?,?,?,?,?,?,?)");
        p.setString(2, creatorAttributes.getAttributesID());
        p.setString(1, creatorAttributes.getCreatorID());
        p.setString(3, creatorAttributes.getWork());
        p.setString(4, creatorAttributes.getLocation());
        p.setString(5, creatorAttributes.getBio());
        p.setString(6, creatorAttributes.getInfluencerLevel());
        p.setString(7, creatorAttributes.getTone());
        p.setString(8, creatorAttributes.getTechLevel());
        p.setString(9, creatorAttributes.getMindedness());
        p.setString(10, creatorAttributes.getAge());
        p.executeUpdate();
    }
}
