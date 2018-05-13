/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentScene {

    private Parent root;

    public InsertDocumentScene() {
        Button cancelBtn = new Button("Cancel");
        Button commitBtn = new Button("Commit");

        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox btnsContainer = new HBox(4, cancelBtn, spacer, commitBtn);

        TextArea documentArea = new TextArea();
        
        BorderPane borderPane = new BorderPane(documentArea);
        borderPane.setBottom(btnsContainer);

        AnchorPane.setBottomAnchor(borderPane, 0d);
        AnchorPane.setTopAnchor(borderPane, 0d);
        AnchorPane.setLeftAnchor(borderPane, 0d);
        AnchorPane.setRightAnchor(borderPane, 0d);

        root = new AnchorPane(borderPane);
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 320, 240);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(Color.GHOSTWHITE);
        return scene;
    }
}
