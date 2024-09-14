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

import com.pbs.Entities.Creator;
import com.pbs.Entities.Favorite;
import com.pbs.Entities.Product;
import com.pbs.Entities.ScanHistory;

public class Data {
    private static final Logger LOGGER = Logger.getLogger(Data.class.getName());

    Connection con;
    PreparedStatement p;
    Statement s;
    ResultSet rs;
    String url, username, password, port;

    public Data() {
        url = "jdbc:mysql://pbs-285adc52-pbs-8acb.h.aivencloud.com:18028/defaultdb";
        username = "avnadmin";
        password = "AVNS_ymzLSOatFTVAxEQEQGQ";
        port = "18028";
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
        p = con.prepareStatement("INSERT INTO Creator VALUES(?,?,?,?,?)");
        p.setString(1, creator.getCreatorID());
        p.setString(2, creator.getFirstName());
        p.setString(3, creator.getSurname());
        p.setString(4, creator.getEmail());
        p.setString(5, creator.getPassword());
        p.executeUpdate();
    }

    public Creator getCreator(String email) throws SQLException {
        Creator creator = new Creator();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Creator WHERE email = '" + email + "'");
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
        s = con.createStatement();
        rs = s.executeQuery("SELECT COUNT(*) FROM Creator");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }


    

    public void insertProduct(Product product) throws SQLException {
        String insertQuery = "INSERT INTO Product (productID, name, price, barcode, currency, imageURL, category, brand, commissionRate, affiliate, webURL, affiliateWebURL, description, earningsPerClick, totalSalesVolume, couponsAvailable, topRated, isNew, isPriorityListing, onSale, onPromotion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
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
            p.executeUpdate();
        }
    }
    

    public Product getProduct(String productID) throws SQLException {
        Product product = new Product();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Product WHERE productID = '" + productID + "'");
        while (rs.next()) {
            product.setProductID(rs.getString("productID"));
            product.setPrice(rs.getString("price"));
            product.setBarcode(rs.getString("barcode"));
            product.setName(rs.getString("name"));
            product.setCurrency(rs.getString("currency"));
            product.setImageURL(rs.getString("imageURL"));
            product.setLogoURI(rs.getString("logoURI"));
            product.setCategory(rs.getString("category"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setAffiliate(rs.getString("affiliate"));
            product.setCommissionRate(rs.getString("commissionRate"));
        }
        return product;
    }

    public Product getProductUsingImage(String imageURL) throws SQLException {
        Product product = new Product();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Product WHERE imageURL = '" + imageURL + "'");
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
        s = con.createStatement();
        rs = s.executeQuery("SELECT COUNT(*) FROM Product");
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return Integer.toString(count);
    }

    public boolean productIDExists(String productID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Product WHERE productID = '" + productID + "'");
        return rs.next();
    }

    public boolean productNameExists(String name) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Product WHERE name = '" + name + "'");
        return rs.next();
    }

    public boolean productBarcodeExists(String barcode) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Product WHERE barcode = '" + barcode + "'");
        return rs.next();
    }

    public boolean productImageURLExists(String imageURL) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Product WHERE imageURL = '" + imageURL + "'");
        return rs.next();
    }

    public void insertFavorite(Favorite favorite) throws SQLException {
        p = con.prepareStatement("INSERT INTO Favorite VALUES(?,?,?)");
        p.setString(1, favorite.getFavoriteID());
        p.setString(2, favorite.getCreatorID());
        p.setString(3, favorite.getProductID());
        p.executeUpdate();
        closeConnection();
    }

    public List<Favorite> getFavorites(String creatorID) throws SQLException {
        List<Favorite> favorites = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Favorite WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            Favorite favorite = new Favorite();
            favorite.setFavoriteID(rs.getString("favoriteID"));
            favorite.setCreatorID(rs.getString("creatorID"));
            favorite.setProductID(rs.getString("productID"));
            favorites.add(favorite);
        }
        return favorites;
    }

    //check to see if a favorite row exists using a creatorID and productID
    public boolean favoriteExists(String creatorID, String productID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Favorite WHERE creatorID = '" + creatorID + "' AND productID = '" + productID + "'");
        return rs.next();
    }

    public void insertScanHistory(ScanHistory scanHistory) throws SQLException {
        p = con.prepareStatement("INSERT INTO scan_history VALUES(?,?,?,?,?,?)");
        p.setString(1, scanHistory.getScanID());
        p.setString(2, scanHistory.getCreatorID());
        p.setString(4, scanHistory.getProductBarcode());
        p.setString(3, scanHistory.getDateOfScan());
        p.setString(5, scanHistory.getName());
        p.setString(6, scanHistory.getPhoto());
        p.executeUpdate();
    }

    public ScanHistory getScanHistory(String scanID) throws SQLException {
        ScanHistory scanHistory = new ScanHistory();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM scan_history WHERE scanID = '" + scanID + "'");
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

    public List<ScanHistory> getScanHistories(String creatorID) throws SQLException {
        List<ScanHistory> scanHistories = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM scan_history WHERE creatorID = '" + creatorID + "'");
        while (rs.next()) {
            ScanHistory scanHistory = new ScanHistory();
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
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Creator WHERE email = '" + email + "'");
        return rs.next();
    }

    public boolean creatorIDExists(String creatorID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Creator WHERE creatorID = '" + creatorID + "'");
        return rs.next();
    }

    public void deleteCreator(String email) throws SQLException {
        s = con.createStatement();
        s.executeUpdate("DELETE FROM Creator WHERE email = '" + email + "'");
    }

    public void deleteProduct(String productID) throws SQLException {
        s = con.createStatement();
        s.executeUpdate("DELETE FROM Product WHERE productID = '" + productID + "'");
    }

    public void deleteFavorite(String favoriteID) throws SQLException {
        s = con.createStatement();
        s.executeUpdate("DELETE FROM Favorite WHERE favoriteID = '" + favoriteID + "'");
    }

    public void deleteScanHistory(String scanID) throws SQLException {
        s = con.createStatement();
        s.executeUpdate("DELETE FROM ScanHistory WHERE scanID = '" + scanID + "'");
    }




    
}
