/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.tests;

import edu.vanier.controllers.PostalCodeController;
import edu.vanier.models.PostalCode;
import java.util.HashMap;


/**
 *
 * @author Spyros
 */
public class Driver{
    
    
    public static void main(String[] args) {
        
        /*Checks if the testParse return value is true, indicating that the parsing
            of the csv was succesful*/
        if (testParse(false)){
            
            //Values below can be changed depending on what the user wants to test
            testDistanceTo("H7A", "X0A");
            testNearbyLocations("T5H", 50);
            
        }
        
    }
    
    
    /**
     * 
     * This method tests that csv is properly parsed and prints the parsed 
     * values if the user sets the print parameter to true.
     * 
     * @param print if set to true the parsed information will be printed in the console
     * @return Returns either true or false depending on whether the csv could be parsed
     */
    public static boolean testParse(boolean print){
        
        try{
            
            PostalCodeController PCC = new PostalCodeController();
            HashMap<String, PostalCode> postalCodeHashmap = PCC.parse();
            
            if (print){
                
                for (PostalCode postalCodes : postalCodeHashmap.values()){
            
                    System.out.println(postalCodes);
            
                }
                
            }
            
            System.out.println("Test passed");
            
            return true;
            
        }catch(NullPointerException e){
            
            System.out.println("Test failed, check that the file path is correct.");
            
            return false;
            
        }catch(Exception e){
            
            e.printStackTrace();
            
            return false;
            
        }
        
        

        
    }
    
    
    /**
     * 
     * This method tests the distanceTo method by printing the value between two 
     * postal codes to the console (considering that the postal codes are valid).
     * 
     * @param postalCode1 postal Code string value representing the postal code the user
     * would like to start from.
     * @param postalCode2  postal Code string value representing the postal code the user
     * would like to go to.
     */
    public static void testDistanceTo(String postalCode1, String postalCode2){
        
        try{
            
            PostalCodeController PCC = new PostalCodeController();
            System.out.println("Distance from " + postalCode1 + " to " + postalCode2 + " is " + 
                    PCC.distanceTo(postalCode1.toUpperCase(), postalCode2.toUpperCase()) + " Kilometers");
            
        }catch(NullPointerException e){
            
            System.out.println("Cannot test the distance between two postal codes as"
                    + " one or more of the postal codes have not been inputted or are not valid");
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        
        
    }
    
    /**
     * 
     * This method tests the nearbyLocations method by printing the toString 
     * of PostalCode objects within a specified radius of a given postal code 
     * (considering that the postal code is valid).
     * 
     * @param postalCode1 postal Code string value representing the postal code the user 
     * wants to start from.
     * @param distance Distance of the radius wanted to search for nearby locations.
     */
    public static void testNearbyLocations(String postalCode1, double distance){
        
        try{
            
            PostalCodeController PCC = new PostalCodeController();
            HashMap<String, PostalCode> postalCodeHashmap = PCC.parse();

            PostalCode from = postalCodeHashmap.get(postalCode1.toUpperCase());

            System.out.println("Nearby locations within " + distance + "km of postal code: " + from.getPostalCode() + " are as follows\n");

            for(PostalCode postalCode : PCC.nearbyLocations(postalCode1.toUpperCase(), distance).values()){

                System.out.println(postalCode);

            }

        }catch(NullPointerException e){
            
            System.out.println("A valid postal code was not inputted");

            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        
        
    }

    
    
}
