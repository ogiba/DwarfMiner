/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentScene {
    
    private Parent root;
    
    public InsertDocumentScene() {
        root = new AnchorPane();
    }
    
    public Scene getScene() {
        Scene scene = new Scene(root, 320, 240);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(Color.GHOSTWHITE);
        return scene;
    }
}
