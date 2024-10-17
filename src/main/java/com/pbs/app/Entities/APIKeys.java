package com.pbs.app.Entities;

public class APIKeys {
    String impactUsername, impactPassword, creatorID, APIKeysID, ebayKey;
    public APIKeys(String impactUsername, String impactPassword, String creatorID, String APIKeysID, String ebayKey) {
        this.impactUsername = impactUsername;
        this.impactPassword = impactPassword;
        this.creatorID = creatorID;
        this.APIKeysID = APIKeysID;
        this.ebayKey = ebayKey;
    }
    public APIKeys() {
    }
    public String getImpactUsername() {
        return impactUsername;
    }
    public void setImpactUsername(String impactUsername) {
        this.impactUsername = impactUsername;
    }
    public String getImpactPassword() {
        return impactPassword;
    }
    public void setImpactPassword(String impactPassword) {
        this.impactPassword = impactPassword;
    }
    public String getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }
    public String getAPIKeysID() {
        return APIKeysID;
    }
    public void setAPIKeysID(String APIKeysID) {
        this.APIKeysID = APIKeysID;
    }
    public String getEbayKey() {
        return ebayKey;
    }
    public void setEbayKey(String ebayKey) {
        this.ebayKey = ebayKey;
    }
}
