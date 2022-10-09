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
    
    //The number of the postal code in the csv file (Not shown to user)
    private int number;

    //The name of the area that the postal code is situated in
    private String name;
    //The country that the postal code is in
    private String country;
    //The postal code itself
    private String postalCode;
    //The province that the postal code is in
    private String province;
    //The latitude of the postal code
    private double latitude;
    //The longitude of the postal code
    private double longitude;
    
    //Contructors

    //Empty constructor
    public PostalCode() {
        
        
    }
    
    /*Contructor that allows the csv to fill a hashmap with the PostalCode 
        object while specifying it's properties*/
    public PostalCode(int number, String name, String country, String postalCode, String province, double latitude, double longitude) {
        this.number = number;
        this.name = name;
        this.country = country;
        this.postalCode = postalCode;
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    
    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    //ToString of the postal code class, allowing the object to be displayed to the user
    @Override
    public String toString(){
        
        String string = getName() + ", " + getCountry() + ", " + getProvince() + ", " + getPostalCode() + ", " + getLatitude() + ", " + getLongitude();
        
        return string;
    }
    
    
}
