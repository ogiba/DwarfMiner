package pl.ogiba.dwarf;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import pl.ogiba.dwarf.scenes.IMainView;
import pl.ogiba.dwarf.scenes.MainScene;

public class MainApp extends Application {

    private Stage window;

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        window = stage;
        
        IMainView mainScene = new MainScene();

        stage.setTitle("SplitPane");
        stage.setScene(mainScene.getScene());
        stage.show();
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
