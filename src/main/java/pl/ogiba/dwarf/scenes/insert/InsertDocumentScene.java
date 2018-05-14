/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import pl.ogiba.dwarf.utils.base.BaseScene;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentScene extends BaseScene implements IInsertDocumentView{

    private Stage stage;
    private Parent root;
    private InsertDocumentPresenter presenter;

    public InsertDocumentScene() {
        this.presenter = new InsertDocumentPresenter();
        
        Button cancelBtn = setupCancelBtn();
        Button commitBtn = new Button("Commit");

        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox btnsContainer = new HBox(4, cancelBtn, spacer, commitBtn);

        TextArea documentArea = new TextArea(); 
        documentArea.getStylesheets().add("/styles/TextArea.css");
        
        BorderPane borderPane = new BorderPane(documentArea);
        borderPane.setBottom(btnsContainer);

        AnchorPane.setBottomAnchor(borderPane, 0d);
        AnchorPane.setTopAnchor(borderPane, 0d);
        AnchorPane.setLeftAnchor(borderPane, 0d);
        AnchorPane.setRightAnchor(borderPane, 0d);

        root = new AnchorPane(borderPane);
        
        stage = new Stage();
        stage.setTitle("Insert new document");
        stage.setScene(getScene());
    }

    @Override
    public Scene getScene() {
        Scene scene = new Scene(root, 320, 240);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(Color.GHOSTWHITE);
        return scene;
    }

    @Override
    public void show() {
        stage.show();
    }

    @Override
    public void setDatabaseReference(MongoDatabase database) {
        presenter.transferDatabaseReference(database);
    }
    
    private Button setupCancelBtn() {
        final Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(this::handleCancelAction);
        return cancelBtn;
    }
    
    private void handleCancelAction(ActionEvent event) {
        stage.close();
    }
    
    
}
