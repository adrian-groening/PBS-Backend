package com.pbs.Entities;

public class Creator {
    public String creatorID, firstName, surname, email, password;
    public Creator() {
    }
    public Creator(String creatorID, String firstName, String surname, String email, String password) {
        this.creatorID = creatorID;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
    public String getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
