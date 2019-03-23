/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert

import com.mongodb.client.MongoCollection
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.bson.Document
import pl.ogiba.dwarf.utils.base.BaseScene

/**
 *
 * @author robertogiba
 */
class InsertDocumentScene : BaseScene(), IInsertDocumentView {

    private val stage = setupStage()
    private val presenter: InsertDocumentPresenter = InsertDocumentPresenter()
    private val documentArea = setupDocumentArea()
    private val root: Parent

    init {
        val cancelBtn = setupCancelButton()
        val commitBtn = setupCommitButton()

        val spacer = Pane()
        HBox.setHgrow(spacer, Priority.ALWAYS)

        val btnsContainer = HBox(4.0, cancelBtn, spacer, commitBtn)

        val borderPane = BorderPane(documentArea).apply {
            bottom = btnsContainer
        }.also { pane ->
            AnchorPane.setBottomAnchor(pane, 0.0)
            AnchorPane.setTopAnchor(pane, 0.0)
            AnchorPane.setLeftAnchor(pane, 0.0)
            AnchorPane.setRightAnchor(pane, 0.0)
        }

        root = AnchorPane(borderPane)

        stage.scene = getScene()
    }

    override fun getScene(): Scene {
        val scene = Scene(root, 320.0, 240.0)
        scene.stylesheets.add("/styles/Styles.css")
        scene.fill = Color.GHOSTWHITE
        return scene
    }

    override fun show() {
        stage.show()
    }

    override fun setCollectionReference(documentCollection: MongoCollection<Document>) {
        presenter.documentCollection = documentCollection
    }

    private fun setupCancelButton(): Button {
        return Button("Cancel").apply {
            onAction = EventHandler<ActionEvent> { handleCancelAction(it) }
        }
    }

    private fun handleCancelAction(event: ActionEvent) {
        stage.close()
    }

    private fun setupStage(): Stage {
        return Stage().apply {
            title = "Insert new document"
        }
    }

    private fun setupDocumentArea(): TextArea {
        return TextArea().apply {
            stylesheets.add("/styles/TextArea.css")
        }
    }

    private fun setupCommitButton(): Button {
        return Button("Commit").apply {
            onAction = EventHandler<ActionEvent> { handleCommitAction(it) }
        }
    }

    private fun handleCommitAction(event: ActionEvent) {
        presenter.insertDataToDb(documentArea.text)
    }
}
