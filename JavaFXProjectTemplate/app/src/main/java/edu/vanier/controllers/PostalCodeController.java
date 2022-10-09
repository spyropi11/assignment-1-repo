/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import edu.vanier.models.PostalCode;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class PostalCodeController{
    
    // Controls for main menu
    
    //Button to go to the distance calculator(DC) menu
    @FXML
    Button openDCMenuButton;
    //Button to go to the nearby postal codes calculator(NPC) menu
    @FXML
    Button openNPCMenuButton;
    
    //Controls for the distance calculator(DC) menu
    
    /*Button that calls for the contoller to compute the distance 
        between both postal codes*/
    @FXML
    Button computeDistanceButton;
    //Label that is changed to the value of the distance computed
    @FXML
    Label distanceValue;
    @FXML
    TextField postalCode1TextField;
    @FXML
    TextField postalCode2TextField;
    //Button to go back to main menu
    @FXML
    Button backDCButton;
    
    //Controls for the nearby postal codes calculator(NPC) menu
    
    /*Button that calls for the contoller to compute and display the nearby 
        locations found within the specified radius of a postal code*/
    @FXML
    Button nearbyLocationsButton;
    @FXML
    TextField radiusTextField;
    @FXML
    TextField postalCodeTextField;
    //Button to go back to main menu
    @FXML
    Button backNPCButton;
    //List view object that displays the ToString values of each nearby postal code
    @FXML
    ListView<String> postalCodeListView;
    
    
    //Hashmap holding the parsed postal code objects along with their keys from the csv file
    private HashMap<String, PostalCode> postalCodes = new HashMap<>();
    
    /* Default csvFilePath is set, if the user wants to change it they can use the PCC constructor 
    that modifies the file path */
    private String csvFilePath = "/data/zipcodes.csv";

    //Constructors
    
    //This constructor uses the default csv file path
    public PostalCodeController() {
        
    }
    
    //This constructor allows the user to change the file path from it's default setting
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
     * This method parses the csv found from the data section of the project and 
     * loads the data into a HashMap containing PostalCode objects along with their 
     * keys which are the postal codes themselves.
     * 
     * @param event
     * @return Returns a hashmap object containing PostalCode objects that can be accessed using postal code String keys.
     */
    public HashMap<String, PostalCode> parse(){
    
        String completeFilePath = getClass().getResource(csvFilePath).getPath();
        
        try{
            
            CSVReader reader = new CSVReaderBuilder(new FileReader(completeFilePath)).build();
            String[] nextLine;
            
            /*Checking that while the csv still has values to parse it continues to fill
                the hashmap with the parsed information*/
            while ((nextLine = reader.readNext()) != null){
                
                /*All numbers represent the indexes of the current nextLine array 
                    that holds the information from the csv file*/
                postalCodes.put(nextLine[2], new PostalCode(Integer.parseInt(nextLine[0]),nextLine[3],nextLine[1],
                nextLine[2],nextLine[4],Double.parseDouble(nextLine[5]),Double.parseDouble(nextLine[6])));
                
            }
            
            
        }catch(Exception e){
            
            System.out.println(e.getMessage());
            
        }
        
    
        return getPostalCodes();
    }
    
    
    
    /**
     * 
     * This method takes 2 postal code String values and computes the distance 
     * between them.
     * 
     * @param postalCode1 postal code String representing the postal code you want to start from.
     * @param postalCode2 postal code String representing the postal code you want to go to.
     * @return Returns the distance between the two postal codes.
     */
    public double distanceTo(String postalCode1, String postalCode2){
        
        //Calling the parse method fills the Controller's postalCodes hashmap property
        parse();
        
        /*Using this hashmap we can retrieve the from and to postal code objects 
            using their keys*/
        PostalCode from = postalCodes.get(postalCode1);
        PostalCode to = postalCodes.get(postalCode2);
        
        //Haversine formula is below
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
     * This method takes a postal code String value as well as a radius double value
     * and computes nearby PostalCode objects within said radius.
     * 
     * @param postalCode1 postal code String representing the postal code you want to start from.
     * @param distance Distance of the radius you want to search for nearby locations.
     * @return Hashmap 
     */
    
    public HashMap<String, PostalCode> nearbyLocations(String postalCode1, double distance){
        
        //Calling the parse method fills the Controller's postalCodes hashmap property
        parse();
        
        /*A hashmap containing the PostalCode objects of the nearby loactions as well as 
            their associated keys is created*/
        HashMap<String, PostalCode> nearbyLocations = new HashMap<>();
        
        //Iterates through postalCodes hashmap
        for (PostalCode postalCode : postalCodes.values() ){
            
            //Checks whether the current postal code is within the radius of the specified postal code
            if(distanceTo(postalCode1, postalCode.getPostalCode()) <= distance){
                
                //If so, puts that PostalCode along with its key in the nearby locations hashmap
                nearbyLocations.put(postalCode.getPostalCode(), postalCode);
                
            }
                    
        }
        
        return nearbyLocations;
    }
    
    /**
     * This method takes values from the distance calculator(DC) menu's postalCode1 text field and 
     * postalCode2 text field and uses them along with the distanceTo method to change 
     * the Label distanceValue in the scene to the computed distance between the two postal codes.
     * 
     * @param event 
     */
    @FXML
    public void handleDistanceTo(ActionEvent event){
        
        try{
            
            System.out.println("Distance button pressed");
            distanceValue.setText(Math.round(distanceTo(postalCode1TextField.getText().toUpperCase(),postalCode2TextField.getText().toUpperCase())) + "km");
            
        }catch(NullPointerException e){
            
            System.out.println("One or more postal codes have not been inputted or are not valid");
            
            Alert saveAlert = new Alert(Alert.AlertType.ERROR);
            saveAlert.setHeaderText("Error");
            saveAlert.setContentText("One or more postal codes have not been inputted or are not valid");
            
            saveAlert.showAndWait();
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
        
    }
    
    /**
     * 
     * This method takes values from the nearby postal codes calculator(NPC) menu's 
     * postalCode text field as well as the radius text field and uses the 
     * nearbyLocations method to compute and display nearby locations using the 
     * scene's postalCode list view object.
     * 
     * @param event 
     */
    @FXML
    public void handlenearbyLocations(ActionEvent event){
        
        
        try{
            System.out.println("Nearby locations button pressed");
            postalCodeListView.getItems().clear();

            for(PostalCode postalCode : nearbyLocations(postalCodeTextField.getText().toUpperCase(), Double.parseDouble(radiusTextField.getText())).values()){

                postalCodeListView.getItems().add(postalCode.toString());

            }
            
            
        }catch(NullPointerException e){
            
            System.out.println("A valid postal code was not inputted");
            
            Alert saveAlert = new Alert(Alert.AlertType.ERROR);
            saveAlert.setHeaderText("Error");
            saveAlert.setContentText("A valid postal code was not inputted");
            
            saveAlert.showAndWait();
            
        }catch(NumberFormatException e){
            
            System.out.println("A field is either empty or the inputted text does not "
                    + "match the expected value");
            
            Alert saveAlert = new Alert(Alert.AlertType.ERROR);
            saveAlert.setHeaderText("Error");
            saveAlert.setContentText("a field is either empty or the inputted text does not "
                    + "match the expected value");
            
            saveAlert.showAndWait();
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
    /**
     * This method switches the scene from the main menu to DC menu.
     * 
     * @param event
     * @throws IOException 
     */
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
    
    
    /**
     * This method switches the scene from the main menu to the NPC menu.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    public void handleSwitchToNPCMenu(ActionEvent event) throws IOException{
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/nearby_postal_codes_menu.fxml"));
        
        PostalCodeController mainContoller = new PostalCodeController();
        loader.setController(mainContoller);
        
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * 
     * This menu switches the scene from the DC or NPC menu back to the main menu.
     * 
     * @param event
     * @throws IOException 
     */
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

