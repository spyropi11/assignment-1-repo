/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.tests;

import edu.vanier.controllers.PostalCodeController;
import edu.vanier.models.PostalCode;
import java.util.HashMap;
import javafx.event.ActionEvent;


/**
 *
 * @author Spyros
 */
public class Driver{
    
    
    public static void main(String[] args) {
        
        if (testParse(false)){
            
            testDistanceTo("H7A", "X0A");
            testNearbyLocations("X0B", 800);
            
        }
        
    }
    
    public static boolean testParse(boolean print){
        
        try{
            
            PostalCodeController PCC = new PostalCodeController();
            HashMap<String, PostalCode> postalCodeHashmap = PCC.parse(new ActionEvent());
            
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
            
        }
        
        

        
    }
    
    public static void testDistanceTo(String postalCode1, String postalCode2){
        
        PostalCodeController PCC = new PostalCodeController();

        System.out.println("Distance from " + postalCode1 + " to " + postalCode2 + " is " + PCC.distanceTo(postalCode1, postalCode2) + " Kilometers");
        
        
    }
    
    public static void testNearbyLocations(String postalCode1, double distance){
        
        PostalCodeController PCC = new PostalCodeController();
        HashMap<String, PostalCode> postalCodeHashmap = PCC.parse(new ActionEvent());
        
        PostalCode from = postalCodeHashmap.get(postalCode1);
        
        System.out.println("Nearby locations within " + distance + "km of postal code: " + from.getPostalCode() + " are as follows\n");
        
        for(PostalCode postalCode : PCC.nearbyLocations(postalCode1, 1000).values()){
            
            System.out.println(postalCode);
            
        }
        
        
    }

    
    
}
