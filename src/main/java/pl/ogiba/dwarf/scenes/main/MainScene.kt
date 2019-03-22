/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.main

import com.mongodb.client.MongoCollection
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.SplitPane
import javafx.scene.control.TextArea
import javafx.scene.control.TextFormatter
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.bson.Document
import pl.ogiba.dwarf.scenes.insert.InsertDocumentScene
import pl.ogiba.dwarf.utils.JsonConverter
import pl.ogiba.dwarf.utils.base.BaseScene
import java.util.*

/**
 *
 * @author ogiba
 */
class MainScene : BaseScene(), IMainView {

    private val root = AnchorPane()
    private val nodesTree = setupTree()
    private val dataArea = setupTextArea()
    private val connectButton = setupConnectButton()
    private val insertDocumentButton = setupInsertDocumentBtn()

    private val presenter = MainPresenter(this)

    init {
        val nodesContainer = setupNodesContainer(nodesTree)

        val dataBorderPane = BorderPane(dataArea)

        HBox(4.0).apply {
            children.add(insertDocumentButton)
        }.also { dataActionsContainer ->
            dataBorderPane.top = dataActionsContainer
        }

        val splitPane = setupRootPane(nodesContainer, setupDataContainer(dataBorderPane))

        val container = AnchorPane(splitPane)

        BorderPane(container).apply {
            top = connectButton
        }.also { borderPane ->
            AnchorPane.setBottomAnchor(borderPane, 0.0)
            AnchorPane.setLeftAnchor(borderPane, 0.0)
            AnchorPane.setRightAnchor(borderPane, 0.0)
            AnchorPane.setTopAnchor(borderPane, 0.0)

            root.children.add(borderPane)
        }
    }

    override fun getScene(): Scene {
        return Scene(root, 640.0, 480.0).apply {
            stylesheets.add("/styles/Styles.css")
            fill = Color.GHOSTWHITE
        }
    }

    override fun onConnectionResult(isConnected: Boolean) {
        runOnUiThread {
            connectButton.text = if (isConnected) "Disconnect" else "Connect to DB"
            dataArea.isDisable = !isConnected
            nodesTree.setDisable(!isConnected)
        }

        presenter.loadData()
    }

    override fun onDataLoaded(dbName: String) {
        runOnUiThread {
            val dataBaseItem = TreeItem(dbName)

            nodesTree.root.children.add(dataBaseItem)

            presenter.loadCollections()
        }
    }

    override fun onCollectionsLoaded(collections: ArrayList<String>) {
        runOnUiThread {
            val dbTreeItem = nodesTree.root.children.first()

            collections.forEach { name ->
                dbTreeItem.children.add(TreeItem(name))
            }
        }
    }

    override fun onColectionSelected() {
        runOnUiThread { insertDocumentButton.isDisable = false }
    }

    override fun onSelectedCollectionLoaded(data: String) {
        dataArea.text = data
    }

    override fun onInsertApplied(document: MongoCollection<Document>) {
        val stage = Stage()

        InsertDocumentScene().apply {
            setCollectionReference(document)
        }.show()
    }

    private fun setupRootPane(vararg elems: Node): SplitPane {
        return SplitPane().apply {
            autosize()
            orientation = Orientation.HORIZONTAL
            items.addAll(*elems)
            setDividerPositions(0.3, 0.9)
        }.let { splitPane ->
            AnchorPane.setLeftAnchor(splitPane, 10.0)
            AnchorPane.setRightAnchor(splitPane, 10.0)
            AnchorPane.setTopAnchor(splitPane, 10.0)
            AnchorPane.setBottomAnchor(splitPane, 10.0)
            splitPane
        }
    }

    private fun setupTree(): TreeView<String> {
        return TreeView(TreeItem("ServerName")).apply {
            isDisable = true
            onMouseClicked = EventHandler<MouseEvent> { handleTreeMouseClick(it) }
        }
    }

    private fun handleTreeMouseClick(mouseEvent: MouseEvent) {
        with(nodesTree.selectionModel.selectedItem) {
            if (isLeaf) {
                println("Selected item: $value")
                presenter.loadSelectedCollection(value)
            }
        }
    }

    private fun setupNodesContainer(nodesView: TreeView<String>): AnchorPane {
        return AnchorPane().also { nodesContainer ->
            AnchorPane.setLeftAnchor(nodesView, 0.0)
            AnchorPane.setRightAnchor(nodesView, 0.0)
            AnchorPane.setTopAnchor(nodesView, 0.0)
            AnchorPane.setBottomAnchor(nodesView, 0.0)

            nodesContainer.children.add(nodesView)
        }
    }

    private fun setupTextArea(): TextArea {
        return TextArea().apply {
            stylesheets.add("/styles/TextArea.css")
            textFormatter = TextFormatter(JsonConverter())
            isDisable = true
        }
    }

    private fun setupDataContainer(dataArea: Node): AnchorPane {
        return AnchorPane().also { dataContainer ->
            AnchorPane.setLeftAnchor(dataArea, 0.0)
            AnchorPane.setRightAnchor(dataArea, 0.0)
            AnchorPane.setTopAnchor(dataArea, 0.0)
            AnchorPane.setBottomAnchor(dataArea, 0.0)

            dataContainer.children.add(dataArea)
        }
    }

    private fun setupInsertDocumentBtn(): Button {
        return Button("Insert document").apply {
            onAction = EventHandler<ActionEvent> { handleInsertAction(it) }
            isDisable = true
        }
    }

    private fun setupConnectButton(): Button {
        return Button("Connect to DB").apply {
            setOnAction { presenter.checkConnection() }
        }
    }

    private fun handleInsertAction(event: ActionEvent) {
        presenter.proceedInsertAction()
    }
}
