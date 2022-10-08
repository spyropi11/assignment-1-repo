/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import edu.vanier.models.PostalCode;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class PostalCodeController{

    
    @FXML
    Button computeDistanceButton;
    @FXML
    Label distanceValue;
    @FXML
    TextField postalCode1TextField;
    @FXML
    TextField postalCode2TextField;
    @FXML
    Button backDCButton;
    @FXML
    Button openDCMenuButton;
    @FXML
    Button openNPCMenuButton;
    
    
    private HashMap<String, PostalCode> postalCodes = new HashMap<>();
    
    /* Default csvFilePath is set, if the user wants to change it they can use the PCC constructor 
    that modifies the file path */
    private String csvFilePath = "/data/zipcodes.csv";

    
    //Constructors
    public PostalCodeController() {
        
    }
    
    public PostalCodeController(String csvFilePath) {
        
        this.csvFilePath = csvFilePath;
        
    }
    

    //Getters and Setters
    
    public HashMap<String, PostalCode> getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(HashMap<String, PostalCode> postalCodes) {
        this.postalCodes = postalCodes;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }
    
    
    //Methods
    
    /**
     * @param event
     * @return Returns a hashmap object containing PostalCode objects that can be accessed using postal code String keys.
     */
    @FXML
    public HashMap<String, PostalCode> parse(ActionEvent event){
    
        String completeFilePath = getClass().getResource(csvFilePath).getPath();
        
        try{
            
            CSVReader reader = new CSVReaderBuilder(new FileReader(completeFilePath)).build();
            String[] nextLine;
            
            while ((nextLine = reader.readNext()) != null){
                
                postalCodes.put(nextLine[2], new PostalCode(Integer.parseInt(nextLine[0]),nextLine[3],nextLine[1],
                nextLine[2],nextLine[4],Double.parseDouble(nextLine[5]),Double.parseDouble(nextLine[6])));
                
            }
            
            
        }catch(Exception e){
            
            System.out.println(e.getMessage());
            
        }
        
    
        return postalCodes;
    }
    
    
    
    /**
     * 
     * @param from PostalCode object representing the postal code you want to start from.
     * @param to PostalCode object representing the postal code you want to go to.
     * @return Returns the distance between the two postal codes.
     */
    public double distanceTo(String postalCode1, String postalCode2){
        
        HashMap<String, PostalCode> postalCodeHashmap = parse(new ActionEvent());
        
        PostalCode from = postalCodeHashmap.get(postalCode1);
        PostalCode to = postalCodeHashmap.get(postalCode2);
        
        //ALL NUMBERS ARE IN THEIR RESPECTIVE SI UNITS
        final double RADIUS = 6371; /*kilometers*/
        
        
        double deltaLongitude = (to.getLongitude() - from.getLongitude()) * (Math.PI/180);
        double deltaLatitude = (to.getLatitude() - from.getLatitude()) * (Math.PI/180);
        
        
        double a = (Math.pow((Math.sin(deltaLatitude/2)), 2)) + Math.cos(from.getLatitude() * (Math.PI/180)) 
                * Math.cos(to.getLatitude() * (Math.PI/180)) * Math.pow((Math.sin(deltaLongitude/2)), 2);
        
        double c = 2 * (Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
        double distance = RADIUS * c;
        
        return distance;
    }
    
    /**
     * 
     * @param from PostalCode object representing the postal code you want to start from.
     * @param distance Distance of the radius you want to search for nearby locations.
     * @return Hashmap 
     */
    
    public HashMap<String, PostalCode> nearbyLocations(String postalCode1, double distance){
        
        HashMap<String, PostalCode> nearbyLocations = new HashMap<>();
        
        for (PostalCode postalCode : postalCodes.values() ){
            
            if(distanceTo(postalCode1, postalCode.getPostalCode()) <= distance){
                
                nearbyLocations.put(postalCode.getPostalCode(), postalCode);
                
            }
                    
        }
        
        return nearbyLocations;
    }
    
    @FXML
    public void handleDistanceTo(ActionEvent event){
        
        System.out.println("Distance button pressed");
        distanceValue.setText(Math.round(distanceTo(postalCode1TextField.getText(),postalCode2TextField.getText())) + "km");
        
    }
    
    @FXML
    public void handleSwitchToDCMenu(ActionEvent event) throws IOException{
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/distance_finder_menu.fxml"));
        
        PostalCodeController mainContoller = new PostalCodeController();
        loader.setController(mainContoller);
        
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void handleSwitchToMainMenu(ActionEvent event) throws IOException{
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/postal_code_main_menu.fxml"));
        
        PostalCodeController mainContoller = new PostalCodeController();
        loader.setController(mainContoller);
        
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    
}

