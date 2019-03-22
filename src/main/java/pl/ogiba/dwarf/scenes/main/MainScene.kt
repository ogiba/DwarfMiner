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
    private val connectBtn = setupConnectButton()
    private val insertDocumentBtn = setupInsertDocumentBtn()

    private val presenter = MainPresenter(this)

    init {
//        val hbox = HBox(20.0).apply {
//            translateX = 20.0
//            translateY = 20.0
//        }

        val nodesContainer = setupNodesContainer(nodesTree)

        val dataActionsContainer = HBox(4.0)

        dataActionsContainer.children.add(insertDocumentBtn)

        val dataBorderPane = BorderPane(dataArea)
        dataBorderPane.top = dataActionsContainer

        val dataContainer = setupDataContainer(dataBorderPane)

        val splitPane = setupRootPane(nodesContainer, dataContainer)

        val container = AnchorPane(splitPane)
        val borderPane = BorderPane(container)

        borderPane.top = connectBtn

        AnchorPane.setBottomAnchor(borderPane, 0.0)
        AnchorPane.setLeftAnchor(borderPane, 0.0)
        AnchorPane.setRightAnchor(borderPane, 0.0)
        AnchorPane.setTopAnchor(borderPane, 0.0)

        root.children.add(borderPane)
    }

    override fun getScene(): Scene {
        val scene = Scene(root, 640.0, 480.0)
        scene.stylesheets.add("/styles/Styles.css")
        scene.fill = Color.GHOSTWHITE
        return scene
    }

    override fun onConnectionResult(isConnected: Boolean) {
        runOnUiThread {
            val btnTitle = if (isConnected) "Disconnect" else "Connect to DB"
            connectBtn.text = btnTitle
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
            val dbTreeItem = nodesTree.root.children[0]

            collections.forEach { name ->
                val collectionName = TreeItem(name)
                dbTreeItem.children.add(collectionName)
            }
        }
    }

    override fun onColectionSelected() {
        runOnUiThread { insertDocumentBtn.isDisable = false }
    }

    override fun onSelectedCollectionLoaded(data: String) {
        dataArea.text = data
    }

    override fun onInsertApplied(document: MongoCollection<Document>) {
        val stage = Stage()

        val insertDocumentScene = InsertDocumentScene()
        insertDocumentScene.setCollectionReference(document)
        insertDocumentScene.show()
    }

    private fun setupRootPane(vararg elems: Node): SplitPane {
        val splitPane = SplitPane()
        splitPane.autosize()
        splitPane.orientation = Orientation.HORIZONTAL

        splitPane.items.addAll(*elems)
        splitPane.setDividerPositions(0.3, 0.9)

        AnchorPane.setLeftAnchor(splitPane, 10.0)
        AnchorPane.setRightAnchor(splitPane, 10.0)
        AnchorPane.setTopAnchor(splitPane, 10.0)
        AnchorPane.setBottomAnchor(splitPane, 10.0)

        return splitPane
    }

    private fun setupTree(): TreeView<String> {
        val rootItem = TreeItem("ServerName")
        return TreeView(rootItem).apply {
            isDisable = true
            onMouseClicked = EventHandler<MouseEvent> { handleTreeMouseClick(it) }
        }
    }

    private fun handleTreeMouseClick(mouseEvent: MouseEvent) {
        val selectedItem = nodesTree.selectionModel.selectedItem as TreeItem<*>
        if (selectedItem.isLeaf) {
            println("Selected item: " + selectedItem.value)
            presenter.loadSelectedCollection(selectedItem.value as String)
        }
    }

    private fun setupNodesContainer(nodesView: TreeView<*>): AnchorPane {
        val nodesContainer = AnchorPane()

        AnchorPane.setLeftAnchor(nodesView, 0.0)
        AnchorPane.setRightAnchor(nodesView, 0.0)
        AnchorPane.setTopAnchor(nodesView, 0.0)
        AnchorPane.setBottomAnchor(nodesView, 0.0)

        nodesContainer.children.add(nodesView)

        return nodesContainer
    }

    private fun setupTextArea(): TextArea {
        return TextArea().apply {
            stylesheets.add("/styles/TextArea.css")
            textFormatter = TextFormatter(JsonConverter())
            isDisable = true
        }
    }

    private fun setupDataContainer(dataArea: Node): AnchorPane {
        val dataContainer = AnchorPane()

        AnchorPane.setLeftAnchor(dataArea, 0.0)
        AnchorPane.setRightAnchor(dataArea, 0.0)
        AnchorPane.setTopAnchor(dataArea, 0.0)
        AnchorPane.setBottomAnchor(dataArea, 0.0)

        dataContainer.children.add(dataArea)

        return dataContainer
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
