package pl.ogiba.dwarf;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import pl.ogiba.dwarf.scenes.IMainView;
import pl.ogiba.dwarf.scenes.MainScene;

public class MainApp extends Application {

    private Stage window;

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        window = stage;

        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);

        MongoDatabase db = mongoClient.getDatabase("dwarf_test");
        
        IMainView mainScene = new MainScene();

        stage.setTitle("SplitPane");
        stage.setScene(mainScene.getScene());
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
