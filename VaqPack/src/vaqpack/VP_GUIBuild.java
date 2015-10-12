/*-----------------------------------------------------------------------------*
 * VP_GUIBuild.java
 * - Constructs the GUI and maintains references to key components
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VP_GUIBuild {

    private final StackPane mainLayout;        // mainLayout type is temporary
    private final VP_GUIEvents GE;
    private final int sceneWidth = 800, // width is temporary
            sceneHeight = 600; // height is temporary

    /*------------------------------------------------------------------------*
     * VP_GUIBuild()
     * - Constructor. Creates the mainLayout, calls functions to create all
     * layout subsections, and registers all event handlers for components.
     * - parameter VP_GUIEvents stored to access events
     *------------------------------------------------------------------------*/
    protected VP_GUIBuild(VP_GUIEvents GE) {
        this.GE = GE;
        // temporarily mainLayout is a stackpane
        mainLayout = new StackPane();
        buildTestLayer();
    }

    /*------------------------------------------------------------------------*
     * buildTestLayer()
     * - Creates a temporary layer in the mainLyout stackpane.
     * - no parameters
     * - no return
     *------------------------------------------------------------------------*/
    private void buildTestLayer() {
        VBox testVBox = new VBox();
        testVBox.setPrefSize(sceneWidth, sceneHeight);
        testVBox.setAlignment(Pos.CENTER);
        testVBox.setSpacing(40);
        Label testLabel = new Label("Testing Alert Boxes and Error Handler");
        Button testButton1 = new Button("NON-CRITICAL ERROR");
        testButton1.setOnAction(GE.new TestEvent(1100));
        Button testButton2 = new Button("CRITICAL ERROR");
        testButton2.setOnAction(GE.new TestEvent(1101));
        testVBox.getChildren().addAll(testLabel, testButton1, testButton2);
        mainLayout.getChildren().add(testVBox);
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected StackPane getMainLayout() {
        return mainLayout;
    }

    protected int getSceneWidth() {
        return sceneWidth;
    }

    protected int getSceneHeight() {
        return sceneHeight;
    }

}
