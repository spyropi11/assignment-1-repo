package edu.vanier.ui;

import edu.vanier.controllers.PostalCodeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 18 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/18/
 * @see: Build Scripts/build.gradle
 * @author Sleiman Rabah.
 */
public class MainApp extends Application{

    
    @Override
    public void start(Stage stage) throws Exception {
        
        /* Creation of fxml loader as well as specification of the path 
            detailing where to retrieve the fxml file */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/postal_code_main_menu.fxml"));
        
        /* Creation of an instance of the PostalCodeController that will be used
            by the main menu to handle actions*/
        PostalCodeController mainContoller = new PostalCodeController();
        
        //Setting the loader's controller to the previous instance
        loader.setController(mainContoller);
        
        // Loading the fxml into a Pane object
        Pane root = loader.load();
        
        Scene scene = new Scene(root);
        
        //Loading the scene into the window(stage)
        stage.setScene(scene);
        
        stage.setTitle("Main Menu");
        stage.sizeToScene();
        stage.show();
        
    }
    
    public static void main(String[] args) {
        
        launch(args);
        
        
    }
    
    

    
}