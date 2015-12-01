/*-----------------------------------------------------------------------------*
 * VP_Left.java
 * - Everything involving the tree view (left side of GUI) of the wizard
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1300
 *-----------------------------------------------------------------------------*/
package vaqpack;

import vaqpack.peripherals.VP_Sounds;
import vaqpack.components.VP_TreeItem;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VP_Left extends VBox {
    private final VP_GUIController controller;
    private final TreeItem<String> falseRoot;
    
    /*------------------------------------------------------------------------*
     * VP_Left()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_Left(VP_GUIController controller) {
        this.controller = controller;
        this.falseRoot = new TreeItem();
    }

    
    private class TreeClick implements EventHandler<MouseEvent> {
        private final TreeView<String> treeView;

        private TreeClick(TreeView<String> treeView) {
            this.treeView = treeView;
        }
        
        @Override
        public void handle(MouseEvent event) {
            Node node = event.getPickResult().getIntersectedNode();
            if ((!(node instanceof StackPane)) && (node instanceof LabeledText || (node instanceof TreeCell && ((TreeCell) node).getText() != null)))
            {
                try {
                    VP_Sounds.play(0);
                    int wizardNumber = (int) ((VP_TreeItem)treeView.getSelectionModel().getSelectedItem()).getWizardNumber();
                    double position = (double) ((VP_TreeItem)treeView.getSelectionModel().getSelectedItem()).getPositionProp().getValue();
                    controller.quickJump(wizardNumber, position);
                    System.out.println("position = " + ((VP_TreeItem)treeView.getSelectionModel().getSelectedItem()).getPositionProp().getValue());
                }
                catch (Exception e) {
                    // just eat it, shouldn't take you anywhere anyway
                }
            }
        }
    }

    
    /*------------------------------------------------------------------------*
     * build()
     * - Builds the tree view. Called in a task, to build in the background
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {

        this.setId("treeContainer");
        VBox.setVgrow(this, Priority.ALWAYS);
        setPrefWidth(200);
        falseRoot.setExpanded(true);
        TreeView<String> tree = new TreeView(falseRoot);
        tree.setOnMouseClicked(new TreeClick(tree));
        tree.setShowRoot(false);
        tree.prefWidthProperty().bind(widthProperty());
        tree.prefHeightProperty().bind(heightProperty());
        tree.getStyleClass().add("treeStyle");
        setPadding(new Insets(1, 1, 1, 1));
        getChildren().addAll(tree);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    protected TreeItem<String> getFalseRoot() {
        return falseRoot;
    }
}