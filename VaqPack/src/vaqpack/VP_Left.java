/**
 * VP_Left.java - Everything involving the tree view (left side of GUI) of the
 * wizard. FILE ID 1300
 */
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

/**
 * The left is the left side of the main BorderPane layout. This extends VBox
 * and consists of an interactive and dynamic tree view.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Left extends VBox {

    private final VP_GUIController controller;
    private final TreeItem<String> falseRoot;

    /**
     * Constructor. Stores the controller, and initializes the tree root.
     *
     * @param controller Stores the GUI controller for convenience in accessing
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    protected VP_Left(VP_GUIController controller) {
        this.controller = controller;
        this.falseRoot = new TreeItem();
    }

    /**
     * Builds the tree view in the empty left side of the GUI. Called in a task,
     * to build in the background.
     *
     * @since 1.0
     */
    protected void build() {
        setId("treeContainer");
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
    /**
     * Allows the user to jump to a specific location in the wizard depending on
     * which tree item was clicked.
     *
     * @since 1.0
     */
    private class TreeClick implements EventHandler<MouseEvent> {

        private final TreeView<String> treeView;

        /**
         * Constructor. Sets the tree view.
         *
         * @param treeView Reference to the TreeView containing the clicked tree
         * item.
         * @since 1.0
         */
        private TreeClick(TreeView<String> treeView) {
            this.treeView = treeView;
        }

        /**
         * @param event A MouseEvent when the user clicks on node or leaf of the
         * tree.
         * @since 1.0
         */
        @Override
        public void handle(MouseEvent event) {
            Node node = event.getPickResult().getIntersectedNode();
            if ((!(node instanceof StackPane)) && (node instanceof LabeledText || (node instanceof TreeCell && ((TreeCell) node).getText() != null))) {
                try {
                    VP_Sounds.play(0);
                    int wizardNumber = (int) ((VP_TreeItem) treeView.getSelectionModel().getSelectedItem()).getWizardNumber();
                    double position = (double) ((VP_TreeItem) treeView.getSelectionModel().getSelectedItem()).getPositionProp().getValue();
                    controller.getCenter().cancelActionFunction();
                    controller.getCenter().showScreen(wizardNumber, position);
                    System.out.println("position = " + ((VP_TreeItem) treeView.getSelectionModel().getSelectedItem()).getPositionProp().getValue());
                } catch (Exception e) {
                    // just eat it, shouldn't take you anywhere anyway
                }
            }
        }
    }
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    /**
     * Accessor method.
     *
     * @return The false root of tree that shows the main branches as individual
     * roots.
     * @since 1.0
     */
    protected TreeItem<String> getFalseRoot() {
        return falseRoot;
    }
}
