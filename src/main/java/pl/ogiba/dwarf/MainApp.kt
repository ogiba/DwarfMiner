package pl.ogiba.dwarf

import javafx.application.Application
import javafx.application.Application.launch
import javafx.stage.Stage
import pl.ogiba.dwarf.scenes.IMainView
import pl.ogiba.dwarf.scenes.MainScene

class MainApp : Application() {

    private var window: Stage? = null

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        window = stage

        val mainScene = MainScene()

        stage.title = "SplitPane"
        stage.scene = mainScene.scene
        stage.show()
    }

    companion object {

        /**
         * The main() method is ignored in correctly deployed JavaFX application.
         * main() serves only as fallback in case the application can not be
         * launched through deployment artifacts, e.g., in IDEs with limited FX
         * support. NetBeans ignores main().
         *
         * @param args the command line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainApp::class.java)
        }
    }

}
