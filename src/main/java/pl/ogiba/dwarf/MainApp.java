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
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.ogiba.dwarf.utils.FileUtils;

public class MainApp extends Application {

    private Stage window;

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        window = stage;

        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);

        MongoDatabase db = mongoClient.getDatabase("dwarf_test");

//        Button button = new Button();
//        button.setMinSize(100, 50);
//
//        button.setText("Insert");
//
//
        HBox hbox = new HBox(20);
        hbox.setTranslateX(20);
        hbox.setTranslateY(20);

        SplitPane splitPane = new SplitPane();
        splitPane.autosize();
        splitPane.setOrientation(Orientation.HORIZONTAL);

        final Button r1 = new Button("Right Button");

        TreeView nodesTree = setupTree();

//        ScrollPane scrollPane = new ScrollPane(nodesTree);
        AnchorPane nodesContainer = new AnchorPane();

        AnchorPane.setLeftAnchor(nodesTree, 0.0);
        AnchorPane.setRightAnchor(nodesTree, 0.0);
        AnchorPane.setTopAnchor(nodesTree, 0.0);
        AnchorPane.setBottomAnchor(nodesTree, 0.0);

        nodesContainer.getChildren().add(nodesTree);

        TextArea dataArea = setupTextArea();

        AnchorPane dataContainer = new AnchorPane();

        AnchorPane.setLeftAnchor(dataArea, 0.0);
        AnchorPane.setRightAnchor(dataArea, 0.0);
        AnchorPane.setTopAnchor(dataArea, 0.0);
        AnchorPane.setBottomAnchor(dataArea, 0.0);

        dataContainer.getChildren().add(dataArea);

        splitPane.getItems().addAll(nodesContainer, dataContainer);
        splitPane.setDividerPositions(0.3f, 0.9f);

        AnchorPane anchorPane = new AnchorPane();

        AnchorPane.setLeftAnchor(splitPane, 10.0);
        AnchorPane.setRightAnchor(splitPane, 10.0);
        AnchorPane.setTopAnchor(splitPane, 10.0);
        AnchorPane.setBottomAnchor(splitPane, 10.0);

        anchorPane.getChildren().add(splitPane);

        Scene scene = new Scene(anchorPane, 640, 480);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(Color.GHOSTWHITE);

        stage.setTitle("SplitPane");
        stage.setScene(scene);
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

    private TextArea setupTextArea() {
        final TextArea textArea = new TextArea();
        textArea.getStylesheets().add("/styles/TextArea.css");
        return textArea;
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
