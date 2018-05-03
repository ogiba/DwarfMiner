package pl.ogiba.dwarf;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);

        MongoDatabase db = mongoClient.getDatabase("dwarf_test");

//        TreeView<String> treeView = setupTree();
//
//        ScrollPane root = new ScrollPane();
//        root.setContent(treeView);
//
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(5);
//        gridPane.setVgap(5);
//        ColumnConstraints column1 = new ColumnConstraints(100);
//        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
//        column2.setHgrow(Priority.ALWAYS);
//        gridPane.getColumnConstraints().addAll(column1, column2);
//
//        TextArea textArea = new TextArea();
//        Button button = new Button();
//        button.setMinSize(100, 50);
//        GridPane.setHalignment(button, HPos.RIGHT);
//
//        button.setText("Insert");
//
//        gridPane.add(root, 0, 0, 2, 1);
//        gridPane.add(textArea, 2, 0, 2, 1);
//        gridPane.add(button, 1, 1, 2, 1);
//
//        GridPane.setFillWidth(button, true);
//
//        Scene scene = new Scene(gridPane, 500, 500);
////        scene.getStylesheets().add("/styles/Styles.css");
//
//        stage.setTitle("JavaFX and Maven");
//        stage.setScene(scene);
//        stage.show();
        HBox hbox = new HBox(20);
        hbox.setTranslateX(20);
        hbox.setTranslateY(20);
        
        

        SplitPane splitPane = new SplitPane();
        splitPane.autosize();
        splitPane.setOrientation(Orientation.HORIZONTAL);
//        splitPane1.setPrefSize(200, 200);
        final Button l1 = new Button("Left Button");
        final Button r1 = new Button("Right Button");
        
        ScrollPane scrollPane = new ScrollPane(l1);
//        BorderPane borderPane = new BorderPane(l1);
        BorderPane borderPane2 = new BorderPane(r1);
        
        splitPane.getItems().addAll(scrollPane, borderPane2);
        hbox.getChildren().add(splitPane);
        
        AnchorPane anchorPane = new AnchorPane();
        
        AnchorPane.setLeftAnchor(splitPane, 10.0);
        AnchorPane.setRightAnchor(splitPane, 10.0);
        AnchorPane.setTopAnchor(splitPane, 10.0);
        AnchorPane.setBottomAnchor(splitPane, 10.0);
        
        anchorPane.getChildren().add(splitPane);

        Scene scene = new Scene(anchorPane, 560, 240);
        scene.setFill(Color.GHOSTWHITE);
        stage.setScene(scene);
        stage.setTitle("SplitPane");
        stage.show();
    }

    private TreeView setupTree() {
        TreeItem<String> treeItemRoot = new TreeItem<>("Root");

        TreeItem<String> nodeItemA = new TreeItem<>("Item A");
        TreeItem<String> nodeItemB = new TreeItem<>("Item B");
        TreeItem<String> nodeItemC = new TreeItem<>("Item C");
        treeItemRoot.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC);

        TreeItem<String> nodeItemA1 = new TreeItem<>("Item A1");
        TreeItem<String> nodeItemA2 = new TreeItem<>("Item A2");
        TreeItem<String> nodeItemA3 = new TreeItem<>("Item A3");
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2, nodeItemA3);

        return new TreeView<>(treeItemRoot);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
