/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.models;

/**
 *
 * @author Spyros
 */
public class PostalCode {
    
    //Properties
    private int number;
    
    private String id;
    private String country;
    private String postalCode;
    private String province;
    private double latitude;
    private double longitude;
    
    //Contructors

    public PostalCode() {
    }
    

    public PostalCode(int number, String id, String country, String postalCode, String province, double latitude, double longitude) {
        this.number = number;
        this.id = id;
        this.country = country;
        this.postalCode = postalCode;
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    
    //Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
    
    //Methods
    @Override
    public String toString(){
        
        String string = getNumber() + ", " + getId() + ", " + getCountry() + ", " + getProvince() + ", " + getPostalCode() + ", " + getLatitude() + ", " + getLongitude();
        
        return string;
    }
    
    
}
