/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author ogiba
 */
public class MainScene implements IMainView {

    private Parent root;
    private TreeView nodesTree;
    private TextArea dataArea;

    public MainScene() {
        HBox hbox = new HBox(20);
        hbox.setTranslateX(20);
        hbox.setTranslateY(20);

        final Button r1 = new Button("Right Button");

        nodesTree = setupTree();

        AnchorPane nodesContainer = setupNodesContainer(nodesTree);

        dataArea = setupTextArea();

        AnchorPane dataContainer = setupDataContainer(dataArea);

        SplitPane splitPane = setupRootPane(nodesContainer, dataContainer);

        root = new AnchorPane();

        ((AnchorPane) root).getChildren().add(splitPane);
    }

    @Override
    public Scene getScene() {
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(Color.GHOSTWHITE);
        return scene;
    }

    private SplitPane setupRootPane(Node... elems) {
        SplitPane splitPane = new SplitPane();
        splitPane.autosize();
        splitPane.setOrientation(Orientation.HORIZONTAL);

        splitPane.getItems().addAll(elems);
        splitPane.setDividerPositions(0.3f, 0.9f);

        AnchorPane.setLeftAnchor(splitPane, 10.0);
        AnchorPane.setRightAnchor(splitPane, 10.0);
        AnchorPane.setTopAnchor(splitPane, 10.0);
        AnchorPane.setBottomAnchor(splitPane, 10.0);

        return splitPane;
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

    private AnchorPane setupNodesContainer(TreeView nodesView) {
        AnchorPane nodesContainer = new AnchorPane();

        AnchorPane.setLeftAnchor(nodesView, 0.0);
        AnchorPane.setRightAnchor(nodesView, 0.0);
        AnchorPane.setTopAnchor(nodesView, 0.0);
        AnchorPane.setBottomAnchor(nodesView, 0.0);

        nodesContainer.getChildren().add(nodesView);

        return nodesContainer;
    }

    private TextArea setupTextArea() {
        final TextArea textArea = new TextArea();
        textArea.getStylesheets().add("/styles/TextArea.css");

        return textArea;
    }

    private AnchorPane setupDataContainer(TextArea dataArea) {
        final AnchorPane dataContainer = new AnchorPane();

        AnchorPane.setLeftAnchor(dataArea, 0.0);
        AnchorPane.setRightAnchor(dataArea, 0.0);
        AnchorPane.setTopAnchor(dataArea, 0.0);
        AnchorPane.setBottomAnchor(dataArea, 0.0);

        dataContainer.getChildren().add(dataArea);

        return dataContainer;
    }
}
